package org.electronic.store.ecommercestore.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link org.electronic.store.ecommercestore.entities.Category}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto{
    String categoryId;

    @NotBlank(message = "Title is required")
    @Min(value = 4, message = "Title must be at least 3 characters")
    String title;

    @NotBlank(message = "Description is required")
    String description;


    String coverImage;
}