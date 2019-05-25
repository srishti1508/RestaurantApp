package tgs.com.restaurantapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tgs.com.restaurantapp.retrofit.ApiClient;
import tgs.com.restaurantapp.retrofit.InterfaceApi;

public class Table extends Fragment {
    RecyclerView recyclerView;
    private ProgressDialog pDialog;
    Button manageprofile, fee_structure, change_pwd;
    ImageView pro_pic;
    TextView name, enroll_no;
    TableAdapter tableAdapter;
    private List<TableModel> albumList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_table, container, false);

        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(mLayoutManager);
        getServiceResponseData();
/*
        albumList = new ArrayList<>();
        albumList.add(new TableModel("1"));
        albumList.add(new TableModel("2"));
        albumList.add(new TableModel("3"));
        albumList.add(new TableModel("4"));
        albumList.add(new TableModel("5"));
        albumList.add(new TableModel("6"));
        albumList.add(new TableModel("7"));
        albumList.add(new TableModel("8"));
        albumList.add(new TableModel("9"));
        albumList.add(new TableModel("10"));
        albumList.add(new TableModel("11"));
        albumList.add(new TableModel("12"));*/

        return view;
    }


    private void getServiceResponseData() {

        InterfaceApi api = ApiClient.getClient().create(InterfaceApi.class);
        Call<TableModel> call = api.tablestatus_report("5199");
        call.enqueue(new Callback<TableModel>() {
            @Override
            public void onResponse(Call<TableModel> call, Response<TableModel> response) {
                final TableModel status = response.body();

                if (status.getStatus().equals("1")) {

                    tableAdapter = new TableAdapter(getActivity(),status);
                    recyclerView.setAdapter(tableAdapter);

                } else {

                    Toast.makeText(getActivity(), ""+status.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<TableModel> call, Throwable t) {

                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();

            }
        });
    }



    private class TableAdapter extends RecyclerView.Adapter<TableAdapter.MyViewHolder>  {
        private Context mContext;
        private List<TableModel.Response> albumList;
        public TableAdapter(Context mContext,TableModel albumList) {
            this.mContext = mContext;
            this.albumList = albumList.getResponse();
        }
        @Override
        public TableAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.table_single, parent, false);
            return new TableAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final TableAdapter.MyViewHolder holder, int position) {
            final TableModel.Response table = albumList.get(position);
            holder.textnum.setText(table.getTbl_name());
            holder.texttime.setText(table.getTbl_time());
            if(table.getTbl_type().equals("Empty")){
                holder.Table.setImageResource(R.drawable.dinnertable);
            }else{
                holder.Table.setImageResource(R.drawable.table);
            }

            /*holder.Table.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    holder.textnum.setBackgroundColor(Color.parseColor("#ef0202"));
                    holder.textnum.setTextColor(Color.parseColor("#fcfbfb"));
                    Toast.makeText(getActivity(), "Table "+" is Booked" ,Toast.LENGTH_SHORT).show();
                }
            });*/
        }

        @Override
        public int getItemCount() {

            return albumList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView Table;
            TextView textnum,texttime;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                Table=itemView.findViewById(R.id.table);
                textnum=itemView.findViewById(R.id.textnum);
                texttime=itemView.findViewById(R.id.texttime);
            }
        }
    }
}
