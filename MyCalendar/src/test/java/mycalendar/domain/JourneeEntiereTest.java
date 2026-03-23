package mycalendar.domain;

import mycalendar.domain.vo.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class JourneeEntiereTest {

    @Test
    void descriptionSpecifiqueJourneeEntiere() {
        JourneeEntiere je = new JourneeEntiere(
                new TitreEvenement("Férié"),
                new Proprietaire("RH"),
                new DateHeureDebut(LocalDateTime.of(2026, 7, 14, 0, 0)) // 14 juillet
        );

        String desc = je.description();
        assertTrue(desc.contains("Journée entière : Férié"));
        assertTrue(desc.contains("2026-07-14"));
    }

    @Test
    void dureeEstFixeA24Heures() {
        JourneeEntiere je = new JourneeEntiere(
                new TitreEvenement("Anniversaire"),
                new Proprietaire("Team"),
                new DateHeureDebut(LocalDateTime.of(2026, 5, 1, 0, 0))
        );

        assertEquals(24 * 60, je.duree().minutes());
    }

    @Test
    void chevaucheToutRdvCeJourLa() {
        JourneeEntiere je = new JourneeEntiere(
                new TitreEvenement("Férié"),
                new Proprietaire("RH"),
                new DateHeureDebut(LocalDateTime.of(2026, 7, 14, 0, 0)) // durera jusqu'au 15 à 00:00
        );

        RendezVousPersonnel rdvDedans = new RendezVousPersonnel(
                new TitreEvenement("Point"),
                new Proprietaire("Bob"),
                new DateHeureDebut(LocalDateTime.of(2026, 7, 14, 14, 0)),
                new DureeEvenement(60)
        );

        RendezVousPersonnel rdvDehors = new RendezVousPersonnel(
                new TitreEvenement("Point 2"),
                new Proprietaire("Bob"),
                new DateHeureDebut(LocalDateTime.of(2026, 7, 15, 10, 0)),
                new DureeEvenement(60)
        );

        assertTrue(je.chevauche(rdvDedans));
        assertFalse(je.chevauche(rdvDehors));
    }
}
