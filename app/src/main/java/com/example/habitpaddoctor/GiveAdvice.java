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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GiveAdvice extends AppCompatActivity {

    private String appID,userID,doctorID;
    private TextInputLayout adviceLayout;
    private MaterialButton adviceSubmitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Give Patient Advice");
        setContentView(R.layout.activity_give_advice);

        appID = getIntent().getExtras().getString("appID");
        userID = getIntent().getExtras().getString("userID");
        doctorID = getIntent().getExtras().getString("doctorID");

        adviceLayout = findViewById(R.id.doctor_advice_layout);
        adviceSubmitBtn = findViewById(R.id.advive_submit_btn);

        adviceSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!validateAdvice()){
                    return;
                }

                addAdvice(appID, adviceLayout.getEditText().getText().toString().trim());
            }
        });

    }

    private void addAdvice(final String appID, final String advice){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.ADD_USER_ADVICE_URL, new Response.Listener<String>()  {
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
                    Toast.makeText(getApplicationContext(),"Add Error!",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GiveAdvice.this, error.toString(),Toast.LENGTH_LONG).show();
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
        String val = adviceLayout.getEditText().getText().toString().trim();

        if (val.isEmpty()) {
            adviceLayout.setError("Field cannot be empty");
            return false;
        } else {
            adviceLayout.setError(null);
            adviceLayout.setErrorEnabled(false);
            return true;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        Intent intent = new Intent(GiveAdvice.this, DoctorAppointments.class);
        intent.putExtra("appID", appID);
        intent.putExtra("userID", userID);
        intent.putExtra("doctorID", doctorID);
        startActivity(intent);
        finish();
    }
}