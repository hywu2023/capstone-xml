package kr.co.company.medicine;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//    CalendarView calendarView;
    RecyclerView rv_list;
    ListItemRecyclerViewAdapter listItemRecyclerViewAdapter;
    ArrayList<ListItem> selectingList = new ArrayList<>();
    ArrayList<PillList> pillList = new ArrayList<>();
    MyDBHelper myHelper;
    BackPressCloseHandler backPressCloseHandler;
    LinearLayout calendar, self, map, mypage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        calendarView = findViewById(R.id.calendarView);

        backPressCloseHandler = new BackPressCloseHandler(this);
//        LoginActivity LA = (LoginActivity) LoginActivity.LogActivity;
//        LA.finish();

        // XML -- 0902 S

        FloatingActionButton floatbtn = findViewById(R.id.floatbtn);


        calendar = findViewById(R.id.im_calendar);
        self = findViewById(R.id.im_assignment);
        map = findViewById(R.id.im_location);
        mypage = findViewById(R.id.im_person);


        self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecomMainActivity.class);
                startActivity(intent);
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
            }
        });

        mypage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MypageActivity.class);
                startActivity(intent);
            }
        });

        // XML -- 0902 E


        // MapActivity -- 0902 S

        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("키해시는 :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        int permission2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission2 == PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(MainActivity.this,"이미 권한이 부여되어 있음.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "권한을 설정해야함.", Toast.LENGTH_LONG).show();
            String[] need_perm = new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION};
            ActivityCompat.requestPermissions(MainActivity.this, need_perm, 777);

        }

        // MapActivity -- 0902 E

        floatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MedSearch.class);
                startActivity(intent);
            }
        }); //임시 코드


        myHelper = new MyDBHelper(this);
        selectingList.addAll(myHelper.allListItems());
        rv_list = (RecyclerView) findViewById(R.id.rv_list);
        listItemRecyclerViewAdapter = new ListItemRecyclerViewAdapter(selectingList, this);
        rv_list.setAdapter(listItemRecyclerViewAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_list.setLayoutManager(layoutManager);


        // 0902 S

        pillList.addAll(myHelper.allPListItems());


        // 0902 E


//        // 1010 캘린더 날짜 고정 코드
//        View empty = findViewById(R.id.empty);
//        empty.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });
    }

//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
//                String  Year = String.valueOf(year);
//                String  Month = String.valueOf(month);
//                String  curDate = String.valueOf(dayOfMonth);
//
//
//                String Selected = Year + "-" + Month + "-" + curDate;
//            }
//        });

    // MapActivity -- 0902 S
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 777) {
            if(grantResults.length > 0){
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(MainActivity.this,"사용자가 권한을 승인함.", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"사용자가 권한을 거부함.", Toast.LENGTH_LONG).show();
                }
            }
            else{
                Toast.makeText(MainActivity.this,"권한설정 여부에 대한 결과정보가 존재하지 않음.", Toast.LENGTH_LONG).show();
            }

        }
    }
    // MapActivity -- 0902 E

    // backPressController
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

}