package net.skhu.qrcheck;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import net.skhu.qrcheck.domain.Att;
import net.skhu.qrcheck.domain.Message;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanQRActivity extends AppCompatActivity {
    RetrofitService retrofitService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        retrofitService = new RetrofitService();
        retrofitService.init();
        retrofitService.retrofitAPI = retrofitService.retrofit.create(RetrofitAPI.class);

        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setPrompt("");
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "스캐너 종료", Toast.LENGTH_LONG).show();
            } else {

                String[] split = result.getContents().split(" ");
                Intent intent = getIntent();
                int id = intent.getIntExtra("id", 0);
                final TextView scanResult = findViewById(R.id.scanResult);

                Toast.makeText(ScanQRActivity.this, split[0] + " " + split[1] + " " + id, Toast.LENGTH_LONG).show();

                if (split[0].equals("late")) {

                    Call<Message> call = retrofitService.retrofitAPI.lateQR(Integer.parseInt(split[1]), id);
                    call.enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            Toast.makeText(ScanQRActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            scanResult.setText(response.body().getMessage());
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {
                            Toast.makeText(ScanQRActivity.this, "정보받아오기 실패", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else if(split[0].equals("check")) {
                    Call<Message> call = retrofitService.retrofitAPI.checkQR(Integer.parseInt(split[1]), id);

                    call.enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            Toast.makeText(ScanQRActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            scanResult.setText(response.body().getMessage());
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {
                            Toast.makeText(ScanQRActivity.this, "정보받아오기 실패", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    Toast.makeText(ScanQRActivity.this, "성공회대 출석체크 QR코드가 아닙니다.", Toast.LENGTH_SHORT).show();

                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
