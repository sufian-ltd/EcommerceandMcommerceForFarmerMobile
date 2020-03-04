package com.example.e_commerceandm_commerceforfarmer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderProductActivity extends AppCompatActivity {

    Product product;
    int customerId;
    TextView tvName,tvPrice,tvUnit,tvOrderDate;
//    Spinner spnDeliverySystem,spnPaymentSystem;
    EditText etQtn;
    CalendarView dpDeliveryDate;
    String orderDate;
    RadioGroup rgDelSys,rgPaySys;
    RadioButton rbLD,rbLP,rbCOD,rbBkash;
    EditText etBkashNumber,etBkashPin;
    ImageView ivImage;
    boolean flag;
    String deliveryDate="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_product);
        product=(Product)getIntent().getSerializableExtra("product");
        customerId=getIntent().getIntExtra("customerId",-1);
        tvName=findViewById(R.id.tvName);
        tvPrice=findViewById(R.id.tvPrice);
        tvUnit=findViewById(R.id.tvUnit);
        tvOrderDate=findViewById(R.id.tvOrderDate);
//        spnDeliverySystem=findViewById(R.id.spnDeliverySystem);
//        spnPaymentSystem=findViewById(R.id.spnPaymentSystem);
        rgDelSys=findViewById(R.id.rgDelSys);
        rgPaySys=findViewById(R.id.rgPaySys);
        etQtn=findViewById(R.id.etQtn);
        dpDeliveryDate=findViewById(R.id.dpDeliveryDate);
        rbLD=findViewById(R.id.rbLD);
        rbLP=findViewById(R.id.rbLP);
        rbBkash=findViewById(R.id.rbBkash);
        rbCOD=findViewById(R.id.rbCOD);
        etBkashNumber=findViewById(R.id.etBkashNumber);
        etBkashPin=findViewById(R.id.etBkashPin);
        ivImage=findViewById(R.id.ivImage);
        dpDeliveryDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                deliveryDate=day+"/"+(month+1)+"/"+year+"";
            }
        });
        flag=true;
        initialize();
    }

    public void initialize(){
        ivImage.setImageBitmap(getBitmapByEncodedString(product.getImage()));
        tvName.setText("Product Name: "+product.getName());
        tvPrice.setText("Before: "+product.getBeforeDiscount()+"TK  After Discount: "+product.getAfterDiscount()+"TK");
        orderDate=dateCalculation();
        tvOrderDate.setText("Order Date: "+orderDate);
        tvUnit.setText(product.getUnit());
    }
    public String dateCalculation()
    {
        Calendar cal;
        cal = Calendar.getInstance();
        SimpleDateFormat sdf;
        sdf = new SimpleDateFormat();
        return sdf.format(cal.getTime());
    }

    public void order(View view) {
        if(!flag){
            Toast.makeText(this, "Your Order Already Done", Toast.LENGTH_SHORT).show();
            finish();
        }
        if(etQtn.getText().equals("") || rgDelSys.getCheckedRadioButtonId()==-1 || rgPaySys.getCheckedRadioButtonId()==-1){
            return;
        }
        Double qtn=Double.parseDouble(etQtn.getText().toString());
        if(qtn>product.getQtn()){
            Toast.makeText(this, "The Product Quantity Limit Exceed", Toast.LENGTH_SHORT).show();
            return;
        }
        int productId=product.getId();
        String productName=product.getName();
        int cost=(int)(qtn*product.getAfterDiscount());
        if(deliveryDate.equals("")) {
            Toast.makeText(this, "Please Select delivery Date..!!", Toast.LENGTH_SHORT).show();
            return;
        }

        String payment="";
        if(rgPaySys.getCheckedRadioButtonId()==R.id.rbCOD)
            payment=rbCOD.getText().toString();
        else if(rgPaySys.getCheckedRadioButtonId()==R.id.rbBkash){
            payment=rbBkash.getText().toString();
            if(etBkashNumber.equals("") || etBkashPin.equals("")) {
                Toast.makeText(this, "Bkash Number and Pin required..!!!", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Toast.makeText(this, ""+payment, Toast.LENGTH_SHORT).show();

        String status="Pending";
        ApiInterface apiInterface=ApiClient.getRetrofit().create(ApiInterface.class);
        Call<ServerResponse> call=apiInterface.order(customerId,productId,productName,qtn,cost,orderDate,deliveryDate,status,payment);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse=response.body();
                String message=serverResponse.getResponse();
                if(message.equals("inserted")){
                    Toast.makeText(OrderProductActivity.this, "Your Order is Successful", Toast.LENGTH_SHORT).show();
                    flag=false;
                    finish();
                }
                else if(message.equals("not inserted")){
                    Toast.makeText(OrderProductActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Toast.makeText(OrderProductActivity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Bitmap getBitmapByEncodedString(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.NO_WRAP);
        InputStream input=new ByteArrayInputStream(decodedString);
        Bitmap bitmap = BitmapFactory.decodeStream(input);
        return bitmap;

//        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        return decodedByte;

//        String imageDataBytes = base64String.substring(base64String.indexOf(",")+1);
//        InputStream stream = new ByteArrayInputStream(Base64.decode(imageDataBytes.getBytes(), Base64.DEFAULT));
//        return BitmapFactory.decodeStream(stream);
    }
}
