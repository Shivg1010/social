package dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class JpaUser {
    private UUID userId;

    private String firstName;

    private String uniqueName;

    private String password;

    private String email;

    private String profileUrl;

    private String bio;

    private String lastName;

    private boolean privateAccount = false;

    private LocalDate createdAt;
}
