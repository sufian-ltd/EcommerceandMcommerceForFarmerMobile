package com.example.e_commerceandm_commerceforfarmer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransactionActivity extends AppCompatActivity {

    ListView listView;
    List<Order> list;
    int userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        userId=getIntent().getIntExtra("userId",-1);
        listView=findViewById(R.id.listView);
        getDeliveredOrders();
    }
    public void getDeliveredOrders(){
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<Order>> call=apiInterface.getOrders(userId,"Delivery Complete");
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                list=response.body();
                setAdapter();
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Toast.makeText(TransactionActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setAdapter(){
        OrderAdapter adapter=new OrderAdapter(this,list);
        listView.setAdapter(adapter);
    }
}
