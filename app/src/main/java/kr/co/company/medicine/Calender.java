package kr.co.company.medicine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

/**
 * mainactivity에서 캘린더 다루는 코드들은 전부다 여기로 옮기기(0912)
 */
public class Calender extends Fragment {
    public CalendarView calendarView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_calendar, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarView = requireActivity().findViewById(R.id.calendarView);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        RecyclerView recyclerView = requireActivity().findViewById(R.id.Med_list);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() // 캘린더 날짜 누를 시
        {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {


            }
        });

    }
}

