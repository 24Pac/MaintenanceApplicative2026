package mycalendar.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import mycalendar.domain.vo.*;

public class CalendarManager {
    public List<Evenement> nouveauxEvenements;

    public CalendarManager() {
        this.nouveauxEvenements = new ArrayList<>();
    }

    public List<mycalendar.domain.vo.Conflit> ajouter(Evenement e) {
        List<mycalendar.domain.vo.Conflit> conflits = nouveauxEvenements.stream()
                .filter(existant -> existant.chevauche(e))
                .map(existant -> new mycalendar.domain.vo.Conflit(existant, e))
                .collect(java.util.stream.Collectors.toList());
        
        nouveauxEvenements.add(e);
        return conflits;
    }

    public List<Evenement> evenementsDansPeriode(PeriodeRecherche periode) {
        return nouveauxEvenements.stream()
                .filter(e -> e.estDansPeriode(periode))
                .collect(java.util.stream.Collectors.toList());
    }

    public List<mycalendar.domain.vo.Conflit> detecterConflits() {
        return nouveauxEvenements.stream()
                .flatMap(e1 -> nouveauxEvenements.stream()
                        .filter(e2 -> nouveauxEvenements.indexOf(e1) < nouveauxEvenements.indexOf(e2))
                        .filter(e1::chevauche)
                        .map(e2 -> new mycalendar.domain.vo.Conflit(e1, e2)))
                .collect(java.util.stream.Collectors.toList());
    }

    public void supprimerParId(mycalendar.domain.vo.EventId id) {
        nouveauxEvenements.removeIf(e -> e.id().equals(id));
    }

    public List<Evenement> tous() {
        return new java.util.ArrayList<>(nouveauxEvenements);
    }
}
