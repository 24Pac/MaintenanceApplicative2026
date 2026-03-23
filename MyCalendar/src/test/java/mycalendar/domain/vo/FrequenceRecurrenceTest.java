package mycalendar.domain.vo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FrequenceRecurrenceTest {

    @Test
    void creationAvecFrequenceValide() {
        FrequenceRecurrence f = new FrequenceRecurrence(7);
        assertEquals(7, f.jours());
    }

    @Test
    void frequenceZeroInterdite() {
        assertThrows(IllegalArgumentException.class, () -> new FrequenceRecurrence(0));
    }

    @Test
    void frequenceNegativeInterdite() {
        assertThrows(IllegalArgumentException.class, () -> new FrequenceRecurrence(-5));
    }

    @Test
    void deuxFrequencesIdentiquesEgales() {
        assertEquals(new FrequenceRecurrence(7), new FrequenceRecurrence(7));
    }

    @Test
    void descriptionHebdomadaire() {
        assertEquals("7 jours", new FrequenceRecurrence(7).formatTexte());
    }
}
