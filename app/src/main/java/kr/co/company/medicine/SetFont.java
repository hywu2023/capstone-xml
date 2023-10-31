package kr.co.company.medicine;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.Slider;

public class SetFont extends AppCompatActivity {

    Button btn_font_0, btn_font_1, btn_font_2, btn_exit;
    SeekBar seekBar;
    Slider slider;

    float font_size = 1.0f;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_font);

        btn_font_0 = findViewById(R.id.btn_font_0);
        btn_font_1 = findViewById(R.id.btn_font_1);
        btn_font_2 = findViewById(R.id.btn_font_2);
        seekBar = findViewById(R.id.seekBar);
        slider = findViewById(R.id.slider);
        btn_exit = findViewById(R.id.btn_exit);

        checkSystemWritePermission();
        SliderInit();

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        btn_font_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.System.putFloat(getBaseContext().getContentResolver(),
                        Settings.System.FONT_SCALE, (float) 1.3);
            }
        });

        btn_font_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.System.putFloat(getBaseContext().getContentResolver(),
                        Settings.System.FONT_SCALE, (float) 1.0);
            }
        });

        btn_font_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Settings.System.putFloat(getBaseContext().getContentResolver(),
                        Settings.System.FONT_SCALE, (float) 0.8);
            }
        });
    }

    private void SliderInit() {
        slider.setValueFrom(0.5f);
        slider.setValueTo(1.5f);
        slider.setStepSize(0.1f);
        slider.setValue(1.0f);

        slider.addOnSliderTouchListener(touchListener);
    }

    private final Slider.OnSliderTouchListener touchListener =
            new Slider.OnSliderTouchListener() {
                @Override
                public void onStartTrackingTouch(Slider slider) {

                }

                @Override
                public void onStopTrackingTouch(Slider slider) {

                    Settings.System.putFloat(getBaseContext().getContentResolver(),
                            Settings.System.FONT_SCALE, slider.getValue());

                }
            };
    boolean mHasWriteSettingPermission;

    // 폰트 스케일 변경 권한
    private boolean checkSystemWritePermission() {
        boolean retVal = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            retVal = Settings.System.canWrite(this);
            Log.d("TAG", "Can Write Settings: " + retVal);
            if (retVal) {
                ///Permission granted by the user
            } else {
                //permission not granted navigate to permission screen
                openAndroidPermissionsMenu();
            }
        }
        return retVal;
    }

    private void openAndroidPermissionsMenu() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(Uri.parse("package:" + this.getPackageName()));
        startActivity(intent);
    }
    
    @Override
    protected void onDestroy() { // 앱 종료시 글자 원래 크기로 복구하는 코드
        if (isFinishing()) {
            restoreBringnessSystemSetting();
        }
        super.onDestroy();
    }

    private void restoreBringnessSystemSetting() {
        Settings.System.putFloat(getBaseContext().getContentResolver(),
                Settings.System.FONT_SCALE, (float) 1.0);
    }
}