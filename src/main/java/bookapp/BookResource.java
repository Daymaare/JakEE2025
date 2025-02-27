package bookapp;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("books")
public class BookResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String firstCreate() {
        return "First Test";
    }

}
