package kr.co.company.medicine;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingActivity extends AppCompatActivity {


    FirebaseAuth fa;
    FirebaseUser fu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        fa = FirebaseAuth.getInstance();
        fu = fa.getCurrentUser();


        if(fu != null) {
            //Toast.makeText(SettingActivity.this, "현재 사용자 : " + fu.getEmail(), Toast.LENGTH_SHORT).show();
            Log.d("hihi", "Setting fa : " + fa);
        } else {
            //Toast.makeText(SettingActivity.this, "FirebaseUser가 null 상태임", Toast.LENGTH_SHORT).show();
        }

        Button bt1 = findViewById(R.id.button1);
        Button bt3 = findViewById(R.id.button3);
        Button bt4 = findViewById(R.id.button4);


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PwchangeDialog pd = new PwchangeDialog();
                pd.show(getSupportFragmentManager(),"change_pw");




/*
                if(fu != null) {
                    //Toast.makeText(SettingActivity.this, "현재 사용자 : " + fu.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(SettingActivity.this, "FirebaseUser가 null 상태임", Toast.LENGTH_SHORT).show();
                }

                String myID = et.getText().toString().trim();

                if (!myID.equals("")) {
                    Toast.makeText(SettingActivity.this, myID, Toast.LENGTH_SHORT).show();

                    FirebaseAuth.getInstance().sendPasswordResetEmail(myID).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Log.d("hihi", "이메일 전송 완료");
                                Toast.makeText(SettingActivity.this, "이메일 전송완료", Toast.LENGTH_SHORT).show();
                            }else {
                                Log.d("hihi", "이메일 전송 실패");
                                Toast.makeText(SettingActivity.this, "이메일 전송실패", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }else{
                    Toast.makeText(SettingActivity.this, "먼저 ID를 입력하세요.", Toast.LENGTH_SHORT).show();
                }

 */
            }
        });


        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fu != null) {
                    //Toast.makeText(SettingActivity.this, "현재 사용자 : " + fu.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(SettingActivity.this, "FirebaseUser가 null 상태임", Toast.LENGTH_SHORT).show();
                }

                if (fa != null) {
                    fa.signOut();
                    //Toast.makeText(SettingActivity.this, "정상적으로 로그아웃됨.", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(SettingActivity.this, "FirebaseAuth 참조변수가 null 상태이므로 로그아웃을 정상적으로 수행할수 없음", Toast.LENGTH_SHORT).show();
                }

                SharedPreferences sp=getSharedPreferences("medicine_shared", Activity.MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                ed.putString("auto_login", "false");
                ed.commit();

                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent);

                finish();
            }
        });

         /*
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fu != null) {
                    //Toast.makeText(SettingActivity.this, "현재 사용자 : " + fu.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(SettingActivity.this, "FirebaseUser가 null 상태임", Toast.LENGTH_SHORT).show();
                }

                Log.d("hihi", "현재 유저 : " + fu);


                if(fu != null) {
                    fu.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("hihi", "회원탈퇴 성공");
                                Toast.makeText(SettingActivity.this, "회원탈퇴 성공", Toast.LENGTH_SHORT).show();

                                SharedPreferences sp = getSharedPreferences("medicine_shared", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor ed = sp.edit();
                                ed.putString("auto_login", "false");
                                ed.commit();


                                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                                startActivity(intent);

                                finish();
                            } else {
                                Log.d("hihi", "회원탈퇴 실패");
                                Toast.makeText(SettingActivity.this, "회원탈퇴 실패", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    //Toast.makeText(SettingActivity.this, "FirebaseUser 참조변수가 null 상태이므로 회원탈퇴를 정상적으로 수행할수 없음", Toast.LENGTH_SHORT).show();
                }


            }
        });
*/

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(fu != null) {
                    //Toast.makeText(SettingActivity.this, "현재 사용자 : " + fu.getEmail(), Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(SettingActivity.this, "FirebaseUser가 null 상태임", Toast.LENGTH_SHORT).show();
                }

                Log.d("hihi", "현재 유저 : " + fu);




                AlertDialog.Builder bd = new AlertDialog.Builder(SettingActivity.this);

                //기본적인 대화상자 모양을 구성한다.
                bd.setTitle("경고");
                bd.setMessage("정말 탈퇴하시겠습니까?");
                bd.setIcon(android.R.drawable.ic_dialog_alert);

                bd.setPositiveButton("예", new DialogInterface.OnClickListener() {   //대화상자내의 버튼에 연계된 OnClick리스너
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(SettingActivity.this, "예 버튼이 눌림.", Toast.LENGTH_LONG ).show();  //이 위치는 DialogInterface.OnClickListener

                        if(fu != null) {
                            fu.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("hihi", "회원탈퇴 성공");
                                        Toast.makeText(SettingActivity.this, "회원탈퇴 성공", Toast.LENGTH_SHORT).show();

                                        SharedPreferences sp = getSharedPreferences("medicine_shared", Activity.MODE_PRIVATE);
                                        SharedPreferences.Editor ed = sp.edit();
                                        ed.putString("auto_login", "false");
                                        ed.commit();


                                        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                                        startActivity(intent);

                                        finish();
                                    } else {
                                        Log.d("hihi", "회원탈퇴 실패");
                                        Toast.makeText(SettingActivity.this, "회원탈퇴 실패", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            //Toast.makeText(SettingActivity.this, "FirebaseUser 참조변수가 null 상태이므로 회원탈퇴를 정상적으로 수행할수 없음", Toast.LENGTH_SHORT).show();
                        }


                    }
                });


                bd.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(SettingActivity.this, "아니오 버튼이 눌림.", Toast.LENGTH_LONG).show();
                    }
                });



                bd.setCancelable(false);  //대화상자 바깥 영역 터치시 대화상자가 dismiss 되는것을 허용하지 않음.


                bd.create().show();


            }
        });




    }

}