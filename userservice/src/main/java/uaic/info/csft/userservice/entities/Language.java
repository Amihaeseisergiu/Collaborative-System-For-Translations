package uaic.info.csft.userservice.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Proficiencies proficiency;

    public Language(String language, Proficiencies proficiency) {
        this.name = language;
        this.proficiency = proficiency;
    }
}