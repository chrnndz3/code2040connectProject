package com.example.twiganator.code2040connect;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by twiganator on 7/16/17.
 */

public class RetrievalRequest extends StringRequest {

    private static final String USERS_REQUEST_URL = "https://code2040.000webhostapp.com/get_many_users.php";
    private Map<String, String> params;

    // $first_name, $last_name, $username, $password, $email, $company, $university
    public RetrievalRequest(Response.Listener<String> listener){
        super(Method.GET, USERS_REQUEST_URL, listener, null);


    }
}
