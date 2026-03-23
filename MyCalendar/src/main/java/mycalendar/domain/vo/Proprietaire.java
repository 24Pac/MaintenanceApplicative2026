package mycalendar.domain.vo;

public record Proprietaire(String valeur) {

    public Proprietaire {
        if (valeur == null || valeur.isBlank()) {
            throw new IllegalArgumentException("Le propriétaire ne peut pas être vide ou null");
        }
    }
}
