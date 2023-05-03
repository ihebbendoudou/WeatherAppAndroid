package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView myListe;
    private TextView city;
    private TextView temp;
    private TextView Humidity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        city=(TextView) findViewById(R.id.city);
        temp=(TextView) findViewById(R.id.temp);
        Humidity=(TextView) findViewById(R.id.humidity) ;
//

        String url = "https://api.open-meteo.com/v1/forecast?latitude=36.68&longitude=10.17&hourly=temperature_2m,relativehumidity_2m,precipitation,cloudcover,visibility,windspeed_10m,temperature_80m";
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            Double latitude = response.getDouble("latitude");
                            Double longitude = response.getDouble("longitude");
                            String timezone = response.getString("timezone");
                            JSONObject hourly = response.getJSONObject("hourly");
                            JSONArray timeArray = hourly.getJSONArray("time");
                            String time = timeArray.getString(8);
                            JSONArray temperatureArray = hourly.getJSONArray("temperature_2m");
                            Double temperature = temperatureArray.getDouble(8);
                            JSONArray humidityArray = hourly.getJSONArray("relativehumidity_2m");
                            int humidity = humidityArray.getInt(0);
                            JSONArray precipitationArray = hourly.getJSONArray("precipitation");
                            Double precipitation = precipitationArray.getDouble(8);


                            city.setText("city : Tunisie");
                            temp.setText("Temperature = "+temperature+" °C");
                            Humidity.setText("humidity = "+humidity+"");

                        }catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error)
                            {
                                Toast.makeText(MainActivity.this, "Invalid URL", Toast.LENGTH_SHORT).show();
                            }
                        });
        queue.add(jsonObjectRequest);
    }
}