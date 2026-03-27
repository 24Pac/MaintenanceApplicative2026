package mycalendar.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import mycalendar.domain.vo.*;

@JsonTypeName("periodique")
public class EvenementPeriodique extends Evenement {

    private final FrequenceRecurrence frequence;

    public EvenementPeriodique(TitreEvenement titre, Proprietaire proprietaire, DateHeureDebut dateDebut,
                               DureeEvenement duree, FrequenceRecurrence frequence) {
        super(titre, proprietaire, dateDebut, duree);
        this.frequence = frequence;
    }

    @Override
    public String description() {
        return "Événement périodique : " + titre().valeur() + " tous les " + frequence.formatTexte();
    }

    @Override
    public boolean estDansPeriode(PeriodeRecherche periode) {
        // Calculer le nombre maximum d'occurrences à tester pour couvrir la période
        // (Approximation simple pour éviter le while initial)
        long joursEntre = java.time.temporal.ChronoUnit.DAYS.between(dateDebut().valeur(), periode.fin().valeur());
        int maxOccurrences = (int) (joursEntre / frequence.jours()) + 1;

        return java.util.stream.IntStream.range(0, maxOccurrences)
                .mapToObj(i -> dateDebut().plusJours(i * frequence.jours()))
                .anyMatch(occurence -> {
                    DateHeureDebut finOccurence = occurence.plusMinutes(duree().minutes());
                    return !periode.debut().estApres(finOccurence) && !periode.fin().estAvant(occurence);
                });
    }

    @Override
    protected boolean estEnConflitInterne(DateHeureDebut autreDebut, DureeEvenement autreDuree) {
        int maxOccurrencesTestables = 365 / frequence.jours() + 1;

        return java.util.stream.IntStream.range(0, maxOccurrencesTestables)
                .mapToObj(i -> dateDebut().plusJours(i * frequence.jours()))
                .anyMatch(occurence -> {
                    DateHeureDebut fin1 = occurence.plusMinutes(duree().minutes());
                    DateHeureDebut fin2 = autreDebut.plusMinutes(autreDuree.minutes());
                    return occurence.estAvant(fin2) && fin1.estApres(autreDebut);
                });
    }
}
