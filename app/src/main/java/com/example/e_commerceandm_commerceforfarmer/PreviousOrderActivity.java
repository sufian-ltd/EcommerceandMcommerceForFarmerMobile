package com.example.e_commerceandm_commerceforfarmer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreviousOrderActivity extends AppCompatActivity {

    ListView listView;
    List<Order> list;
    int id,userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_order);
        userId=getIntent().getIntExtra("userId",-1);
        listView=findViewById(R.id.listView);
        getPreviousOrders();
        registerForContextMenu(listView);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                id=list.get(i).getId();
                return false;
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Do You received Your Order ?");
        menu.add(0, v.getId(),0,"Yes");
        menu.add(1, v.getId(),1,"No");

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if(item.getTitle()=="Yes") {
            doneOrderByCustomer();
        }
        return true;
    }
    private void doneOrderByCustomer(){
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call=apiInterface.doneOrderByCustomer(id);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse=response.body();
                String message=serverResponse.getResponse();
                if(message.equals("saved")){
                    Toast.makeText(PreviousOrderActivity.this, "Your Order Status is Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else if(message.equals("not saved")){
                    Toast.makeText(PreviousOrderActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(PreviousOrderActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getPreviousOrders(){
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<Order>> call=apiInterface.getOrders(userId,"Sending");
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                list=response.body();
                setAdapter();
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Toast.makeText(PreviousOrderActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setAdapter(){
        OrderAdapter adapter=new OrderAdapter(this,list);
        listView.setAdapter(adapter);
    }
}
