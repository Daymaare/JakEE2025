#!/bin/bash
set -e

CONFIG_FILE="/tmp/configure-datasource-resolved.cli"
MARKER_FILE="/tmp/datasource_configured"

if [ ! -f "$MARKER_FILE" ]; then
  echo "Configuring PostgreSQL datasource with:"
  echo "Host: $DB_HOST"
  echo "Port: $DB_PORT"
  echo "Database: $DB_NAME"
  echo "Datasource name: $DS_NAME"
  echo "JNDI name: $DS_JNDI"

  # Create a temporary CLI script with environment variables replaced
  eval "cat <<EOF
  $(cat /tmp/configure-datasource.cli)
  EOF" > "$CONFIG_FILE"

  # Show the final CLI script for debugging
  echo "Using CLI script:"
  cat "$CONFIG_FILE"

  # Execute the CLI script to configure datasource before starting WildFly
  echo "Executing CLI script to configure datasource..."
  "${JBOSS_HOME}/bin/jboss-cli.sh" --file="$CONFIG_FILE"

  # Create marker file to indicate configuration is done
  touch "$MARKER_FILE"
else
  echo "Datasource already configured. Skipping configuration."
fi

echo "Starting WildFly server..."
exec ${JBOSS_HOME}/bin/standalone.sh -b 0.0.0.0 "$@"