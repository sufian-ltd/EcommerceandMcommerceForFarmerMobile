package com.example.e_commerceandm_commerceforfarmer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainMenuActivity extends AppCompatActivity {

    List<Customer> list;
    int customerId;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getCustomer();
    }
    public void getCustomer(){
        String email=getIntent().getStringExtra("email");
        String password=getIntent().getStringExtra("password");
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<Customer>> call=apiInterface.getCustomer(email,password);
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                list=response.body();
                Customer user=list.get(0);
                customerId=user.getId();
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Toast.makeText(MainMenuActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void category(View view) {
        Intent intent=new Intent(MainMenuActivity.this,CategoryActivity.class);
        intent.putExtra("customerId",customerId);
        startActivity(intent);
    }

    public void myTransaction(View view) {
        Intent intent=new Intent(MainMenuActivity.this,TransactionActivity.class);
        intent.putExtra("userId",customerId);
        startActivity(intent);
    }

    public void myProfile(View view) {
        Intent intent=new Intent(MainMenuActivity.this,ProfileActivity.class);
        intent.putExtra("customerId",customerId);
        startActivity(intent);
    }

    public void product(View view) {
        Intent intent=new Intent(MainMenuActivity.this,ProductActivity.class);
        intent.putExtra("customerId",customerId);
        intent.putExtra("category","");
        startActivity(intent);
    }

    public void pendingOrder(View view) {
        Intent intent=new Intent(MainMenuActivity.this,PendingOrderActivity.class);
        intent.putExtra("userId",customerId);
        startActivity(intent);
    }

    public void prevOrder(View view) {
        Intent intent=new Intent(MainMenuActivity.this,PreviousOrderActivity.class);
        intent.putExtra("userId",customerId);
        startActivity(intent);
    }

    public void logout(View view) {
        sharedPreferences=getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.remove("email");
        editor.remove("password");
        editor.apply();
        finish();

    }
}
