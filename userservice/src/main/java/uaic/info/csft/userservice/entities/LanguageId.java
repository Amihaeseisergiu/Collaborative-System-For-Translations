package uaic.info.csft.userservice.entities;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
public class LanguageId implements Serializable {

    String name;
    Proficiencies proficiency;
}
