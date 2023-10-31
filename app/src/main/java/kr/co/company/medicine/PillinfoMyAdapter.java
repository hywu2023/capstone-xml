package kr.co.company.medicine;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class PillinfoMyAdapter extends RecyclerView.Adapter<PillinfoMyAdapter.MyViewHolder>{
    private String drugString;
    private ArrayList<Pill> mList;
    private LayoutInflater mInflate;
    private Context mContext;
    private String data = null;
    private Intent intent;
    private String searchString;

//    private ImageButton detail_imgbtn;

    private static final String sort = "name";

    PillinfoMyAdapter(Context context, ArrayList<Pill> mList) {//생성자를 context와 배열로 초기화해줌
        this.mList = mList;
        this.mInflate = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.pill_info_list, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);



        //최초 view에 대한 list item에 대한 view를 생성함.
        //이 onBindViewHolder친구한테 실질적으로 매칭해주는 역할을 함.
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(holder.itemView)
                .load(mList.get(position).getPill_image()).error(R.drawable.unprovided_pill_img)
                .into(holder.side_image);

        holder.side_drugName.setText(mList.get(position).getPill_name());
        holder.side_company.setText(mList.get(position).getPill_company());
        holder.side_className.setText(mList.get(position).getPill_className());
        holder.side_ingr.setText(mList.get(position).getPill_etcOtcName());





        //해당하는 holder를 눌렀을 때 intent를 이용해서 상세정보 페이지로 넘겨줌
        holder.detail_imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        //알고싶은 약의 상세정보를 누르면 그 약의 이름을 받아와 다시 파싱을 시작함
                        //그렇기 때문에 약의 이름을 drugString에 저장해준 후 그 이름을 getXmlData()의 메서드로 넘겨줌
                        drugString = mList.get(position).getPill_name();
                        data = getXmlData(drugString);//drugString에 해당하는 데이터를 string형식으로 가져와 data변수에 저장해줌

                        intent = new Intent(mContext, PillinfoDetail.class);//intent를 초기화해주는 코드

                        //앞에는 key값, 뒤에는 실제 값
                        intent.putExtra("Drug", mList.get(position).getPill_name());//drug의 이름을 넘겨줌
                        intent.putExtra("data", data);//파싱한 데이터들을 "data"의 키로 넘겨줌

                        intent.putExtra("count", 1);
                        intent.putExtra("sort",sort);

                        //전체의 intent를 실제로 넘겨주는 코드.
                        mContext.startActivity(intent.addFlags(FLAG_ACTIVITY_NEW_TASK));

                    }
                }).start();
            }
        });

    }
    @Override
    public int getItemCount() {
        return (mList != null ? mList.size() : 0);
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView side_image;
        public TextView side_drugName;
        public TextView side_company;
        public TextView side_className;
        public TextView side_ingr;

        public ImageButton detail_imgbtn;
        public View sideView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sideView = itemView;
            side_image = itemView.findViewById(R.id.side_image);
            side_drugName = itemView.findViewById(R.id.side_drugName);
            side_company = itemView.findViewById(R.id.side_company);
            side_className = itemView.findViewById(R.id.side_className);
            side_ingr = itemView.findViewById(R.id.side_ingr);
            detail_imgbtn = itemView.findViewById(R.id.detail_imgbtn);
        }
    }

    String getXmlData(String string){

        StringBuffer buffer = new StringBuffer();

        try {//인코딩을 위한 try catch문
            searchString = URLEncoder.encode(string, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        String requestUrl = "https://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList?serviceKey="
                + "Wbx1pgBtpDEvILEo1O%2F2PnVF3E4341b6%2B%2FlXuG2JI7pFFuadMLVnEyQavX2BSj2C23M%2B%2BsjQJmCPQ6qiBbMUvA%3D%3D"
                + "&itemName=" + searchString;

        Log.e("drugSearch : ", requestUrl);

        try {
            //일단 false로 선언해준 후 파싱해온 tag이름과 같으면 true로 바꾸어 배열에 넣어줄것임
            boolean efcy_Qesitm = false; // 효능효과
            boolean useMethod_Qesitm = false; // 사용 방법
            boolean atpnWarn_Qesitm = false; // 사용전 반드시 알아야 할 내용
            boolean atpn_Qesitm = false; // 사용상 주의사항
            boolean intrc_Qesitm = false; // 약 사용시 주의 할 약 또는 음식
            boolean se_Qesitm = false; // 약 복용시 어떤 이상반응이 나오는지
            boolean depositMethod_Qesitm = false; // 약보관 방법
            boolean bizrno_c = false; // 마지막 약 코드


            //실질적으로 파싱해서 inputstream해주는 코드
            URL url = new URL(requestUrl);
            InputStream is = url.openStream();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new InputStreamReader(is, "UTF-8"));

            //파싱해온 주소의 eventType을 가져옴. 이것을 이용하여 파싱의 시작과 끝을 구분해좀
            int eventType = parser.getEventType();

            parser.next();
            //eventType이 END_DOCUMENT이 아닐때까지 while문이 돌아감
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT://eventType이 START_DOCUMENT일 경우
                        break;
                    case XmlPullParser.END_TAG://eventType이 END_TAG일 경우, 태그가 끝나는 부분

                        if(parser.getName().equals("body")){
                            buffer.append("\n");
                            buffer.append("※ 허가 취소된 의약품이거나 상세정보를 제공하지 않는 의약품입니다. ※");
                        }
                        break;

                    case XmlPullParser.START_TAG://eventType이 START_TAG일 경우, 태그가 시작되는 부분
                        if (parser.getName().equals("item")) {
                            buffer.append("\n");
                        }

                        if (parser.getName().equals("efcyQesitm")) { // 효능효과
                            efcy_Qesitm = true;
                        }
                        if (parser.getName().equals("useMethodQesitm")) { //사용방법
                            useMethod_Qesitm = true;
                        }
                        if (parser.getName().equals("atpnWarnQesitm")) { // 사용전 반드시 알아야 할 내용
                            atpnWarn_Qesitm = true;
                        }
                        if (parser.getName().equals("atpnQesitm")) { // 사용상 주의사항
                            atpn_Qesitm = true;
                        }
                        if (parser.getName().equals("intrcQesitm")) { // 약 사용시 주의 할 약 또는 음식
                            intrc_Qesitm = true;
                        }
                        if (parser.getName().equals("seQesitm")) { // 약 복용시 어떤 이상반응이 나오는지
                            se_Qesitm = true;
                        }
                        if (parser.getName().equals("depositMethodQesitm")) { // 약보관 방법
                            depositMethod_Qesitm = true;
                        }
                        if (parser.getName().equals("bizrno")) {// 마지막 약 코드
                            bizrno_c = true;
                        }
                        break;

                    case XmlPullParser.TEXT ://eventType이 TEXT일 경우
                        if (efcy_Qesitm) {//효능효과부분을 가져오는 코드
                            String efcy_Qesitm_text = parser.getText();//text를 가져옴
                            buffer.append("【 효능효과 】 \n"+efcy_Qesitm_text);
                            buffer.append("\n"); //꼭필요

                            efcy_Qesitm = false;

                        } else if (useMethod_Qesitm) { // 사용 방법

                            String useMethod_Qesitm_text = parser.getText();//text를 가져옴
                            buffer.append("\n【 사용 방법 】 \n"+useMethod_Qesitm_text);
                            buffer.append("\n"); //꼭필요

                            useMethod_Qesitm = false;

                        } else if (atpnWarn_Qesitm) { // 사용전 반드시 알아야 할 내용

                            String atpnWarn_Qesitm_text = parser.getText();//text를 가져옴
                            buffer.append("\n【 사용전 반드시 알아야 할 내용 】\n"+atpnWarn_Qesitm_text);
                            buffer.append("\n"); //꼭필요

                            atpnWarn_Qesitm = false;

                        } else if (atpn_Qesitm) {// 사용상 주의사항

                            String atpn_Qesitm_text = parser.getText();//text를 가져옴
                            buffer.append("\n【 사용시 주의사항 】\n"+atpn_Qesitm_text);
                            buffer.append("\n"); //꼭필요

                            atpn_Qesitm = false;

                        } else if (intrc_Qesitm) { // 약 사용시 주의 할 약 또는 음식

                            String intrc_Qesitm_text = parser.getText();//text를 가져옴
                            buffer.append("\n【 약 사용시 주의 할 약 또는 음식 】\n"+intrc_Qesitm_text);
                            buffer.append("\n"); //꼭필요

                            intrc_Qesitm = false;

                        } else if (se_Qesitm) { // 약 복용시 어떤 이상반응이 나오는지

                            String se_Qesitm_text = parser.getText();//text를 가져옴
                            buffer.append("\n【 약 복용시 어떤 이상반응이 나오는지 】\n"+se_Qesitm_text);
                            buffer.append("\n"); //꼭필요

                            se_Qesitm = false;

                        } else if (depositMethod_Qesitm) { // 약보관 방법

                            String depositMethod_Qesitm_text = parser.getText();//text를 가져옴
                            buffer.append("\n【 약 보관 방법 】\n"+depositMethod_Qesitm_text);
                            buffer.append("\n"); //꼭필요

                            depositMethod_Qesitm = false;

                        } else if (bizrno_c) { //마지막 약 코드

                            String bizrno_text = parser.getText();//text를 가져옴
                            buffer.append("\n"+bizrno_text);
                            buffer.append("\n"); //꼭필요

                            bizrno_c = false;//다시 false로 돌리는 초기화함

                        }
                }
                eventType = parser.next();//다음 parser를 찾아옴
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();//buffer를 String형식으로 return해줌
    }
}