package readerFile;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class ReaderFileXls {

    private final File exlsFile;

    //страница файла
    private XSSFSheet sheet;
    private XSSFWorkbook book;
    //желаемая страница
    private String sheetName;

    //Читает файл и перебрасывает на 1 страницу по ID
    public ReaderFileXls(File exlsFile) throws IOException {
        this.exlsFile = exlsFile;
        try {
            FileInputStream fs = new FileInputStream(exlsFile);
            book = new XSSFWorkbook(fs);
            sheet = book.getSheetAt(0);
        } catch (IOException e) {
            throw new IOException("Wrong file");
        }
    }

    //Читает файл и перебрасывает на 1 страницу по sheetName
    public ReaderFileXls(File exlsFile, String sheetName) throws IOException {
        this.exlsFile = exlsFile;
        try {
            FileInputStream fs = new FileInputStream(exlsFile);
            book = new XSSFWorkbook(fs);
            sheet = book.getSheet(sheetName);
        } catch (IOException e) {
            throw new IOException("Неправильный файл");
        }
    }

    //Преобразование ячейки в строчку
    private String cellToString(XSSFCell cell) throws Exception {
        Object result = null;
        CellType type = cell.getCellType();
        switch (type) {
            case NUMERIC:
                result = cell.getNumericCellValue();
                break;
            case STRING:
                result = cell.getStringCellValue();
                break;
            case FORMULA:
                result = cell.getCellFormula();
                break;
            case BLANK:
                result = "";
                break;
            default:
                throw new Exception("Ошибка чтения ячейки");
        }
        return result.toString();
    }

    //Получение количества столбцов на странице
    private int xlsxCountColumn() {
        return sheet.getRow(0).getFirstCellNum();
    }

    //Получение количества строк на странице
    private int xlsxCountRow() {
        return sheet.getLastRowNum() + 1;
    }

    //Метод для удаления null строк
    private String[][] deleteNulls(String[][] oldArray) {
        return Arrays.stream(oldArray)
                .filter(row -> Arrays.stream(row).anyMatch(Objects::nonNull))
                .filter(row -> Arrays.stream(row).anyMatch(x -> x != ""))
                .toArray(String[][]::new);
    }

    //метод для сборки данных по ID
    public String[][] getSheetData() throws Exception {
        int numberOfColumn = xlsxCountColumn();
        int numberOfRows = xlsxCountRow();
        String[][] data = new String[numberOfRows - 1][numberOfColumn];
        for (int i = 1; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumn; j++) {
                XSSFRow row = sheet.getRow(i);
                XSSFCell cell = row.getCell(j);
                if (cell == null)
                    break;
                String value = cellToString(cell);
                data[i - 1][j] = value;
            }
        }
        data = deleteNulls(data);
        return data;
    }

    //метод для сборки данных по sheetName
    public String[][] getSheetData(String sheetName) throws Exception {
        int numberOfColumn = xlsxCountColumn();
        int numberOfRows = xlsxCountRow();
        String[][] data = new String[numberOfRows - 1][numberOfColumn];
        for (int i = 1; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumn; j++) {
                XSSFRow row = book.getSheet(sheetName).getRow(i);
                XSSFCell cell = row.getCell(j);
                if (cell == null)
                    break;
                String value = cellToString(cell);
                data[i - 1][j] = value;
            }
        }
        data = deleteNulls(data);
        return data;
    }

    //поиск по всем ячейкам по sheetName
    public boolean isSheetContainsStringStream(String expected) throws Exception {
        return Arrays.stream(getSheetData())
                .flatMap(x -> Arrays.stream(x))
                .anyMatch(x -> x != null && x.contains(expected));
    }
}
