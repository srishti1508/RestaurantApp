package tgs.com.restaurantapp;

import android.app.ProgressDialog;
import android.content.Context;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tgs.com.restaurantapp.retrofit.ApiClient;
import tgs.com.restaurantapp.retrofit.InterfaceApi;

public class Customer extends Fragment {
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
        View view = inflater.inflate(R.layout.activity_customer, container, false);

        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
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
        Call<CustomerModel> call = api.customer_report("5199");
        call.enqueue(new Callback<CustomerModel>() {
            @Override
            public void onResponse(Call<CustomerModel> call, Response<CustomerModel> response) {
                final CustomerModel status = response.body();

                if (status.getStatus().equals("1")) {

                    tableAdapter = new TableAdapter(getActivity(),status);
                    recyclerView.setAdapter(tableAdapter);

                } else {

                    Toast.makeText(getActivity(), ""+status.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<CustomerModel> call, Throwable t) {

                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();

            }
        });
    }



    private class TableAdapter extends RecyclerView.Adapter<TableAdapter.MyViewHolder>  {
        private Context mContext;
        private List<CustomerModel.Response> albumList;
        public TableAdapter(Context mContext,CustomerModel albumList) {
            this.mContext = mContext;
            this.albumList = albumList.getResponse();
        }
        @Override
        public TableAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.customer_single, parent, false);
            return new TableAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final TableAdapter.MyViewHolder holder, int position) {
            final CustomerModel.Response table = albumList.get(position);

            holder.CustomerName.setText(table.getName());
            holder.Mobile.setText(table.getPhone());
            holder.Email.setText(table.getEmail());
            holder.Discount.setText(table.getDiscount());
            holder.Date.setText(table.getCreated_at());


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

            TextView CustomerName,Mobile,Email,Discount,Date;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                CustomerName=itemView.findViewById(R.id.CustomerName);
                Mobile=itemView.findViewById(R.id.Mobile);
                Email=itemView.findViewById(R.id.Email);
                Discount=itemView.findViewById(R.id.Discount);
                Date=itemView.findViewById(R.id.Date);
            }
        }
    }
}
