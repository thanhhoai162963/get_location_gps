package com.example.send_gps;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {
    TextView mtxtlocal,mtxtAddress;

    protected double vido;
    protected double kinhdo;
    protected boolean gps_on, gps_off;
    protected LocationManager mlocationManager;
    protected LocationListener mlocationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtxtlocal = findViewById(R.id.textLocation);
        mtxtAddress = findViewById(R.id.txtAdress);
        mlocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);// lấy dịch vụ vị trí

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mlocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                0, 0, this);//mình đi đến đâu lấy gps đến đó, mintime thời gian giữa 2lan update,
        // mình dịch sang tiếng việt hiểu à :))
    }

    @Override
    public void onLocationChanged(Location location) {
        Geocoder geocoder;//mã hóa lớp chuyển đổi kinh do vi do thanh dia chi
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());//tạo 1 lớp geo có vị trí mà hệ thống set rồi.
        vido= location.getLatitude();
        kinhdo =location.getLongitude();
        try {
            addresses = geocoder.getFromLocation(vido,kinhdo,1);//cho vo mảng
            if(addresses != null && addresses.size()>0)
            {
                String address= addresses.get(0).getAddressLine(0);
                String city=addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                mtxtAddress.setText(address+"\n"+city+"\n"+state+"\n"+country);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        mtxtlocal.setText("Latitude:" + vido+ "\n"+"Longtitude:"+ kinhdo);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
