package com.example.belkotsaharakibank;

import com.example.belkotsaharakibank.Entity.CheckingAccount;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class PdfGeneratorUtil {
    public static  byte[] generateCheckingAccountReport(List<CheckingAccount> activities) throws DocumentException{
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        for (CheckingAccount activity : activities){
            document.add(new Paragraph(activity.getFirstName() + " " +
                    activity.getFirstName() + " " +
                    activity.getLastName() + " " +
                    activity.getDate() + " " +
                    activity.getDeposit() + " " +
                    activity.getWithdrawal()));
        }
        document.close();
        return out.toByteArray();
    }
}
