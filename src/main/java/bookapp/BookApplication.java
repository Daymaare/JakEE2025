package bookapp;


import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("api")
public class BookApplication extends Application {
    //Needed to enable Jakarta REST and specify path

}
