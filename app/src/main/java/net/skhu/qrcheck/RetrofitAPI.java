package net.skhu.qrcheck;

import net.skhu.qrcheck.domain.Att;
import net.skhu.qrcheck.domain.Message;
import net.skhu.qrcheck.domain.Registration;
import net.skhu.qrcheck.domain.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitAPI {
    @GET("api/student/{id}")
    Call<Student> student(@Path("id") int id);

    @GET("api/login/{stuNum}/{password}")
    Call<Student> login(@Path("stuNum") String stuNum, @Path("password") String password);

    @GET("api/student/{id}/attendances")
    Call<List<Att>> studentAttendances(@Path("id") int id);

    @GET("api/late/{roomCode}/{stuId}")
    Call<Message> lateQR(@Path("roomCode") int roomCode, @Path("stuId") int stuId);

    @GET("api/check/{courId}/{stuId}")
    Call<Message> checkQR(@Path("courId") int courId, @Path("stuId") int stuId);
}
