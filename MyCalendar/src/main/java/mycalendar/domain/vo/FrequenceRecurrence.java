package mycalendar.domain.vo;

public record FrequenceRecurrence(int jours) {

    public FrequenceRecurrence {
        if (jours <= 0) {
            throw new IllegalArgumentException("La fréquence doit être strictement positive");
        }
    }

    public String formatTexte() {
        return jours + " jours";
    }
}
