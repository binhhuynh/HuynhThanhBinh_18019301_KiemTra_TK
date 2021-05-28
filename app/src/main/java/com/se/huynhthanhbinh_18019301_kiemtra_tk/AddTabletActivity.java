package com.se.huynhthanhbinh_18019301_kiemtra_tk;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddTabletActivity extends AppCompatActivity {
    private EditText etName, etBrand, etOS, etScreenSize;
    private Button btnAddTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tablet);

        etName = findViewById(R.id.etName);
        etBrand = findViewById(R.id.etBrand);
        etOS = findViewById(R.id.etOperatingSystem);
        etScreenSize = findViewById(R.id.etScreenSize);

        btnAddTablet = findViewById(R.id.btnAddTablet);

        btnAddTablet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tablet tablet = new Tablet();
                tablet.setName(etName.getText().toString());
                tablet.setBrand(etBrand.getText().toString());
                tablet.setOperatingSystem(etOS.getText().toString());
                tablet.setScreenSize(Double.parseDouble(etScreenSize.getText().toString()));
                addTablet(tablet);
                startActivity(new Intent(AddTabletActivity.this, TabletActivity.class));
            }
        });
    }

    public void addTablet(Tablet tablet) {
        String url = "https://60ad9c2b80a61f0017331458.mockapi.io/api/tablets";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(AddTabletActivity.this, "Created", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddTabletActivity.this, "Can't created", Toast.LENGTH_SHORT).show();
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

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
}