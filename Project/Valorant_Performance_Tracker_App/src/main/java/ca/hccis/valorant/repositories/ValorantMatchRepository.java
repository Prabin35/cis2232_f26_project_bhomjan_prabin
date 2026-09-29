package ca.hccis.valorant.repositories;

import ca.hccis.valorant.jpa.entity.ValorantMatch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValorantMatchRepository extends CrudRepository<ValorantMatch, Integer> {
    /**
     * Use Spring Data JPA functionality to find the matches whose player name
     * contains the string passed in.
     *
     * @param name The name to find
     * @return The list of matches
     * @author Prabin Bhomjan
     * @since 20260928
     */
    List<ValorantMatch> findByPlayerNameContaining(String name);
}
