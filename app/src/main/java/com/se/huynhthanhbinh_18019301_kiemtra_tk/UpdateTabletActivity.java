package com.se.huynhthanhbinh_18019301_kiemtra_tk;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class UpdateTabletActivity extends AppCompatActivity {
    private EditText etName, etBrand, etOS, etScreenSize;
    private Button btnUpdate;
    private Tablet tablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tablet);

        etName = findViewById(R.id.etName);
        etBrand = findViewById(R.id.etBrand);
        etOS = findViewById(R.id.etOperatingSystem);
        etScreenSize = findViewById(R.id.etScreenSize);

        btnUpdate = findViewById(R.id.btnUpdate);

        tablet = (Tablet) getIntent().getSerializableExtra("tablet");

        etName.setText(tablet.getName());
        etBrand.setText(tablet.getBrand());
        etOS.setText(tablet.getOperatingSystem());
        etScreenSize.setText(String.valueOf(tablet.getScreenSize()));



        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tablet updatedTablet = new Tablet();
                updatedTablet.setId(tablet.getId());
                updatedTablet.setName(etName.getText().toString().trim());
                updatedTablet.setBrand(etBrand.getText().toString().trim());
                updatedTablet.setOperatingSystem(etOS.getText().toString().trim());
                updatedTablet.setScreenSize(Double.parseDouble(etScreenSize.getText().toString().trim()));
                updateTablet(updatedTablet);
                startActivity(new Intent(UpdateTabletActivity.this, TabletActivity.class));
            }
        });
    }

    public void updateTablet(Tablet tablet) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://60ad9c2b80a61f0017331458.mockapi.io/api/tablets/" + tablet.getId();
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(UpdateTabletActivity.this, "Updated", Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(this, "Error when update tablet", Toast.LENGTH_SHORT).show()) {
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