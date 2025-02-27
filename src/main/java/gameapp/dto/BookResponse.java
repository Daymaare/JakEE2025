package gameapp.dto;


import java.math.BigDecimal;

public class BookResponse {
    private String title;

    //Döljer info
    //@JsonbTransient
    private int pages;
    private String author;
    private BigDecimal price = BigDecimal.TEN;

    public BookResponse() {

    }

    public BookResponse(String title, int pages, String author) {
        this.title = title;
        this.pages = pages;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
