package com.example.practica22octubre2021.Library.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class Utilerias {

    public static boolean checkInternetConnection(Context context) {
        // get Connectivity Manager object to check connection
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        // Check for network connections
        if (isConnected) {
            return true;
        } else {
            Toast.makeText(context, "Sin Conexión a Internet", Toast.LENGTH_LONG).show();
            return false;
        }

    }

}
