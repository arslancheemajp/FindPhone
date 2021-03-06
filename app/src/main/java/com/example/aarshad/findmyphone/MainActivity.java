package com.example.aarshad.findmyphone;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkLocationPermission();
        GlobalInfo globalInfo = new GlobalInfo(this);
        globalInfo.loadData();

    }

    public void startService(){
        // start location track

        if (!TrackLocation.isRunning){
            TrackLocation trackLocation = new TrackLocation();
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, trackLocation);
        }
    }

    void checkLocationPermission(){
        if ( Build.VERSION.SDK_INT >= 23){
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED  ){
                requestPermissions(new String[]{
                                android.Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION },
                        REQUEST_CODE_ASK_PERMISSIONS);
                return ;
            }
        }
        startService();

    }
    //get acces to location permsion
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startService();
                } else {
                    // Permission Denied
                    Toast.makeText( this,"Permission Denied" , Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}
