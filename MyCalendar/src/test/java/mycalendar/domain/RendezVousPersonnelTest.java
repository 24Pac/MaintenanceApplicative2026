package mycalendar.domain;

import mycalendar.domain.vo.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RendezVousPersonnelTest {

    @Test
    void descriptionContientDetailsRdv() {
        RendezVousPersonnel rdv = new RendezVousPersonnel(
                new TitreEvenement("Dentiste"),
                new Proprietaire("Alice"),
                new DateHeureDebut(LocalDateTime.of(2026, 3, 23, 10, 0)),
                new DureeEvenement(45)
        );

        String desc = rdv.description();
        assertTrue(desc.contains("RDV : Dentiste"));
        assertTrue(desc.contains("2026-03-23T10:00"));
        assertTrue(desc.contains("45min"));
    }

    @Test
    void estDansPeriode() {
        RendezVousPersonnel rdv = new RendezVousPersonnel(
                new TitreEvenement("Test"),
                new Proprietaire("A"),
                new DateHeureDebut(LocalDateTime.of(2026, 3, 23, 10, 0)),
                new DureeEvenement(60)
        );

        PeriodeRecherche periodeEnglobante = new PeriodeRecherche(
                new DateHeureDebut(LocalDateTime.of(2026, 3, 23, 0, 0)),
                new DateHeureDebut(LocalDateTime.of(2026, 3, 23, 23, 59))
        );
        assertTrue(rdv.estDansPeriode(periodeEnglobante));

        PeriodeRecherche periodeAvant = new PeriodeRecherche(
                new DateHeureDebut(LocalDateTime.of(2026, 3, 22, 0, 0)),
                new DateHeureDebut(LocalDateTime.of(2026, 3, 22, 23, 59))
        );
        assertFalse(rdv.estDansPeriode(periodeAvant));
    }

    @Test
    void chevaucheAvecAutreRdv() {
        RendezVousPersonnel rdv1 = new RendezVousPersonnel(
                new TitreEvenement("Test1"), new Proprietaire("A"),
                new DateHeureDebut(LocalDateTime.of(2026, 3, 23, 10, 0)),
                new DureeEvenement(60)
        );

        RendezVousPersonnel rdv2 = new RendezVousPersonnel(
                new TitreEvenement("Test2"), new Proprietaire("A"),
                new DateHeureDebut(LocalDateTime.of(2026, 3, 23, 10, 30)),
                new DureeEvenement(60)
        );

        assertTrue(rdv1.chevauche(rdv2));
        assertTrue(rdv2.chevauche(rdv1));
    }

    @Test
    void neChevauchePasSiApres() {
        RendezVousPersonnel rdv1 = new RendezVousPersonnel(
                new TitreEvenement("Test1"), new Proprietaire("A"),
                new DateHeureDebut(LocalDateTime.of(2026, 3, 23, 10, 0)),
                new DureeEvenement(60)
        );

        RendezVousPersonnel rdv2 = new RendezVousPersonnel(
                new TitreEvenement("Test2"), new Proprietaire("A"),
                new DateHeureDebut(LocalDateTime.of(2026, 3, 23, 11, 0)), // commence pile à la fin
                new DureeEvenement(60)
        );

        assertFalse(rdv1.chevauche(rdv2));
        assertFalse(rdv2.chevauche(rdv1));
    }
}
