package mycalendar.domain.vo;

import java.util.Objects;

public record PeriodeRecherche(DateHeureDebut debut, DateHeureDebut fin) {

    public PeriodeRecherche {
        Objects.requireNonNull(debut, "Le début de période ne peut pas être null");
        Objects.requireNonNull(fin, "La fin de période ne peut pas être null");
        if (fin.estAvant(debut)) {
            throw new IllegalArgumentException("La fin de période doit être après le début");
        }
    }

    public boolean contient(DateHeureDebut date) {
        return !date.estAvant(debut) && !date.estApres(fin);
    }
}
