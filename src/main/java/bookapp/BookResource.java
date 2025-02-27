package bookapp;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

@Path("books")
public class BookResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Book firstCreate() {
        return new Book("Boken som finns",123);

    }

    @GET
    @Path("many")
    @Produces(MediaType.APPLICATION_JSON)
    public Books manyCreate() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("Boken som finns",123));
        books.add(new Book("Boken som inte finns",456));
        return new Books(books,2);
    }

    public record Books(List<Book> books, int totalBooks) {

    }

}
