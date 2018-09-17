package test.com.assessment.Interface;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import test.com.assessment.Class.ModelClass;

public interface RetrofitAPI {
    //@POST("oauth/access_token")
    String BASE_URL="http://www.checkgaadi.com/reporting/vrm/api/test_new/";
    @Headers("Content-Type: application/json")
    @POST("int/gettabledata.php")
    Call<ModelClass> getTokenAccess(@Header("Authorization") String cred, @Body RequestBody tokenReequest);
//    Call<ResponseBody> getData(@Field("username") String user, @Field("password") String pass);
    //Call<ResponseBody> getData(@Query("username") String credentials, @Query("password") String password);

}
