package javafx.chart;

import javafx.scene.paint.Color;

public enum CSSClassLabel {
    JUST_CLICK("just-click", "Just Click", 0, Color.rgb(0xFF, 0xAA, 0x3E)),
    CREDENTIAL("credential", "Credential", 1, Color.rgb(0xFF, 0x42, 0x6A)),
    NO_CLICK("no-click", "No Click", 2, Color.rgb(0xCC, 0xCC, 0xCC)),
    CLASSLESS("", "", Integer.MAX_VALUE, Color.rgb(0xFF, 0xFF, 0xFF));

    public final String cssClass, label;
    public final int position;
    private final Color color;

    private CSSClassLabel(String cssClass, String label, int position, Color color) {
        this.cssClass = cssClass;
        this.label = label;
        this.position = position;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
