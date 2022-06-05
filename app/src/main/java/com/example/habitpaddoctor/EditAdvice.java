package com.example.habitpaddoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditAdvice extends AppCompatActivity {

    private String appID,userID,doctorID;
    private TextInputLayout adviceUpdateLayout;
    private MaterialButton adviceUpdateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Update Advice");
        setContentView(R.layout.activity_edit_advice);

        appID = getIntent().getExtras().getString("appID");
        userID = getIntent().getExtras().getString("userID");
        doctorID = getIntent().getExtras().getString("doctorID");

        adviceUpdateLayout = findViewById(R.id.doctor_advice_update_layout);
        adviceUpdateBtn = findViewById(R.id.advive_update_btn);

        getAdvice(appID);

        adviceUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!validateAdvice()){
                    return;
                }

                updateAdvice(appID, adviceUpdateLayout.getEditText().getText().toString().trim());
            }
        });

    }

    private void getAdvice(final String appID){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.GET_USER_ADVICE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i("tagconvertstr", "[" + response + "]");

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("advice");

                            String success = jsonObject.getString("success");

                            if (success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String adviceDetail = object.getString("adviceDetail").trim();

                                    adviceUpdateLayout.getEditText().setText(adviceDetail);
                                }
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditAdvice.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("appointmentID", appID);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void updateAdvice(final String appID, final String advice){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.EDIT_USER_ADVICE_URL, new Response.Listener<String>()  {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("tagconvertstr", "["+response+"]");
                    JSONObject jsonObject = new JSONObject(response);

                    String success = jsonObject.getString("success");
                    String message = jsonObject.getString("message");

                    if (success.equals("1")) {
                        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Edit Error!",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditAdvice.this, error.toString(),Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("appointmentID",appID);
                params.put("adviceDetail",advice);

                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    private  boolean validateAdvice() {
        String val = adviceUpdateLayout.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            adviceUpdateLayout.setError("Field cannot be empty");
            return false;
        } else {
            adviceUpdateLayout.setError(null);
            adviceUpdateLayout.setErrorEnabled(false);
            return true;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        Intent intent = new Intent(EditAdvice.this, DoctorAppointments.class);
        intent.putExtra("appID", appID);
        intent.putExtra("userID", userID);
        intent.putExtra("doctorID", doctorID);
        startActivity(intent);
        finish();
    }


}