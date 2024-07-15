package com.marina.comptaApi.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
