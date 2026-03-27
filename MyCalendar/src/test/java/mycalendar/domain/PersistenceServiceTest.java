package mycalendar.domain;

import mycalendar.domain.vo.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PersistenceServiceTest {

    @TempDir
    Path tempDir;

    @Test
    public void testSaveAndLoad() throws IOException {
        Path filePath = tempDir.resolve("calendar.json");
        PersistenceService service = new PersistenceService(filePath.toString());

        List<Evenement> evenements = Arrays.asList(
                new RendezVousPersonnel(
                        new TitreEvenement("RDV 1"),
                        new Proprietaire("Alice"),
                        new DateHeureDebut(LocalDateTime.of(2026, 3, 27, 10, 0)),
                        new DureeEvenement(60)
                ),
                new Reunion(
                        new TitreEvenement("Réunion 1"),
                        new Proprietaire("Bob"),
                        new DateHeureDebut(LocalDateTime.of(2026, 3, 27, 14, 0)),
                        new DureeEvenement(90),
                        new Lieu("Salle A"),
                        new Participants(Arrays.asList("Alice", "Bob"))
                )
        );

        service.save(evenements);
        List<Evenement> loaded = service.load();

        assertEquals(evenements.size(), loaded.size());
        assertEquals(evenements.get(0).titre(), loaded.get(0).titre());
        assertEquals(evenements.get(1).titre(), loaded.get(1).titre());
        assertEquals(evenements.get(1).getClass(), loaded.get(1).getClass());
    }
}
