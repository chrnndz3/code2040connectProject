package com.example.twiganator.code2040connect;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by scevallos on 7/15/17.
 */

public class RegisterRequest extends StringRequest{

    private static final String REGISTER_REQUEST_URL = "https://code2040.000webhostapp.com/Register.php";
    private Map<String, String> params;

    // $first_name, $last_name, $username, $password, $email, $company, $university
    public RegisterRequest(String first_name, String last_name, String username, String password, String email, String company, String university, Response.Listener<String> listener){
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("first_name", first_name);
        params.put("last_name", last_name);
        params.put("username", username);
        params.put("password", password);
        params.put("email", email);
        params.put("company", company);
        params.put("university", university);

    }

    public Map<String, String> getParams() {
        return params;
    }
}
