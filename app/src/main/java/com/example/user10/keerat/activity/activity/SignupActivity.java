package com.example.user10.keerat.activity.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ParseException;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user10.keerat.R;
import com.example.user10.keerat.activity.Class.ConfigInfo;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    private Button buttonsignup, buttonfb, buttongoogle;
    private TextView tvloginwith;
    String email, password, reference, data,deviceid,mob,typevan,saveservice;
    private EditText editTextemail, editTextpass, etreference,etmob;
    private static final String email_pattern =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide toolbaar
      /*  android.support.v7.app.ActionBar AB=getSupportActionBar();
        AB.hide();*/

        setContentView(R.layout.activity_signup);

        buttonfb = (Button) findViewById(R.id.btn_facebook);
        buttonsignup = (Button) findViewById(R.id.btn_signup);
        etreference = (EditText) findViewById(R.id.et_refernce);
        buttongoogle = (Button) findViewById(R.id.btn_google);
        tvloginwith = (TextView) findViewById(R.id.tv_loginwith);
        editTextemail = (EditText) findViewById(R.id.et_setemail);
        editTextpass = (EditText) findViewById(R.id.et_setpassword);
        etmob= (EditText) findViewById(R.id.et_mob);

       // deviceid = FirebaseInstanceId.getInstance().getToken();
        typevan = getIntent().getExtras().getString("typ");
        Log.d("type........",typevan);

        Typeface typefacemedium = Typeface.createFromAsset(getAssets(), "font/AvenirNext-Medium.ttf");

        buttonfb.setTypeface(typefacemedium);
        buttongoogle.setTypeface(typefacemedium);
        buttonsignup.setTypeface(typefacemedium);
        etreference.setTypeface(typefacemedium);
        tvloginwith.setTypeface(typefacemedium);
        editTextemail.setTypeface(typefacemedium);
        editTextpass.setTypeface(typefacemedium);
        etmob.setTypeface(typefacemedium);


        buttonsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = editTextemail.getText().toString().trim();
                password = editTextpass.getText().toString().trim();
                mob = etmob.getText().toString().trim();

                Matcher matcherObj = Pattern.compile(email_pattern).matcher(email);

                if (!matcherObj.matches()) {
                    editTextemail.setError("Invalid Email");
                } else if (editTextpass.getText().toString().trim().length() < 6) {
                    editTextpass.setError("Password Length is short");
                } else if (etreference.getText().toString().trim().length() == 0) {
                    etreference.setError("Reference NO. is Mandatory");
                } else if (etmob.getText().toString().trim().length() < 10) {
                    etmob.setError("Please Enter Valid Mobile Number");
                } else  {
                    registerUser();
//                    new gettingReffNumber().execute();
                }
            }
        });
    }

    private void registerUser() {
        email = editTextemail.getText().toString().trim();
        mob = etmob.getText().toString().trim();
        password = editTextpass.getText().toString().trim();
        reference = etreference.getText().toString().trim();



        StringRequest stringRequest = new StringRequest(Request.Method.POST, ConfigInfo.signUp,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("response..........", response);


                        try {


                            JSONObject result = new JSONObject(response);
                            JSONArray routearray = result.getJSONArray("get_ref_no");
                            for (int i = 0; i < routearray.length(); i++) {

                                saveservice = routearray.getJSONObject(i).getString("service");
                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("UserProfile", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("serv", saveservice);

                                Log.d("servvvv",saveservice);
                                editor.commit();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }




                        Toast.makeText(getApplicationContext(),response.toString(), Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(SignupActivity.this, MobileOrEmailActivity.class);
                        intent.putExtra("data",response.toString());
                        intent.putExtra("email",email);
                        intent.putExtra("mob",mob);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignupActivity.this, "Error Sign Up Process.Please Check your entered Credentials", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("UserProfile", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email", email);
                editor.putString("mob", mob);
                editor.putString("password", password);
                editor.putString("reference", reference);
                editor.commit();



                Map<String, String> params = new HashMap<String, String>();
                params.put("email_id", email);
                params.put("mobile", mob);
                params.put("service", typevan);
                params.put("password", password);
                params.put("ref_no", reference);
       //         params.put("device_id", deviceid);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}





