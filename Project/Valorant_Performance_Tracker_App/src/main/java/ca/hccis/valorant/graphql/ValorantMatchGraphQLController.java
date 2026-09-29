package ca.hccis.valorant.graphql;

import ca.hccis.valorant.jpa.entity.ValorantMatch;
import ca.hccis.valorant.repositories.ValorantMatchRepository;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ValorantMatchGraphQLController {

    private final ValorantMatchRepository repo;

    public ValorantMatchGraphQLController(ValorantMatchRepository repo) {
        this.repo = repo;
    }

    @QueryMapping
    public List<ValorantMatch> matches() {
        List<ValorantMatch> all = new ArrayList<>();
        repo.findAll().forEach(all::add);
        return all;
    }

    @QueryMapping
    public ValorantMatch matchById(@Argument Integer id) {
        Optional<ValorantMatch> opt = repo.findById(id);
        return opt.orElse(null);
    }

    @QueryMapping
    public List<ValorantMatch> findByPlayer(@Argument String name) {
        return repo.findByPlayerNameContaining(name);
    }

    @MutationMapping
    public ValorantMatch createMatch(@Argument ValorantMatchInput input) {
        return repo.save(input.toEntity());
    }

    @MutationMapping
    public ValorantMatch updateMatch(@Argument Integer id, @Argument ValorantMatchInput input) {
        ValorantMatch entity = input.toEntity();
        entity.setMatchId(id);
        return repo.save(entity);
    }

    @MutationMapping
    public Boolean deleteMatch(@Argument Integer id) {
        if (!repo.existsById(id)) {
            return false;
        }
        repo.deleteById(id);
        return true;
    }
}
