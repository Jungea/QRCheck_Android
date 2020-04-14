package net.skhu.qrcheck;

import net.skhu.qrcheck.domain.Att;
import net.skhu.qrcheck.domain.Registration;
import net.skhu.qrcheck.domain.Student;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitAPI {
    // GET/POST/DELETE/PUT 메소드들을 인터페이스에 구현하여 사용할 수 있다.
//    @GET("api/registration/{sid}/{cid}")
//    Call<List<Contributor>> contributors(@Path("sid") int sid,  @Path("cid") int cid);
    @GET("api/student/{id}")
    Call<Student> student(@Path("id") int id);

    @GET("api/login/{stuNum}/{password}")
    Call<Student> login(@Path("stuNum") String stuNum, @Path("password") String password);

    @GET("api/student/{id}/attendances")
    Call<List<Att>> studentAttendances(@Path("id") int id);



//    @GET("/repos/{owner}/{repo}/contributors")
//        // JSON Array를 리턴하므로 List<>가 되었다
//    Call<List<Contributor>> contributors(
//            // param 값으로 들어가는 것들이다
//            @Path("owner") String owner,
//            @Path("repo") String repo);
}
