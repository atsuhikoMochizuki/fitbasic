package fr.mochizuki.generic_api.cross_cutting.enums;

public enum ExperienceLevelEnum {
    JUNIOR("Débutant"),
    MID_LEVEL("Intermédiaire"),
    SENIOR("Confirmé"),
    EXPERT("Expert");

    private final String displayName;

    ExperienceLevelEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}