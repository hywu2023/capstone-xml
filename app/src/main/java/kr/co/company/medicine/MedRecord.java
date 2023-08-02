package kr.co.company.medicine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MedRecord extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    int i = 1; //pk
    FirebaseAuth firebaseAuth;



    ImageView btn_back;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Button start, end;
    EditText number, name;
    LinearLayout timelayout1, timelayout2, timelayout3, tl1, tl2, tl3;
    SQLiteDatabase sqlDB;
    Integer timesPerDay;
    Integer[] day_array;
    String[] time_array;
    String startday, endday;
    TextView startDate, endDate;
    Integer Y, M, D;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_record);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        //setTimePicker();

//        -----------------------------------------------//


        EditText alname = findViewById(R.id.nameEdit);
        EditText aldays = findViewById(R.id.daysEdit);
        Button timeText1 = findViewById(R.id.timeText1);
        Button timeText2 = findViewById(R.id.timeText2);
        Button timeText3 = findViewById(R.id.timeText3);
        Button submitBtn = findViewById(R.id.submitBtn);

        //// 0713

        start = (Button) findViewById(R.id.startBtn);
        end = (Button) findViewById(R.id.endBtn);
//        name = (EditText) findViewById(R.id.name);
        startDate = (TextView) findViewById(R.id.startDate);
        endDate = (TextView) findViewById(R.id.endDate);

        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);

        Y = null;
        M = null;
        D = null;

        startday = null;
        endday = null;


        tl1 = findViewById(R.id.tl1);
        tl2 = findViewById(R.id.tl2);
        tl3 = findViewById(R.id.tl3);
        Button select1 = (Button) findViewById(R.id.select1);
        Button select2 = (Button) findViewById(R.id.select2);
        Button select3 = (Button) findViewById(R.id.select3);

        select1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tl1.setVisibility(View.VISIBLE);
                tl2.setVisibility(View.GONE);
                tl3.setVisibility(View.GONE);
            }
        });

        select2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tl1.setVisibility(View.VISIBLE);
                tl2.setVisibility(View.VISIBLE);
                tl3.setVisibility(View.GONE);
            }

        });
        select3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tl1.setVisibility(View.VISIBLE);
                tl2.setVisibility(View.VISIBLE);
                tl3.setVisibility(View.VISIBLE);
            }
        });

        start.setOnClickListener(new View.OnClickListener() { //시작일 설정 버튼 누를시
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MedRecord.this, mDateSetListener, mYear, mMonth, mDay).show();
            }

            public DatePickerDialog.OnDateSetListener mDateSetListener =
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            mYear = year;
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;
                            Y = year;
                            M = monthOfYear + 1;
                            D = dayOfMonth;
                            UpdateNow();
                        }
                    };

            void UpdateNow() {
                if (mMonth < 9) {
                    if (mDay < 10) {
                        startDate.setText(String.format(" %d년 0%d월 0%d일", mYear, mMonth + 1, mDay));
                        startday = String.format("%d-0%d-0%d", mYear, mMonth + 1, mDay);
                    } else {
                        startDate.setText(String.format(" %d년 0%d월 %d일", mYear, mMonth + 1, mDay));
                        startday = String.format("%d-0%d-%d", mYear, mMonth + 1, mDay);
                    }
                } else {
                    if (mDay < 10) {
                        startDate.setText(String.format(" %d년 %d월 0%d일", mYear, mMonth + 1, mDay));
                        startday = String.format("%d-%d-0%d", mYear, mMonth + 1, mDay);
                    } else {
                        startDate.setText(String.format(" %d년 %d월 %d일", mYear, mMonth + 1, mDay));
                        startday = String.format("%d-%d-%d", mYear, mMonth + 1, mDay);
                    }
                }
            }
        });


        end.setOnClickListener(new View.OnClickListener() { //종료일 설정 버튼 누를시
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MedRecord.this, mDateSetListener, mYear, mMonth, mDay).show();
            }

            public DatePickerDialog.OnDateSetListener mDateSetListener =
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            mYear = year;
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;
                            UpdateNow();
                        }
                    };

            void UpdateNow() {
                Toast toast3 = new Toast(MedRecord.this);
                View toastView3 = (View) View.inflate(MedRecord.this, R.layout.toast, null);
                TextView toastText3 = (TextView) toastView3.findViewById(R.id.toast1);
                toastText3.setText("잘못된 선택입니다.");
                toast3.setView(toastView3);

                if (mMonth < 9) {
                    if (mYear < Y) {
                        toast3.show();
                    } else if (mYear == Y && mMonth + 1 < M) {
                        toast3.show();
                    } else if (mYear == Y && mMonth + 1 == M && mDay < D) {
                        toast3.show();
                    } else if (mDay < 10) {
                        endDate.setText(String.format(" %d년 0%d월 0%d일", mYear, mMonth + 1, mDay));
                        endday = String.format("%d-0%d-0%d", mYear, mMonth + 1, mDay);
                    } else {
                        endDate.setText(String.format(" %d년 0%d월 %d일", mYear, mMonth + 1, mDay));
                        endday = String.format("%d-0%d-%d", mYear, mMonth + 1, mDay);
                    }
                } else {
                    if (mYear < Y) {
                        toast3.show();
                    } else if (mYear == Y && mMonth + 1 < M) {
                        toast3.show();
                    } else if (mYear == Y && mMonth + 1 == M && mDay < D) {
                        toast3.show();
                    } else if (mDay < 10) {
                        endDate.setText(String.format(" %d년 %d월 0%d일", mYear, mMonth + 1, mDay));
                        endday = String.format("%d-%d-0%d", mYear, mMonth + 1, mDay);
                    } else {
                        endDate.setText(String.format(" %d년 %d월 %d일", mYear, mMonth + 1, mDay));
                        endday = String.format("%d-%d-%d", mYear, mMonth + 1, mDay);
                    }
                }
            }
        });


        /// 0718S

        ChipGroup cycle = findViewById(R.id.cycle);
        List<Integer> cycle_a = cycle.getCheckedChipIds();
        StringBuilder cycle_sb = new StringBuilder();
