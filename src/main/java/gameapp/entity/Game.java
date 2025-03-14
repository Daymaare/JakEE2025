package gameapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Setter
@Getter
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"title", "developer"}))
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Developer is required")
    private String developer;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Release Date must not be null")
    private LocalDate releaseDate;

    @Column(unique = true)
    @Pattern(regexp = "^\\d{12}$", message = "UPC must be 12 digits")
    private String upc; //Universal Product Code


    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", developer='" + developer + '\'' +
                ", description='" + description + '\'' +
                ", releaseDate=" + releaseDate +
                ", upc='" + upc + '\'' +
                '}';
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return getId() != null && Objects.equals(getId(), game.getId());
    }

    @Override
    public final int hashCode() {
        return getClass().hashCode();
    }

}
