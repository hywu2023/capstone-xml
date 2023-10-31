package kr.co.company.medicine;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListItemRecyclerViewAdapter extends RecyclerView.Adapter<ListItemRecyclerViewAdapter.ListItemRecyclerViewHolder> {
    private ArrayList<ListItem> sList;
    private LayoutInflater sInflate;
    private Context sContext;
    MyDBHelper myHelper;

    public ListItemRecyclerViewAdapter(ArrayList<ListItem> sList, Context sContext) {
        this.sList = sList;
        this.sInflate = LayoutInflater.from(sContext);
        this.sContext = sContext;
    }

    @NonNull
    @Override
    public ListItemRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = sInflate.inflate(R.layout.rv_item_list, viewGroup, false);
        ListItemRecyclerViewHolder viewHolder = new ListItemRecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemRecyclerViewHolder holder, final int i) {
        holder.mediList.setText(sList.get(i).mediList);
        holder.mediName.setText(sList.get(i).mediName);
        holder.startDate.setText(sList.get(i).startDate);
        holder.endDate.setText(sList.get(i).endDate);
        holder.timesPerDay.setText(String.valueOf(sList.get(i).timesPerDay));

        if (sList.get(i).mon == 1) {
            holder.day_mon.setText("월");
        } else {
            holder.day_mon.setText("");
        }

        if (sList.get(i).tue == 1) {
            holder.day_tue.setText("화");
        } else {
            holder.day_tue.setText("");
        }

        if (sList.get(i).wed == 1) {
            holder.day_wed.setText("수");
        } else {
            holder.day_wed.setText("");
        }

        if (sList.get(i).thu == 1) {
            holder.day_thu.setText("목");
        } else {
            holder.day_thu.setText("");
        }

        if (sList.get(i).fri == 1) {
            holder.day_fri.setText("금");
        } else {
            holder.day_fri.setText("");
        }

        if (sList.get(i).sat == 1) {
            holder.day_sat.setText("토");
        } else {
            holder.day_sat.setText("");
        }

        if (sList.get(i).sun == 1) {
            holder.day_sun.setText("일");
        } else {
            holder.day_sun.setText(" ");
        }
        holder.timesPerDay.setText(String.valueOf(sList.get(i).timesPerDay));
        holder.lay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(sContext);
                dlg.setTitle("다음 활동");
                dlg.setMessage("수행할 일을 선택하세요");
                dlg.setPositiveButton("약 설정 수정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(sContext, ModifyActivity.class);
                        intent.putExtra("mediId", sList.get(i).mediId);
                        intent.putExtra("mediName", sList.get(i).mediName);
                        intent.putExtra("startDate", sList.get(i).startDate);
                        intent.putExtra("endDate", sList.get(i).endDate);
                        intent.putExtra("timesPerDay", sList.get(i).timesPerDay);
                        intent.putExtra("mon", sList.get(i).mon);
                        intent.putExtra("tue", sList.get(i).tue);
                        intent.putExtra("wed", sList.get(i).wed);
                        intent.putExtra("thu", sList.get(i).thu);
                        intent.putExtra("fri", sList.get(i).fri);
                        intent.putExtra("sat", sList.get(i).sat);
                        intent.putExtra("sun", sList.get(i).sun);
                        intent.putExtra("oneTime", sList.get(i).oneTime);
                        intent.putExtra("twoTime", sList.get(i).twoTime);
                        intent.putExtra("threeTime", sList.get(i).threeTime);
                        Log.d("Modi1", String.valueOf(i));
                        Log.d("Modi2", String.valueOf(sList.get(i).mediId));
                        sContext.startActivity(intent);
                    }
                });
                dlg.setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myHelper = new MyDBHelper(sContext);
                        myHelper.deleteItem(sList.get(i).mediId);
                        Log.d("Modi3", String.valueOf(i));
                        Log.d("Modi4", String.valueOf(sList.get(i).mediId));
                        sList.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i, sList.size());
                    }
                });
                dlg.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return sList.size();
    }

    public static class ListItemRecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView mediName, startDate, endDate, timesPerDay, day_mon, day_tue, day_wed, day_thu, day_fri, day_sat, day_sun, mediList;
        public LinearLayout lay1;

        public ListItemRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mediList = itemView.findViewById(R.id.txt_rv_item_list_mediList);
            mediName = itemView.findViewById(R.id.txt_rv_item_list_mediName);
            day_mon = itemView.findViewById(R.id.txt_rv_item_list_mon);
            day_tue = itemView.findViewById(R.id.txt_rv_item_list_tue);
            day_wed = itemView.findViewById(R.id.txt_rv_item_list_wed);
            day_thu = itemView.findViewById(R.id.txt_rv_item_list_thu);
            day_fri = itemView.findViewById(R.id.txt_rv_item_list_fri);
            day_sat = itemView.findViewById(R.id.txt_rv_item_list_sat);
            day_sun = itemView.findViewById(R.id.txt_rv_item_list_sun);
            startDate = itemView.findViewById(R.id.txt_rv_item_list_startDate);
            endDate = itemView.findViewById(R.id.txt_rv_item_list_endDate);
            timesPerDay = itemView.findViewById(R.id.txt_rv_item_list_timesPerDay);
            lay1 = itemView.findViewById(R.id.lay1);
        }
    }
}
