package mycalendar.cli;

import mycalendar.domain.*;
import mycalendar.domain.vo.*;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CalendarManager calendar = new CalendarManager();
        Scanner scanner = new Scanner(System.in);
        String utilisateur = null;
        boolean continuer = true;

        String utilisateurs[] = new String[99];
        String motsDePasses[] = new String[99];
        int nbUtilisateurs = 0;

        while (true) {

            if (utilisateur == null) {
                System.out.println("  _____         _                   _                __  __");
                System.out.println(" / ____|       | |                 | |              |  \\/  |");
                System.out.println(
                        "| |       __ _ | |  ___  _ __    __| |  __ _  _ __  | \\  / |  __ _  _ __    __ _   __ _   ___  _ __");
                System.out.println(
                        "| |      / _` || | / _ \\| '_ \\  / _` | / _` || '__| | |\\/| | / _` || '_ \\  / _` | / _` | / _ \\| '__|");
                System.out.println(
                        "| |____ | (_| || ||  __/| | | || (_| || (_| || |    | |  | || (_| || | | || (_| || (_| ||  __/| |");
                System.out.println(
                        " \\_____| \\__,_||_| \\___||_| |_| \\__,_| \\__,_||_|    |_|  |_| \\__,_||_| |_| \\__,_| \\__, | \\___||_|");
                System.out.println(
                        "                                                                                   __/ |");
                System.out.println(
                        "                                                                                  |___/");

                System.out.println("1 - Se connecter");
                System.out.println("2 - Créer un compte");
                System.out.println("Choix : ");

                switch (scanner.nextLine()) {
                    case "1":
                        System.out.print("Nom d'utilisateur: ");
                        utilisateur = scanner.nextLine();

                        if (utilisateur.equals("Roger")) {
                            String motDePasse = scanner.nextLine();
                            if (!motDePasse.equals("Chat")) {
                                utilisateur = null;
                            }
                        } else {
                            if (utilisateur.equals("Pierre")) {
                                String motDePasse = scanner.nextLine();
                                if (!motDePasse.equals("KiRouhl")) {
                                    utilisateur = null;
                                }
                            } else {
                                System.out.print("Mot de passe: ");
                                String motDePasse = scanner.nextLine();

                                for (int i = 0; i < nbUtilisateurs; i = i + 1) {
                                    if (utilisateurs[i].equals(utilisateur) && motsDePasses[i].equals(motDePasse)) {
                                        utilisateur = utilisateurs[i];
                                    }
                                }
                            }
                        }
                        break;

                    case "2":
                        System.out.print("Nom d'utilisateur: ");
                        utilisateur = scanner.nextLine();
                        System.out.print("Mot de passe: ");
                        String motDePasse = scanner.nextLine();
                        System.out.print("Répéter mot de passe: ");
                        if (scanner.nextLine().equals(motDePasse)) {
                            utilisateurs[nbUtilisateurs] = utilisateur;
                            motsDePasses[nbUtilisateurs] = motDePasse;
                            nbUtilisateurs = nbUtilisateurs + 1;
                        } else {
                            System.out.println("Les mots de passes ne correspondent pas...");
                            utilisateur = null;
                        }
                        break;
                }
            }

            while (continuer && utilisateur != null) {
                System.out.println("\nBonjour, " + utilisateur);
                System.out.println("=== Menu Gestionnaire d'Événements ===");
                System.out.println("1 - Voir les événements");
                System.out.println("2 - Ajouter un rendez-vous perso");
                System.out.println("3 - Ajouter une réunion");
                System.out.println("4 - Ajouter un évènement périodique");
                System.out.println("5 - Ajouter une journée entière (Nouveau !)");
                System.out.println("6 - Détecter les conflits");
                System.out.println("7 - Se déconnecter");
                System.out.print("Votre choix : ");

                String choix = scanner.nextLine();

                switch (choix) {
                    case "1":
                        System.out.println("\n=== Menu de visualisation d'Événements ===");
                        System.out.println("1 - Afficher TOUS les événements");
                        System.out.println("2 - Afficher les événements d'un MOIS précis");
                        System.out.println("3 - Afficher les événements d'une SEMAINE précise");
                        System.out.println("4 - Afficher les événements d'un JOUR précis");
                        System.out.println("5 - Retour");
                        System.out.print("Votre choix : ");

                        choix = scanner.nextLine();

                        switch (choix) {
                            case "1":
                                calendar.afficherEvenements();
                                break;

                            case "2":
                                System.out.print("Entrez l'année (AAAA) : ");
                                int anneeMois = Integer.parseInt(scanner.nextLine());
                                System.out.print("Entrez le mois (1-12) : ");
                                int mois = Integer.parseInt(scanner.nextLine());

                                LocalDateTime debutMois = LocalDateTime.of(anneeMois, mois, 1, 0, 0);
                                LocalDateTime finMois = debutMois.plusMonths(1).minusSeconds(1);

                                afficherListe(calendar.evenementsDansPeriode(new PeriodeRecherche(
                                        new DateHeureDebut(debutMois),
                                        new DateHeureDebut(finMois)
                                )));
                                break;

                            case "3":
                                System.out.print("Entrez l'année (AAAA) : ");
                                int anneeSemaine = Integer.parseInt(scanner.nextLine());
                                System.out.print("Entrez le numéro de semaine (1-52) : ");
                                int semaine = Integer.parseInt(scanner.nextLine());

                                LocalDateTime debutSemaine = LocalDateTime.now()
                                        .withYear(anneeSemaine)
                                        .with(WeekFields.of(Locale.FRANCE).weekOfYear(), semaine)
                                        .with(WeekFields.of(Locale.FRANCE).dayOfWeek(), 1)
                                        .withHour(0).withMinute(0);
                                LocalDateTime finSemaine = debutSemaine.plusDays(7).minusSeconds(1);

                                afficherListe(calendar.evenementsDansPeriode(new PeriodeRecherche(
                                        new DateHeureDebut(debutSemaine),
                                        new DateHeureDebut(finSemaine)
                                )));
                                break;

                            case "4":
                                System.out.print("Entrez l'année (AAAA) : ");
                                int anneeJour = Integer.parseInt(scanner.nextLine());
                                System.out.print("Entrez le mois (1-12) : ");
                                int moisJour = Integer.parseInt(scanner.nextLine());
                                System.out.print("Entrez le jour (1-31) : ");
                                int jour = Integer.parseInt(scanner.nextLine());

                                LocalDateTime debutJour = LocalDateTime.of(anneeJour, moisJour, jour, 0, 0);
                                LocalDateTime finJour = debutJour.plusDays(1).minusSeconds(1);

                                afficherListe(calendar.evenementsDansPeriode(new PeriodeRecherche(
                                        new DateHeureDebut(debutJour),
                                        new DateHeureDebut(finJour)
                                )));
                                break;
                        }
                        break;

                    case "2": {
                        // Ajout simplifié d'un RDV personnel
                        System.out.print("Titre de l'événement : ");
                        mycalendar.domain.vo.TitreEvenement titre = new mycalendar.domain.vo.TitreEvenement(scanner.nextLine());
                        mycalendar.domain.vo.Proprietaire prop = new mycalendar.domain.vo.Proprietaire(utilisateur);
                        System.out.print("Année (AAAA) : ");
                        int annee = Integer.parseInt(scanner.nextLine());
                        System.out.print("Mois (1-12) : ");
                        int moisRdv = Integer.parseInt(scanner.nextLine());
                        System.out.print("Jour (1-31) : ");
                        int jourRdv = Integer.parseInt(scanner.nextLine());
                        System.out.print("Heure début (0-23) : ");
                        int heure = Integer.parseInt(scanner.nextLine());
                        System.out.print("Minute début (0-59) : ");
                        int minute = Integer.parseInt(scanner.nextLine());
                        mycalendar.domain.vo.DateHeureDebut debut = new mycalendar.domain.vo.DateHeureDebut(LocalDateTime.of(annee, moisRdv, jourRdv, heure, minute));
                        System.out.print("Durée (en minutes) : ");
                        mycalendar.domain.vo.DureeEvenement duree = new mycalendar.domain.vo.DureeEvenement(Integer.parseInt(scanner.nextLine()));

                        calendar.ajouter(new RendezVousPersonnel(titre, prop, debut, duree));

                        System.out.println("Événement ajouté.");
                        break;
                    }

                    case "3": {
                        // Ajout simplifié d'une réunion
                        System.out.print("Titre de l'événement : ");
                        TitreEvenement titre = new TitreEvenement(scanner.nextLine());
                        Proprietaire prop = new Proprietaire(utilisateur);
                        System.out.print("Année (AAAA) : ");
                        int annee = Integer.parseInt(scanner.nextLine());
                        System.out.print("Mois (1-12) : ");
                        int moisRdv = Integer.parseInt(scanner.nextLine());
                        System.out.print("Jour (1-31) : ");
                        int jourRdv = Integer.parseInt(scanner.nextLine());
                        System.out.print("Heure début (0-23) : ");
                        int heure = Integer.parseInt(scanner.nextLine());
                        System.out.print("Minute début (0-59) : ");
                        int minute = Integer.parseInt(scanner.nextLine());
                        DateHeureDebut dateDebut = new DateHeureDebut(LocalDateTime.of(annee, moisRdv, jourRdv, heure, minute));
                        System.out.print("Durée (en minutes) : ");
                        DureeEvenement duree = new DureeEvenement(Integer.parseInt(scanner.nextLine()));
                        System.out.println("Lieu :");
                        Lieu lieu = new Lieu(scanner.nextLine());
                        
                        String participants = utilisateur;
                        
                        boolean encore = true;
                        System.out.println("Ajouter un participant ? (oui / non)");
                        while (scanner.nextLine().equals("oui"))
                        {
                            System.out.print("Participants : " + participants);
                            participants += ", " + scanner.nextLine();
                        }

                        List<String> partsList = java.util.Arrays.stream(participants.split(","))
                                .map(String::trim).collect(java.util.stream.Collectors.toList());

                        calendar.ajouter(new Reunion(titre, prop, dateDebut, duree, lieu, new Participants(partsList)));

                        System.out.println("Événement ajouté.");
                        break;
                    }

                    case "4": {
                        // Ajout simplifié d'un événement périodique
                        System.out.print("Titre de l'événement : ");
                        TitreEvenement titre = new TitreEvenement(scanner.nextLine());
                        Proprietaire prop = new Proprietaire(utilisateur);
                        System.out.print("Année (AAAA) : ");
                        int annee = Integer.parseInt(scanner.nextLine());
                        System.out.print("Mois (1-12) : ");
                        int moisRdv = Integer.parseInt(scanner.nextLine());
                        System.out.print("Jour (1-31) : ");
                        int jourRdv = Integer.parseInt(scanner.nextLine());
                        System.out.print("Heure début (0-23) : ");
                        int heure = Integer.parseInt(scanner.nextLine());
                        System.out.print("Minute début (0-59) : ");
                        int minute = Integer.parseInt(scanner.nextLine());
                        DateHeureDebut dateDebut = new DateHeureDebut(LocalDateTime.of(annee, moisRdv, jourRdv, heure, minute));
                        
                        System.out.print("Durée (en minutes) : ");
                        DureeEvenement duree = new DureeEvenement(Integer.parseInt(scanner.nextLine()));
                        
                        System.out.print("Frequence (en jours) : ");
                        FrequenceRecurrence frequence = new FrequenceRecurrence(Integer.parseInt(scanner.nextLine()));

                        calendar.ajouter(new EvenementPeriodique(titre, prop, dateDebut, duree, frequence));
                        System.out.println("Événement ajouté.");
                        break;
                    }

                    case "5": {
                        System.out.print("Titre de l'événement (Journée Entière) : ");
                        TitreEvenement titre = new TitreEvenement(scanner.nextLine());
                        Proprietaire prop = new Proprietaire(utilisateur);
                        System.out.print("Année (AAAA) : ");
                        int annee = Integer.parseInt(scanner.nextLine());
                        System.out.print("Mois (1-12) : ");
                        int moisRdv = Integer.parseInt(scanner.nextLine());
                        System.out.print("Jour (1-31) : ");
                        int jourRdv = Integer.parseInt(scanner.nextLine());
                        DateHeureDebut dateDebut = new DateHeureDebut(LocalDateTime.of(annee, moisRdv, jourRdv, 0, 0));

                        calendar.ajouter(new JourneeEntiere(titre, prop, dateDebut));
                        System.out.println("Événement ajouté.");
                        break;
                    }

                    case "6": {
                        java.util.List<mycalendar.domain.vo.Conflit> conflits = calendar.detecterConflits();
                        if (conflits.isEmpty()) {
                            System.out.println("Aucun conflit détecté !");
                        } else {
                            System.out.println("=== " + conflits.size() + " CONFLITS DÉTECTÉS ===");
                            for (mycalendar.domain.vo.Conflit c : conflits) {
                                System.out.println("  - Conflit entre [" + c.event1().description() + "] ET [" + c.event2().description() + "]");
                            }
                        }
                        break;
                    }

                    default:
                        System.out.println("Déconnexion ! Voulez-vous continuer ? (O/N)");
                        continuer = scanner.nextLine().trim().equalsIgnoreCase("oui");

                        utilisateur = null;
                }
            }
        }
    }

    private static void afficherListe(List<Evenement> evenements) {
        if (evenements.isEmpty()) {
            System.out.println("Aucun événement trouvé pour cette période.");
        } else {
            System.out.println("Événements trouvés : ");
            for (Evenement e : evenements) {
                System.out.println("- [" + e.id().valeur().substring(0, 8) + "] " + e.description());
            }
        }
    }
}
