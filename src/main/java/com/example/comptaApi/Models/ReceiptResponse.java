package com.example.comptaApi.Models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceiptResponse {
    private String file_name;
    private List<Receipt> receipts;

}

/*classe qui représente une facture*/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Receipt {
    private String merchant_name;
    private String merchant_address;
    private String merchant_phone;
    private String merchant_website;
    private String merchant_tax_reg_no;
    private String receipt_no;
    private String date;
    private String time;
    private List<Item> items;
    private String currency;
    private double total;
    private Double subtotal;
    private Double tax;


}



/*
un item correspond à une ligne d'une facture*/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class Item {
    private double amount;
    private String category;
    private String description;
    private String flags;
    private Integer qty;
    private String remarks;
    private String tags;
    private Double unitPrice;
}
