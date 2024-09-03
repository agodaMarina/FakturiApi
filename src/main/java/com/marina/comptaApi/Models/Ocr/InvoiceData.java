package com.marina.comptaApi.Models.Ocr;

import lombok.Data;

import java.util.List;

@Data
public class InvoiceData {
    private String Supplier;
    private String Date;
    private String InvoiceNumber;
    private String total;
    private List<String> items;
    private String tax;
    private String TotalWithTax;
}
