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

    public void ajouter(Evenement e) {
        nouveauxEvenements.add(e);
    }

    public List<Evenement> evenementsDansPeriode(PeriodeRecherche periode) {
        return nouveauxEvenements.stream()
                .filter(e -> e.estDansPeriode(periode))
                .collect(java.util.stream.Collectors.toList());
    }

    public List<mycalendar.domain.vo.Conflit> detecterConflits() {
        List<mycalendar.domain.vo.Conflit> conflits = new java.util.ArrayList<>();
        for (int i = 0; i < nouveauxEvenements.size(); i++) {
            Evenement e1 = nouveauxEvenements.get(i);
            for (int j = i + 1; j < nouveauxEvenements.size(); j++) {
                Evenement e2 = nouveauxEvenements.get(j);
                if (e1.chevauche(e2)) {
                    conflits.add(new mycalendar.domain.vo.Conflit(e1, e2));
                }
            }
        }
        return conflits;
    }

    public void supprimerParId(mycalendar.domain.vo.EventId id) {
        nouveauxEvenements.removeIf(e -> e.id().equals(id));
    }

    public List<Evenement> tous() {
        return new java.util.ArrayList<>(nouveauxEvenements);
    }
}
