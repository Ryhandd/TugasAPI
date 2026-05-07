package com.example.api;

import android.content.Intent;
import android.os.Bundle;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvDosen;
    DosenAdapter adapter;
    List<Dosen> dosenList = new ArrayList<>();
    FloatingActionButton fabAdd;

    int editPosition = -1;

    private final ActivityResultLauncher<Intent> formLauncher =
        registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    Dosen dosen = new Dosen();
                    dosen.nip    = data.getStringExtra("nip");
                    dosen.nama   = data.getStringExtra("nama");
                    dosen.alamat = data.getStringExtra("alamat");
                    dosen.foto = data.getStringExtra("foto");
                    boolean isEdit = data.getBooleanExtra("isEdit", false);

                    if (isEdit && editPosition >= 0) {
                        adapter.updateItem(editPosition, dosen);
                    } else {
                        adapter.addItem(dosen);
                        rvDosen.scrollToPosition(0);
                    }
                }
            }
        );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_dark));
        }

        rvDosen = findViewById(R.id.rvDosen);
        rvDosen.setLayoutManager(new LinearLayoutManager(this));

        adapter = new DosenAdapter(dosenList, (dosen, position) -> {
            editPosition = position;
            Intent intent = new Intent(this, FormDosenActivity.class);
            intent.putExtra("mode", "edit");
            intent.putExtra("nip", dosen.nip);
            intent.putExtra("nama", dosen.nama);
            intent.putExtra("alamat", dosen.alamat);
            intent.putExtra("foto", dosen.foto);
            formLauncher.launch(intent);
        });
        rvDosen.setAdapter(adapter);

        fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> {
            editPosition = -1;
            Intent intent = new Intent(this, FormDosenActivity.class);
            intent.putExtra("mode", "add");
            formLauncher.launch(intent);
        });

        getDataDosen();
    }

    private void getDataDosen() {
        ApiService api = RetrofitClient.getClient().create(ApiService.class);
        api.getDosen().enqueue(new Callback<List<Dosen>>() {
            @Override
            public void onResponse(Call<List<Dosen>> call, Response<List<Dosen>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dosenList.clear();
                    dosenList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Dosen>> call, Throwable t) {
                Log.e("ERROR", t.getMessage());
            }
        });
    }
}