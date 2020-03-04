package com.example.e_commerceandm_commerceforfarmer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

//    @GET("isUser.php")
//    Call<ServerResponse> isUser(@Query("userName") String userName,@Query("password") String password);
//
//    @GET("getUser.php")
//    Call<List<User>> getUser(@Query("userName") String userName, @Query("password") String password);

    @GET("registration.php")
    Call<ServerResponse> registration(@Query("name") String name,@Query("email") String email,
      @Query("password") String password, @Query("contact") String contact, @Query("address") String address
        ,@Query("question") String question,@Query("answer") String answer);

    @GET("isCustomer.php")
    Call<ServerResponse> isCustomer(@Query("email") String email, @Query("password") String password);

    @GET("getCustomer.php")
    Call<List<Customer>> getCustomer(@Query("email") String email, @Query("password") String password);

    @GET("getCustomerByEmail.php")
    Call<List<Customer>> getCustomerByEmail(@Query("email") String email);

    @GET("getSupplier.php")
    Call<List<Supplier>> getSupplier(@Query("email") String email, @Query("password") String password);

    @GET("getCustomerById.php")
    Call<List<Customer>> getCustomerById(@Query("id") int id);

    @GET("getAllCategory.php")
    Call<List<Category>> getAllCategory();

    @GET("getAllProduct.php")
    Call<List<Product>> getAllProduct();

    @GET("getOrders.php")
    Call<List<Order>> getOrders(@Query("userId") int userId,@Query("status") String status);

    @GET("doneOrderBySupplier.php")
    Call<ServerResponse> doneOrderBySupplier(@Query("orderId") int orderId,@Query("supplierId") int supplierId);

    @GET("doneOrderByCustomer.php")
    Call<ServerResponse> doneOrderByCustomer(@Query("orderId") int orderId);

    @GET("getOrderByOrderId.php")
    Call<List<Order>> getOrderByOrderId(@Query("id") int id);

    @GET("getProductsByCategory.php")
    Call<List<Product>> getProductsByCategory(@Query("category") String category);

    @GET("order.php")
    Call<ServerResponse> order(@Query("userId") int userId, @Query("productId") int productId,
                               @Query("productName") String productName
            , @Query("qtn") Double qtn, @Query("cost") int cost, @Query("orderDate") String orderDate,
                               @Query("deliveryDate") String deliveryDate, @Query("status") String status, @Query("payment") String payment);
    @GET("editProfile.php")
    Call<ServerResponse> editProfile(@Query("id") int id, @Query("name") String name, @Query("email")
            String email, @Query("password") String password, @Query("contact") String contact,
                                     @Query("address") String address);

    @GET("updatePassword.php")
    Call<ServerResponse> updatePassword(@Query("email") String email,@Query("password") String password);
}
