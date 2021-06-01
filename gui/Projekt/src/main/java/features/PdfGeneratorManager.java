package main.java.features;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import javafx.collections.ObservableList;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import main.java.App;
import main.java.controllers.auth.Login;
import main.java.dao.PackageTypeDAO;
import main.java.dao.PackagesDAO;
import main.java.dao.UserInfosDAO;
import main.java.dao.UsersDAO;
import main.java.entity.PdfDTO;
import main.java.entity.UserInfos;
import main.java.entity.Users;

import java.io.File;
import java.io.IOException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;



import static java.time.temporal.ChronoUnit.DAYS;

public class PdfGeneratorManager {

    private static Font catFont = new Font(Font.FontFamily.HELVETICA, 18,
            Font.BOLD);
    private static Font smallFont = new Font(Font.FontFamily.HELVETICA, 12,
            Font.NORMAL);
    private static Font subFont = new Font(Font.FontFamily.HELVETICA, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.HELVETICA, 12,
            Font.BOLD);

    /**
     * creating a pdf file
     * @param start date
     * @param end date
     * @param display type of date display in the report
     * @param pathFile path and filename (extension set to pdf in ManagerRaports.java)
     * @throws IOException if doesn't find a path then throw IOException
     * @throws DocumentException error while processing the document
     */
    public static void createPdf(Date start, Date end, boolean display, String pathFile) throws IOException, DocumentException {

        File file = new File(pathFile);
        file.getParentFile().mkdirs();
        new PdfGeneratorManager().fillPdf(pathFile, start, end, display);
    }

    /**
     * filling the file with data
     * @param dest name file
     * @param start date
     * @param end date
     * @param display type of date display in the report
     * @throws IOException if doesn't find a path then throw IOException
     * @throws DocumentException error while processing the document0
     */
    public void fillPdf(String dest, Date start, Date end, boolean display) throws IOException, DocumentException {

        int tableNumber = 0;

        Font font = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD, GrayColor.GRAYWHITE);
        BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.CACHED);
        Font tableFont = new Font(baseFont);
        smallBold = new Font(baseFont, 12, Font.BOLD);

        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();

        PdfPTable mainTable = new PdfPTable(2);
        mainTable.setWidthPercentage(100);

        Image image = Image.getInstance(getClass().getClassLoader().getResource("main/resources/images/outbox_black.png"));
        image.scaleAbsolute(100, 100);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();

        LocalDate localEnd = Instant.ofEpochMilli(end.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

        UserInfos ui = UserInfosDAO.getUserInfoByID(Login.getUserInfoID()).get(0);
        Users uu = UsersDAO.getUsersId(Login.getUserID()).get(0);

        Paragraph paragraph;


        if(display){
            paragraph = new Paragraph(
                    App.getLanguageProperties("reportGeneratedBy") + ui.getName() + " "
                            + ui.getSurname() + "\n\n" + App.getLanguageProperties("dateGenerated")
                            + simpleDateFormat.format(now)
                            + "\n\n" + App.getLanguageProperties("ofDay") + start,tableFont);
        }
        else{
            paragraph = new Paragraph(
                    App.getLanguageProperties("reportGeneratedBy") + ui.getName() + " "
                            + ui.getSurname() + "\n\n" + App.getLanguageProperties("dateGenerated")
                            + simpleDateFormat.format(now)
                            + "\n\n" + App.getLanguageProperties("fromThePeriod") + start + " - "
                            + localEnd,tableFont);
        }


        paragraph.setAlignment(Paragraph.ALIGN_BOTTOM);
        mainTable.addCell(new PdfPCell(image));
        mainTable.addCell(paragraph);
        document.add(mainTable);

        Paragraph data = new Paragraph(App.getLanguageProperties("dataPackages"), subFont);
        document.add(data);

        ObservableList<PdfDTO> packList = PackagesDAO.readPackagesForPdf(start, end);

        int smallCounter = 0;
        int midCounter = 0;
        int bigCounter = 0;
        for(int i = 0; i < packList.size(); i++) {
            if (packList.get(i).getSize().equals("mała") ) {
                smallCounter++;

            }
            if (packList.get(i).getSize().equals("średnia")) {
                midCounter++;
            }
            if (packList.get(i).getSize().equals("duża")) {
                bigCounter++;
            }
        }

        Double smallPrice = Double.valueOf(PackageTypeDAO.getPackageTypes().get(0).getPrice());
        Double midPrice = Double.valueOf(PackageTypeDAO.getPackageTypes().get(1).getPrice());
        Double bigPrice = Double.valueOf(PackageTypeDAO.getPackageTypes().get(2).getPrice());

        Double packValue = smallCounter*smallPrice + midCounter*midPrice + bigCounter*bigPrice;

        Paragraph value = new Paragraph(App.getLanguageProperties("valueOfPackages") + packValue + " zł", tableFont);
        document.add(value);

        Paragraph number = new Paragraph(App.getLanguageProperties("numberOfPackages"), smallBold);
        document.add(number);

        Paragraph small = new Paragraph(App.getLanguageProperties("small")+": " + smallCounter, tableFont);
        document.add(small);

        Paragraph medium = new Paragraph(App.getLanguageProperties("medium")+": " + midCounter, tableFont);
        document.add(medium);

        Paragraph big = new Paragraph(App.getLanguageProperties("big")+": " + bigCounter, tableFont);
        document.add(big);

        Paragraph empty = new Paragraph();
        addEmptyLine(empty, 2);
        document.add(empty);



        float[] columnWidths = {1, 5, 5, 5, 5, 5};
        PdfPTable table = new PdfPTable(columnWidths);
        table.setWidthPercentage(100);
        table.getDefaultCell().setUseAscender(true);
        table.getDefaultCell().setUseDescender(true);


        table.addCell(createCell("#", font));
        table.addCell(createCell(App.getLanguageProperties("packageNumber"), font));
        table.addCell(createCell(App.getLanguageProperties("size"), font));
        table.addCell(createCell(App.getLanguageProperties("city"), font));
        table.addCell(createCell(App.getLanguageProperties("voivodeship"), font));
        table.addCell(createCell(App.getLanguageProperties("date"), font));
        table.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));

        

        table.setHeaderRows(1);
        ObservableList<PdfDTO> list = PackagesDAO.readPackagesForPdf(start, end);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        for (int counter = 0; counter < list.size(); counter++) {
            if(list.get(counter).getAreaID() == uu.getAreaId()){
                tableNumber++;
                if (tableNumber % 2 == 1){
                    table.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
                }else{
                    table.getDefaultCell().setBackgroundColor(GrayColor.LIGHT_GRAY);
                }
                table.addCell(String.valueOf(counter + 1));
                table.addCell(new Phrase(list.get(counter).getPackageNumber(), tableFont));
                table.addCell(new Phrase(list.get(counter).getSize(), tableFont));
                table.addCell(new Phrase(list.get(counter).getCity(), tableFont));
                table.addCell(new Phrase(list.get(counter).getVoivodeship(), tableFont));
                table.addCell(new Phrase(simpleDateFormat.format(list.get(counter).getDate()), tableFont));
            }
        }
        document.add(table);
        document.close();
    }

    /**
     * Method that create cell in table
     * @param text text
     * @param font font
     * @return
     */
    private PdfPCell createCell(String text, Font font){
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(GrayColor.GRAYBLACK);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    /**
     * Method that add empty line to pdf
     * @param paragraph paragraph
     * @param number number of empty lines
     */
    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}