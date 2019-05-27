package tgs.com.restaurantapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Wave;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tgs.com.restaurantapp.retrofit.ApiClient;
import tgs.com.restaurantapp.retrofit.InterfaceApi;

public class SaleSearchedReport extends Fragment {
    RecyclerView recyclerView;
   ProgressBar progressBar;
    Button manageprofile, fee_structure, change_pwd;
    ImageView rightimage;
    TextView name, activityName;
    String date="";
    TableAdapter tableAdapter;
    Dialog dialog;
    String month, SelectedYear;
    private List<TableModel> albumList;
    public static  String SelectedMonth;
    int currnet_year, decrease, increase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_sale, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        recyclerView=view.findViewById(R.id.recycler_view);
        rightimage=view.findViewById(R.id.rightimage);
        activityName=view.findViewById(R.id.activityName);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        progressBar = (ProgressBar)view.findViewById(R.id.progress);
        Sprite doubleBounce = new Wave();
        progressBar.setIndeterminateDrawable(doubleBounce);

        Bundle bundle=getArguments();
        date=bundle.getString("Date");
        //setHasOptionsMenu(true);
        getServiceResponseData(date);
        rightimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "HELLO", Toast.LENGTH_SHORT).show();
                ExpenseDateReport fragment = new ExpenseDateReport();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frag_container, fragment);
                ft.commit();
            }
        });

        return view;
    }
   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calender:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

  @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menusearch, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }*/



    private void getServiceResponseData(String Date) {

        InterfaceApi api = ApiClient.getClient().create(InterfaceApi.class);
        Call<SaleModel> call = api.get_sale_report("5199",Date);
        call.enqueue(new Callback<SaleModel>() {
            @Override
            public void onResponse(Call<SaleModel> call, Response<SaleModel> response) {
               progressBar.setVisibility(View.GONE);
               recyclerView.setVisibility(View.VISIBLE);
                final SaleModel status = response.body();

                if (status.getStatus().equals("1")) {
                    activityName.setText("Total Sale : " +status.getGrand_total());
                    tableAdapter = new TableAdapter(getActivity(),status);
                    recyclerView.setAdapter(tableAdapter);

                } else {

                    Toast.makeText(getActivity(), ""+status.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SaleModel> call, Throwable t) {

                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
                getActivity().onBackPressed();

            }
        });
    }

    private class TableAdapter extends RecyclerView.Adapter<TableAdapter.MyViewHolder>  {
        private Context mContext;
        private List<SaleModel.Response> albumList;
        public TableAdapter(Context mContext,SaleModel albumList) {
            this.mContext = mContext;
            this.albumList = albumList.getResponse();
        }
        @Override
        public TableAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.sale_single, parent, false);
            return new TableAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull final TableAdapter.MyViewHolder holder, int position) {
            final SaleModel.Response table = albumList.get(position);


            holder.subtotal.setText(table.getSubtotal());
            holder.discount.setText(table.getDiscount());
            holder.total.setText(table.getTotal());
            holder.paid.setText(table.getStatus());


        }

        @Override
        public int getItemCount() {

            return albumList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView subtotal,discount,total,paid;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);


                subtotal=itemView.findViewById(R.id.subtotal);
                discount=itemView.findViewById(R.id.discount);
                total=itemView.findViewById(R.id.total);
                paid=itemView.findViewById(R.id.paid);
            }
        }
    }


}
