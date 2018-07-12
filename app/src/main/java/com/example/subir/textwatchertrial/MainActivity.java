package com.example.subir.textwatchertrial;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.subir.textwatchertrial.network.CallAddr;
import com.example.subir.textwatchertrial.network.NetworkStatus;
import com.example.subir.textwatchertrial.network.OnWebServiceResult;
import com.example.subir.textwatchertrial.utils.CommonUtilities;
import com.squareup.okhttp.FormEncodingBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements OnWebServiceResult, AdapterView.OnItemClickListener {

    TextView textView;
    AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        autoCompleteTextView.addTextChangedListener(watch);

        textView = findViewById(R.id.textView);
    }

    @Override
    public void getWebResponse(String result, CommonUtilities.SERVICE_TYPE type) {

        List<String> stations = new ArrayList<>();
        try{
            JSONObject obj= new JSONObject(result);
            JSONArray arr=obj.getJSONArray("stations");
            for(int i=0;i<arr.length();i++){
                JSONObject jsonObject=arr.getJSONObject(i);
                String station_name = jsonObject.getString("name");
                stations.add(station_name);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, stations);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        autoCompleteTextView.setAdapter(dataAdapter);
        autoCompleteTextView.setThreshold(1);

        autoCompleteTextView.setOnItemClickListener(this);
    }

    public void hitrequest(CharSequence s) {
        FormEncodingBuilder parameters = new FormEncodingBuilder();
        parameters.add("page", "1");

        if (NetworkStatus.getInstance(this).isConnectedToInternet()) {
            String urlpt1 = "https://api.railwayapi.com/v2/suggest-station/name/";
            String urlpt2 = "/apikey/ipg7l3kcnd/";
            String url = urlpt1+s+urlpt2;
            CallAddr call = new CallAddr(this, url, parameters, CommonUtilities.SERVICE_TYPE.GET_DATA, this);
            call.execute();
        } else {
            Toast.makeText(this, "No Network ! You are offline.", Toast.LENGTH_LONG).show();
        }
    }

    TextWatcher watch = new TextWatcher() {

        @Override
        public void afterTextChanged(Editable arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int a, int b, int c) {
            // TODO Auto-generated method stub
            hitrequest(s);
            //output.setText(s);
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        textView.setText(item);
    }
}
