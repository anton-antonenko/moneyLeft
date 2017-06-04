package com.antony.messageanalizer.connector;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.antony.messageanalizer.bom.SMS;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antony on 19.10.2014.
 */
public class MessageReadConnector extends Activity {

   public static final String CONTENT_SMS_INBOX = "content://sms/inbox";

   public MessageReadConnector(Context context) {
      this.context = context;
   }

   Context context;

   public List<SMS> getAllSMS() {
      List<SMS> smsList = new ArrayList<SMS>();
      Uri uri = Uri.parse(CONTENT_SMS_INBOX);
      Cursor cursor = context.getContentResolver().query(uri, null, null ,null,null);
      startManagingCursor(cursor);
      if(cursor.moveToFirst()) {
         for(int i=0; i < cursor.getCount(); i++) {
            SMS sms = new SMS();
            sms.setBody(cursor.getString(cursor.getColumnIndexOrThrow("body")).toString());
            sms.setNumber(cursor.getString(cursor.getColumnIndexOrThrow("address")).toString());
            sms.setDate(new Date(cursor.getLong(cursor.getColumnIndexOrThrow("date"))));
            smsList.add(sms);
            cursor.moveToNext();
         }
      }
      if (cursor != null) {
         cursor.close();
      }
      return smsList;
   }
}
