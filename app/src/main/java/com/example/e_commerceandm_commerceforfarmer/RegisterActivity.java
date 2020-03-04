package com.example.e_commerceandm_commerceforfarmer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText etName,etEmail,etPass,etConPass,etContact,etAddress,etQues;
    Spinner spnQues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        etName=findViewById(R.id.etName);
        etEmail=findViewById(R.id.etEmail);
        etPass=findViewById(R.id.etPassword);
        etConPass=findViewById(R.id.etConfirmPassword);
        etContact=findViewById(R.id.etContact);
        etAddress=findViewById(R.id.etAddress);
        etQues=findViewById(R.id.etQues);
        spnQues=findViewById(R.id.spnQues);
        initSpinner();
    }

    public void register(View view) {
        String name=etName.getText().toString();
        final String email=etEmail.getText().toString();
        final String password=etPass.getText().toString();
        String confirmPassword=etConPass.getText().toString();
        String contact=etContact.getText().toString();
        String address=etAddress.getText().toString();
        if(name.equals("") || email.equals("") || password.equals("") || confirmPassword.equals("")
                || contact.equals("") || address.equals("")){
            Toast.makeText(this, "Please Make Sure Your Information", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length()<4){
            Toast.makeText(this, "Password Should be at least 4 character", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.equals(confirmPassword)){
            Toast.makeText(this, "Password and Confirm password do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!Patterns.PHONE.matcher(contact).matches()){
            Toast.makeText(this, "Please enter a valid contact number", Toast.LENGTH_SHORT).show();
            return;
        }
        if(spnQues.getSelectedItem().toString().equals("Select Password Recovery Question") || etQues.getText().toString().equals("")){
            Toast.makeText(this, "Please confirm password recovery option..!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call=apiInterface.registration(etName.getText().toString(),etEmail.getText().toString(),
                etPass.getText().toString(),etContact.getText().toString(),etAddress.getText().toString(),
                spnQues.getSelectedItem().toString(),etQues.getText().toString());
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse=response.body();
                String message=serverResponse.getResponse();
                if(message.equals("exist")){
                    Toast.makeText(RegisterActivity.this, "This user is already exist", Toast.LENGTH_SHORT).show();
                }
                else if(message.equals("inserted")){
                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else if(message.equals("not inserted")){
                    Toast.makeText(RegisterActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSpinner(){
        String str[]={"Select Password Recovery Question","Your hobby?","Your cousin name?",
        "Your nickname?","Your favourite dialog?","Your favourite person name?"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,str);
        spnQues.setAdapter(adapter);
    }
}
