package com.antony.messageanalizer.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antony.messageanalizer.R;
import com.antony.messageanalizer.connector.MessageReadConnector;
import com.antony.messageanalizer.service.MoneyAnalyzer;

public class PerDayAmountFragment extends Fragment {

   private MessageReadConnector smsService;

   private MoneyAnalyzer analyzer;

   public static final PerDayAmountFragment newInstance() {
      return new PerDayAmountFragment();
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.per_day_activity, container, false);
      setSmsService(new MessageReadConnector(container.getContext()));
      setAnalyzer(new MoneyAnalyzer());
      getAnalyzer().setSmsList(getSmsService().getAllSMS());
      getAnalyzer().setContext(getActivity());

      TextView totalAmount = (TextView) view.findViewById(R.id.perDayAmount);
      totalAmount.setText(String.valueOf(getAnalyzer().getPerDayAmount()));
      return view;
   }

   public MessageReadConnector getSmsService() {
      return smsService;
   }

   public void setSmsService(MessageReadConnector smsService) {
      this.smsService = smsService;
   }

   public MoneyAnalyzer getAnalyzer() {
      return analyzer;
   }

   public void setAnalyzer(MoneyAnalyzer analyzer) {
      this.analyzer = analyzer;
   }
}
