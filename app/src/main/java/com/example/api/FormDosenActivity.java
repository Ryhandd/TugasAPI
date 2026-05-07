package com.example.api;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormDosenActivity extends AppCompatActivity {

    EditText etNama, etNip, etAlamat, etFoto;
    Button btnSimpan;
    String mode = "add";
    String nipLama = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_dosen);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_dark));
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etNama   = findViewById(R.id.etNama);
        etNip    = findViewById(R.id.etNip);
        etAlamat = findViewById(R.id.etAlamat);
        etFoto = findViewById(R.id.etFoto);
        btnSimpan = findViewById(R.id.btnSimpan);

        mode = getIntent().getStringExtra("mode");
        if ("edit".equals(mode)) {
            getSupportActionBar().setTitle("Edit Dosen");
            nipLama = getIntent().getStringExtra("nip");
            etNama.setText(getIntent().getStringExtra("nama"));
            etNip.setText(nipLama);
            etAlamat.setText(getIntent().getStringExtra("alamat"));
            etFoto.setText(getIntent().getStringExtra("foto"));
            etNip.setEnabled(false);
        } else {
            getSupportActionBar().setTitle("Tambah Dosen");
        }

        btnSimpan.setOnClickListener(v -> {
            String nama   = etNama.getText().toString().trim();
            String nip    = etNip.getText().toString().trim();
            String alamat = etAlamat.getText().toString().trim();
            String foto   = etFoto.getText().toString().trim();
            if (nama.isEmpty() || nip.isEmpty() || alamat.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show();
                return;
            }

            Dosen dosen = new Dosen();
            dosen.nama   = nama;
            dosen.nip    = nip;
            dosen.alamat = alamat;
            dosen.foto   = foto.isEmpty() ? null : foto;

            btnSimpan.setEnabled(false);
            btnSimpan.setText("Menyimpan...");

            ApiService api = RetrofitClient.getClient().create(ApiService.class);

            if ("edit".equals(mode)) {
                api.updateDosen("eq." + nipLama, dosen)
                    .enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Log.d("API", "Update code: " + response.code());
                            if (response.isSuccessful()) {
                                Intent result = new Intent();
                                result.putExtra("nip", nip);
                                result.putExtra("nama", nama);
                                result.putExtra("alamat", alamat);
                                result.putExtra("foto", foto);
                                result.putExtra("isEdit", true);
                                setResult(RESULT_OK, result);
                                Toast.makeText(FormDosenActivity.this,
                                        "Dosen berhasil diperbarui", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                btnSimpan.setEnabled(true);
                                btnSimpan.setText("Simpan");
                                try {
                                    String errorBody = response.errorBody().string();
                                    Log.e("API", "Error body: " + errorBody);
                                } catch (Exception e) {
                                    Log.e("API", "Cant read error: " + e.getMessage());
                                }
                                Toast.makeText(FormDosenActivity.this,
                                        "Gagal memperbarui: " + response.code(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            btnSimpan.setEnabled(true);
                            btnSimpan.setText("Simpan");
                            Log.e("API", "Failure: " + t.getMessage());
                            Toast.makeText(FormDosenActivity.this,
                                    "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            } else {
                api.addDosen(dosen).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.d("API", "Add code: " + response.code());
                        if (response.isSuccessful()) {
                            Intent result = new Intent();
                            result.putExtra("nip", nip);
                            result.putExtra("nama", nama);
                            result.putExtra("alamat", alamat);
                            result.putExtra("foto", foto);
                            result.putExtra("isEdit", false);
                            setResult(RESULT_OK, result);
                            Toast.makeText(FormDosenActivity.this,
                                    "Dosen berhasil ditambahkan", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            btnSimpan.setEnabled(true);
                            btnSimpan.setText("Simpan");
                            try {
                                Log.e("API", response.errorBody().string());
                            } catch (Exception ignored) {}
                            Toast.makeText(FormDosenActivity.this,
                                    "Gagal menambahkan: " + response.code(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        btnSimpan.setEnabled(true);
                        btnSimpan.setText("Simpan");
                        Log.e("API", "Failure: " + t.getMessage());
                        Toast.makeText(FormDosenActivity.this,
                                "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}