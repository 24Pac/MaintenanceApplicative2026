package mycalendar.domain;

import mycalendar.domain.vo.*;

public abstract class Evenement {
    private final EventId id;
    private final TitreEvenement titre;
    private final Proprietaire proprietaire;
    private final DateHeureDebut dateDebut;
    private final DureeEvenement duree;

    protected Evenement(TitreEvenement titre, Proprietaire proprietaire, DateHeureDebut dateDebut, DureeEvenement duree) {
        this.id = EventId.generate();
        this.titre = titre;
        this.proprietaire = proprietaire;
        this.dateDebut = dateDebut;
        this.duree = duree;
    }

    public EventId id() { return id; }
    public TitreEvenement titre() { return titre; }
    public Proprietaire proprietaire() { return proprietaire; }
    public DateHeureDebut dateDebut() { return dateDebut; }
    public DureeEvenement duree() { return duree; }

    public abstract String description();

    public boolean estDansPeriode(PeriodeRecherche periode) {
        DateHeureDebut finEvent = dateDebut.plusMinutes(duree.minutes());
        return !periode.debut().estApres(finEvent) && !periode.fin().estAvant(dateDebut);
    }

    public boolean chevauche(Evenement autre) {
        return this.estEnConflitInterne(autre.dateDebut(), autre.duree())
                || autre.estEnConflitInterne(this.dateDebut(), this.duree());
    }

    protected boolean estEnConflitInterne(DateHeureDebut autreDebut, DureeEvenement autreDuree) {
        DateHeureDebut fin1 = this.dateDebut.plusMinutes(this.duree.minutes());
        DateHeureDebut fin2 = autreDebut.plusMinutes(autreDuree.minutes());

        return this.dateDebut.estAvant(fin2) && fin1.estApres(autreDebut);
    }
}
