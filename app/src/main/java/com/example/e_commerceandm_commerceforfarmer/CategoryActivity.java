package com.example.e_commerceandm_commerceforfarmer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    ListView listView;
    List<Category> list;
    int customerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        listView=findViewById(R.id.listViewCategory);
        customerId=getIntent().getIntExtra("customerId",-1);
        getCategories();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String category=list.get(i).getName();
                Intent intent=new Intent(CategoryActivity.this,ProductActivity.class);
                intent.putExtra("category",category);
                intent.putExtra("customerId",customerId);
                startActivity(intent);
            }
        });
    }
    public void getCategories(){
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<Category>> call=apiInterface.getAllCategory();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                list=response.body();
                setAdapter();
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(CategoryActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setAdapter(){
        CategoryAdapter adapter=new CategoryAdapter(this,list);
        listView.setAdapter(adapter);
    }
}
