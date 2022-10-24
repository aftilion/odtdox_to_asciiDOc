package my.convertor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class convertor {

//    //метод определения расширения файла
//    private static String getFileExtension(File file) {
//        String fileName = file.getName();
//        // если в имени файла есть точка и она не является первым символом в названии файла
//        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
//            // то вырезаем все знаки после последней точки в названии файла, то есть ХХХХХ.txt -> txt
//            return fileName.substring(fileName.lastIndexOf(".")+1);
//            // в противном случае возвращаем заглушку, то есть расширение не найдено
//        else return "";
//    }

    public static void main(String[] args) throws IOException {
        try (
              InputStream inputStream = Files.newInputStream(Path.of("./test1.docx"));)
        {
//            List<Element> parsedDocument = new Parser(inputStream).parse();
//            new Generator("ToDocx6").create(parsedDocument);
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (Exception e) {
            System.out.println("We have error");
        }
    }
}
