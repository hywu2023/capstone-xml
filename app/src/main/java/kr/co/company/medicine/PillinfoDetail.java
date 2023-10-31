package kr.co.company.medicine;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PillinfoDetail extends AppCompatActivity {

    TextView textView;
    TextView detailStr;

    String drugString;
    String str_detailStr;


    String sort = null; // form, name 중 어느 어댑터에서 넘어온 건지 구분하기 위함


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pillinfo_detail);

        textView = findViewById(R.id.textView);
        detailStr = findViewById(R.id.detailStr);


        //Drug라는 key값으로 NameMyAdapter에서 intent해줄때 넘겨준 값을 가져옴.
        drugString = getIntent().getStringExtra("Drug");//String값으로 받아옴. 이것은 약의 이름을 받아오는것.
        str_detailStr = getIntent().getStringExtra("data");
        //NameMyAdapter.java파일에서 intent로 넘겨준 image를 받아와 byte배열에 저장 후 decode하여 imageview에 보여줌.
        sort = getIntent().getStringExtra("sort");
        Log.e("form/sort??",sort);


        // 이미지 넘겨주는 형식이 다르게 때문에 bitmap, string 구분하기 위해 if문 사용
        if(sort.equals("name")){

            //textView와 imageView에 받아온 값들을각각 저장해줌.
            textView.setText(drugString);
            detailStr.setText(str_detailStr);

        }

        else if(sort.equals("form")){
            //textView와 imageView에 받아온 값들을각각 저장해줌.
            textView.setText(drugString);
            detailStr.setText(str_detailStr);
        }



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}