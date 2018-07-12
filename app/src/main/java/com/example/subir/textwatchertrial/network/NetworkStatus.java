package com.example.subir.textwatchertrial.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by AdityaDua on 12/07/17.
 */

public class NetworkStatus {

    private static  NetworkStatus instance=new NetworkStatus();
    static Context context;
    ConnectivityManager connectivityManager;
    boolean connected=false;

    public static NetworkStatus getInstance(Context ctx){
        context=ctx;
        return instance;
    }

    public boolean isOnline(Context con){
        try{
            connectivityManager =(ConnectivityManager)con.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            connected= networkInfo!=null && networkInfo.isAvailable() && networkInfo.isConnected();
            return connected;
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(con,"Check Network Connection its unavailable"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
        return  connected;
    }

    public boolean isConnectedToInternet(){
        connectivityManager =(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo =connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null){
            return true;
        }else {return false;}
    }
}
