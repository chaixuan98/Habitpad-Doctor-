package com.example.habitpaddoctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.habitpaddoctor.Adapter.AppointmentViewPagerAdapter;
import com.example.habitpaddoctor.Adapter.DoctorAppointmentAdapter;
import com.example.habitpaddoctor.Model.DoctorAppointment;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DoctorAppointments extends AppCompatActivity {
    private RecyclerView doctorAppointmentRecyclerView;
    private RecyclerView.LayoutManager manager;
    private DoctorAppointmentAdapter doctorAppointmentAdapter;
    private List<DoctorAppointment> doctorAppointments;

    private String doctorID;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("My Appointment");
        setContentView(R.layout.activity_doctor_appointments);

        tabLayout = findViewById(R.id.appointment_tab_layout);
        viewPager = findViewById(R.id.appointment_view_pager);

        tabLayout.setupWithViewPager(viewPager);

        AppointmentViewPagerAdapter appointmentViewPagerAdapter = new AppointmentViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        appointmentViewPagerAdapter.addFragment(new ActiveAppointmentFragment(), "Active");
        appointmentViewPagerAdapter.addFragment(new PastAppointmentFragment(), "Past");
        viewPager.setAdapter(appointmentViewPagerAdapter);


        //doctorAppointmentRecyclerView = findViewById(R.id.doctor_appointments_rv);

//        doctorID = getIntent().getExtras().getString("doctorID");
//
//        manager = new LinearLayoutManager(DoctorAppointments.this);
//        doctorAppointmentRecyclerView.setLayoutManager(manager);
//        doctorAppointmentRecyclerView.setHasFixedSize(true);
//        doctorAppointments = new ArrayList<>();
//        doctorAppointmentAdapter = new DoctorAppointmentAdapter(DoctorAppointments.this, doctorAppointments);
//
//        getDoctorAppointment();
    }

//    private void getDoctorAppointment() {
//
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.show();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.GET_DOCTOR_APPOINTMENT_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        progressDialog.dismiss();
//                        try {
//                            Log.i("tagconvertstr", "["+response+"]");
//
//                            JSONObject jsonObject = new JSONObject(response);
//                            JSONArray jsonArray = jsonObject.getJSONArray("doctorapp");
//
//                            String success = jsonObject.getString("success");
//
//                            if (success.equals("1")) {
//
//                                for (int i = 0; i < jsonArray.length(); i++) {
//
//                                    JSONObject object = jsonArray.getJSONObject(i);
//
//                                    String appointmentID = object.getString("appointmentID").trim();
//                                    String userID = object.getString("userID").trim();
//                                    String userPhoto = object.getString("userPhoto").trim();
//                                    String username = object.getString("username").trim();
//                                    String appointmentDate = object.getString("appointmentDate").trim();
//                                    String appointmentTime = object.getString("appointmentTime").trim();
//                                    String appointmentRemark = object.getString("appointmentRemark").trim();
//
//
//                                    DoctorAppointment doctorAppointment = new DoctorAppointment(appointmentID,userID,userPhoto, username, appointmentDate, appointmentTime, appointmentRemark);
//                                    doctorAppointments.add(doctorAppointment);
//                                }
//                            }
//
//                        }catch (Exception e){
//                            progressDialog.dismiss();
//                            e.printStackTrace();
//                        }
//
//                        doctorAppointmentRecyclerView.setAdapter(doctorAppointmentAdapter);
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                progressDialog.dismiss();
//                Toast.makeText(DoctorAppointments.this, error.toString(), Toast.LENGTH_LONG).show();
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("doctorID", doctorID);
//                return params;
//            }
//        };
//
//        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
//    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onBackPressed() {
        Intent intent = new Intent(DoctorAppointments.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}