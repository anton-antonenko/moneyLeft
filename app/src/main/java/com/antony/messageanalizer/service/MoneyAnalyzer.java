package com.antony.messageanalizer.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.antony.messageanalizer.R;
import com.antony.messageanalizer.bom.SMS;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MoneyAnalyzer {

   public static final String DOSTUPNYI_ZALYSHOK = "zalyshok";

   public static final String POPOVNENNYA = "Popovnennya rakhunku";

   public static final String UAH = "UAH";

   public static final String BANK_NUMBER = "729";

   public static final String DATE_OF_SALARY_KEY = "DATE_OF_SALARY_KEY";

   private List<SMS> smsList;

   private int dayOfSalary = 10;

   private Date lastSalaryDate;

   private Context context;

   public int getTotalAmount() {
      SMS sms = getLastSMSWithAmountOrNull();
      if (sms != null) {
         String amount = sms.getBody().substring(sms.getBody().lastIndexOf(DOSTUPNYI_ZALYSHOK) +
                                                         DOSTUPNYI_ZALYSHOK.length(),
                                                 sms.getBody().lastIndexOf(UAH));
         return Double.valueOf(amount).intValue();
      }
      return 0;
   }

   public int getPerDayAmount() {
      if (getDaysToSalary() != 0) {
         return getTotalAmount() / getDaysToSalary();
      }
      return getTotalAmount();
   }

   public int getDaysToSalary() {
      Calendar curdate = Calendar.getInstance();
      Calendar dateOfSalary = Calendar.getInstance();
      if (curdate.get(Calendar.DAY_OF_MONTH) < getLastDayOfSalary()) {
         return getLastDayOfSalary() - curdate.get(Calendar.DAY_OF_MONTH);
      }
      dateOfSalary.set(Calendar.MONTH, dateOfSalary.get(Calendar.MONTH) + 1);
      dateOfSalary.set(Calendar.DAY_OF_MONTH, getLastDayOfSalary());
      Date date1 = curdate.getTime();
      Date date2 = dateOfSalary.getTime();
      long diff = date2.getTime() - date1.getTime();
      return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
   }

   private SMS getLastSMSWithAmountOrNull() {
      for (SMS sms : getSmsList()) {
         if (sms.getNumber().equals(BANK_NUMBER) && sms.getBody().contains(DOSTUPNYI_ZALYSHOK)) {
            return sms;
         }
      }
      return null;
   }

   private SMS getLastSMSWithRefillOrNull() {
      for (SMS sms : getSmsList()) {
         if (sms.getNumber().equals(BANK_NUMBER) && sms.getBody().contains(POPOVNENNYA) && !sms.getBody().contains("70.00")) {
            return sms;
         }
      }
      return null;
   }

   private int getLastDayOfSalary() {
      if (getLastSalaryDate() == null) {
         setLastSalaryDate(getLastDateOfSalaryOrNull());
      }
      if (getLastSalaryDate() != null) {
         Calendar dateOfSalary = Calendar.getInstance();
         dateOfSalary.setTime(getLastSalaryDate());
         return dateOfSalary.get(Calendar.DAY_OF_MONTH);
      }
      return getDayOfSalary();
   }

   private Date getLastDateOfSalaryOrNull() {
      updateLastDateOfSalary();
      long dateOfSalary = getPreferences().getLong(DATE_OF_SALARY_KEY, 0);
      return dateOfSalary != 0 ? new Date(dateOfSalary) : null;
   }

   private void updateLastDateOfSalary(){
      SMS sms = getLastSMSWithRefillOrNull();
      if (sms != null) {
         long dateOfSalary = getPreferences().getLong(DATE_OF_SALARY_KEY, 0);
         if (sms.getDate().getTime() > dateOfSalary) {
            SharedPreferences.Editor editor = getPreferences().edit();
            editor.putLong(DATE_OF_SALARY_KEY, sms.getDate().getTime());
            editor.apply();
         }
      }
   }

   private List<SMS> getSmsList() {
      return smsList;
   }

   public void setSmsList(List<SMS> smsList) {
      this.smsList = smsList;
   }

   private int getDayOfSalary() {
      return dayOfSalary;
   }

   private SharedPreferences getPreferences() {
      return getContext().getSharedPreferences(getContext().getString(R.string.preferenceFileKey), Context.MODE_PRIVATE);
   }

   public Context getContext() {
      return context;
   }

   public void setContext(Context context) {
      this.context = context;
   }

   private Date getLastSalaryDate() {
      return lastSalaryDate;
   }

   private void setLastSalaryDate(Date lastSalaryDate) {
      this.lastSalaryDate = lastSalaryDate;
   }
}
