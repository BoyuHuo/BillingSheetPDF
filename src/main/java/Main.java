import entity.Address;
import entity.InvoiceInfo;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        BillingPDFServiceImp billingPDFService = new BillingPDFServiceImp();
        //mock billData
        Map<String,String> billData = new HashMap<>();
        billData.put("Machine Learning","17.90");
        billData.put("Notebook","1.20");
        billData.put("Cygnus","0.01");

        billingPDFService.companyAddress = new Address("Nebula AI Inc.","666 Rue Sherbrooke Ouest","Montreal, Quebec","H3A 0B2","Canada");
        billingPDFService.billingAddress = new Address("John Doe","10 Street Name","Montreal, Quebec","1B3 0B2","Canada");
        billingPDFService.invoiceInfo = new InvoiceInfo("462083","DD Month, 2019","Due immediately","40121321541343215421");
        billingPDFService.accountNumber = "1234567891456789";
        billingPDFService.billingPeriod = "June 1, 2019 - June 30, 2019";
        billingPDFService.billData = billData;

        billingPDFService.totalBeforeTax = "19.24";
        billingPDFService.gst="0.00";
        billingPDFService.pst="0.00";
        billingPDFService.usdDeducted = "-4.00";
        billingPDFService.grandTotal = "15.24";

        try {

            billingPDFService.generatePdfBill();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
