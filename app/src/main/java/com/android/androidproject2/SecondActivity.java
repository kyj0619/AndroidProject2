package com.android.androidproject2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SecondActivity extends AppCompatActivity implements OnMapReadyCallback {
    String title2;
    int etime;
    private FusedLocationProviderClient mFusedLocationClient;
    GoogleMap mGoogleMap = null;
    private DBHelper mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        title2 = intent.getStringExtra("title");
        etime = intent.getIntExtra("time",-1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mDbHelper = new DBHelper(this);
        EditText edittitle = findViewById(R.id.today);
        edittitle.setText(title2);


        TimePicker settimePicker = (TimePicker) findViewById(R.id.timepicker1);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            settimePicker.setHour(etime);
            settimePicker.setMinute(0);
        } else {
            settimePicker.setCurrentHour(etime);
            settimePicker.setCurrentMinute(0);
        }

        TimePicker settimePicker2 = (TimePicker) findViewById(R.id.timepicker2);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            settimePicker2.setHour(etime);
            settimePicker2.setMinute(10);
        } else {
            settimePicker2.setCurrentHour(etime);
            settimePicker2.setCurrentMinute(10);
        }


        Button endbtn = findViewById(R.id.endbtn);
        endbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Button search = findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAddress();
            }
        });
        Button savebtn = findViewById(R.id.save);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertRecord();
                viewAllToTextView();
            }
        });

        Button delbtn = findViewById(R.id.delete);
        delbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteRecord();
                viewAllToTextView();
            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
    }

    private void getAddress() {
        EditText addressTextView = (EditText) findViewById(R.id.location);
        String input = addressTextView.getText().toString();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.KOREA);
            List<Address> addresses = geocoder.getFromLocationName(input,1);
            if (addresses.size() >0) {
                Address bestResult = (Address) addresses.get(0);
                LatLng location2 = new LatLng(bestResult.getLatitude(),bestResult.getLongitude());
                    mGoogleMap.addMarker(
                            new MarkerOptions().
                                    position(location2).
                                    title("위치")
                    );
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location2,15));

            }
        } catch (IOException e) {
            Log.e(getClass().toString(),"Failed in using Geocoder.", e);
            return;
        }
    }

    final int REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION= 0;
    Location mLastLocation;

    private void getLastLocation() {
        // 1. 위치 접근에 필요한 권한 검사 및 요청
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    SecondActivity.this,            // MainActivity 액티비티의 객체 인스턴스를 나타냄
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},        // 요청할 권한 목록을 설정한 String 배열
                    REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION    // 사용자 정의 int 상수. 권한 요청 결과를 받을 때
            );
            return;
        }

        // 2. Task<Location> 객체 반환
        Task task = mFusedLocationClient.getLastLocation();

        // 3. Task가 성공적으로 완료 후 호출되는 OnSuccessListener 등록
        task.addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(SecondActivity.this);
                // 4. 마지막으로 알려진 위치(location 객체)를 얻음.
                if (location != null) {
                    mLastLocation = location;
                    //updateUI();
                } else
                    Toast.makeText(getApplicationContext(),
                            "No location detected",
                            Toast.LENGTH_SHORT)
                            .show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        LatLng location = new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude());
        googleMap.addMarker(
                new MarkerOptions().
                        position(location).
                        title("한성대학교"));

        // move the camera
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
    }

    //제거 버튼 확인용(작업 중 제거 가능)
    private void deleteRecord() {
        EditText _id = (EditText)findViewById(R.id.testid);
        mDbHelper.deleteUserBySQL(_id.getText().toString());
    }

    private void insertRecord() {
        EditText title = (EditText)findViewById(R.id.today);
        EditText loc = (EditText)findViewById(R.id.location);
        EditText memo = (EditText)findViewById(R.id.memo);
        TimePicker timePicker = (TimePicker) findViewById(R.id.timepicker1);
        TimePicker timePicker2 = (TimePicker) findViewById(R.id.timepicker2);
        String stime, etime;
        final int hour, minute, ehour, eminute;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        } else {
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            ehour = timePicker2.getHour();
            eminute = timePicker.getMinute();
        } else {
            ehour = timePicker2.getCurrentHour();
            eminute = timePicker.getCurrentMinute();
        }
        stime = String.valueOf(hour)+"시"+String.valueOf(minute)+"분";
        etime = String.valueOf(ehour)+"시"+String.valueOf(eminute)+"분";

        mDbHelper.insertUserBySQL(title.getText().toString(),stime,etime,loc.getText().toString(),memo.getText().toString());
    }
    //데이터 베이스 확인용 (작업 중 제거 가능)
    private void viewAllToTextView() {
        TextView result = (TextView)findViewById(R.id.viewid);

        Cursor cursor = mDbHelper.getAllUsersBySQL();

        StringBuffer buffer = new StringBuffer();
        while (cursor.moveToNext()) {
            buffer.append(cursor.getInt(0)+" \t");
            buffer.append(cursor.getString(1)+" \t");
            buffer.append(cursor.getString(2)+" \t");
            buffer.append(cursor.getString(3)+" \t");
            buffer.append(cursor.getString(4)+" \t");
            buffer.append(cursor.getString(5)+"\n");
        }
        result.setText(buffer);
    }
}