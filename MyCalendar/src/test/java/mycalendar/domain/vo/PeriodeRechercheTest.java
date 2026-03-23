package mycalendar.domain.vo;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class PeriodeRechercheTest {

    @Test
    void creationAvecBornesValides() {
        DateHeureDebut debut = new DateHeureDebut(LocalDateTime.of(2026, 3, 1, 0, 0));
        DateHeureDebut fin = new DateHeureDebut(LocalDateTime.of(2026, 3, 31, 23, 59));
        PeriodeRecherche p = new PeriodeRecherche(debut, fin);
        assertEquals(debut, p.debut());
        assertEquals(fin, p.fin());
    }

    @Test
    void debutApresFinInterdit() {
        DateHeureDebut debut = new DateHeureDebut(LocalDateTime.of(2026, 4, 1, 0, 0));
        DateHeureDebut fin = new DateHeureDebut(LocalDateTime.of(2026, 3, 1, 0, 0));
        assertThrows(IllegalArgumentException.class, () -> new PeriodeRecherche(debut, fin));
    }

    @Test
    void debutNullInterdit() {
        DateHeureDebut fin = new DateHeureDebut(LocalDateTime.of(2026, 3, 31, 23, 59));
        assertThrows(NullPointerException.class, () -> new PeriodeRecherche(null, fin));
    }

    @Test
    void contientDate() {
        DateHeureDebut debut = new DateHeureDebut(LocalDateTime.of(2026, 3, 1, 0, 0));
        DateHeureDebut fin = new DateHeureDebut(LocalDateTime.of(2026, 3, 31, 23, 59));
        PeriodeRecherche p = new PeriodeRecherche(debut, fin);

        DateHeureDebut dans = new DateHeureDebut(LocalDateTime.of(2026, 3, 15, 10, 0));
        DateHeureDebut dehors = new DateHeureDebut(LocalDateTime.of(2026, 4, 15, 10, 0));
        assertTrue(p.contient(dans));
        assertFalse(p.contient(dehors));
    }
}
