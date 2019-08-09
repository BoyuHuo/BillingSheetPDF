import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import entity.Address;
import entity.InvoiceInfo;

import java.util.HashMap;
import java.util.Map;


public class BillingPDFServiceImp implements BillingPDFService {

    //Overall data
    public String pdfDest = "/home/baiyu/Documents/Billing Details.pdf";
    public String companyLogo = "/home/baiyu/Documents/logo.jpg";
    public Address companyAddress;
    public String greatingMsg = "Greetings from Orion, we are writing to provide you with an electronic invoice for your use of Orion Web Service. Additional information about your bill, individual service charge details, and your account history are available le on the Account Activity Page. ";

    //Billing Head
    public Address billingAddress;
    public InvoiceInfo invoiceInfo;
    public String accountNumber = "1234567891456789";
    public String billingPeriod = "June 1, 2019 - June 30, 2019";


    //Billing data
    public Map<String,String> billData = new HashMap<String,String>();

    //Billing summary
    public String totalBeforeTax;
    public String  gst;
    public String pst;
    public String creditDeducted;
    public String usdDeducted;
    public String grandTotal;



    public void generatePdfBill() throws Exception {
        // Creating a PdfWriter
        PdfWriter writer = new PdfWriter(pdfDest);

        // Creating a PdfDocument
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.addNewPage();

        // Creating a Document by passing PdfDocument object to its constructor
        Document document = new Document(pdfDoc, PageSize.LETTER);
        document.setMargins(40, 40, 40, 40);

        //font
        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
        document.setFont(font);

        // Creating header and footer
        setHeader(document, pdfDoc);
        setFooter(document, pdfDoc);

        // Create the content of the bill
        Table brandInfo = generateBrandTitle();
        Table customerInfo = generateCustomerInfo();
        Paragraph greetingMsg = generateGreetingMsg();
        Table billingDetail = generateBillingDetailTable();

        document.add(brandInfo);
        document.add(customerInfo);
        document.add(greetingMsg);
        document.add(billingDetail);

        // Closing the document
        document.close();
    }

    public void setHeader(Document doc, PdfDocument pdfDoc) {
        int n = pdfDoc.getNumberOfPages();
        for (int i = 1; i <= n; i++) {
            doc.showTextAligned(new Paragraph(String.format("page %s of %s", i, n)),
                    559, 810, i, TextAlignment.RIGHT, VerticalAlignment.TOP, 0);
        }
    }

    public void setFooter(Document doc, PdfDocument pdfDoc) {
        String text = "You will be automatically charged for the amount due. No further action is required on your part.\nThank you for your business.";
        int n = pdfDoc.getNumberOfPages();
        for (int i = 1; i <= n; i++) {
            doc.showTextAligned(new Paragraph(text),
                    35, 35, i, TextAlignment.LEFT, VerticalAlignment.BOTTOM, 0);
        }
    }

    public Table generateCustomerInfo() throws Exception {
        float[] pointColumnWidths = {300F, 150F, 150F};
        Table table = new Table(pointColumnWidths).setMarginBottom(30);

        Text invoiceTag  = new Text("Invoice").setBold().setFontSize(20);
        Paragraph p = new Paragraph(invoiceTag);
        table.addCell(new Cell().add(p).setBorder(Border.NO_BORDER));

        table.addCell(new Cell().setBorder(Border.NO_BORDER));
        table.addCell(new Cell().setBorder(Border.NO_BORDER));

        table.addCell(new Cell().add("Bill to").setBorder(Border.NO_BORDER).setBold());
        table.addCell(new Cell().add("Details").setBorder(Border.NO_BORDER).setBold());
        table.addCell(new Cell().add("").setBorder(Border.NO_BORDER).setBold());

        Text cusName = new Text(billingAddress.getName()+"\n");
        Text cusStreet = new Text(billingAddress.getStreet()+"\n");
        Text cusCity = new Text(billingAddress.getCity()+"\n");
        Text cusPost = new Text(billingAddress.getPostCode()+"\n");
        Text cusCountry = new Text(billingAddress.getCountry());



        Paragraph cusAddress = new Paragraph().add(cusName).add(cusStreet).add(cusCity).add(cusPost).add(cusCountry);
        table.addCell(new Cell().add(cusAddress).setBorder(Border.NO_BORDER));

        Text numTag = new Text("Invoice Number:\n");
        Text dateTag = new Text("Issue Date:\n");
        Text termsTag = new Text("Payment Terms\n");
        Text cardNumTag = new Text("Card Number:");
        Paragraph billingTag = new Paragraph().add(numTag).add(dateTag).add(termsTag).add(cardNumTag);
        table.addCell(new Cell().add(billingTag).setBorder(Border.NO_BORDER));


        Text numVal = new Text(invoiceInfo.getInvoiceNum()+"\n");
        Text dateVal = new Text(invoiceInfo.getIssueDate()+"\n");
        Text termsVal = new Text(invoiceInfo.getPaymentTerm()+"\n");
        Text cardNumVal = new Text(invoiceInfo.getCardNum());

        Paragraph billingVal = new Paragraph().add(numVal).add(dateVal).add(termsVal).add(cardNumVal);
        table.addCell(new Cell().add(billingVal).setBorder(Border.NO_BORDER));


        return table;
    }

