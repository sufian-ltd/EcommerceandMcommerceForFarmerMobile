package com.example.e_commerceandm_commerceforfarmer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    Customer customer;
    TextView tvId;
    EditText etName,etEmail,etPass,etContact,etAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        tvId=findViewById(R.id.tvId);
        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etPass=findViewById(R.id.etPassword);
        etContact=findViewById(R.id.etContact);
        etAddress=findViewById(R.id.etAddress);
        customer=(Customer) getIntent().getSerializableExtra("customer");
        initialize();
    }
    public void initialize(){
        tvId.setText("User ID: "+customer.getId());
        etName.setText(customer.getName());
        etEmail.setText(customer.getEmail());
        etPass.setText(customer.getPassword());
        etContact.setText(customer.getContact());
        etAddress.setText(customer.getAddress());
    }

    public void editProfile(View view) {
        String name=etName.getText().toString();
        String email=etEmail.getText().toString();
        String password=etPass.getText().toString();
        String contact=etContact.getText().toString();
        String address=etAddress.getText().toString();
        if(name.equals("") || email.equals("") || password.equals("") || contact.equals("") || address.equals("")){
            Toast.makeText(this, "Please Make Sure Your Information", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!Patterns.PHONE.matcher(contact).matches()){
            Toast.makeText(this, "Please enter a valid contact number", Toast.LENGTH_SHORT).show();
        }
        if(password.length()<4){
            Toast.makeText(this, "Password Should be at least 4 character", Toast.LENGTH_SHORT).show();
            return;
        }
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call=apiInterface.editProfile(customer.getId(),etName.getText().toString(),
        etEmail.getText().toString(),etPass.getText().toString(),etContact.getText().toString(),etAddress.getText().toString());
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse=response.body();
                String message=serverResponse.getResponse();
                if(message.equals("update")){
                    Toast.makeText(EditProfileActivity.this, "Your Profile is update", Toast.LENGTH_SHORT).show();
                }
                else if(message.equals("not update")){
                    Toast.makeText(EditProfileActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
