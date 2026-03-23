package mycalendar.domain.vo;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class ParticipantsTest {

    @Test
    void creationAvecListeValide() {
        Participants p = new Participants(List.of("Alice", "Bob"));
        assertEquals(List.of("Alice", "Bob"), p.liste());
    }

    @Test
    void listeVideInterdite() {
        assertThrows(IllegalArgumentException.class, () -> new Participants(List.of()));
    }

    @Test
    void listeNullInterdite() {
        assertThrows(IllegalArgumentException.class, () -> new Participants(null));
    }

    @Test
    void participantsEstImmuable() {
        List<String> noms = new java.util.ArrayList<>(List.of("Alice"));
        Participants p = new Participants(noms);
        noms.add("Bob");
        assertEquals(1, p.liste().size());
    }

    @Test
    void formatTexte() {
        Participants p = new Participants(List.of("Alice", "Bob", "Charlie"));
        assertEquals("Alice, Bob, Charlie", p.formatTexte());
    }
}
