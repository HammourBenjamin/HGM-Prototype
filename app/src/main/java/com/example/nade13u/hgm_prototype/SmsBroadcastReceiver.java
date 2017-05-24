package com.example.nade13u.hgm_prototype;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by nade13u on 10/05/2017.
 */

public class SmsBroadcastReceiver extends BroadcastReceiver {

    private static final String SMS_BUNDLE = "pdus";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();

        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";

            for (int i = 0; i < sms.length; i++) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                String smsBody = smsMessage.getMessageBody().toString();
                String address = smsMessage.getOriginatingAddress();

                smsMessageStr += "SMS de: " + address + "\n";
                smsMessageStr += smsBody + "\n";
            }

            Toast.makeText(context, smsMessageStr, Toast.LENGTH_SHORT).show();

            SmsActivity inst = SmsActivity.instance();
            inst.updateList(smsMessageStr);
        }
    }
}
