package main.java.features;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.sun.scenario.effect.ImageData;
import javafx.collections.ObservableList;
import javafx.scene.control.Cell;
import main.java.App;
import main.java.dao.PackageTypeDAO;
import main.java.dao.PackagesDAO;
import main.java.entity.PackagesDTO;
import main.java.entity.PdfAreaDTO;
import main.java.entity.PdfDTO;

public class PdfGenerator {

    private static Font subFont = new Font(Font.FontFamily.HELVETICA, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.HELVETICA, 12,
            Font.BOLD);

    /**
     * Method that creating a PDF file
     * @param start date
     * @param end date
     * @param pathFile path and filename (extension set to pdf in AdminRaport.java)
     * @throws IOException if doesn't find a path then throw IOException
     * @throws DocumentException error while processing the document
     */
    public static void createPdf(Date start, Date end, String pathFile) throws IOException, DocumentException {
        File file = new File(pathFile);
        file.getParentFile().mkdirs();
        new PdfGenerator().fillPdf(pathFile, start, end);
    }

    /**
     * Method that filling PDF file with data from base
     * @param dest name file
     * @param start date
     * @param end date
     * @throws IOException if doesn't find a path then throw IOException
     * @throws DocumentException error while processing the document
     */
    public void fillPdf(String dest, Date start, Date end) throws IOException, DocumentException {
        Font font = new Font(Font.FontFamily.HELVETICA, 13, Font.BOLD, GrayColor.GRAYWHITE);
        BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.CACHED);
        Font tableFont = new Font(baseFont);

        Document document = new Document(PageSize.A4.rotate());
        PdfWriter.getInstance(document, new FileOutputStream(dest));
        document.open();

        PdfPTable mainTable = new PdfPTable(2);
        mainTable.setWidthPercentage(100);

        Image image = Image.getInstance("main/resources/images/outbox_black.png");
        image.scaleAbsolute(100, 100);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();

        LocalDate localEnd = Instant.ofEpochMilli(end.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();

        Paragraph paragraph = new Paragraph(
                App.getLanguageProperties("reportGeneratedBy") + "Admin" + "\n" + simpleDateFormat.format(now)
                        + "\n" + App.getLanguageProperties("fromThePeriod") + start + " - " + localEnd.minusDays(1),tableFont);



        PdfPCell cell = new PdfPCell(image);
        cell.setBorder(Rectangle.NO_BORDER);
        PdfPCell cell2 = new PdfPCell(paragraph);
        cell2.setBorder(Rectangle.NO_BORDER);
        cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);


        paragraph.setAlignment(Paragraph.ALIGN_BOTTOM);
        mainTable.addCell(cell);
        mainTable.addCell(cell2);
        document.add(mainTable);



        Paragraph empty = new Paragraph();
        addEmptyLine(empty, 1);
        document.add(empty);


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


        addEmptyLine(empty, 2);
        document.add(empty);


        float[] columnAreaWidths = {1, 5, 5, 5, 5, 5};
        PdfPTable tableArea = new PdfPTable(columnAreaWidths);
        tableArea.setWidthPercentage(100);
        tableArea.getDefaultCell().setUseAscender(true);
        tableArea.getDefaultCell().setUseDescender(true);


        tableArea.addCell(createCell("#", font));
        tableArea.addCell(createCell(App.getLanguageProperties("areaName"), font));
        tableArea.addCell(createCell(App.getLanguageProperties("smallNumber"), font));
        tableArea.addCell(createCell(App.getLanguageProperties("medNumber"), font));
        tableArea.addCell(createCell(App.getLanguageProperties("bigNumber"), font));
        tableArea.addCell(createCell(App.getLanguageProperties("packValue"), font));
        tableArea.getDefaultCell().setBackgroundColor(new GrayColor(0.75f));



        tableArea.setHeaderRows(1);
        List<PdfAreaDTO> listArea = PackageTypeDAO.readAreasForPdf(start, end);


        for (int i = 0; i < listArea.size(); i++) {

            if (listArea.get(i).getSmallPackages() == 0 && listArea.get(i).getMediumPackages() == 0 &&
                    listArea.get(i).getBigPackages() == 0) {

                listArea.remove(i);
                i--;
            }
        }

        tableArea.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for (int counter = 0; counter < listArea.size(); counter++) {
            if (counter % 2 == 1){
                tableArea.getDefaultCell().setBackgroundColor(GrayColor.GRAYWHITE);
            }else{
                tableArea.getDefaultCell().setBackgroundColor(GrayColor.LIGHT_GRAY);
            }
            tableArea.addCell(String.valueOf(counter + 1));
            tableArea.addCell(new Phrase(listArea.get(counter).getAreaName(), tableFont));
            tableArea.addCell(new Phrase(String.valueOf(listArea.get(counter).getSmallPackages()), tableFont));
            tableArea.addCell(new Phrase(String.valueOf(listArea.get(counter).getMediumPackages()), tableFont));
            tableArea.addCell(new Phrase(String.valueOf(listArea.get(counter).getBigPackages()), tableFont));
            tableArea.addCell(new Phrase(String.valueOf(listArea.get(counter).getSmallPackages() * smallPrice +
                    listArea.get(counter).getMediumPackages() * midPrice +
                    listArea.get(counter).getBigPackages() * bigPrice), tableFont));

        }
        document.add(tableArea);


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
        if (Preference.readPreference("language").equals("english")) {
            for (PdfDTO pdfDTO : list) {
                if (pdfDTO.getSize().equals("mała")) {
                    pdfDTO.setSize("small");
                }
                else if (pdfDTO.getSize().equals("średnia")) {
                    pdfDTO.setSize("medium");
                }
                else if (pdfDTO.getSize().equals("duża")) {
                    pdfDTO.setSize("big");
                }
            }
        }
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for (int counter = 0; counter < list.size(); counter++) {
            if (counter % 2 == 1){
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