
package kr.co.company.medicine;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextView Resigettxt, LoginBtn;
    EditText EmailText, PasswordText;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth mAuth;
    public static Activity LogActivity;

    SharedPreferences sp;
    SharedPreferences.Editor ed;
    String auto_login;
    boolean auto;


//    // [START on_start_check_user]
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            currentUser.reload();
//        }
//    }
//    // [END on_start_check_user]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        CheckBox cb = findViewById(R.id.checkBox);
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    //Toast.makeText(LoginActivity.this,"체크됨", Toast.LENGTH_SHORT).show();

                    auto=true;
                }else{
                    //Toast.makeText(LoginActivity.this,"체크 해제됨", Toast.LENGTH_SHORT).show();

                    auto=false;

                }

            }
        });

        sp=getSharedPreferences("medicine_shared",Activity.MODE_PRIVATE);
        ed=sp.edit();
        if (sp!= null){
            auto_login = sp.getString("auto_login", "false");
        }

        if (auto_login.equals("true")){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }



        LogActivity = LoginActivity.this;

        mAuth = FirebaseAuth.getInstance();

        Log.d("hihi", "Login fa : " + mAuth);

        if(mAuth.getCurrentUser() != null) {
            //Toast.makeText(LoginActivity.this, "현재 사용자 : " + mAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(LoginActivity.this, "FirebaseUser가 null 상태임. 즉, 로그아웃 상태임", Toast.LENGTH_SHORT).show();
        }


        //firebaseAuth를 사용하면 명시적으로 로그아웃을 하지 않는 한 계속해서 로그인 상태를 유지시켜줌
//       if (mAuth.getCurrentUser() != null){
//           startActivity(new Intent(LoginActivity.this, MainActivity.class));
//           finish();
//       }

        //버튼 등록하기
        Resigettxt = findViewById(R.id.register_btn);
        LoginBtn = findViewById(R.id.login_btn);

        Resigettxt.setOnClickListener(new View.OnClickListener(){ //가입 버튼이 눌리면
            @Override
            public void onClick(View v) {
                //intent함수를 통해 register액티비티 함수를 호출한다.
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });



        LoginBtn.setOnClickListener(new View.OnClickListener() { //로그인 버튼이 눌리면
            @Override
            public void onClick(View v) {

                EmailText = findViewById(R.id.username);
                PasswordText = findViewById(R.id.password);

                String email = EmailText.getText().toString().trim();
                String pwd = PasswordText.getText().toString().trim();

                mAuth.signInWithEmailAndPassword(email, pwd)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    if (auto){
                                        ed.putString("auto_login", "true");
                                        ed.commit();
                                    }else{
                                        ed.putString("auto_login", "false");
                                        ed.commit();
                                    }


                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();

                                } else {
                                    Toast.makeText(LoginActivity.this, "로그인 오류", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }


}


