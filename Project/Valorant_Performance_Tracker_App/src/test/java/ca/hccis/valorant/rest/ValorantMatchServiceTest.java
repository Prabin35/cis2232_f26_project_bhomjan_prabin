package ca.hccis.valorant.rest;

import ca.hccis.valorant.jpa.entity.ValorantMatch;
import ca.hccis.valorant.repositories.ValorantMatchRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValorantMatchServiceTest {

    @Mock
    private ValorantMatchRepository repo;

    @InjectMocks
    private ValorantMatchService service;

    @Test
    void create_savesMatchAndReturnsSavedMatch() {
        ValorantMatch input = new ValorantMatch();
        input.setPlayerName("SamplePlayer#001");
        input.setAgent("Jett");
        input.setMapName("Ascent");
        input.setKills(20);
        input.setDeaths(14);
        input.setAssists(8);
        input.setDamage(3200);
        input.setRounds(21);
        input.setKastRounds(16);

        when(repo.save(any(ValorantMatch.class))).thenAnswer(invocation -> {
            ValorantMatch saved = invocation.getArgument(0);
            saved.setMatchId(1);
            return saved;
        });

        ResponseEntity<ValorantMatch> response = service.create(input);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getMatchId());
        assertEquals("SamplePlayer#001", response.getBody().getPlayerName());

        ArgumentCaptor<ValorantMatch> captor = ArgumentCaptor.forClass(ValorantMatch.class);
        verify(repo).save(captor.capture());
        assertEquals("Jett", captor.getValue().getAgent());
    }
}
