package kr.co.company.medicine;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArraySet;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public CalendarView calendarView;
    public TextView diaryTextView;
    long counts;
    RecyclerView Med_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button Button_add = findViewById(R.id.button);
        calendarView = findViewById(R.id.calendarView);
        RecyclerView recyclerView = findViewById(R.id.Med_list);
        ArrayList<String> MedNameSet = new ArrayList<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        //임시 코드
        Button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MedSearch.class);
                startActivity(intent);
            }
        }); //임시 코드

        db.collection("test").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() { // db 불러오기
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //Log.d("ssss", document.getId() + " => " + document.getData());
//                                Query test = db.collection("test"); //테스트에 등록된 데이터 갯수
//                                AggregateQuery countQuery = test.count();//테스트에 등록된 데이터 갯수
                        for (int i = 0; i<3; i++) {
                            MedNameSet.add(document.getData().get("allabel").toString()); //약 이름 작성
                        }
                    }
                } else {
                    Log.d("ssss", "Error getting documents.", task.getException());
                }
            }
        });


        //===== 테스트를 위한 더미 데이터 생성 ===================
        ArrayList<String> testDataSet = new ArrayList<>();
        for (int i = 0; i<20; i++) {
            testDataSet.add("TEST DATA" + i); //약 이름 작성
        }
        //========================================================
        //--- LayoutManager는 아래 3가지중 하나를 선택하여 사용 ---
        // 1) LinearLayoutManager()
        // 2) GridLayoutManager()
        // 3) StaggeredGridLayoutManager()
        //---------------------------------------------------------
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((Context) this);
        recyclerView.setLayoutManager(linearLayoutManager);  // LayoutManager 설정

        CustomAdapter customAdapter = new CustomAdapter(testDataSet);
        recyclerView.setAdapter(customAdapter); // 어댑터 설정


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() // 캘린더 날짜 누를 시
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {

                db.collection("alarm")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        //Log.d("ssss", document.getId() + " => " + document.getData());
//                                        medView1.setText(document.getData().get("allabel").toString());
                                    }
                                } else {
                                    Log.d("ssss", "Error getting documents.", task.getException());
                                }
                            }
                        });


            }
        });




//        public void countAggregateCollection() { // 데이터 갯수 세는 코드
//            // [START count_aggregate_collection]
//            Query query = db.collection("test");
//            AggregateQuery countQuery = query.count();
//            countQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
//                @Override
//                public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
//                    if (task.isSuccessful()) {
//                        // Count fetched successfully
//                        AggregateQuerySnapshot snapshot = task.getResult();
////                        Log.d(TAG, "Count: " + snapshot.getCount());
//                    } else {
////                        Log.d(TAG, "Count failed: ", task.getException());
//                    }
//                }
//            });
//            // [END count_aggregate_collection]
//        }






    }
}