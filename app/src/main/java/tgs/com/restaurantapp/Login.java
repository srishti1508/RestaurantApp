package tgs.com.restaurantapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tgs.com.restaurantapp.retrofit.ApiClient;
import tgs.com.restaurantapp.retrofit.InterfaceApi;

public class Login extends AppCompatActivity {
    EditText Login,Passowrd;
    android.widget.Button Button;
    private ProgressDialog pDialog;

    // private static final String TAG = "Loginact";
    public static final String PREFS_NAME = "LoginPrefs";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getString("logged", "").toString().equals("logged")) {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        Login=findViewById(R.id.mail);
        Passowrd=findViewById(R.id.password);
        Button=findViewById(R.id.button);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(Login.getText().toString().equals("admin") && Passowrd.getText().toString().equals("1234")) {
                       /* SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("logged", "logged");
                        editor.putString("user_id", "1");
                        editor.putString("user_name", "Jungle Cook");
                        editor.putString("user_type", "admin");
                        editor.commit();
*/

                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                    }
              //  getServiceResponseData();
            }
        });
    }
    private void getServiceResponseData() {
        System.out.println("hello//////// = " +Login.getText().toString());
        System.out.println("hi....... = " +Passowrd.getText().toString());
        showDialog();
        InterfaceApi api = ApiClient.getClient().create(InterfaceApi.class);
        Call<LoginModel> call = api.Login(ApiClient.Apikey, Login.getText().toString(), Passowrd.getText().toString());
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                final LoginModel loginModel = response.body();
                hideDialog();
                if (loginModel.getStatus().equals("1")) {
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("logged", "logged");
                    editor.putString("user_id", loginModel.getResponse().get(0).getUser_id());
                    editor.putString("user_name", loginModel.getResponse().get(0).getUser_name());
                    editor.putString("user_type", loginModel.getResponse().get(0).getUser_type());
                    editor.commit();

                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Login.this, loginModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                hideDialog();
            }
        });
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
        pDialog.setCancelable(true);
    }
    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}