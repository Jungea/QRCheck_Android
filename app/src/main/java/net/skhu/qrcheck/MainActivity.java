package net.skhu.qrcheck;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import net.skhu.qrcheck.domain.Student;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //    public Retrofit retrofit;
//    public RetrofitAPI retrofitAPI;
//    //    private final String BASE_URL = "http://10.0.2.2:8080";
//    private final String BASE_URL = "http://192.168.0.163:8080";
    RetrofitService retrofitService;
    public static Activity _MainActivity;

    static final String TAG = "내로그";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _MainActivity = MainActivity.this;

        retrofitService = new RetrofitService();
        retrofitService.init();
        retrofitService.retrofitAPI = retrofitService.retrofit.create(RetrofitAPI.class);

    }

    public void onLoginClick(View view) {
        EditText editText_id = findViewById(R.id.id);
        String id = editText_id.getText().toString();


        EditText editText_password = findViewById(R.id.password);
        String password = editText_password.getText().toString();


        if (isEmptyOrWhiteSpace(id))
            editText_id.setError("아이디를 입력하세요");
        else if (isEmptyOrWhiteSpace(password))
            editText_password.setError("비밀번호를 입력하세요");
        else {
            Call<Student> call = retrofitService.retrofitAPI.login(id, password);

            call.enqueue(new Callback<Student>() {
                @Override
                public void onResponse(Call<Student> call, Response<Student> response) {
                    String msg = response.body().getName() + " " + response.body().getId();
//                    Toast.makeText(MainActivity.this, "로그인 완료", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this, UserActivity.class);
                    intent.putExtra("id", response.body().getId());
                    intent.putExtra("name", response.body().getName());
                    intent.putExtra("stuNum", response.body().getStuNum());
//                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();

                    startActivity(intent);
                    //액티비티 전환 애니메이션 설정하는 부분
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }

                @Override
                public void onFailure(Call<Student> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "로그인 정보가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                }
            });
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

    static boolean isEmptyOrWhiteSpace(String s) {
        if (s == null) return true;
        return s.trim().length() == 0;
    }

}
