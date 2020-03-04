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

public class ProductActivity extends AppCompatActivity {

    ListView listView;
    List<Product> list;
    Product product;
    int customerId;
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        listView=findViewById(R.id.listViewProduct);
        customerId=getIntent().getIntExtra("customerId",-1);
        category=getIntent().getStringExtra("category");
        if(category.equals("")) {
            getProducts();
        }
        else {
            getProductsByCategory();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                product=list.get(i);
                Intent intent=new Intent(ProductActivity.this,OrderProductActivity.class);
                intent.putExtra("product",product);
                intent.putExtra("customerId",customerId);
                startActivity(intent);
            }
        });
    }
    public void getProducts(){
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<Product>> call=apiInterface.getAllProduct();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                list=response.body();
                setAdapter();
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getProductsByCategory(){
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<Product>> call=apiInterface.getProductsByCategory(category);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                list=response.body();
                setAdapter();
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(ProductActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setAdapter(){
        ProductAdapter adapter=new ProductAdapter(this,list);
        listView.setAdapter(adapter);
    }
}
