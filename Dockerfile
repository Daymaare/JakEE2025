# By default, build on JDK 21 on UBI 9.
ARG jdk=23-jre
ARG dist=ubi9-minimal
FROM eclipse-temurin:${jdk}-${dist}

# Wildfly and PostgreSQL versions
ARG WILDFLY_VERSION=35.0.1.Final
ARG WILDFLY_SHA1=35e61cfe2b14bab1f0644d4967090fe7de8590dd
ARG POSTGRESQL_DRIVER_VERSION=42.7.5
ARG POSTGRESQL_JDBC_DRIVER=postgresql-${POSTGRESQL_DRIVER_VERSION}.jar

# Install necessary packages in single layer
RUN if [ -x "$(command -v microdnf)" ]; then \
        microdnf update -y && \
        microdnf install --best --nodocs -y shadow-utils && \
        microdnf clean all; \
    elif [ -x "$(command -v apt-get)" ]; then \
        apt-get update && \
        apt-get install -y --no-install-recommends shadow && \
        rm -rf /var/lib/apt/lists/*; \
    fi

# Set environment variables
ENV JBOSS_HOME=/opt/jboss/wildfly \
    LAUNCH_JBOSS_IN_BACKGROUND=true

# Create jboss user and directory structure
RUN groupadd -r jboss -g 1000 && useradd -u 1000 -r -g jboss -m -d /opt/jboss -s /sbin/nologin -c "JBoss user" jboss \
    && chmod 755 /opt/jboss

# Add the WildFly distribution to /opt, and make wildfly the owner of the extracted tar content
# Make sure the distribution is available from a well-known place
RUN cd $HOME \
    && curl -L -O https://github.com/wildfly/wildfly/releases/download/$WILDFLY_VERSION/wildfly-preview-$WILDFLY_VERSION.tar.gz \
    && sha1sum wildfly-preview-$WILDFLY_VERSION.tar.gz | grep $WILDFLY_SHA1 \
    && tar xf wildfly-preview-$WILDFLY_VERSION.tar.gz \
    && mv $HOME/wildfly-preview-$WILDFLY_VERSION $JBOSS_HOME \
    && rm wildfly-preview-$WILDFLY_VERSION.tar.gz \
    && chown -R jboss:0 ${JBOSS_HOME} \
    && chmod -R g+rw ${JBOSS_HOME}

# Download PostgreSQL JDBC Driver
RUN curl -L -o ${JBOSS_HOME}/standalone/deployments/postgresql.jar \
    https://jdbc.postgresql.org/download/${POSTGRESQL_JDBC_DRIVER} \
    && chmod 664 ${JBOSS_HOME}/standalone/deployments/postgresql.jar

# Create the .dodeploy marker file
RUN touch ${JBOSS_HOME}/standalone/deployments/postgresql.jar.dodeploy && \
    chmod 664 ${JBOSS_HOME}/standalone/deployments/postgresql.jar

# Copy CLI scripts for datasource and driver configuration
COPY --chown=jboss:0 docker/configure-datasource.cli /tmp/
COPY --chown=jboss:0 docker/entrypoint.sh /opt/jboss/

# Make the entrypoint script executable
RUN chmod +x /opt/jboss/entrypoint.sh

# Copy your application WAR file
COPY --chown=jboss:0 target/*.war ${JBOSS_HOME}/standalone/deployments/

# Set safe default environment variables
ENV DB_HOST=db-host-placeholder \
    DB_PORT=5432 \
    DB_NAME=db-name-placeholder \
    DB_USER=db-user-placeholder \
    DB_PASSWORD=change-me \
    DS_NAME=postgresql.jar \
    DS_JNDI=java:/PostgresDS

# Switch to non-root user
USER jboss

# Expose the ports
EXPOSE 8080

# Add health check
HEALTHCHECK --interval=30s --timeout=3s CMD curl -f http://localhost:8080/health || exit 1

# Use custom entrypoint script
ENTRYPOINT ["/opt/jboss/entrypoint.sh"]