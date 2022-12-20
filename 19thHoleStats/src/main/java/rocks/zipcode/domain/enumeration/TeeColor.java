package rocks.zipcode.domain.enumeration;

/**
 * The TeeColor enumeration.
 */
public enum TeeColor {
    BLUE("Blue"),
    WHITE("White"),
    GOLD("Gold"),
    RED("Red");

    private final String value;

    TeeColor(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
