package mycalendar.domain.vo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DureeEvenementTest {

    @Test
    void creationAvecDureeValide() {
        DureeEvenement d = new DureeEvenement(60);
        assertEquals(60, d.minutes());
    }

    @Test
    void dureeZeroInterdite() {
        assertThrows(IllegalArgumentException.class, () -> new DureeEvenement(0));
    }

    @Test
    void dureeNegativeInterdite() {
        assertThrows(IllegalArgumentException.class, () -> new DureeEvenement(-1));
    }

    @Test
    void deuxDureesIdentiquesEgales() {
        assertEquals(new DureeEvenement(30), new DureeEvenement(30));
    }
}
