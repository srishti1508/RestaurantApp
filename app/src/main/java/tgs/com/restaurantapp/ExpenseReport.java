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

public class ExpenseReport extends Fragment {
    RecyclerView recyclerView;
    private ProgressDialog pDialog;
    Button manageprofile, fee_structure, change_pwd;
    ImageView pro_pic;
    TextView name, enroll_no;
    String date="";
    TableAdapter tableAdapter;
    private List<TableModel> albumList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_expense, container, false);

        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        getServiceResponseData(date);

        return view;
    }


    private void getServiceResponseData(String Date) {

        InterfaceApi api = ApiClient.getClient().create(InterfaceApi.class);
        Call<ExpenseModel> call = api.expense_report("5199",Date);
        call.enqueue(new Callback<ExpenseModel>() {
            @Override
            public void onResponse(Call<ExpenseModel> call, Response<ExpenseModel> response) {
                final ExpenseModel status = response.body();

                if (status.getStatus().equals("1")) {

                    tableAdapter = new TableAdapter(getActivity(),status);
                    recyclerView.setAdapter(tableAdapter);

                } else {

                    Toast.makeText(getActivity(), ""+status.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ExpenseModel> call, Throwable t) {

                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();

            }
        });
    }



    private class TableAdapter extends RecyclerView.Adapter<TableAdapter.MyViewHolder>  {
        private Context mContext;
        private List<ExpenseModel.Response> albumList;
        public TableAdapter(Context mContext,ExpenseModel albumList) {
            this.mContext = mContext;
            this.albumList = albumList.getResponse();
        }
        @Override
        public TableAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.expense_single, parent, false);
            return new TableAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final TableAdapter.MyViewHolder holder, int position) {
            final ExpenseModel.Response table = albumList.get(position);
/*
            holder.ProductName.setText(table.getPro_name());
            holder.Category.setText(table.getPro_category());
            holder.Quantity.setText(table.getPro_quantity());
            holder.Rate.setText(table.getPro_price());
            holder.Cost.setText(table.getPro_total());*/


        }

        @Override
        public int getItemCount() {

            return albumList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView ProductName,Category,Quantity,Rate,Cost;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                ProductName=itemView.findViewById(R.id.ProductName);
                Category=itemView.findViewById(R.id.Category);
                Quantity=itemView.findViewById(R.id.Quantity);
                Rate=itemView.findViewById(R.id.Rate);
                Cost=itemView.findViewById(R.id.Cost);
            }
        }
    }


}
