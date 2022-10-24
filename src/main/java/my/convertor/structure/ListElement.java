package my.convertor.structure;

public class ListElement extends Element {
    private final int elementLvl;

    public ListElement(Paragraph paragraph, int elementLvl) {
        super();
        this.elementLvl = elementLvl;
        for (var str: paragraph.getChildElements()) {
            this.addChildElement(str);
        }
    }

    public int getElementLvl() {
        return elementLvl;
    }

    public Paragraph getLikeParagraph() {
        Paragraph paragraph = new Paragraph();
        for (var str: getChildElements()) {
            paragraph.addChildElement(str);
        }
        return paragraph;
    }
}
