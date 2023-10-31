package kr.co.company.medicine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MypageActivity extends AppCompatActivity {


    TextView drugIng, drugEnd, pillInfo, setting;
    LinearLayout calendar, self, map, mypage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        drugEnd =  findViewById(R.id.drugEnd);
        pillInfo =  findViewById(R.id.pillInfo);
        setting =  findViewById(R.id.setting);

        FirebaseAuth fa = FirebaseAuth.getInstance();
        FirebaseUser fu = fa.getCurrentUser();
        String currEmail = fu.getEmail();

        TextView tv6 = findViewById(R.id.textView6);
        tv6.setText(currEmail);

        calendar = findViewById(R.id.im_calendar);
        self = findViewById(R.id.im_assignment);
        map = findViewById(R.id.im_location);
        mypage = findViewById(R.id.im_person);


        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        self.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecomMainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class);
                startActivity(intent);
                finish();
            }
        });

        drugEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EndedActivity.class);
                startActivity(intent);
            }
        });

        pillInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PillinfoActivity.class);
                startActivity(intent);
            }
        });


        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                startActivity(intent);
            }
        });

    }

    public void onBackPressed() {
        finish();
    }
}
