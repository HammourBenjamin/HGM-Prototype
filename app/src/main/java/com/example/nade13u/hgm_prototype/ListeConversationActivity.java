package com.example.nade13u.hgm_prototype;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListeConversationActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listConversation;
    ArrayList<String> listeNumContactes;
    ArrayList<Sms> listeLastSms;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.e("onCreate","Debut ok");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_conversation);
        listConversation = (ListView)findViewById(R.id.list_conversation);

        listeNumContactes = new ArrayList<String>();
        listeNumContactes.add("650167644");
        listeNumContactes.add("606550909");
        listeNumContactes.add("668090111");

        listeLastSms = new ArrayList<Sms>();
        for(int i=0;i<listeNumContactes.size();i++)
        {
            listeLastSms.add(getLastSMS(listeNumContactes.get(i)));
        }

        CustomerAdapter customerAdapter = new CustomerAdapter();

        listConversation.setAdapter(customerAdapter);
        listConversation.setSelection(customerAdapter.getCount()-1);
        listConversation.setOnItemClickListener(this);

        Log.e("onCreate","Fin ok");
    }

    @Override
    protected void onStart()
    {
        Log.e("onStart","Debut ok");

        super.onStart();

        Log.e("onStart","Fin ok");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        String numero = listeNumContactes.get(position).toString();
        Intent intentSmsActivity = new Intent(this,SmsActivity.class);
        intentSmsActivity.putExtra("numero",numero);
        startActivity(intentSmsActivity);
    }

    private Sms getLastSMS(String numero)
    {
        Uri uri = Uri.parse("content://sms");
        Sms msg_recup = null;
        String[] arg = {"%"+numero};


        Cursor cursor = getContentResolver().query(uri, null, "address LIKE ?",arg, "date DESC");

        if(cursor.moveToFirst())
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

                msg_recup = new Sms(body, date, SMStype);
        }
        cursor.close();

        return msg_recup;
    }

    class CustomerAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return listeNumContactes.size();
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

                convertView = getLayoutInflater().inflate(R.layout.item_liste_conversation,null);
                TextView num = (TextView)convertView.findViewById(R.id.lbl_numero);
                TextView sms = (TextView)convertView.findViewById(R.id.lbl_lastMessage);

                num.setText(listeNumContactes.get(position));
                sms.setText(listeLastSms.get(position).getBody());

            return convertView;
        }
    }

}
