package tgs.com.restaurantapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ProductSearchingReport extends Fragment {

    EditText date;
    Button Search;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_itemsearch, container, false);
         date=view.findViewById(R.id.date);
         Search=view.findViewById(R.id.search);



        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProductSearchedReport fragment = new ProductSearchedReport();
                Bundle args = new Bundle();
                args.putString("Item", date.getText().toString());
                fragment.setArguments(args);
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frag_container, fragment);
                ft.commit();
            }
        });
        return view;
    }
}
