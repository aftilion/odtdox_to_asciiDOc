package my.convertor.generator.asciidoc;
import my.convertor.structure.*;
import org.springframework.stereotype.Service;
import java.io.*;
import java.util.List;

@Service
public class Generator {
    public void create(List<Element> structure , String fileName) throws IOException {
        try(FileWriter writer = new FileWriter(fileName + ".adoc", false)) {
            writer.write(createContent(structure));
        }
    }
    private String createContent(List<Element> structure) {
        StringBuilder result = new StringBuilder();
        for (var str: structure) {
            if (str instanceof Header currentHeader) {
                result.append(createHeader(currentHeader));
            }
            if (str instanceof Paragraph currentParagraph) {
                result.append(createParagraph(currentParagraph));
            }
            if (str instanceof DocList currentDocList) {
                result.append(createDocList(currentDocList));
            }
            if (str instanceof Table currentTable) {
                result.append(createTable(currentTable));
            }
            result.append("\n\n");
        }
        return result.toString();
    }

    private StringBuffer createHeader(Header header) {
        StringBuffer resultHeader = new StringBuffer();
        resultHeader.append("=".repeat(Math.max(0, header.getImportance())));
        resultHeader.append(" ");
        List<Element> childElements = header.getChildElements();
        for (var str: childElements) {
            resultHeader.append(createParagraph((Paragraph) str));
        }
        return resultHeader;
    }

    private StringBuffer createDocList(DocList docList) {
        StringBuffer resultDocList = new StringBuffer();
        List<Element> childElements = docList.getChildElements();
        String typeList = docList.getTypeList();
        for (var str: childElements) {
            ListElement listElement = (ListElement) str;
            int nestedLvl = listElement.getElementLvl() + 1;
            if (typeList.equals("bullet")) {
                resultDocList.append("*".repeat(Math.max(0, nestedLvl)));
            } else {
                resultDocList.append(".".repeat(Math.max(0, nestedLvl)));
            }
            resultDocList.append(" ");
            resultDocList.append(createParagraph(listElement.getLikeParagraph()));
            resultDocList.append("\n");
        }
        return resultDocList;
    }

    private StringBuffer createTable(Table table) {
        StringBuffer resultTable = new StringBuffer("|===\n");
        List<Element> rows = table.getChildElements();
        for (var row: rows) {
            List<Element> cells = row.getChildElements();
            for (var cell: cells) {
                resultTable.append("|");
                for (var paragraph: cell.getChildElements()) {
                    resultTable.append(createParagraph((Paragraph) paragraph));
                    resultTable.append("\n\n");
                }
            }
            resultTable.append("\n");
        }
        resultTable.append("|===");
        return resultTable;
    }

    private StringBuffer createParagraph(Paragraph paragraph) {
        StringBuffer resultParagraph = new StringBuffer();
        List<Element> childElements = paragraph.getChildElements();
        for (var str: childElements) {
            if (str instanceof Text text) {
                resultParagraph.append(createText(text));
            }
            if (str instanceof Image image) {
                resultParagraph.append(createImage(image));
            }
        }
        return resultParagraph;
    }

    private StringBuffer createText(Text text) {
        StringBuffer resultText = new StringBuffer();
        String sym = "";
        if (text.isBold()) sym += "**";
        if (text.isItalic()) sym += "__";
        resultText.append(sym).append(text.getContent()).append(sym);
        return resultText;
    }

    private StringBuffer createImage(Image image) {
        return new StringBuffer(image.getImageText());
    }
}

