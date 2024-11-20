package com.boss.a1recivingapirequest;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ArrayList<String> arrnames=new ArrayList<>();
        ListView listView;
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        listView=findViewById(R.id.listview);

        //this should be implemented after internet access,adding maven to property,and adding dependency
        //get api

        String baseUrl = "https://api.openweathermap.org/data/2.5/forecast";
        String latitude = "85";
        String longitude = "27";
        String apiKey = "0274809a4f4beabd5012160cca701abd";
        String url = baseUrl + "?lat=" + latitude + "&lon=" + longitude + "&appid=" + apiKey;
        Log.d("API Request", "URL: " + url);
        AndroidNetworking.initialize(this);
        AndroidNetworking.get(url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Res",response.toString());
                        try {
                            for(int i=0;i<response.length();i++) {
                                JSONArray jsonResultArr = response.getJSONArray("list");
                                JSONObject jsonResultobj = jsonResultArr.getJSONObject(i);
                                String Name = jsonResultobj.getString("dt");
                                arrnames.add((Name));
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1,arrnames);
                                listView.setAdapter(arrayAdapter);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ResError",anError.toString());

                    }
                });

        /////get api

//        every thing is save but the .bodyParameter("parameter")  // is added but in get it is added after question mark

        //// for post api








    }
}