package mycalendar.domain.vo;

public record TitreEvenement(String valeur) {

    public TitreEvenement {
        if (valeur == null || valeur.isBlank()) {
            throw new IllegalArgumentException("Le titre ne peut pas être vide ou null");
        }
    }
}
