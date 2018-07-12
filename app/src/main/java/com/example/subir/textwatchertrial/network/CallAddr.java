package com.example.subir.textwatchertrial.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.subir.textwatchertrial.utils.CommonUtilities;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import okio.Buffer;
import java.util.concurrent.TimeUnit;


public class CallAddr extends AsyncTask<String, Void, String> {

    Context context;
    String result = "";
    FormEncodingBuilder formBody;
    String url;
    OnWebServiceResult resultListener;
    CommonUtilities.SERVICE_TYPE Servicetype;
    Request request;


    public Request getRequest() {
        return request;
    }


    public CallAddr(Context context, String url, FormEncodingBuilder formBody, CommonUtilities.SERVICE_TYPE Servicetype, OnWebServiceResult resultListener) {
        this.context = context;
        this.formBody = formBody;
        this.url = url;
        this.resultListener = resultListener;
        this.Servicetype = Servicetype;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        OkHttpClient client =new OkHttpClient();
        client.setConnectTimeout(120, TimeUnit.SECONDS); // connect timeout
        client.setReadTimeout(120, TimeUnit.SECONDS); // socket timeout
        //formBody.add("platform", "android");

        RequestBody body = formBody.build();
        Request request = new Request.Builder()
                .url(url)
                //.post(body)
                .build();
        Log.e("CallAddr " + Servicetype, "url= " + url + " params= " + bodyToString(request));
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                result = response.toString();
                if (result.equals("") || result.equals("null") || result.length() == 0) {
                    // CommonUtilities.showToast(context, "Something went wrong. Try later.");
                }
            } else {

            }
            result = response.body().string();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.e("CallAddr", "service_type= " + Servicetype + " result= " + s);
        resultListener.getWebResponse(s, Servicetype);
    }

    private static String bodyToString(final Request request) {

        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (Exception e) {
            return "did not work";
        }
    }
}

/**
 * Created by AdityaDua on 12/07/17.


public class CallAddr extends AsyncTask<String,Void,String> {
    Context context;
    String result="";
    FormEncodingBuilder formBody;
    String url;
    OnWebServiceResult resultlistener;
    CommonUtilities.SERVICE_TYPE serviceType;
    Request request;

    public Request getRequest(){
        return  request;

    }

    public CallAddr(Context context,String url,FormEncodingBuilder formBody,
                    CommonUtilities.SERVICE_TYPE serviceType,OnWebServiceResult resultlistener) {
        this.context = context;
        //this.result = result;
        this.formBody = formBody;
        this.url = url;
        this.resultlistener = resultlistener;
        this.serviceType = serviceType;
        //this.request = request;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(120, TimeUnit.SECONDS);
        client.setReadTimeout(120,TimeUnit.SECONDS);

        RequestBody body = formBody.build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        Log.i("Request Sent","URL :: "+url+body.toString());

        try {
            Response response = client.newCall(request).execute();
            if(!response.isSuccessful()){
                result = response.toString();
                if(result.equals("")||result.equals("null")||result.length()==0){
                    Toast.makeText(context,"Error while Processing",Toast.LENGTH_LONG).show();
                    // Error OCCURED
                }
            }
            result=response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Toast.makeText(context,"fetching successful",Toast.LENGTH_LONG).show();
    }
}
 */
