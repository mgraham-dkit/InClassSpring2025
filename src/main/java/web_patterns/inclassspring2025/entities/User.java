package web_patterns.inclassspring2025.entities;

import lombok.*;

@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @EqualsAndHashCode.Include
    private int userID;
    @NonNull
    private String username;
    @NonNull
    private String password;
}
