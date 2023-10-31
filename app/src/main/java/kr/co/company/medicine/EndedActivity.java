package kr.co.company.medicine;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EndedActivity extends AppCompatActivity {
    RecyclerView end_list;
    ArrayList<EndedItem> endedList = new ArrayList<>();
    MyDBHelper myHelper;

    EndedItemRecyclerViewAdapter EndedItemRecyclerViewAdapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endeditem);


        myHelper = new MyDBHelper(this);
        endedList.addAll(myHelper.allEndedItems());
        end_list = (RecyclerView) findViewById(R.id.end_list);
        EndedItemRecyclerViewAdapter = new EndedItemRecyclerViewAdapter(endedList, this);
        end_list.setAdapter(EndedItemRecyclerViewAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        end_list.setLayoutManager(layoutManager);
    }
}
