package test.com.assessment.Activity;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import test.com.assessment.Class.DataModel;
import test.com.assessment.Class.ModelClass;
import test.com.assessment.Interface.RetrofitAPI;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {


    private ArrayList<DataModel>  arrData;
    private String[] strArrData;
    private String name,city,salary,code,date,designation;
    private EditText edtUsername,edtPassword;
    private Button btnLogin;
    private String username,password;
    private JSONObject jsonCred;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            edtPassword=(EditText)findViewById(R.id.password);
            edtUsername=(EditText)findViewById(R.id.username);
            btnLogin=(Button)findViewById(R.id.btn_login);
            progressBar=(ProgressBar)findViewById(R.id.login_progress);


            arrData=new ArrayList<>();

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    username=edtUsername.getText().toString();
                    password=edtPassword.getText().toString();
                    jsonCred = new JSONObject();


                    if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
                        progressBar.setVisibility(View.VISIBLE);

                        try {
                            jsonCred.put("username", username);
                            jsonCred.put("password", password);
                            sendAPIRequest(jsonCred,username,password);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else {
                        showError("Enter your Credentials ");

                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void sendAPIRequest(JSONObject jsonCred, String username, String password) {

        String cred=username+":"+password;
        String auth = "Basic " + Base64.encodeToString(cred.getBytes(), Base64.NO_WRAP);
        buildAPI(auth,jsonCred);


    }

    private void buildAPI(String auth, JSONObject jsonCred) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(RetrofitAPI.BASE_URL)
                .build();


        RetrofitAPI logEasyAPi = retrofit.create(RetrofitAPI.class);


        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonCred.toString());

        Call<ModelClass> call = logEasyAPi.getTokenAccess(auth, body);

        call.enqueue(new Callback<ModelClass>() {
            @Override
            public void onResponse(Call<ModelClass> call, Response<ModelClass> response) {

                try {
                    progressBar.setVisibility(View.GONE);

                    if(response.isSuccessful()) {
                        JSONArray jsonArray = new JSONObject(response.body().getTABLE_DATA()).getJSONArray("data");
                        parseJSONArray(jsonArray);

                    }else {
                        showError("Incorrect Credentials");

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ModelClass> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                showError("Please try again later");
            }
        });
    }

    private void parseJSONArray(JSONArray jsonArray) {
        try{
            for (int i = 0; i < jsonArray.length(); i++) {


                name = jsonArray.getJSONArray(i).getString(0);
                designation = jsonArray.getJSONArray(i).getString(1);
                city = jsonArray.getJSONArray(i).getString(2);
                code = jsonArray.getJSONArray(i).getString(3);
                date = jsonArray.getJSONArray(i).getString(4);
                salary = jsonArray.getJSONArray(i).getString(5);

                arrData.add(new DataModel(name, designation, city, code, date, salary));
            }

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("arrData", arrData);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showError(String message) {

        Snackbar.make(findViewById(R.id.login_layout), message, Snackbar.LENGTH_LONG)
                .show();
    }

}

