package mycalendar.domain.vo;

public record DureeEvenement(int minutes) {

    public DureeEvenement {
        if (minutes <= 0) {
            throw new IllegalArgumentException("La durée doit être strictement positive");
        }
    }
}
