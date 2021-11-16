package uaic.info.csft.userservice.entities;

import lombok.Getter;

@Getter
public enum Proficiencies {
    A1("A1", 1),
    A2("A2", 2),
    B1("B1", 3),
    B2("B2", 4),
    C1("C1", 5),
    C2("C2", 6),
    NATIVE("NATIVE", 7);

    String name;
    Integer index;

    Proficiencies(String name, Integer index)
    {
        this.name = name;
        this.index = index;
    }
}
