package mycalendar.domain.vo;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class DateHeureDebutTest {

    @Test
    void creationAvecDateValide() {
        LocalDateTime dt = LocalDateTime.of(2026, 3, 23, 10, 0);
        DateHeureDebut d = new DateHeureDebut(dt);
        assertEquals(dt, d.valeur());
    }

    @Test
    void dateNullInterdite() {
        assertThrows(NullPointerException.class, () -> new DateHeureDebut(null));
    }

    @Test
    void deuxDatesIdentiquesEgales() {
        LocalDateTime dt = LocalDateTime.of(2026, 3, 23, 10, 0);
        assertEquals(new DateHeureDebut(dt), new DateHeureDebut(dt));
    }

    @Test
    void estAvant() {
        DateHeureDebut d1 = new DateHeureDebut(LocalDateTime.of(2026, 3, 23, 10, 0));
        DateHeureDebut d2 = new DateHeureDebut(LocalDateTime.of(2026, 3, 23, 11, 0));
        assertTrue(d1.estAvant(d2));
        assertFalse(d2.estAvant(d1));
    }
}
