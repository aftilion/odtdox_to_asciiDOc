package my.convertor;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

import java.io.InputStream;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class OdtToPdfConverter {

    public void convert(InputStream in) throws Exception {

        // 1) Load ODT file and set Velocity template engine and cache it to the registry

       // InputStream in = Files.newInputStream(Paths.get(System.getProperty("user.dir").concat("\\test1.odt")));
        IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

        // 2) Create Java model context
        IContext context = report.createContext();

        // 3) Set PDF as format converter
        Options options = Options.getTo(ConverterTypeTo.PDF);

        // 3) Generate report by merging Java model with the ODT and convert it to PDF
        OutputStream out = new FileOutputStream(new File(("./odtMntPdf.pdf")));
        report.convert(context, options, out);
    }
}