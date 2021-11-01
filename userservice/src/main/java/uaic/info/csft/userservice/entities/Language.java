package uaic.info.csft.userservice.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Language")
@Table(name = "language")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Proficiencies proficiency;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "languages")
    private Set<AppUser> appUsers = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "language", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> posts = new HashSet<>();

    public Language(String language, Proficiencies proficiency) {
        this.name = language;
        this.proficiency = proficiency;
    }
}