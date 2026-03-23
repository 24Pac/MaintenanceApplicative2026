package mycalendar.domain.vo;

import java.time.LocalDateTime;
import java.util.Objects;

public record DateHeureDebut(LocalDateTime valeur) {

    public DateHeureDebut {
        Objects.requireNonNull(valeur, "La date/heure de début ne peut pas être null");
    }

    public boolean estAvant(DateHeureDebut autre) {
        return this.valeur.isBefore(autre.valeur);
    }

    public boolean estApres(DateHeureDebut autre) {
        return this.valeur.isAfter(autre.valeur);
    }

    public DateHeureDebut plusMinutes(int minutes) {
        return new DateHeureDebut(this.valeur.plusMinutes(minutes));
    }

    public DateHeureDebut plusJours(int jours) {
        return new DateHeureDebut(this.valeur.plusDays(jours));
    }
}
