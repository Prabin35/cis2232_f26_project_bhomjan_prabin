package ca.hccis.valorant.rest;

import ca.hccis.valorant.jpa.entity.ValorantMatch;
import ca.hccis.valorant.repositories.ValorantMatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for accessing Valorant matches using REST.
 *
 * @author Prabin Bhomjan
 * @since 20260928
 */
@RestController
@RequestMapping("/api/ValorantMatchService/v1/matches")
public class ValorantMatchService {

    private final ValorantMatchRepository repo;

    @Autowired
    public ValorantMatchService(ValorantMatchRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public ResponseEntity<List<ValorantMatch>> getAll() {

        Iterable<ValorantMatch> matchesIterable = repo.findAll();
        List<ValorantMatch> matches = new ArrayList<>();
        matchesIterable.forEach(matches::add);

        if (matches.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(matches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ValorantMatch> getById(@PathVariable Integer id) {

        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {

        return repo.findById(id)
                .map(entity -> {
                    repo.delete(entity);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.noContent().build());
    }

    @PostMapping
    public ResponseEntity<ValorantMatch> create(@RequestBody ValorantMatch obj) {

        ValorantMatch saved = repo.save(obj);

        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ValorantMatch> update(
            @PathVariable Integer id,
            @RequestBody ValorantMatch obj) {

        obj.setMatchId(id);

        return ResponseEntity.ok(repo.save(obj));
    }
}
