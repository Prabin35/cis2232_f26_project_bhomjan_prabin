package ca.hccis.valorant.bo;

import ca.hccis.valorant.jpa.entity.ValorantMatch;

import java.util.ArrayList;

public class ValorantMatchValidationBO {

    /**
     * KAST rounds are a subset of the rounds played, so they can not be
     * greater than the rounds played.
     *
     * @param match the match being added or modified
     * @return list of validation errors (empty if valid)
     */
    public static ArrayList<String> validateKastRounds(ValorantMatch match) {

        ArrayList<String> errors = new ArrayList<>();

        if (match.getKastRounds() != null && match.getRounds() != null
                && match.getKastRounds() > match.getRounds()) {
            errors.add("KAST rounds can not be greater than rounds played");
        }

        return errors;
    }

}
