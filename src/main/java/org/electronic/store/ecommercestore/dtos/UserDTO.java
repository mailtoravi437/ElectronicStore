package org.electronic.store.ecommercestore.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.electronic.store.ecommercestore.validate.ImageNameValid;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String userId;

    @Size(min = 3, max = 15, message = "Name must be between 3 and 50 characters")
    private String name;

    @Email(message = "Invalid User email !!")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @Size(min = 4, max=6, message="Invalid gender")
    private String gender;

    @NotBlank(message = "About is required yourself")
    private String about;

    @ImageNameValid
    private String imageName;
}
