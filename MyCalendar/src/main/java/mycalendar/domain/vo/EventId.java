package mycalendar.domain.vo;

import java.util.Objects;
import java.util.UUID;

public record EventId(String valeur) {

    public EventId {
        Objects.requireNonNull(valeur, "L'identifiant ne peut pas être null");
    }

    public static EventId generate() {
        return new EventId(UUID.randomUUID().toString());
    }

    public static EventId fromString(String valeur) {
        return new EventId(valeur);
    }
}
