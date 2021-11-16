package uaic.info.csft.userservice.comparator;

import uaic.info.csft.userservice.entities.Proficiencies;

import java.util.Comparator;

public class ProficienciesComparator implements Comparator<Proficiencies> {

    @Override
    public int compare(Proficiencies o1, Proficiencies o2) {
        int returnValue = 0;
        if (o1.getIndex() > o2.getIndex()) {
            returnValue = 1;
        } else if (o1.getIndex() < o2.getIndex()) {
            returnValue = -1;
        }
        return returnValue;
    }
}
