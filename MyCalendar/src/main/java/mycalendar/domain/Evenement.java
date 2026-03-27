package mycalendar.domain;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import mycalendar.domain.vo.*;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = RendezVousPersonnel.class, name = "rdv"),
        @JsonSubTypes.Type(value = Reunion.class, name = "reunion"),
        @JsonSubTypes.Type(value = EvenementPeriodique.class, name = "periodique"),
        @JsonSubTypes.Type(value = JourneeEntiere.class, name = "journee")
})
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public abstract class Evenement {
    @JsonProperty("id")
    private final EventId id;
    @JsonProperty("titre")
    private final TitreEvenement titre;
    @JsonProperty("proprietaire")
    private final Proprietaire proprietaire;
    @JsonProperty("dateDebut")
    private final DateHeureDebut dateDebut;
    @JsonProperty("duree")
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
