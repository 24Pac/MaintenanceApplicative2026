package mycalendar.domain.vo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProprietaireTest {

    @Test
    void creationAvecNomValide() {
        Proprietaire p = new Proprietaire("Alice");
        assertEquals("Alice", p.valeur());
    }

    @Test
    void nomVideInterdit() {
        assertThrows(IllegalArgumentException.class, () -> new Proprietaire(""));
    }

    @Test
    void nomNullInterdit() {
        assertThrows(IllegalArgumentException.class, () -> new Proprietaire(null));
    }

    @Test
    void deuxProprietairesIdentiquesEgaux() {
        assertEquals(new Proprietaire("Alice"), new Proprietaire("Alice"));
    }
}
