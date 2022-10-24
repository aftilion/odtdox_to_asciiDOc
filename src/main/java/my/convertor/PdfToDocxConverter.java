package my.convertor;

import com.aspose.pdf.Document;
import com.aspose.pdf.SaveFormat;

public class PdfToDocxConverter {

    public void convert()  {
        // 1) Create a PdfDocument object
        Document doc = new Document("./odtMntPdf.pdf");

        // 2) Save resultant DOCX file
        doc.save("./tmpAsponce.docx", SaveFormat.DocX);
    }
}