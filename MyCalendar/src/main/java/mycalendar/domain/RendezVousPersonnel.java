package mycalendar.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import mycalendar.domain.vo.*;

@JsonTypeName("rdv")
public class RendezVousPersonnel extends Evenement {

    public RendezVousPersonnel(TitreEvenement titre, Proprietaire proprietaire, DateHeureDebut dateDebut, DureeEvenement duree) {
        super(titre, proprietaire, dateDebut, duree);
    }

    @Override
    public String description() {
        return "RDV : " + titre().valeur() + " à " + dateDebut().valeur().toString() + " (" + duree().minutes() + "min)";
    }
}
