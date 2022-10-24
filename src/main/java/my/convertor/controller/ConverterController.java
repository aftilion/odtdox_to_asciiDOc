
package my.convertor.controller;
import my.convertor.OdtToPdfConverter;
import my.convertor.PdfToDocxConverter;
import my.convertor.generator.asciidoc.Generator;
import my.convertor.parser.docx.Parser;
import my.convertor.structure.Element;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class ConverterController {
    private final Parser parser;
    private final Generator generator;

    public ConverterController(Parser parser, Generator generator) {
        this.generator = generator;
        this.parser = parser;
    }

    //метод определения расширения файла
    private static String getFileExtension(File file) {
        String fileName = file.getName();
        // если в имени файла есть точка и она не является первым символом в названии файла
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            // то вырезаем все знаки после последней точки в названии файла, то есть ХХХХХ.txt -> txt
            return fileName.substring(fileName.lastIndexOf(".")+1);
            // в противном случае возвращаем заглушку, то есть расширение не найдено
        else return "";
    }


    @GetMapping("/")
    public String mainPage() {
        return "home";
    }

    @PostMapping("/upload")
    public Object handleUploadFile(@RequestParam("file") MultipartFile file , @RequestParam("type") String value ) throws Exception {
        UrlResource resource;
        System.out.println("Отработал");
        if (value.equals("docx")) {
            System.out.println("Зашел в docx");
            try {
                InputStream inputStream = file.getInputStream();
                List<Element> parsedDocument = this.parser.parse(inputStream);
                this.generator.create(parsedDocument, "ToDocx7");
                Path path = Paths.get(System.getProperty("user.dir").concat("\\ToDocx7.adoc"));
                resource = new UrlResource(path.toUri());
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else if (value.equals("odt")) {
            InputStream inputStream = file.getInputStream();
            System.out.println("Зашел в odt");
            OdtToPdfConverter converterOdtPdf = new OdtToPdfConverter();
            converterOdtPdf.convert(inputStream);
            System.out.println("Зашел в odt to pdf");
            PdfToDocxConverter pdfToDocxConverter = new PdfToDocxConverter();
            pdfToDocxConverter.convert();
            System.out.println("Зашел в odt to docx");
            try {
                InputStream in = Files.newInputStream(Paths.get(System.getProperty("user.dir").concat("\\tmpAsponce.docx")));
                List<Element> parsedDocument = this.parser.parse(in);
                System.out.println("Зашел в odt to docx parse");
                this.generator.create(parsedDocument, "ToDocx7");
                System.out.println("Зашел в odt to docx parse create ");
                Path path = Paths.get(System.getProperty("user.dir").concat("\\ToDocx7.adoc"));
                resource = new UrlResource(path.toUri());
                System.out.println("Зашел в odt to docx parse create good");
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        }
        else {
            return null;
        }
    }
}
