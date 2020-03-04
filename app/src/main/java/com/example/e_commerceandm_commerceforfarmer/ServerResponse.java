package com.example.e_commerceandm_commerceforfarmer;

import com.google.gson.annotations.SerializedName;

public class ServerResponse {

    public ServerResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @SerializedName("response")
    private String response;
}
