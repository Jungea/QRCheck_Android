package net.skhu.qrcheck;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    public static Activity _ScanQRActivity;
    RetrofitService retrofitService;
    Intent intent;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        UserActivity UA = (UserActivity) UserActivity._UserActivity;
        UA.finish();

        _ScanQRActivity = ScanQRActivity.this;

        retrofitService = new RetrofitService();
        retrofitService.init();
        retrofitService.retrofitAPI = retrofitService.retrofit.create(RetrofitAPI.class);

        intent = getIntent();
        id = intent.getIntExtra("id", 0);
        String name = intent.getStringExtra("name");
        ;
        String stuNum = intent.getStringExtra("stuNum");
        intent = new Intent(this, UserActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("stuNum", stuNum);

        TextView textView_Info = findViewById(R.id.userInfo);
        textView_Info.setText(name + "(" + stuNum + ")");


        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setBeepEnabled(false);
        intentIntegrator.setPrompt("");
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        final TextView scanResult = findViewById(R.id.scanResult);
        if (result != null) {
            if (result.getContents() == null) {
                scanResult.setText("스캐너 종료");
            } else {

                String[] split = result.getContents().split(" ");


                Toast.makeText(ScanQRActivity.this, split[0] + " " + split[1] + " " + id, Toast.LENGTH_LONG).show();

                if (split[0].equals("late")) {

                    Call<Message> call = retrofitService.retrofitAPI.lateQR(Integer.parseInt(split[1]), id);
                    call.enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            scanResult.setText(response.body().getMessage());
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {
                            scanResult.setText("정보를 받아오지 못했습니다.");
                        }
                    });

                } else if (split[0].equals("check")) {
                    Call<Message> call = retrofitService.retrofitAPI.checkQR(Integer.parseInt(split[1]), id);

                    call.enqueue(new Callback<Message>() {
                        @Override
                        public void onResponse(Call<Message> call, Response<Message> response) {
                            Toast.makeText(ScanQRActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                            scanResult.setText(response.body().getMessage());
                        }

                        @Override
                        public void onFailure(Call<Message> call, Throwable t) {
                            scanResult.setText("정보를 받아오지 못했습니다.");
                        }
                    });

                } else {
                    scanResult.setText("성공회대 출석체크 QR코드가 아닙니다.");
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private long backKeyPressedTime = 0;
    private Toast toast;

    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(this, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            finish();
            toast.cancel();
        }

    }

    public void onOkClick(View view) {
        startActivity(intent);
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }
}
