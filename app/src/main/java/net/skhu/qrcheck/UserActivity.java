package net.skhu.qrcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
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
    String name;
    String stuNum;

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


        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        name = intent.getStringExtra("name");
        stuNum = intent.getStringExtra("stuNum");

        TextView textView_Info = findViewById(R.id.userInfo);
        textView_Info.setText(name+"\n("+stuNum+")");

        final TableLayout tableLayout = findViewById(R.id.tableLayout);

        Call<List<Att>> call = retrofitService.retrofitAPI.studentAttendances(id);
        call.enqueue(new Callback<List<Att>>() {
            @Override
            public void onResponse(Call<List<Att>> call, Response<List<Att>> response) {
                List<Att> atts = response.body();

                for (int i = 0; i < atts.size(); ++i) {
                    TableRow tableRow = new TableRow(UserActivity.this);
                    tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

                    TextView text = new TextView(UserActivity.this);
                    text.setText(atts.get(i).getName());
                    text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    tableRow.addView(text);

                    for (int j = 0; j < 6; ++j) {
                        text = new TextView(UserActivity.this);
                        text.setText(atts.get(i).getState().get(j).toString());
                        text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                        text.setGravity(Gravity.CENTER);
                        tableRow.addView(text);
                    }
                    tableLayout.addView(tableRow);
                }
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
        intent.putExtra("name", name);
        intent.putExtra("stuNum", stuNum);
        startActivity(intent);
    }
}
