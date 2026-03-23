package mycalendar.domain.vo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LieuTest {

    @Test
    void creationAvecLieuValide() {
        Lieu lieu = new Lieu("Salle A");
        assertEquals("Salle A", lieu.valeur());
    }

    @Test
    void lieuVideInterdit() {
        assertThrows(IllegalArgumentException.class, () -> new Lieu(""));
    }

    @Test
    void lieuNullInterdit() {
        assertThrows(IllegalArgumentException.class, () -> new Lieu(null));
    }

    @Test
    void deuxLieuxIdentiquesEgaux() {
        assertEquals(new Lieu("Salle A"), new Lieu("Salle A"));
    }
}
