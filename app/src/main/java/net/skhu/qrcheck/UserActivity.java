package net.skhu.qrcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.skhu.qrcheck.domain.Att;
import net.skhu.qrcheck.domain.Registration;
import net.skhu.qrcheck.domain.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {
    public static Activity _UserActivity;
    RetrofitService retrofitService;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        MainActivity MA = (MainActivity) MainActivity._MainActivity;
        MA.finish();

        ScanQRActivity SA = (ScanQRActivity) ScanQRActivity._ScanQRActivity;
        if (SA != null)
            SA.finish();

        _UserActivity = UserActivity.this;

        retrofitService = new RetrofitService();
        retrofitService.init();
        retrofitService.retrofitAPI = retrofitService.retrofit.create(RetrofitAPI.class);

        final TextView textView = findViewById(R.id.review);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);


        Call<List<Att>> call = retrofitService.retrofitAPI.studentAttendances(id);
        call.enqueue(new Callback<List<Att>>() {
            @Override
            public void onResponse(Call<List<Att>> call, Response<List<Att>> response) {
                List<Att> atts = response.body();
                for (Att a : atts)
                    textView.append(a.getName() + " " + a.getState() + "\n");
            }

            @Override
            public void onFailure(Call<List<Att>> call, Throwable t) {
                Toast.makeText(UserActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void onQRClick(View view) {
        Intent intent = new Intent(this, ScanQRActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}
