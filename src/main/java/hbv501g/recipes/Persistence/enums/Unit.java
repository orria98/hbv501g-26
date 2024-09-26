package hbv501g.recipes.Persistence.enums;

/**
 * Enum for units of measure, currently contains milliliters and grams
 */
public enum Unit {

    ML("ml"),
    G("g"),;

    private final String unitString;

    Unit(String unitString) {
        this.unitString = unitString;
    }

    @Override
    public String toString() {
        return unitString;
    }
}
