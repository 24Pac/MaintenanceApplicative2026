package mycalendar.domain.vo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TitreEvenementTest {

    @Test
    void creationAvecTitreValide() {
        TitreEvenement titre = new TitreEvenement("Dentiste");
        assertEquals("Dentiste", titre.valeur());
    }

    @Test
    void titreVideInterdit() {
        assertThrows(IllegalArgumentException.class, () -> new TitreEvenement(""));
    }

    @Test
    void titreNullInterdit() {
        assertThrows(IllegalArgumentException.class, () -> new TitreEvenement(null));
    }

    @Test
    void deuxTitresIdentiquesEgaux() {
        assertEquals(new TitreEvenement("Dentiste"), new TitreEvenement("Dentiste"));
    }
}
