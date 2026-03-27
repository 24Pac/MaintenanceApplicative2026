package mycalendar.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import mycalendar.domain.vo.*;

@JsonTypeName("reunion")
public class Reunion extends Evenement {

    private final Lieu lieu;
    private final Participants participants;

    public Reunion(TitreEvenement titre, Proprietaire proprietaire, DateHeureDebut dateDebut, DureeEvenement duree,
                   Lieu lieu, Participants participants) {
        super(titre, proprietaire, dateDebut, duree);
        this.lieu = lieu;
        this.participants = participants;
    }

    @Override
    public String description() {
        return "Réunion : " + titre().valeur() + " à " + lieu.valeur() + " avec " + participants.formatTexte();
    }
}
