package mycalendar.domain;

import mycalendar.domain.vo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CalendarManagerTest {

    private CalendarManager manager;

    @BeforeEach
    void setUp() {
        manager = new CalendarManager();
    }

    @Test
    void testDetecterConflits() {
        DateHeureDebut debut1 = new DateHeureDebut(LocalDateTime.of(2023, 10, 15, 10, 0));
        DureeEvenement duree1 = new DureeEvenement(60);
        RendezVousPersonnel rdv1 = new RendezVousPersonnel(new TitreEvenement("RDV 1"), new Proprietaire("P1"), debut1, duree1);

        DateHeureDebut debut2 = new DateHeureDebut(LocalDateTime.of(2023, 10, 15, 10, 30));
        DureeEvenement duree2 = new DureeEvenement(60);
        RendezVousPersonnel rdv2 = new RendezVousPersonnel(new TitreEvenement("RDV 2"), new Proprietaire("P2"), debut2, duree2);

        manager.nouveauxEvenements.add(rdv1);
        manager.nouveauxEvenements.add(rdv2);

        List<Conflit> conflits = manager.detecterConflits();
        assertEquals(1, conflits.size(), "Il doit y avoir un conflit détecté");
        assertTrue(conflits.get(0).implique(rdv1) && conflits.get(0).implique(rdv2));
    }

    @Test
    void testSupprimerParId() {
        DateHeureDebut debut = new DateHeureDebut(LocalDateTime.of(2023, 10, 15, 10, 0));
        DureeEvenement duree = new DureeEvenement(60);
        RendezVousPersonnel rdv = new RendezVousPersonnel(new TitreEvenement("RDV"), new Proprietaire("P1"), debut, duree);

        manager.nouveauxEvenements.add(rdv);
        assertEquals(1, manager.tous().size());

        manager.supprimerParId(rdv.id());
        assertEquals(0, manager.tous().size(), "L'événement doit être supprimé");
    }

    @Test
    void testAjouterRetourneConflits() {
        DateHeureDebut debut1 = new DateHeureDebut(LocalDateTime.of(2023, 10, 20, 10, 0));
        RendezVousPersonnel rdv1 = new RendezVousPersonnel(new TitreEvenement("RDV 1"), new Proprietaire("P1"), debut1, new DureeEvenement(60));
        manager.ajouter(rdv1);

        DateHeureDebut debut2 = new DateHeureDebut(LocalDateTime.of(2023, 10, 20, 10, 30));
        RendezVousPersonnel rdv2 = new RendezVousPersonnel(new TitreEvenement("RDV 2"), new Proprietaire("P1"), debut2, new DureeEvenement(60));
        
        List<Conflit> conflits = manager.ajouter(rdv2);
        
        assertEquals(1, conflits.size(), "L'ajout doit détecter automatiquement le conflit");
        assertTrue(conflits.get(0).implique(rdv1));
        assertTrue(conflits.get(0).implique(rdv2));
    }
}
