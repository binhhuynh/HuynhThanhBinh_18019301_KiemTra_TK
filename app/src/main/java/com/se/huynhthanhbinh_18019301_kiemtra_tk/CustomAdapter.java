package com.se.huynhthanhbinh_18019301_kiemtra_tk;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<Tablet> tabletList;
    private Activity context;

    public CustomAdapter(Activity context) {
        this.context = context;
        getTablets();
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        Tablet tablet = tabletList.get(position);

        holder.tvName.setText(tablet.getName());
        holder.tvBrand.setText(tablet.getBrand());
        holder.tvOperatingSystem.setText(tablet.getOperatingSystem());
        holder.tvScreenSize.setText(String.valueOf(tablet.getScreenSize()));

        holder.btnRemove.setOnClickListener(view -> {
            Tablet t = tabletList.get(holder.getAdapterPosition());

            RequestQueue queue = Volley.newRequestQueue(context);
            String url = "https://60ad9c2b80a61f0017331458.mockapi.io/api/tablets/" + t.getId();

            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, response -> Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show(), error -> Toast.makeText(context, "Can't deleted", Toast.LENGTH_SHORT).show());

            queue.add(stringRequest);

            tabletList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, tabletList.size());
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tablet t2 = tabletList.get(holder.getAdapterPosition());

                Intent intent = new Intent(view.getContext(), UpdateTabletActivity.class);
                intent.putExtra("tablet", t2);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tabletList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvName, tvBrand, tvOperatingSystem, tvScreenSize;
        private Button btnEdit, btnRemove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvBrand = itemView.findViewById(R.id.tvBrand);
            tvOperatingSystem = itemView.findViewById(R.id.tvOperatingSystem);
            tvScreenSize = itemView.findViewById(R.id.tvScreenSize);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
    }

    public void getTablets() {
        Gson gson = new Gson();
        tabletList = new ArrayList<>();
        String url = "https://60ad9c2b80a61f0017331458.mockapi.io/api/tablets";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                for (int i = 0; i < response.length(); i++) {
                    JSONObject jsonObject = (JSONObject) response.get(i);
                    tabletList.add(gson.fromJson(String.valueOf(jsonObject), Tablet.class));

                }
                notifyDataSetChanged();
            } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> Toast.makeText(context, "Can't get tablets", Toast.LENGTH_SHORT).show());
        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(jsonArrayRequest);
    }
}
