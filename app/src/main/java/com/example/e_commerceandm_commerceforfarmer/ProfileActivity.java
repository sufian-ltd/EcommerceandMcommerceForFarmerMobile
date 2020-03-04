package com.example.e_commerceandm_commerceforfarmer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    int customerId;
    TextView etId, etName,etEmail,etPass,etContact,etAddress;
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        etId=findViewById(R.id.etId);
        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etPass=findViewById(R.id.etPassword);
        etContact=findViewById(R.id.etContact);
        etAddress=findViewById(R.id.etAddress);
        customerId=getIntent().getIntExtra("customerId",-1);
        getCustomer();
    }

    public void initialize(){
        etId.setText("User Id : "+customer.getId());
        etName.setText("Name : "+customer.getName());
        etEmail.setText("Username : "+customer.getEmail());
        etPass.setText("Password : "+customer.getPassword());
        etContact.setText("Contact : "+customer.getContact());
        etAddress.setText("Address : "+customer.getAddress());
    }

    public void editProfile(View view) {
        Intent intent=new Intent(ProfileActivity.this,EditProfileActivity.class);
        intent.putExtra("customer",customer);
        startActivity(intent);
    }
    public void getCustomer(){
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<Customer>> call=apiInterface.getCustomerById(customerId);
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                List<Customer> list=response.body();
                customer=list.get(0);
                initialize();
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
