package com.example.habitpaddoctor.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.habitpaddoctor.EditAdvice;
import com.example.habitpaddoctor.GiveAdvice;
import com.example.habitpaddoctor.Model.DoctorAppointment;
import com.example.habitpaddoctor.R;
import com.example.habitpaddoctor.Urls;
import com.example.habitpaddoctor.ViewUserProfile;
import com.example.habitpaddoctor.VolleySingleton;
import com.google.android.material.button.MaterialButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorAppointmentAdapter extends RecyclerView.Adapter<DoctorAppointmentAdapter.MyViewHolder> {

    private Context mContext;
    private List<DoctorAppointment> doctorAppointments = new ArrayList<>();
    private String doctorID;

    public DoctorAppointmentAdapter(Context context, List<DoctorAppointment> doctorAppointments) {
        this.mContext = context;
        this.doctorAppointments = doctorAppointments;

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView uImg;
        private TextView uName, uDate, uTime, uRemark, uAdvice;
        private MaterialButton uProfileBtn, uAdviceBtn;
        private LinearLayout adviceLayout;
        private ImageButton editAdviceBtn;


        public MyViewHolder(View view) {
            super(view);

            uImg = view.findViewById(R.id.app_user_image);
            uName = view.findViewById(R.id.app_user_name);
            uDate = view.findViewById(R.id.app_user_date);
            uTime = view.findViewById(R.id.app_user_time);
            uRemark = view.findViewById(R.id.app_user_remark);
            uProfileBtn = view.findViewById(R.id.user_profile_btn);
            uAdviceBtn = view.findViewById(R.id.give_advice_btn);

            adviceLayout = view.findViewById(R.id.advice_layout);
            uAdvice = view.findViewById(R.id.advice_detail);

            editAdviceBtn = view.findViewById(R.id.edit_advice_btn);

        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Intent intent = ((Activity) mContext).getIntent();
        doctorID = intent.getExtras().getString("doctorID");

        View view = LayoutInflater.from(mContext).inflate(R.layout.doctor_appointment_raw, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final DoctorAppointment doctorAppointment = doctorAppointments.get(position);

        String userID = doctorAppointment.getUserID();
        String appID = doctorAppointment.getAppID();

        holder.uName.setText(doctorAppointment.getUserName());
        holder.uDate.setText(doctorAppointment.getAppDate());
        holder.uTime.setText(doctorAppointment.getAppTime());
        holder.uRemark.setText(doctorAppointment.getAppRemark());

        Glide.with(holder.uImg.getContext())
                .asBitmap().load(doctorAppointment.getUserImg())
                .error(R.drawable.boy1)
                .fitCenter()
                .into(holder.uImg);

        holder.uProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendToUserProfilePage(userID);
            }
        });

        holder.uAdviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendToAdvicePage(appID, userID);
            }
        });

        holder.editAdviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendToEditAdvicePage(appID, userID);
            }
        });

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

                                    holder.adviceLayout.setVisibility(View.VISIBLE);
                                    holder.uAdvice.setText(adviceDetail);
                                }
                            }

                            if (success.equals("0")) {
                                holder.adviceLayout.setVisibility(View.GONE);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("appointmentID", appID);
                return params;
            }
        };

        VolleySingleton.getInstance(mContext).addToRequestQueue(stringRequest);

    }

    @Override
    public int getItemCount() {
        return doctorAppointments.size();
    }

    private void SendToUserProfilePage(String userID) {
        Intent viewUserIntent = new Intent(mContext, ViewUserProfile.class);
        viewUserIntent.putExtra("userID", userID);
        mContext.startActivity(viewUserIntent);

    }

    private void SendToAdvicePage(String appID, String userID) {
        Intent adviceIntent = new Intent(mContext, GiveAdvice.class);
        adviceIntent.putExtra("appID", appID);
        adviceIntent.putExtra("userID", userID);
        adviceIntent.putExtra("doctorID", doctorID);
        mContext.startActivity(adviceIntent);
    }

    private void SendToEditAdvicePage(String appID, String userID) {
        Intent adviceIntent = new Intent(mContext, EditAdvice.class);
        adviceIntent.putExtra("appID", appID);
        adviceIntent.putExtra("userID", userID);
        adviceIntent.putExtra("doctorID", doctorID);
        mContext.startActivity(adviceIntent);
    }



}
