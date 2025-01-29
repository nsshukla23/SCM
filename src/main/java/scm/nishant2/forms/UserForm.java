package scm.nishant2.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
 
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserForm {

  @NotBlank(message = "Name should be not null")
  @Size(min = 3 , message = "Name should be alleast 3 characters ")
  private String name ;

  @Email(message = "Email should be in valid form ")
  @Pattern(
            regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", 
            message = "Invalid email address"
        )
  private String email;

  @NotBlank(message = "Password should not be blank ")
  @Size(min = 6 , message = "Passwors should be more than 6 characters")
  private String password ;

  @NotEmpty(message = "Mobile space should not be blank")
    @Size(min = 10, max = 10, message = "Mobile number should be 10 digits ")
  private String phoneNumber ;

  @NotBlank(message = "about space should be filled with something ")
  @Size(min=15 , max = 100 , message = "Write between 15 to 100 words Don't extend more ")
  private String about ;

}
