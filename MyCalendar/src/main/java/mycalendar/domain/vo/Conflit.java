package mycalendar.domain.vo;

import mycalendar.domain.Evenement;

public record Conflit(Evenement event1, Evenement event2) {
    public boolean implique(Evenement e) {
        return event1.equals(e) || event2.equals(e);
    }
}
