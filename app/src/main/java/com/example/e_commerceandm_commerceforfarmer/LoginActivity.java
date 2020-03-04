package com.example.e_commerceandm_commerceforfarmer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail,etPass;
    TextView tvRegister,tvForgotPassword;
    RadioButton rbCustomer,rbSuppler;
    RadioGroup rgUserType;
    SharedPreferences sharedPreferences;
    Customer customer;
//    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail=findViewById(R.id.etEmail);
        etPass=findViewById(R.id.etPassword);
        tvRegister=findViewById(R.id.tvRegister);
        tvForgotPassword=findViewById(R.id.tvForgotPassword);
        rbCustomer=findViewById(R.id.rbCustomer);
        rbSuppler=findViewById(R.id.rbSupplier);
        rgUserType=findViewById(R.id.rgUserType);
//        firebaseAuth=FirebaseAuth.getInstance();
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFirstDialog();
            }
        });
    }

    private void showForgotPassDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(customer.getQuestion());
        LinearLayout linearLayout=new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final EditText editTextAns=new EditText(this);
        editTextAns.setHint("Your Answer");
        editTextAns.setWidth(950);
        final Button button=new Button(this);
        button.setText("Check Answer");
        button.setWidth(950);
        final EditText editTextPass=new EditText(this);
        editTextPass.setEnabled(false);
        editTextPass.setHint("New Password");
        editTextPass.setWidth(950);
        linearLayout.addView(editTextAns);
        linearLayout.addView(button);
        linearLayout.addView(editTextPass);
        linearLayout.setPadding(10,10,10,10);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextAns.getText().toString().trim().equalsIgnoreCase(customer.getAnswer().trim())){
                    editTextPass.setEnabled(true);
                }
            }
        });
        builder.setView(linearLayout);
        builder.setPositiveButton("Reset", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!editTextPass.getText().toString().isEmpty())
                    recovery(customer.getEmail(),editTextPass.getText().toString());
                else
                    Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    private void showFirstDialog(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Password Recovery");
        LinearLayout linearLayout=new LinearLayout(this);
        final EditText editTextEmail=new EditText(this);
        editTextEmail.setHint("Your Username");
        editTextEmail.setWidth(950);
        linearLayout.addView(editTextEmail);
        linearLayout.setPadding(10,10,10,10);
        builder.setView(linearLayout);
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!editTextEmail.getText().toString().isEmpty())
                    getQuestionAndAnswer(editTextEmail.getText().toString());
                else
                    Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    private void recovery(String email,String newPassword) {
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call=apiInterface.updatePassword(email,newPassword);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse=response.body();
                String message=serverResponse.getResponse();
                if(message.equals("update")){
                    Toast.makeText(LoginActivity.this, "Your Password is update", Toast.LENGTH_SHORT).show();
                }
                else if(message.equals("not update")){
                    Toast.makeText(LoginActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });

//        firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()){
//                    Toast.makeText(LoginActivity.this, "Main Sent To Your Account", Toast.LENGTH_SHORT).show();
//                }
//            }
//        })
//        .addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(LoginActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences=getSharedPreferences("myPreference", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("email"))
            etEmail.setText(sharedPreferences.getString("email",""));
        if(sharedPreferences.contains("password"))
            etPass.setText(sharedPreferences.getString("password",""));
    }

    public void login(View view) {
        final String email=etEmail.getText().toString();
        final String password=etPass.getText().toString();
        if(email.equals("") || password.equals("")){
            Toast.makeText(this, "Please Fill All..!!!", Toast.LENGTH_SHORT).show();
            return;
        }
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        if(rgUserType.getCheckedRadioButtonId()==R.id.rbCustomer) {
            Call<ServerResponse> call = apiInterface.isCustomer(email, password);
            call.enqueue(new Callback<ServerResponse>() {
                @Override
                public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                    ServerResponse serverResponse = response.body();
                    if (serverResponse.getResponse().equals("exist")) {
                        Toast.makeText(LoginActivity.this, "Login Successful",
                                Toast.LENGTH_SHORT).show();
                        setLoginData(email,password);
                        Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                        intent.putExtra("email", email);
                        intent.putExtra("password", password);
                        startActivity(intent);
                    } else if (serverResponse.getResponse().equals("not exist")) {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(rgUserType.getCheckedRadioButtonId()==R.id.rbSupplier){
            Call<List<Supplier>> call=apiInterface.getSupplier(email,password);
            call.enqueue(new Callback<List<Supplier>>() {
                @Override
                public void onResponse(Call<List<Supplier>> call, Response<List<Supplier>> response) {
                    List<Supplier> list = response.body();
                    if (list.size() > 0 && list.get(0).getOrderId()!=0) {
                        Intent intent = new Intent(LoginActivity.this, SupplierActivity.class);
                        clear();
                        intent.putExtra("orderId", list.get(0).getOrderId());
                        intent.putExtra("supplierId", list.get(0).getId());
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "No Pending Delivery...!!!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Supplier>> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void getQuestionAndAnswer(String email){
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<List<Customer>> call=apiInterface.getCustomerByEmail(email);
        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if(response.body()!=null) {
                    customer = response.body().get(0);
                    showForgotPassDialog();
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Connection failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setLoginData(String email,String password){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("email",email);
        editor.putString("password",password);
        editor.commit();
        clear();
    }
    private void clear(){
        etEmail.setText("");
        etPass.setText("");
    }
}
