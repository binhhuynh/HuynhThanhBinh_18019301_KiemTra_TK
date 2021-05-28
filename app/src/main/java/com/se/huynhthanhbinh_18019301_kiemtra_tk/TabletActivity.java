package com.se.huynhthanhbinh_18019301_kiemtra_tk;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabletActivity extends AppCompatActivity {
    private EditText etName, etBrand, etOS, etScreenSize;
    private Button btnAdd;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private List<Tablet> tablets;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tablet);

        etName = findViewById(R.id.etName);
        etBrand = findViewById(R.id.etBrand);
        etOS = findViewById(R.id.etOperatingSystem);
        etScreenSize = findViewById(R.id.etScreenSize);

        recyclerView = findViewById(R.id.recyclerView);

        gson = new Gson();

        getTablets();

        adapter = new CustomAdapter(tablets, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tablet tablet = new Tablet();
                tablet.setName(etName.getText().toString());
                tablet.setBrand(etBrand.getText().toString());
                tablet.setOperatingSystem(etOS.getText().toString());
                tablet.setScreenSize(Double.parseDouble(etScreenSize.getText().toString()));
                addTablet(tablet);
                getTablets();
            }
        });
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
                        JSONObject object = (JSONObject) response.get(i);
                        tablets.add(gson.fromJson(String.valueOf(object), Tablet.class));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, error -> Toast.makeText(this, "Can't get tablets", Toast.LENGTH_SHORT).show());

        queue.add(jsonArrayRequest);
    }

    public void addTablet(Tablet tablet) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://60ad9c2b80a61f0017331458.mockapi.io/api/tablets";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(TabletActivity.this, "Created", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TabletActivity.this, "Can't created", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("name", tablet.getName());
                params.put("brand", tablet.getBrand());
                params.put("operatingSystem", tablet.getOperatingSystem());
                params.put("screenSize", String.valueOf(tablet.getScreenSize()));

                return params;

            }
        };

        queue.add(stringRequest);
    }
}