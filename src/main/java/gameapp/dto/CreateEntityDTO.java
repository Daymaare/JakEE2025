package gameapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateEntityDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Developer is required")
    private String developer;

    @Size(max = 500, message = "Description can't be longer than 500 characters")
    private String description;

    @NotBlank(message = "Release date is required")
    private String releaseDate;
}
