package mycalendar.domain;

import mycalendar.domain.vo.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EvenementPeriodiqueTest {

    @Test
    void descriptionAfficheFrequence() {
        EvenementPeriodique evt = new EvenementPeriodique(
                new TitreEvenement("Stand-up"),
                new Proprietaire("DevTeam"),
                new DateHeureDebut(LocalDateTime.of(2026, 1, 1, 9, 30)),
                new DureeEvenement(15),
                new FrequenceRecurrence(1)
        );

        String desc = evt.description();
        assertTrue(desc.contains("Événement périodique : Stand-up"));
        assertTrue(desc.contains("tous les 1 jours"));
    }

    @Test
    void estDansPeriodeSiOccurrenceCorrespond() {
        EvenementPeriodique evt = new EvenementPeriodique(
                new TitreEvenement("Hebdo"),
                new Proprietaire("Team"),
                new DateHeureDebut(LocalDateTime.of(2026, 3, 2, 10, 0)), // Lundi
                new DureeEvenement(60),
                new FrequenceRecurrence(7) // Toutes les semaines
        );

        // Semaine du 16 au 22 mars (contient l'occurrence du 16 mars)
        PeriodeRecherche periode = new PeriodeRecherche(
                new DateHeureDebut(LocalDateTime.of(2026, 3, 16, 0, 0)),
                new DateHeureDebut(LocalDateTime.of(2026, 3, 22, 23, 59))
        );

        assertTrue(evt.estDansPeriode(periode));
    }

    @Test
    void chevaucheSiUneOccurrenceConflite() {
        EvenementPeriodique periodique = new EvenementPeriodique(
                new TitreEvenement("Hebdo"),
                new Proprietaire("Team"),
                new DateHeureDebut(LocalDateTime.of(2026, 3, 2, 10, 0)),
                new DureeEvenement(60),
                new FrequenceRecurrence(7)
        );

        RendezVousPersonnel rdvMemeHeure = new RendezVousPersonnel(
                new TitreEvenement("Conflit"),
                new Proprietaire("Bob"),
                new DateHeureDebut(LocalDateTime.of(2026, 3, 16, 10, 30)), // Tombe sur une occurrence !
                new DureeEvenement(30)
        );

        assertTrue(periodique.chevauche(rdvMemeHeure));
        assertTrue(rdvMemeHeure.chevauche(periodique));
    }
}
