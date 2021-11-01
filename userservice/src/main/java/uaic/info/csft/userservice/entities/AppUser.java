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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String userName;
    private String password;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Language> languages;

    @Getter
    public static class AppUserBuilder {

        private Long id;
        private String userName;
        private String password;
        private List<Language> languages;

        public AppUserBuilder() {}

        public AppUser.AppUserBuilder id(final Long id)
        {
            this.id = id;
            return this;
        }

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