//
        // 복약주기 값 StringBuilder에 저장  ----//
        for (int i = 0; i < cycle_a.size(); i++) {
            Chip a = (Chip) cycle.getChildAt(cycle.indexOfChild(cycle.findViewById(cycle_a.get(i))));
            String selectedText = a.getText().toString();
            cycle_sb.append(selectedText);
        }
        String alarmcycle = cycle_sb.toString();

        /// 0718E


        //// 0713

        timelayout1 = (LinearLayout) findViewById(R.id.timeView1);
        timelayout2 = (LinearLayout) findViewById(R.id.timeView2);
        timelayout3 = (LinearLayout) findViewById(R.id.timeView3);

        final Button[] timeSet = new Button[3];
        final Integer[] timeSetID = {R.id.timeText1, R.id.timeText2, R.id.timeText3};
        int j;
        for (j = 0; j < timeSetID.length; j++) {
            timeSet[j] = (Button) findViewById(timeSetID[j]);
        }

        final View[] timePick = new View[3];
        final Integer[] timePickID = {R.layout.timepicker, R.layout.timepicker, R.layout.timepicker};
        final TimePicker[] times = new TimePicker[3];
        final Integer[] timesID = {R.id.timepicker, R.id.timepicker, R.id.timepicker,};
        final TextView[] tv = new TextView[3];
        final Integer[] tvID = {R.id.tv1, R.id.tv2, R.id.tv3};

        int k;
        for (k = 0; k < timePickID.length; k++) {
            timePick[k] = (View) View.inflate(MedRecord.this, timePickID[k], null);
            times[k] = (TimePicker) timePick[k].findViewById(timesID[k]);
            tv[k] = (TextView) findViewById(tvID[k]);
        }

        time_array = new String[3];
        for (k = 0; k < 3; k++) {
            time_array[k] = null;
        }

        for (k = 0; k < timePickID.length; k++) {
            final int index;
            index = k;
            timeSet[index].setOnClickListener(new View.OnClickListener() {
                @SuppressWarnings("deprecation")
                public void onClick(View view) {
                    AlertDialog.Builder dlg1 = new AlertDialog.Builder(MedRecord.this);
                    dlg1.setTitle("시간선택");
                    if (timePick[index].getParent() != null) {
                        ((ViewGroup) timePick[index].getParent()).removeView(timePick[index]);
                    }
                    times[index].setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
                    dlg1.setView(timePick[index]);
                    dlg1.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if (times[index].getCurrentHour() < 12) {
                                if (times[index].getCurrentHour() < 10) {
                                    if (times[index].getCurrentMinute() < 10) {
                                        tv[index].setText("오전 " + 0 + Integer.toString(times[index].getCurrentHour()) + " : " +
                                                0 + Integer.toString(times[index].getCurrentMinute()));
                                        timeSet[index].setText("수정");
                                        time_array[index] = 0 + Integer.toString(times[index].getCurrentHour()) + ":" +
                                                0 + Integer.toString(times[index].getCurrentMinute());
                                    } else {
                                        tv[index].setText("오전 " + 0 + Integer.toString(times[index].getCurrentHour()) + " : "
                                                + Integer.toString(times[index].getCurrentMinute()));
                                        timeSet[index].setText("수정");
                                        time_array[index] = 0 + Integer.toString(times[index].getCurrentHour()) + ":"
                                                + Integer.toString(times[index].getCurrentMinute());
                                    }
                                } else {
                                    if (times[index].getCurrentMinute() < 10) {
                                        tv[index].setText("오전 " + Integer.toString(times[index].getCurrentHour()) + " : "
                                                + 0 + Integer.toString(times[index].getCurrentMinute()));
                                        timeSet[index].setText("수정");
                                        time_array[index] = Integer.toString(times[index].getCurrentHour()) + ":"
                                                + 0 + Integer.toString(times[index].getCurrentMinute());
                                    } else {
                                        tv[index].setText("오전 " + Integer.toString(times[index].getCurrentHour()) + " : "
                                                + Integer.toString(times[index].getCurrentMinute()));
                                        timeSet[index].setText("수정");
                                        time_array[index] = Integer.toString(times[index].getCurrentHour()) + ":"
                                                + Integer.toString(times[index].getCurrentMinute());
                                    }
                                }
                            } else {
                                if (times[index].getCurrentHour() - 12 < 10) {
                                    if (times[index].getCurrentMinute() < 10) {
                                        tv[index].setText("오후 " + 0 + Integer.toString(times[index].getCurrentHour() - 12) + " : "
                                                + 0 + Integer.toString(times[index].getCurrentMinute()));
                                        timeSet[index].setText("수정");
                                        time_array[index] = Integer.toString(times[index].getCurrentHour()) + ":"
                                                + 0 + Integer.toString(times[index].getCurrentMinute());
                                    } else {
                                        tv[index].setText("오후 " + 0 + Integer.toString(times[index].getCurrentHour() - 12) + " : "
                                                + Integer.toString(times[index].getCurrentMinute()));
                                        timeSet[index].setText("수정");
                                        time_array[index] = Integer.toString(times[index].getCurrentHour()) + ":"
                                                + Integer.toString(times[index].getCurrentMinute());
                                    }
                                } else if (times[index].getCurrentMinute() < 10) {
                                    tv[index].setText("오후 " + Integer.toString(times[index].getCurrentHour() - 12) + " : "
                                            + 0 + Integer.toString(times[index].getCurrentMinute()));
                                    timeSet[index].setText("수정");
                                    time_array[index] = Integer.toString(times[index].getCurrentHour()) + ":"
                                            + 0 + Integer.toString(times[index].getCurrentMinute());
                                } else {
                                    tv[index].setText("오후 " + Integer.toString(times[index].getCurrentHour() - 12) + " : "
                                            + Integer.toString(times[index].getCurrentMinute()));
                                    timeSet[index].setText("수정");
                                    time_array[index] = Integer.toString(times[index].getCurrentHour()) + ":"
                                            + Integer.toString(times[index].getCurrentMinute());
                                }
                            }
                        }
                    });
                    dlg1.setNegativeButton("취소", null);
                    dlg1.show();
                }
            });
        }
        //// 0714


        //// 0718

        submitBtn.setOnClickListener(new View.OnClickListener() { // 등록 버튼이 눌리면
            @Override
            public void onClick(View v) {


                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String email = user.getEmail();
                    String uid = user.getUid();
                }

                String user_mail = mAuth.getCurrentUser().getEmail();

                // 복약주기 값 불러오기 ----//
                ChipGroup cycle = findViewById(R.id.cycle);
                List<Integer> cycle_a = cycle.getCheckedChipIds();
                StringBuilder cycle_sb = new StringBuilder();
//
                // 복약주기 값 StringBuilder에 저장  ----//
                for (int i = 0; i < cycle_a.size(); i++) {
                    Chip a = (Chip) cycle.getChildAt(cycle.indexOfChild(cycle.findViewById(cycle_a.get(i))));
                    String selectedText = a.getText().toString();
                    cycle_sb.append(selectedText);
                }
                String alarmcycle = cycle_sb.toString();


                // DB에 저장  ----//

//                Map<String, Object> alarm = new HashMap<>();
//                alarm.put("user_id", user_mail);
//                alarm.put("allabel", alname.getText().toString());
//                alarm.put("alcycle", alarmcycle);
//                alarm.put("aldays", aldays.getText().toString());

                    Map<String, Object> test = new HashMap<>();
                    test.put("user_id", user_mail);
                    test.put("allabel", alname.getText().toString());
                    test.put("alcycle", alarmcycle);
                    test.put("aldays", aldays.getText().toString());
                    test.put("startday", startday);
                    test.put("endday", endday);
                    test.put("time_array1", time_array[0]);
                    test.put("time_array2", time_array[1]);
                    test.put("time_array3", time_array[2]);


                db.collection("test")
                        .add(test)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(MedRecord.this, "등록성공.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MedRecord.this, MainActivity.class));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MedRecord.this, "모든 항목을 입력하세요.", Toast.LENGTH_SHORT).show();
                            }
                        });

            }

        });

        ///0718
    }
}