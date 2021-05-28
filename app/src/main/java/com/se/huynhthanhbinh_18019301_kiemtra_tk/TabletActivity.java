package com.se.huynhthanhbinh_18019301_kiemtra_tk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TabletActivity extends AppCompatActivity {
    private List<Tablet> tablets;
    private CustomAdapter adapter;
    private RecyclerView recyclerView;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet);

        recyclerView = findViewById(R.id.recyclerView);

        gson = new Gson();

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnSignOut = findViewById(R.id.btnSignOut);

        btnSignOut.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(TabletActivity.this, MainActivity.class));
        });

        getTablets();

        adapter = new CustomAdapter(tablets, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(view ->
                startActivity(new Intent(TabletActivity.this, AddTabletActivity.class))
        );
    }

    public void getTablets() {
        tablets = new ArrayList<>();
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://60ad9c2b80a61f0017331458.mockapi.io/api/tablets";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        String brand = jsonObject.getString("brand");
                        String os = jsonObject.getString("operatingSystem");
                        double screenSize = jsonObject.getDouble("screenSize");
                        tablets.add(new Tablet(id, name, brand, os, screenSize));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, error -> Toast.makeText(this, "Can't get tablets", Toast.LENGTH_SHORT).show());

        queue.add(jsonArrayRequest);
    }
}