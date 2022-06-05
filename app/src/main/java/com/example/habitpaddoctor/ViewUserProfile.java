package com.example.habitpaddoctor;

import static com.example.habitpaddoctor.DateHandler.dateFormat2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewUserProfile extends AppCompatActivity {

    private String userID;
    private int level = 0;
    private CircleImageView userImg;
    private TextView userName, userEmail, userPhone, userGender, userAge, userWeight, userHeight,userFamilySuffered, userLifestyle,userBMI, userSmoked, userAlcohol, userMedical, userTDEE, userFoodCal, userWorkoutCal, userWaterNeed;
    private String userPhoto,username,email,phone,gender,age ,weight ,height ,familySuffered,bmi,lifestyle,smoked,alcohol,medical;
    private String strUserTDEE, strUserWaterNeed, strUserFoodGoal, strUserWorkoutGoal;

    LineChart obeseLineChart;
    LineChart foodLineChart;
    LineChart workoutLineChart;
    LineChart waterLineChart;
    LineChart stepLineChart;

    ArrayList<String> xOMonth;
    ArrayList<Entry> yOMonth;

    ArrayList<String> xFMonth;
    ArrayList<Entry> yFMonth;

    ArrayList<String> xWMonth;
    ArrayList<Entry> yWMonth;

    ArrayList<String> xWaMonth;
    ArrayList<Entry> yWaMonth;

    ArrayList<String> xSMonth;
    ArrayList<Entry> ySMonth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("View Patient Profile");
        setContentView(R.layout.activity_view_user_profile);

        userID = getIntent().getExtras().getString("userID");

        userImg = findViewById(R.id.user_image);
        userName = findViewById(R.id.user_name);
        userEmail = findViewById(R.id.user_email);
        userPhone = findViewById(R.id.user_phone);
        userGender = findViewById(R.id.user_gender);
        userAge = findViewById(R.id.user_age);
        userWeight = findViewById(R.id.user_weight);
        userHeight = findViewById(R.id.user_height);
        userFamilySuffered = findViewById(R.id.user_family_suffered);
        userLifestyle = findViewById(R.id.user_lifestyle);
        userBMI = findViewById(R.id.user_bmi);
        userSmoked = findViewById(R.id.user_smoked);
        userAlcohol = findViewById(R.id.user_alcohol);
        userMedical = findViewById(R.id.user_medical);

        userTDEE = findViewById(R.id.user_tdee);
        userFoodCal = findViewById(R.id.user_food_calories);
        userWorkoutCal = findViewById(R.id.user_workout_calories);
        userWaterNeed = findViewById(R.id.user_water_need);

        getUserDetails();

        obeseLineChart = (LineChart) findViewById(R.id.obese_level_chart);

        obeseLineChart.setDrawGridBackground(false);
        obeseLineChart.setDescription("");
        obeseLineChart.setTouchEnabled(true);
        obeseLineChart.setDragEnabled(true);
        obeseLineChart.setScaleEnabled(true);
        obeseLineChart.setPinchZoom(true);

        obeseLineChart.getXAxis().setTextSize(13f);
        obeseLineChart.getAxisLeft().setTextSize(13f);
        obeseLineChart.getXAxis().setDrawGridLines(false);
        obeseLineChart.getAxisRight().setDrawAxisLine(false);
        obeseLineChart.getAxisRight().setDrawLimitLinesBehindData(false);
        obeseLineChart.getAxisRight().setDrawAxisLine(false);
        obeseLineChart.getAxisRight().setDrawGridLines(false);

        obeseLineChart.getAxisLeft().setDrawAxisLine(false);
        obeseLineChart.getAxisLeft().setDrawLimitLinesBehindData(false);
        obeseLineChart.getAxisLeft().setDrawAxisLine(false);
        obeseLineChart.getAxisLeft().setDrawGridLines(false);
        getOMonth(userID);

        foodLineChart = (LineChart) findViewById(R.id.calories_intake_chart);

        foodLineChart.setDrawGridBackground(false);
        foodLineChart.setDescription("");
        foodLineChart.setTouchEnabled(true);
        foodLineChart.setDragEnabled(true);
        foodLineChart.setScaleEnabled(true);
        foodLineChart.setPinchZoom(true);

        foodLineChart.getXAxis().setTextSize(13f);
        foodLineChart.getAxisLeft().setTextSize(13f);
        foodLineChart.getXAxis().setDrawGridLines(false);
        foodLineChart.getAxisRight().setDrawAxisLine(false);
        foodLineChart.getAxisRight().setDrawLimitLinesBehindData(false);
        foodLineChart.getAxisRight().setDrawAxisLine(false);
        foodLineChart.getAxisRight().setDrawGridLines(false);

        foodLineChart.getAxisLeft().setDrawAxisLine(false);
        foodLineChart.getAxisLeft().setDrawLimitLinesBehindData(false);
        foodLineChart.getAxisLeft().setDrawAxisLine(false);
        foodLineChart.getAxisLeft().setDrawGridLines(false);
        getFMonth(userID);

        workoutLineChart = (LineChart) findViewById(R.id.calories_burnt_chart);

        workoutLineChart.setDrawGridBackground(false);
        workoutLineChart.setDescription("");
        workoutLineChart.setTouchEnabled(true);
        workoutLineChart.setDragEnabled(true);
        workoutLineChart.setScaleEnabled(true);
        workoutLineChart.setPinchZoom(true);

        workoutLineChart.getXAxis().setTextSize(13f);
        workoutLineChart.getAxisLeft().setTextSize(13f);
        workoutLineChart.getXAxis().setDrawGridLines(false);
        workoutLineChart.getAxisRight().setDrawAxisLine(false);
        workoutLineChart.getAxisRight().setDrawLimitLinesBehindData(false);
        workoutLineChart.getAxisRight().setDrawAxisLine(false);
        workoutLineChart.getAxisRight().setDrawGridLines(false);

        workoutLineChart.getAxisLeft().setDrawAxisLine(false);
        workoutLineChart.getAxisLeft().setDrawLimitLinesBehindData(false);
        workoutLineChart.getAxisLeft().setDrawAxisLine(false);
        workoutLineChart.getAxisLeft().setDrawGridLines(false);
        getWMonth(userID);

        waterLineChart = (LineChart) findViewById(R.id.water_intake_chart);

        waterLineChart.setDrawGridBackground(false);
        waterLineChart.setDescription("");
        waterLineChart.setTouchEnabled(true);
        waterLineChart.setDragEnabled(true);
        waterLineChart.setScaleEnabled(true);
        waterLineChart.setPinchZoom(true);

        waterLineChart.getXAxis().setTextSize(13f);
        waterLineChart.getAxisLeft().setTextSize(13f);
        waterLineChart.getXAxis().setDrawGridLines(false);
        waterLineChart.getAxisRight().setDrawAxisLine(false);
        waterLineChart.getAxisRight().setDrawLimitLinesBehindData(false);
        waterLineChart.getAxisRight().setDrawAxisLine(false);
        waterLineChart.getAxisRight().setDrawGridLines(false);

        waterLineChart.getAxisLeft().setDrawAxisLine(false);
        waterLineChart.getAxisLeft().setDrawLimitLinesBehindData(false);
        waterLineChart.getAxisLeft().setDrawAxisLine(false);
        waterLineChart.getAxisLeft().setDrawGridLines(false);
        getWaMonth(userID);

        stepLineChart = (LineChart) findViewById(R.id.step_chart);

        stepLineChart.setDrawGridBackground(false);
        stepLineChart.setDescription("");
        stepLineChart.setTouchEnabled(true);
        stepLineChart.setDragEnabled(true);
        stepLineChart.setScaleEnabled(true);
        stepLineChart.setPinchZoom(true);

        stepLineChart.getXAxis().setTextSize(13f);
        stepLineChart.getAxisLeft().setTextSize(13f);
        stepLineChart.getXAxis().setDrawGridLines(false);
        stepLineChart.getAxisRight().setDrawAxisLine(false);
        stepLineChart.getAxisRight().setDrawLimitLinesBehindData(false);
        stepLineChart.getAxisRight().setDrawAxisLine(false);
        stepLineChart.getAxisRight().setDrawGridLines(false);

        stepLineChart.getAxisLeft().setDrawAxisLine(false);
        stepLineChart.getAxisLeft().setDrawLimitLinesBehindData(false);
        stepLineChart.getAxisLeft().setDrawAxisLine(false);
        stepLineChart.getAxisLeft().setDrawGridLines(false);

        getSMonth(userID);
    }

    private void getUserDetails() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.READ_USER_DETAILS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    Log.i("tagconvertstr", "[" + response + "]");
                    JSONObject jsonObject = new JSONObject(response);

                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("read");

                    if (success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);

                             userPhoto = object.getString("userPhoto").trim();
                             username = object.getString("username").trim();
                             email = object.getString("email").trim();
                             phone = object.getString("phone").trim();
                             gender = object.getString("gender").trim();
                             age = object.getString("age").trim();
                             weight = object.getString("weight").trim();
                             height = object.getString("height").trim();
                             familySuffered = object.getString("familySuffered").trim();
                             bmi = object.getString("bmi").trim();
                             lifestyle = object.getString("lifestyle").trim();
                             smoked = object.getString("smoked").trim();
                             alcohol = object.getString("alcohol").trim();
                             medical = object.getString("medical").trim();

                            Glide.with(getApplicationContext()).asBitmap().load(userPhoto)
                                    .fitCenter()
                                    .dontAnimate().into(userImg);

                            userName.setText(username);
                            userEmail.setText(email);
                            userPhone.setText(phone);
                            userGender.setText(gender);
                            userAge.setText(age);
                            userWeight.setText(weight+" kg");
                            userHeight.setText(height+" cm");
                            userFamilySuffered.setText(familySuffered);
                            userBMI.setText(bmi);
                            userLifestyle.setText(lifestyle);
                            userSmoked.setText(smoked);
                            userAlcohol.setText(alcohol);
                            userMedical.setText(medical);

                            SetUserDataToHomePage();


                        }
                    }
                    if (success.equals("0")) {

                        Toast.makeText(ViewUserProfile.this, "Read error", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(ViewUserProfile.this, "Read error" + e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(ViewUserProfile.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("userID", userID);

                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void SetUserDataToHomePage() {
        FormulaCalculations fc = new FormulaCalculations();


        if (!TextUtils.isEmpty(weight) && (!TextUtils.isEmpty(height))
                && !TextUtils.isEmpty(lifestyle) && !TextUtils.isEmpty(gender)
                && !TextUtils.isEmpty(familySuffered)&& !TextUtils.isEmpty(smoked)
                && !TextUtils.isEmpty(alcohol)){


            strUserTDEE = fc.TDEE(age, weight, height, gender, lifestyle);
            userTDEE.setText(strUserTDEE+ " Cal Per Day");

            strUserFoodGoal = fc.FoodCaloriesGoal(strUserTDEE);
            userFoodCal.setText(strUserFoodGoal+" Cal Per Day");

            strUserWorkoutGoal = fc.WorkoutCaloriesGoal(strUserTDEE);
            userWorkoutCal.setText(strUserWorkoutGoal+" Cal Per Day");

            strUserWaterNeed = fc.WaterNeed(weight);
            userWaterNeed.setText(strUserWaterNeed + " ml Per Day");

        }

    }

    private void getOMonth(final String userID){

        xOMonth = new ArrayList<String>();
        yOMonth = new ArrayList<Entry>();
        // Initializing Request queue

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.GET_OBESE_LEVEL_MONTH_GRAPH_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.i("tagconvertstr", "["+response+"]");

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("obesemonth");
                            String success = jsonObject.getString("success");

                            if(success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String obeseLevel = object.getString("obeseLevel");
                                    String addObeseDate = object.getString("addObeseDate").trim();

                                    switch (obeseLevel) {
                                        case "Insufficient_Weight":
                                            level = 0;
                                            break;
                                        case "Normal_Weight":
                                            level = 1;
                                            break;
                                        case "Overweight_Level_I":
                                            level = 2;
                                            break;
                                        case "Overweight_Level_II":
                                            level = 3;
                                            break;
                                        case "Obesity_Type_I":
                                            level = 4;
                                            break;
                                        case "Obesity_Type_II":
                                            level = 5;
                                            break;
                                        case "Obesity_Type_III":
                                            level = 6;
                                            break;

                                    }


                                    xOMonth.add(DateHandler.dateFormat2(addObeseDate));
                                    yOMonth.add(new Entry((level),i));

                                }
                                LineDataSet set1 = new LineDataSet(yOMonth, "Obesity Level" );
                                set1.setLineWidth(1.5f);
                                set1.setCircleRadius(4f);
                                set1.setDrawFilled(true);
                                set1.setDrawValues(false);

                                LineData data = new LineData(xOMonth, set1);
                                obeseLineChart.setData(data);
                                obeseLineChart.setDescription("");
                                obeseLineChart.animateXY(500, 500);
                                obeseLineChart.invalidate();

                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewUserProfile.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("userID",userID);

                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void getFMonth(final String userID){

        xFMonth = new ArrayList<String>();
        yFMonth = new ArrayList<Entry>();
        // Initializing Request queue

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.GET_USER_FOOD_GRAPH_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.i("tagconvertstr", "["+response+"]");

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("fchart");
                            String success = jsonObject.getString("success");

                            if(success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    int totalCalories = object.getInt("totalCalories");
                                    String addFoodDate = object.getString("addFoodDate").trim();

                                    xFMonth.add(dateFormat2(addFoodDate));
                                    yFMonth.add(new Entry((totalCalories),i));

                                }
                                LineDataSet set1 = new LineDataSet(yFMonth, "The calories consumed in kcal");
                                set1.setLineWidth(1.3f);
                                set1.setCircleRadius(4f);
                                set1.setDrawFilled(true);
                                set1.setDrawValues(true);

                                LineData data = new LineData(xFMonth, set1);
                                foodLineChart.setData(data);
                                foodLineChart.setDescription("");
                                data.setValueTextColor(Color.RED);
                                data.setValueTextSize(12f);
                                foodLineChart.animateXY(500, 500);
                                foodLineChart.invalidate();

                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewUserProfile.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("userID",userID);

                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void getWMonth(final String userID){

        xWMonth = new ArrayList<String>();
        yWMonth = new ArrayList<Entry>();
        // Initializing Request queue

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.GET_USER_WORKOUT_GRAPH_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.i("tagconvertstr", "["+response+"]");

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("wchart");
                            String success = jsonObject.getString("success");

                            if(success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    int workoutDateCal = object.getInt("workoutDateCal");
                                    String addWorkoutDate = object.getString("addWorkoutDate").trim();

                                    xWMonth.add(DateHandler.dateFormat2(addWorkoutDate));
                                    yWMonth.add(new Entry((workoutDateCal),i));

                                }
                                LineDataSet set1 = new LineDataSet(yWMonth, "The calories burnt in kcal");
                                set1.setLineWidth(1.3f);
                                set1.setCircleRadius(4f);
                                set1.setDrawFilled(true);
                                set1.setDrawValues(true);

                                LineData data = new LineData(xWMonth, set1);
                                workoutLineChart.setData(data);
                                workoutLineChart.setDescription("");
                                data.setValueTextColor(Color.RED);
                                data.setValueTextSize(12f);
                                workoutLineChart.animateXY(500, 500);
                                workoutLineChart.invalidate();

                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewUserProfile.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("userID",userID);

                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void getWaMonth(final String userID){

        xWaMonth = new ArrayList<String>();
        yWaMonth = new ArrayList<Entry>();
        // Initializing Request queue

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.GET_USER_WATER_GRAPH_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.i("tagconvertstr", "["+response+"]");

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("waterchart");
                            String success = jsonObject.getString("success");

                            if(success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    int drunkWater = object.getInt("drunkWater");
                                    String waterDateTime = object.getString("waterDateTime").trim();

                                    xWaMonth.add(DateHandler.dateFormat2(waterDateTime));
                                    yWaMonth.add(new Entry((drunkWater),i));

                                }
                                LineDataSet set1 = new LineDataSet(yWaMonth, "Consumed water (ml)");
                                set1.setLineWidth(1.3f);
                                set1.setCircleRadius(4f);
                                set1.setDrawFilled(true);
                                set1.setDrawValues(true);

                                LineData data = new LineData(xWaMonth, set1);
                                waterLineChart.setData(data);
                                waterLineChart.setDescription("");
                                data.setValueTextColor(Color.RED);
                                data.setValueTextSize(12f);
                                waterLineChart.animateXY(500, 500);
                                waterLineChart.invalidate();


                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewUserProfile.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("userID",userID);

                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    private void getSMonth(final String userID){

        xSMonth = new ArrayList<String>();
        ySMonth = new ArrayList<Entry>();
        // Initializing Request queue

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.GET_USER_STEP_GRAPH_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.i("tagconvertstr", "["+response+"]");

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("schart");
                            String success = jsonObject.getString("success");

                            if(success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    int totalStep = object.getInt("totalStep");
                                    String stepDate = object.getString("stepDate").trim();

                                    xSMonth.add(DateHandler.dateFormat2(stepDate));
                                    ySMonth.add(new Entry((totalStep),i));

                                }
                                LineDataSet set1 = new LineDataSet(ySMonth, "Step count");
                                set1.setLineWidth(1.3f);
                                set1.setCircleRadius(4f);
                                set1.setDrawFilled(true);
                                set1.setDrawValues(true);

                                LineData data = new LineData(xSMonth, set1);
                                stepLineChart.setData(data);
                                stepLineChart.setDescription("");
                                data.setValueTextColor(Color.RED);
                                data.setValueTextSize(12f);
                                stepLineChart.animateXY(500, 500);
                                stepLineChart.invalidate();


                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewUserProfile.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("userID",userID);

                return params;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

//    public void onBackPressed(){
//        Intent intent = new Intent(ViewUserProfile.this, DoctorAppointments.class);
//        startActivity(intent);
//
//        finish();
//    }
}