package kr.co.company.medicine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PillinfoActivity extends AppCompatActivity {
    EditText chSearch;
    Button chBtn, btn_camera;

    private String image; //사진 주소 값 저장
    private String drugName = null; //검색 결과

    private RecyclerView ch_recy;
    ArrayList<Pill> list;
    ArrayList<Pill> list2;
    PillinfoMyAdapter mAdapter;

    Uri imageUri;
    private List<String> list_ca;


    private LinearLayoutManager linearLayoutManager;

    private TextView chOut;//검색한 노인약이 있는지 확인하는

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pill_info_activity);
//        // 홈으로 이동
//        ImageButton btn_home = findViewById(R.id.gohome);
//
//
//
//
//        // 홈 버튼 클릭이벤트
//        btn_home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 버튼을 누르면 메인화면으로 이동
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                finish();
//            }
//        });

        ch_recy = (RecyclerView)findViewById(R.id.ch_recy);//리사이클러뷰 초기화
        ch_recy.setHasFixedSize(true);//리사이클러뷰 기존 성능 강화

        //리니어레이아웃을 사용하여 리사이클러뷰에 넣어줄것임
        linearLayoutManager = new LinearLayoutManager(this);
        ch_recy.setLayoutManager(linearLayoutManager);

        chSearch = (EditText)findViewById(R.id.chSearch);
        chOut = (TextView) findViewById(R.id.chOut);


        findViewById(R.id.chBtn).setOnClickListener(onClickListener);
        findViewById(R.id.btn_camera).setOnClickListener(onClickListener_camera);

    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            list = new ArrayList<>();

            drugName = chSearch.getText().toString();
            Log.e("drugName : ", drugName);

            if(drugName!=null) {
                childJson();
                mAdapter = new PillinfoMyAdapter(getApplicationContext(), list);
                ch_recy.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }

            if(list.size()!= 0) {
                chOut.setText(" ");
            } else {
                chOut.setText("검색 결과와 일치하는 약이 없습니다.");
            }

        }
    };

    View.OnClickListener onClickListener_camera = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ImagePicker.with(PillinfoActivity.this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start();

        }
    };
    // 카메라 이미지값 전달 코드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK){
            if(data!= null){
                Bitmap imagebitmap = null;
                imageUri = data.getData();
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
                        imagebitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(getContentResolver(),imageUri));
                    }else{
                        imagebitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                list2 = new ArrayList<>();
                Toast.makeText(this, "이미지가 선택되었습니다.", Toast.LENGTH_SHORT).show();
                pill_info_camera();

            }
        }
        else {
            Toast.makeText(this, "이미지가 선택되지 않았습니다.", Toast.LENGTH_SHORT).show();
        }
    }




    //json에서 조건에 맞는 것 검색(식별자) 3가지.
    public void childJson(){
        try{
            InputStream is = getAssets().open("druglist.json"); //assests파일에 저장된 druglist_final.json 파일 열기
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            //
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("drug"); //json파일에서 의약품리스트의 배열명, jsonArray로 저장



            for(int i=0; i<jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.getString("품목명").contains(drugName)) {
                    Pill pill = new Pill();
                    Log.e("drugName : ", drugName);
                    Log.e("1번째 : ", jsonObject.getString("품목명"));
                    pill.setPill_name(jsonObject.getString("품목명"));
                    pill.setPill_company(jsonObject.getString("업소명"));
                    pill.setPill_className(jsonObject.getString("분류명"));
                    pill.setPill_etcOtcName(jsonObject.getString("전문일반구분"));
                    image = jsonObject.getString("큰제품이미지");
                    pill.setPill_image(image);



                    list.add(pill);
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void pill_info_camera() {
        ArrayList<String> tx = new ArrayList<String>();
        List<String> resultTx = new ArrayList<String>();
        try{
            if (imageUri!=null){

                try {
                    InputImage inputImage = InputImage.fromFilePath(PillinfoActivity.this,imageUri);

//                TextRecognizer recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS);
                    TextRecognizer recognizer = TextRecognition.getClient(new KoreanTextRecognizerOptions.Builder().build());
                    Task<Text> result = recognizer.process(inputImage)
                            .addOnSuccessListener(new OnSuccessListener<Text>() {
                                @Override
                                public void onSuccess(Text text) {
                                    // 전체 페이지 출력 코드
//                                String recognizeText = text.getText();
//                                recgText.setText(recognizeText);
//                                ------------------------
                                    StringBuilder result = new StringBuilder();
//                                    List<String> tx = new ArrayList<>();
                                    for (Text.TextBlock block : text.getTextBlocks()) {
                                        String blockText = block.getText();
                                        for (Text.Line line : block.getLines()) {
                                            String TextBlock = line.getText();
                                            for (Text.Element element : line.getElements()) {
                                                String elementText = element.getText();
                                                String[] contas = {"캡술","캡슐","리그","정","동광","익셀", "캅셀","캡슬"};
                                                for (String conta : contas){
                                                    if (elementText.contains(conta)){
                                                        if (elementText.contains("정제")) continue;
                                                        else if (elementText.contains("코팅")) continue;
                                                        else if (elementText.contains("정씩")) continue;
                                                        else if (elementText.contains("1정")) continue;
                                                        else if (elementText.contains("일분")) continue;
                                                        else if (elementText.contains("경질")) continue;
                                                        else if (elementText.contains("결정")) continue;

                                                        result.append(elementText);
                                                        result.append("\n");
                                                        tx.add(elementText.length() > 6 ? elementText
                                                                .replace("캡술", "캡슐")
                                                                .replace("캡슬", "캡슐")
                                                                .replace("일리","밀리")
                                                                .substring(0, elementText.length()-2) : elementText);
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    List<String> resultTx = new ArrayList<String>();
                                    // 리스트 안 중복 값 제거
                                    resultTx = tx.stream().distinct().collect(Collectors.toList());
                                    System.out.println(resultTx);

                                    try {
                                        InputStream is = getAssets().open("druglist.json"); //assests파일에 저장된 druglist_final.json 파일 열기
                                        byte[] buffer = new byte[is.available()];
                                        is.read(buffer);
                                        is.close();
                                        String json = new String(buffer, "UTF-8");

                                        //
                                        JSONObject jsonObject = new JSONObject(json);
                                        JSONArray jsonArray = jsonObject.getJSONArray("drug"); //json파일에서 의약품리스트의 배열명, jsonArray로 저장


                                        for(int i=0; i<jsonArray.length(); i++){
                                            jsonObject = jsonArray.getJSONObject(i);
                                            for(String k : resultTx){
                                                if (jsonObject.getString("품목명").contains(k)) {
                                                    Pill pill = new Pill();
                                                    Log.e("drugName : ", k);
                                                    Log.e("1번째 : ", jsonObject.getString("품목명"));
                                                    pill.setPill_name(jsonObject.getString("품목명"));
                                                    pill.setPill_company(jsonObject.getString("업소명"));
                                                    pill.setPill_className(jsonObject.getString("분류명"));
                                                    pill.setPill_etcOtcName(jsonObject.getString("전문일반구분"));
                                                    image = jsonObject.getString("큰제품이미지");
                                                    pill.setPill_image(image);

                                                    list2.add(pill);

                                                    mAdapter = new PillinfoMyAdapter(getApplicationContext(), list2);
                                                    ch_recy.setAdapter(mAdapter);
                                                    mAdapter.notifyDataSetChanged();

                                                    if(list2.size()!= 0) {
                                                        chOut.setText(" ");
                                                    } else {
                                                        chOut.setText("검색 결과와 일치하는 약이 없습니다.");
                                                    }
                                                }
                                            }
                                        }
                                    } catch(JSONException | IOException e) {
                                       e.printStackTrace();
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(PillinfoActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

}