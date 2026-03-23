package mycalendar.domain;

import mycalendar.domain.vo.*;

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
        DateHeureDebut dateOccurence = dateDebut();
        
        // Optimisation basique : on avance par bonds jusqu'à atteindre ou dépasser la période
        while (dateOccurence.estAvant(periode.debut())) {
            dateOccurence = dateOccurence.plusJours(frequence.jours());
        }

        // Vérifier si cette occurrence ou l'une des suivantes est dans la période
        while (!dateOccurence.estApres(periode.fin())) {
            DateHeureDebut finOccurence = dateOccurence.plusMinutes(duree().minutes());
            if (!periode.debut().estApres(finOccurence) && !periode.fin().estAvant(dateOccurence)) {
                return true;
            }
            dateOccurence = dateOccurence.plusJours(frequence.jours());
        }
        return false;
    }

    @Override
    protected boolean estEnConflitInterne(DateHeureDebut autreDebut, DureeEvenement autreDuree) {
        int maxOccurrencesTestables = 365 / frequence.jours() + 1;
        DateHeureDebut dateOccurence = dateDebut();

        for (int i = 0; i < maxOccurrencesTestables; i++) {
            DateHeureDebut fin1 = dateOccurence.plusMinutes(duree().minutes());
            DateHeureDebut fin2 = autreDebut.plusMinutes(autreDuree.minutes());

            if (dateOccurence.estAvant(fin2) && fin1.estApres(autreDebut)) {
                return true;
            }
            dateOccurence = dateOccurence.plusJours(frequence.jours());
        }
        return false;
    }
}