    public Table generateBrandTitle() throws Exception {

        float[] pointColumnWidths = {350F, 200F};
        Table table = new Table(pointColumnWidths);

        ImageData data = ImageDataFactory.create(companyLogo);
        Image img = new Image(data);
        table.addCell(new Cell().add(img.scaleAbsolute(110, 110)).setBorder(Border.NO_BORDER));

        // Creating a list
        Text companyTag = new Text(companyAddress.getName()+"\n").setBold();
        Text streetTag = new Text(companyAddress.getStreet()+"\n");
        Text cityTag = new Text(companyAddress.getCity()+"\n");
        Text postTag = new Text(companyAddress.getPostCode()+"\n");
        Text countryTag = new Text(companyAddress.getCountry());

        Paragraph p = new Paragraph().add(companyTag).add(streetTag).add(cityTag).add(postTag).add(countryTag);
        table.addCell(new Cell().add(p).setBorder(Border.NO_BORDER).setVerticalAlignment(VerticalAlignment.BOTTOM));

        return table;
    }


    public Paragraph generateGreetingMsg() {
        Paragraph paragraph = new Paragraph(greatingMsg).setMarginBottom(30);
        return paragraph;
    }


    /**
     * Generate the billing detail, including the items, description, tax information etc.
     *
     * @return table: table with all billing item detail
     */
    public Table generateBillingDetailTable() {

        float[] pointColumnWidths = {275F, 275F};
        Table table = new Table(pointColumnWidths);

        //Account Number and Billing Detail
        Text accountTag = new Text("Account Number: ").setBold();
        Text accountVal = new Text(accountNumber);
        Paragraph p = new Paragraph().add(accountTag).add(accountVal);
        table.addCell(new Cell().add(p)
                .setTextAlignment(TextAlignment.LEFT)
                .setBorder(Border.NO_BORDER).setHeight(30));

        Text periodTag = new Text("Billing Period: ").setBold();
        Text periodVal = new Text(billingPeriod);
        Paragraph p1 = new Paragraph().add(periodTag).add(periodVal);
        table.addCell(new Cell().add(p1)
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER));


        //Table Header
        table.addCell(new Cell().add("Orion Service Charges")
                .setTextAlignment(TextAlignment.LEFT)
                .setBold().setBorder(Border.NO_BORDER)
                .setBorderTop(new SolidBorder(Color.BLACK, 1))
                .setBorderBottom(new SolidBorder(Color.BLACK, 1)));

        table.addCell(new Cell().add("Amount($USD)")
                .setTextAlignment(TextAlignment.RIGHT)
                .setBold().setBorder(Border.NO_BORDER)
                .setBorderTop(new SolidBorder(Color.BLACK, 1))
                .setBorderBottom(new SolidBorder(Color.BLACK, 1)));

        //Item detail
        for (Map.Entry<String, String> entry : billData.entrySet()) {

            table.addCell(new Cell().add(entry.getKey()).setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(entry.getValue()).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).setHeight(40));

        }


        table.addCell(new Cell().add("")
                .setTextAlignment(TextAlignment.LEFT)
                .setBold().setBorder(Border.NO_BORDER)
                .setBorderTop(new SolidBorder(Color.BLACK, 1)));

        Table nestedTable = generateTaxDetailTable();
        table.addCell(new Cell().add(nestedTable).setTextAlignment(TextAlignment.LEFT)
                .setBorder(Border.NO_BORDER)
                .setBorderTop(new SolidBorder(Color.BLACK, 1)));

        return table;
    }


    public Table generateTaxDetailTable() {

        float[] pointColumnWidths = {150f, 150f};
        Table table = new Table(pointColumnWidths);

        //Tax detail
        table.addCell(new Cell().add("Total before tax:").setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(totalBeforeTax).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add("GST/HST").setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(gst).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add("PST/RST/QST").setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(pst).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add("Credit Deducted(NBAI)").setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(usdDeducted).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));

        //Grand Total
        table.addCell(new Cell().add("Grand Total:").setTextAlignment(TextAlignment.LEFT).setBorder(Border.NO_BORDER).setBorderTop(new SolidBorder(Color.BLACK, 1)));
        table.addCell(new Cell().add(grandTotal).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).setBorderTop(new SolidBorder(Color.BLACK, 1)));

        return table;
    }


}
