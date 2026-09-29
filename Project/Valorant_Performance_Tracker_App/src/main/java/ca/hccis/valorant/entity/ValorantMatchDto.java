package ca.hccis.valorant.entity;

import ca.hccis.valorant.jpa.entity.ValorantMatch;

import java.util.ArrayList;
import java.util.List;

public class ValorantMatchDto {
    private List<ValorantMatch> matches;

    public ValorantMatchDto() {
        matches = new ArrayList<ValorantMatch>();
    }

    public ValorantMatchDto(List<ValorantMatch> matches) {
        this.matches = matches;
    }

    public void addValorantMatch(ValorantMatch valorantMatch) {
        this.matches.add(valorantMatch);
    }

    public List<ValorantMatch> getMatches() {
        return matches;
    }

    public void setMatches(List<ValorantMatch> matches) {
        this.matches = matches;
    }
}
