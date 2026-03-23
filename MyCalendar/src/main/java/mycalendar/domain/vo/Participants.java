package mycalendar.domain.vo;

import java.util.List;
import java.util.Objects;

public record Participants(List<String> liste) {

    public Participants {
        Objects.requireNonNull(liste, "La liste de participants ne peut pas être null");
        if (liste.isEmpty()) {
            throw new IllegalArgumentException("La liste de participants ne peut pas être vide");
        }
        liste = List.copyOf(liste); // copie défensive, immutable
    }

    public String formatTexte() {
        return String.join(", ", liste);
    }
}
