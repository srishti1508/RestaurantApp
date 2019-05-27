package tgs.com.restaurantapp.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import tgs.com.restaurantapp.CustomerModel;
import tgs.com.restaurantapp.ExpenseModel;
import tgs.com.restaurantapp.LoginModel;
import tgs.com.restaurantapp.ProductModel;
import tgs.com.restaurantapp.SaleModel;
import tgs.com.restaurantapp.TableModel;

public interface InterfaceApi {


    @POST("/index.php/admin/WEBSERVICES/login")
    @FormUrlEncoded
    Call<LoginModel> Login(@Field("APIKEY") String APIKEY,
                           @Field("USERNAME") String USERNAME,
                           @Field("PASSWORD") String PWD
    );

    @POST("tablestatus_report")
    @FormUrlEncoded
    Call<TableModel> tablestatus_report(@Field("KEY") String APIKEY

    );

    @POST("customer_report")
    @FormUrlEncoded
    Call<CustomerModel> customer_report(@Field("KEY") String APIKEY

    );

    @POST("productwise_report")
    @FormUrlEncoded
    Call<ProductModel> productwise_report(@Field("KEY") String APIKEY,@Field("search") String search

    );


    @POST("get_sale_report")
    @FormUrlEncoded
    Call<SaleModel> get_sale_report(@Field("KEY") String APIKEY, @Field("date") String date

    );

    @POST("expense_report")
    @FormUrlEncoded
    Call<ExpenseModel> expense_report(@Field("KEY") String APIKEY, @Field("date") String date

    );


}
