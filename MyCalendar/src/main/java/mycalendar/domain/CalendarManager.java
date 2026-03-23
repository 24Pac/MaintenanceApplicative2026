package mycalendar.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import mycalendar.domain.vo.*;

public class CalendarManager {
    public List<Event> events;
    public List<Evenement> nouveauxEvenements;

    public CalendarManager() {
        this.events = new ArrayList<>();
        this.nouveauxEvenements = new ArrayList<>();
    }

    public void ajouter(Evenement e) {
        nouveauxEvenements.add(e);
        // On n'ajoute pas dans le vieux events car on est en train de le débrancher
    }

    public void ajouterEvent(String type, String title, String proprietaire, LocalDateTime dateDebut, int dureeMinutes,
            String lieu, String participantsStr, int frequenceJours) {
        Event e = new Event(type, title, proprietaire, dateDebut, dureeMinutes, lieu, participantsStr, frequenceJours);
        events.add(e);

        // --- STRANGLER FIG : Double écriture ---
        TitreEvenement t = new TitreEvenement(title);
        Proprietaire p = new Proprietaire(proprietaire);
        DateHeureDebut d = new DateHeureDebut(dateDebut);
        DureeEvenement du = new DureeEvenement(dureeMinutes > 0 ? dureeMinutes : 60);

        Evenement nouvelEvent;
        switch (type) {
            case "RDV_PERSONNEL":
                nouvelEvent = new RendezVousPersonnel(t, p, d, du);
                break;
            case "REUNION":
                Lieu l = new Lieu(lieu != null && !lieu.isEmpty() ? lieu : "Inconnu");
                List<String> partList = java.util.Arrays.stream(participantsStr.split(","))
                        .map(String::trim).collect(java.util.stream.Collectors.toList());
                Participants parts = new Participants(partList);
                nouvelEvent = new Reunion(t, p, d, du, l, parts);
                break;
            case "PERIODIQUE":
                FrequenceRecurrence freq = new FrequenceRecurrence(frequenceJours > 0 ? frequenceJours : 7);
                nouvelEvent = new EvenementPeriodique(t, p, d, du, freq);
                break;
            default:
                nouvelEvent = new RendezVousPersonnel(t, p, d, du);
        }
        nouveauxEvenements.add(nouvelEvent);
    }

    public List<Event> eventsDansPeriode(LocalDateTime debut, LocalDateTime fin) {
        List<Event> result = new ArrayList<>();
        for (Event e : events) {
            if (e.type.equals("PERIODIQUE")) {
                LocalDateTime temp = e.dateDebut;
                while (temp.isBefore(fin)) {
                    if (!temp.isBefore(debut)) {
                        result.add(e);
                        break;
                    }
                    temp = temp.plusDays(e.frequenceJours);
                }
            } else if (!e.dateDebut.isBefore(debut) && !e.dateDebut.isAfter(fin)) {
                result.add(e);
            }
        }
        return result;
    }

    public boolean conflit(Event e1, Event e2) {
        LocalDateTime fin1 = e1.dateDebut.plusMinutes(e1.dureeMinutes);
        LocalDateTime fin2 = e2.dateDebut.plusMinutes(e2.dureeMinutes);

        if (e1.type.equals("PERIODIQUE") || e2.type.equals("PERIODIQUE")) {
            return false; // Simplification abusive
        }

        if (e1.dateDebut.isBefore(fin2) && fin1.isAfter(e2.dateDebut)) {
            return true;
        }
        return false;
    }

    public void afficherEvenements() {
        for (Evenement e : nouveauxEvenements) {
            System.out.println("- [" + e.id().valeur().substring(0, 8) + "] " + e.description());
        }
    }

    public List<Evenement> evenementsDansPeriode(PeriodeRecherche periode) {
        return nouveauxEvenements.stream()
                .filter(e -> e.estDansPeriode(periode))
                .collect(java.util.stream.Collectors.toList());
    }

    public List<mycalendar.domain.vo.Conflit> detecterConflits() {
        List<mycalendar.domain.vo.Conflit> conflits = new java.util.ArrayList<>();
        for (int i = 0; i < nouveauxEvenements.size(); i++) {
            Evenement e1 = nouveauxEvenements.get(i);
            for (int j = i + 1; j < nouveauxEvenements.size(); j++) {
                Evenement e2 = nouveauxEvenements.get(j);
                if (e1.chevauche(e2)) {
                    conflits.add(new mycalendar.domain.vo.Conflit(e1, e2));
                }
            }
        }
        return conflits;
    }

    public void supprimerParId(mycalendar.domain.vo.EventId id) {
        nouveauxEvenements.removeIf(e -> e.id().equals(id));
    }

    public List<Evenement> tous() {
        return new java.util.ArrayList<>(nouveauxEvenements);
    }
}
