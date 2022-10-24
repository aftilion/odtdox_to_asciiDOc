package my.convertor.structure;


public class Header extends Element {
    private final int type;

    public Header(int type) {
        super();
        this.type = type;
    }

    public int getImportance() {
        return type;
    }
}
