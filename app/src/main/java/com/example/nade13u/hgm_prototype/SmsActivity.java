package com.example.nade13u.hgm_prototype;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SmsActivity extends AppCompatActivity implements View.OnClickListener {

    private static SmsActivity inst;
    ArrayList<String> smsMessageList = new ArrayList<String>();
    ListView smsListView;
    ArrayAdapter arrayAdapter;
    ArrayList<Sms> tab_msg = new ArrayList<Sms>();
    Button btn_envoyer;
    EditText edt_message;
    String numero;
    RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;
    RelativeLayout pnl_list;
    RelativeLayout pnl_sms;


    public static SmsActivity instance()
    {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        smsListView = (ListView) findViewById(R.id.liste_sms);
        btn_envoyer = (Button)findViewById(R.id.btn_envoyer);
        edt_message = (EditText)findViewById(R.id.edt_message);
        pnl_list = (RelativeLayout)findViewById(R.id.pnl_list);
        pnl_sms = (RelativeLayout)findViewById(R.id.pnl_sms);
        rb1 = (RadioButton)findViewById(R.id.p1);
        rb2 = (RadioButton)findViewById(R.id.p2);
        rb3 = (RadioButton)findViewById(R.id.p3);

        pnl_list.getLayoutParams().height = getWindowManager().getDefaultDisplay().getHeight() - 2*pnl_sms.getLayoutParams().height ;

        btn_envoyer.setOnClickListener(this);
        numero = getIntent().getStringExtra("numero");
        tab_msg = getSMSDetails(numero);
        CustomerAdapter customerAdapter = new CustomerAdapter();

        smsListView.setAdapter(customerAdapter);
        smsListView.setSelection(customerAdapter.getCount()-1);

    }

    public void updateList(final String smsMessage) {
        arrayAdapter.insert(smsMessage, 0);
        arrayAdapter.notifyDataSetChanged();
    }

    //Requête pour obtenir la liste des sms d'un numero
    private ArrayList<Sms> getSMSDetails(String numero)
    {
        StringBuffer stringBuffer = new StringBuffer();
        Uri uri = Uri.parse("content://sms");
        ArrayList<Sms> msg_recup = new ArrayList<Sms>();
        String[] arg = {"%"+numero};


        Cursor cursor = getContentResolver().query(uri, null, "address LIKE ?",arg, "date ASC");

        if(cursor.moveToFirst())
        {
            do
            {
                String body = cursor.getString(cursor.getColumnIndexOrThrow("body")).toString();
                String type = cursor.getString(cursor.getColumnIndex("type")).toString();
                String date = cursor.getString(cursor.getColumnIndex("date")).toString();

                String SMStype = null;

                switch (Integer.parseInt(type))
                {
                    case 1 : SMStype = "reçu"; break;
                    case 2 : SMStype = "envoyé"; break;
                    case 3 : SMStype = "brouillon"; break;
                }

                Sms sms = new Sms(body, date, SMStype);

                msg_recup.add(sms);

            }while(cursor.moveToNext());

        }
        cursor.close();

        return msg_recup;
    }

    @Override
    public void onClick(View v)
    {
        if(v.getId() == btn_envoyer.getId())
        {
            sendSMS(numero);
        }
    }

    class CustomerAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return tab_msg.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(tab_msg.get(position).getType().equals("reçu"))
            {
                convertView = getLayoutInflater().inflate(R.layout.item_conversation,null);
                TextView text_msg = (TextView)convertView.findViewById(R.id.text_recu);

                text_msg.setText(tab_msg.get(position).getBody());
            }

            else
            {
                convertView = getLayoutInflater().inflate(R.layout.item_conversation_envoi,null);
                TextView text_msg = (TextView)convertView.findViewById(R.id.text_envoi);

                text_msg.setText(tab_msg.get(position).getBody());
            }

            return convertView;
        }
    }

    public  void sendSMS(String numero)
    {
        String message = edt_message.getText().toString();

        try
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(numero,null,message,null,null);
            Toast.makeText(getApplicationContext(),"SMS envoyé",Toast.LENGTH_LONG).show();
            edt_message.setText(null);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Echec de l'envoie", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void onRadioButtonClicked(View view)
    {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.p1:
                if (checked)
                    edt_message.setText(rb1.getText().toString());
                    break;
            case R.id.p2:
                if (checked)
                    edt_message.setText(rb2.getText().toString());
                    break;
            case R.id.p3:
                if (checked)
                    edt_message.setText(rb3.getText().toString());
                    break;
        }
    }
}
