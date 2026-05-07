package com.example.api;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class DosenAdapter extends RecyclerView.Adapter<DosenAdapter.DosenViewHolder> {

    private List<Dosen> dosenList;
    private OnDosenActionListener listener;

    public interface OnDosenActionListener {
        void onEdit(Dosen dosen, int position);
    }

    public DosenAdapter(List<Dosen> dosenList, OnDosenActionListener listener) {
        this.dosenList = dosenList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DosenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dosen, parent, false);
        return new DosenViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DosenViewHolder holder, int position) {
        Dosen dosen = dosenList.get(position);
        holder.tvNama.setText(dosen.nama);
        holder.tvNip.setText("NIP: " + dosen.nip);
        holder.tvAlamat.setText(dosen.alamat);

        Glide.with(holder.itemView.getContext())
            .load(dosen.foto)
            .placeholder(R.drawable.default_avatar)
            .error(R.drawable.default_avatar)
            .circleCrop()
            .into(holder.imgFoto);

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) {
                listener.onEdit(dosen, holder.getAdapterPosition());
            }
        });

        holder.btnHapus.setOnClickListener(v -> {
            int currentPosition = holder.getAdapterPosition();
            if (currentPosition == RecyclerView.NO_ID) return;

            ApiService api = RetrofitClient.getClient().create(ApiService.class);
            api.deleteDosen("eq." + dosen.nip)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            dosenList.remove(currentPosition);
                            notifyItemRemoved(currentPosition);
                            notifyItemRangeChanged(currentPosition, dosenList.size());
                            Toast.makeText(v.getContext(),
                                    "Dosen berhasil dihapus", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(v.getContext(),
                                    "Gagal menghapus dosen", Toast.LENGTH_SHORT).show();
                            Log.e("API", "Delete failed: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(v.getContext(),
                                "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("API", "Delete error: " + t.getMessage());
                    }
                });
        });
    }

    @Override
    public int getItemCount() {
        return dosenList.size();
    }

    public void updateItem(int position, Dosen dosen) {
        dosenList.set(position, dosen);
        notifyItemChanged(position);
    }

    public void addItem(Dosen dosen) {
        dosenList.add(0, dosen);
        notifyItemInserted(0);
    }

    public static class DosenViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvNip, tvAlamat;
        ImageView imgFoto;
        Button btnEdit, btnHapus;

        public DosenViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama   = itemView.findViewById(R.id.tvNama);
            tvNip    = itemView.findViewById(R.id.tvNip);
            tvAlamat = itemView.findViewById(R.id.tvAlamat);
            imgFoto  = itemView.findViewById(R.id.imgFoto);
            btnEdit  = itemView.findViewById(R.id.btnEdit);
            btnHapus = itemView.findViewById(R.id.btnHapus);
        }
    }
}