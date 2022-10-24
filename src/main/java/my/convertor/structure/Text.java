package my.convertor.structure;


public class Text extends Element {
    private final String content;
    private final String color;
    private final boolean bold;
    private final boolean italic;
    private final boolean strike;

    public Text(String content, String color, boolean bold, boolean italic, boolean strike) {
        super();
        this.content = content;
        this.color = color;
        this.bold = bold;
        this.italic = italic;
        this.strike = strike;
    }
    public String getContent() {
        return content;
    }
    public boolean isBold() {
        return bold;
    }
    public boolean isItalic() {
        return italic;
    }
}
