package readerFile;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.$x;

public class ReaderFilePDFAndXLSX {

    @Test
    @DisplayName("Чтение PDF файла из ресурса")
    public void ReaderPDFFileFromResource() throws IOException {
        File filePDF = new File("src/test/resources/pdf.pdf");
        PDF readerPDF = new PDF(filePDF);
        Assertions.assertTrue(readerPDF.text.contains("The Pdf995 Suite offers the following features, all at no cost:"));
        Assertions.assertEquals("PDF", readerPDF.title, "Формат файла PDF");
        Assertions.assertEquals("Software 995", readerPDF.author, "Автор файла Software 995");
    }

    @Test
    @DisplayName("Чтение PDF файла с сайта")
    public void ReaderPDFFileFromWebsite() throws IOException {
        Selenide.open("https://www.pdf995.com/samples/");
        File pdf = $x("//td[@data-sort='pdf.pdf']/a").download();
        PDF readerPDF = new PDF(pdf);
        Assertions.assertEquals("Software 995", readerPDF.author, "Автор файла Software 995");
    }

    @Test
    @DisplayName("Чтение XLSX файла c ресурса")
    public void ReadeXLSXFileFromResource() throws Exception {
        File fileXLSX = new File("src/test/resources/excelFile.xlsx");
        ReaderFileXls readerFileXls = new ReaderFileXls(fileXLSX);
        String[][] data = readerFileXls.getSheetData();
    }
}
