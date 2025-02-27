package gameapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    private Long id;

    @NotBlank(message="Title is required")
    private String title;

    @NotBlank(message="Developer is required")
    private String developer;

    @NotBlank(message="Genre is required")
    private String genre;

    @Size(max=500)
    private String description;

    @Past(message="Release date must be in the past")
    private LocalDateTime releaseDate;

    @Pattern(regexp="^\\d{10}$", message="UPC must be 12 digits")
    private String upc; //Universal Product Code

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }


    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getUpc() {
        return upc;
    }
}
