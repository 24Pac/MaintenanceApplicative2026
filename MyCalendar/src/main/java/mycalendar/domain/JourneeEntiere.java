package mycalendar.domain;

import mycalendar.domain.vo.*;

public class JourneeEntiere extends Evenement {

    public JourneeEntiere(TitreEvenement titre, Proprietaire proprietaire, DateHeureDebut dateDebut) {
        super(titre, proprietaire, dateDebut, new DureeEvenement(24 * 60)); // 24 heures complètes
    }

    @Override
    public String description() {
        return "Journée entière : " + titre().valeur() + " le " + dateDebut().valeur().toLocalDate().toString();
    }
}
