package gameapp;


import gameapp.dto.BookResponse;
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
    public BookResponse firstCreate() {
        return new BookResponse("Boken som finns", 123, "Förf");
    }

    @GET
    @Path("many")
    @Produces(MediaType.APPLICATION_JSON)
    public Books manyCreate() {
        List<BookResponse> books = new ArrayList<>();
        books.add(new BookResponse("1984", 328, "George Orwell"));
        books.add(new BookResponse("To Kill a Mockingbird", 281, "Harper Lee"));
        books.add(new BookResponse("Brave New World", 311, "Aldous Huxley"));
        books.add(new BookResponse("The Great Gatsby", 180, "F. Scott Fitzgerald"));
        books.add(new BookResponse("Moby Dick", 635, "Herman Melville"));
        return new Books(books, books.size());
    }


    public record Books(List<BookResponse> books, int totalBooks) {

    }

}
