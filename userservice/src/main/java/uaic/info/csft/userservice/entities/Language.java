package uaic.info.csft.userservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "languages")
@IdClass(LanguageId.class)
public class Language {

    @Id
    @Column(name = "name")
    private String name;

    @Id
    @Column(name = "proficiency")
    @Enumerated(EnumType.STRING)
    private Proficiencies proficiency;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "languages")
    private Set<User> users = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "language", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> posts = new HashSet<>();

    public Language(String language, Proficiencies proficiency) {
        this.name = language;
        this.proficiency = proficiency;
    }

    @Override
    public String toString()
    {
        return name + " " + proficiency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Language language = (Language) o;
        return name != null && Objects.equals(name, language.getName())
                && proficiency != null && proficiency.equals(language.getProficiency());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, proficiency);
    }
}