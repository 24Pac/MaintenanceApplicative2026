package mycalendar.domain.vo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EventIdTest {

    @Test
    void deuxEventIdSontDifferents() {
        EventId id1 = EventId.generate();
        EventId id2 = EventId.generate();
        assertNotEquals(id1, id2);
    }

    @Test
    void unEventIdEstEgalALuiMeme() {
        EventId id = EventId.generate();
        assertEquals(id, id);
    }

    @Test
    void deuxEventIdAvecMemeValeurSontEgaux() {
        EventId id1 = EventId.generate();
        EventId id2 = EventId.fromString(id1.valeur());
        assertEquals(id1, id2);
    }

    @Test
    void eventIdNonNull() {
        assertNotNull(EventId.generate().valeur());
    }
}
