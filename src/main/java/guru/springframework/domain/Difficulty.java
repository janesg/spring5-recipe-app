package guru.springframework.domain;

public enum Difficulty {

    EASY,
    MODERATE,
    HARD;

    // Added to avoid IntelliJ's issue with values() method in Thymeleaf template for drop-down
    public static Difficulty[] getValues() {
        return values();
    }
}
