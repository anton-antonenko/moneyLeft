package com.antony.messageanalizer.bom;

import java.util.Date;

/**
 * Created by Antony on 19.10.2014.
 */
public class SMS {

   private String number;

   private String body;

   private Date date;

   public String getNumber() {
      return number;
   }

   public void setNumber(String number) {
      this.number = number;
   }

   public String getBody() {
      return body;
   }

   public void setBody(String body) {
      this.body = body;
   }

   public Date getDate() {
      return date;
   }

   public void setDate(Date date) {
      this.date = date;
   }
}
