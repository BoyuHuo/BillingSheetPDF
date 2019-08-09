package entity;

public class InvoiceInfo {
    private String invoiceNum;
    private String issueDate;
    private String paymentTerm;
    private String cardNum;

    public InvoiceInfo(){}
    public InvoiceInfo(String invoiceNum, String issueDate, String paymentTerm, String cardNum) {
        this.invoiceNum = invoiceNum;
        this.issueDate = issueDate;
        this.paymentTerm = paymentTerm;
        this.cardNum = cardNum;
    }

    public String getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(String invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(String paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }
}
