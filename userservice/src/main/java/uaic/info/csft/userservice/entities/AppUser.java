package uaic.info.csft.userservice.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AppUser {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false)
    private UUID id;

    private String userName;
    private String password;

    @OneToMany
    private List<Language> languages;

    @Getter
    public static class AppUserBuilder {

        private UUID id;
        private String userName;
        private String password;
        private List<Language> languages;

        AppUserBuilder() {}

        public AppUser.AppUserBuilder userName(final String userName)
        {
            this.userName = userName;
            return this;
        }

        public AppUser.AppUserBuilder password(final String password)
        {
            this.password = password;
            return this;
        }

        public AppUser.AppUserBuilder languages(final List<Language> languages)
        {
            this.languages = languages;
            return this;
        }

        public AppUser build()
        {
            return new AppUser(this.id, this.userName, this.password, this.languages);
        }
    }
}
