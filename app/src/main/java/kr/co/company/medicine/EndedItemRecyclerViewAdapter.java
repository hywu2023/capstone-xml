package kr.co.company.medicine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EndedItemRecyclerViewAdapter extends RecyclerView.Adapter<EndedItemRecyclerViewAdapter.EndedItemRecyclerViewHolder>{
    private ArrayList<EndedItem> eList;
    private LayoutInflater sInflate;
    private Context sContext;
    MyDBHelper myHelper;

    public EndedItemRecyclerViewAdapter(ArrayList<EndedItem> eList, Context sContext) {
        this.eList = eList;
        this.sInflate = LayoutInflater.from(sContext);
        this.sContext = sContext;
    }

    @NonNull
    @Override
    public EndedItemRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = sInflate.inflate(R.layout.end_item_list, viewGroup, false);
        EndedItemRecyclerViewHolder viewHolder = new EndedItemRecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EndedItemRecyclerViewHolder holder, final int i) {
        holder.mediList.setText(eList.get(i).mediList);
        holder.mediName.setText(eList.get(i).mediName);
        holder.startDate.setText(eList.get(i).startDate);
        holder.endDate.setText(eList.get(i).endDate);
    }

    @Override
    public int getItemCount() {
        return eList.size();
    }

    public static class EndedItemRecyclerViewHolder extends RecyclerView.ViewHolder {
        public TextView mediName, startDate, endDate, timesPerDay, mediList;
        public LinearLayout lay1;

        public EndedItemRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            mediList = itemView.findViewById(R.id.txt_end_item_list_mediList);
            mediName = itemView.findViewById(R.id.txt_end_item_list_mediName);
            startDate = itemView.findViewById(R.id.txt_end_item_list_startDate);
            endDate = itemView.findViewById(R.id.txt_end_item_list_endDate);
        }
    }
}


