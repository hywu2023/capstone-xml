package kr.co.company.medicine;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class PwchangeDialog extends DialogFragment {

    Context ct;

    AlertDialog ad;

    View myview;



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        this.ct = context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ct);

        LayoutInflater inflater = (LayoutInflater) ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myview = inflater.inflate(R.layout.pwchange, null);
        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        setCancelable(false);
        builder.setView(myview);
        ad = builder.create();
        return ad;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (ad != null){

            Button positiveButton = (Button) ad.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText et = myview.findViewById(R.id.et);
                    String myID = et.getText().toString().trim();
                    if (!myID.equals("")){
                        Toast.makeText(ct,myID, Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().sendPasswordResetEmail(myID).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Log.d("hihi", "이메일 전송 완료");
                                    Toast.makeText(ct, "이메일 전송완료", Toast.LENGTH_SHORT).show();
                                }else {
                                    Log.d("hihi", "이메일 전송 실패");
                                    Toast.makeText(ct, "이메일 전송실패", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });





                        ad.dismiss();
                    }
                    else{
                        Toast.makeText(ct,"먼저 아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
