package mycalendar.domain;

import mycalendar.domain.vo.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReunionTest {

    @Test
    void descriptionContientLieuEtParticipants() {
        Reunion reunion = new Reunion(
                new TitreEvenement("Bilan"),
                new Proprietaire("Alice"),
                new DateHeureDebut(LocalDateTime.of(2026, 3, 24, 14, 0)),
                new DureeEvenement(120),
                new Lieu("Salle Conseil"),
                new Participants(List.of("Alice", "Bob", "Charlie"))
        );

        String desc = reunion.description();
        assertTrue(desc.contains("Réunion : Bilan"));
        assertTrue(desc.contains("Salle Conseil"));
        assertTrue(desc.contains("Alice, Bob, Charlie"));
    }
}
