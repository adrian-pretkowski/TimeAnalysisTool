package pl.adi.timeanalysistool.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "First name cannot be empty...")
    @Size(min = 2, message="First name should have at least 2 characters...")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty...")
    @Size(min = 2, message="Last name should have at least 2 characters...")
    private String lastName;

    @NotEmpty(message = "Username cannot be empty...")
    @Size(min = 2, message="Username should have at least 2 characters...")
    @Column(unique = true)
    private String username;

    @NotEmpty(message = "Password cannot be empty...")
    @Size(min = 2, message = "Password should have at least 2 characters...")
    private String password;

    @NotEmpty(message = "E-mail cannot be empty...")
    @Email(message = "The e-mail address provided is invalid...")
    @Column(unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> roles = new ArrayList<>();
}
