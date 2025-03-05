package gameapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateEntityDTO {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Developer is required")
    private String developer;

    @Size(max = 500, message = "Description can't be longer than 500 characters")
    private String description;

    // Release date is optional during updates
    private String releaseDate;
}
