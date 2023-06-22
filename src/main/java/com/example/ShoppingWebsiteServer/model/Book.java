package com.example.ShoppingWebsiteServer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Book {
  private Integer id;
  private String title;
  private String author;
  private String description;

  //    @JsonProperty(value = "usd_price")
  private Double usdPrice;
  private Integer amount;

  public Book(
      Integer id,
      String title,
      String author,
      String description,
      Double usdPrice,
      Integer amount) {
    this.id = id;
    this.title = title;
    this.author = author;
    this.description = description;
    this.usdPrice = usdPrice;
    this.amount = amount;
  }
}
