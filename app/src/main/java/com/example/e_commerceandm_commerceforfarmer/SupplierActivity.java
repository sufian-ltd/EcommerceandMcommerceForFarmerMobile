package com.example.e_commerceandm_commerceforfarmer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupplierActivity extends AppCompatActivity {

    int orderId;
    int supplierId;
    List<Order> list;

    TextView tvName,tvCost,tvQtn,tvDeliveryDate,tvOrderDate;
    CheckBox chdone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);
        tvName=findViewById(R.id.tvName);
        tvCost=findViewById(R.id.tvCost);
        tvQtn=findViewById(R.id.tvQtn);
        tvDeliveryDate=findViewById(R.id.tvDeliveryDate);
        chdone=findViewById(R.id.chDone);
        tvOrderDate=findViewById(R.id.tvOrderDate);
        orderId=getIntent().getIntExtra("orderId",-1);
        supplierId=getIntent().getIntExtra("supplierId",-1);
        getOrderByOrderId();
    }
    public void getOrderByOrderId(){
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<Order>> call=apiInterface.getOrderByOrderId(orderId);
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                list=response.body();
                initialize();
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Toast.makeText(SupplierActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initialize() {
        tvName.setText("Product Name : "+list.get(0).getProductName());
        tvCost.setText("Total Cost : "+list.get(0).getCost());
        tvQtn.setText("Product Quantity : "+list.get(0).getQtn());
        tvDeliveryDate.setText("Delivery Date : "+list.get(0).getDeliveryDate());
        tvOrderDate.setText("Order Date : "+list.get(0).getOrderDate());
    }

    public void save(View view) {
        if(chdone.isChecked()){
            ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
            Call<ServerResponse> call=apiInterface.doneOrderBySupplier(orderId,supplierId);
            call.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    ServerResponse serverResponse=response.body();
                    String message=serverResponse.getResponse();
                    if(message.equals("saved")){
                        Toast.makeText(SupplierActivity.this, "Your Order Status is Saved", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else if(message.equals("not saved")){
                        Toast.makeText(SupplierActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Toast.makeText(SupplierActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
