package com.se.huynhthanhbinh_18019301_kiemtra_tk;

import android.app.Activity;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<Tablet> tabletList;
    private Activity context;

    public CustomAdapter(List<Tablet> tabletList, Activity context) {
        this.tabletList = tabletList;
        this.context = context;
        notifyDataSetChanged();
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

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tablet t = tabletList.get(holder.getAdapterPosition());

                RequestQueue queue = Volley.newRequestQueue(context);
                String url = "https://60ad9c2b80a61f0017331458.mockapi.io/api/" + t.getId();

                StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                    }
                }, error -> Toast.makeText(context, "Can't deleted", Toast.LENGTH_SHORT).show());

                queue.add(stringRequest);

                tabletList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, tabletList.size());
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
}
