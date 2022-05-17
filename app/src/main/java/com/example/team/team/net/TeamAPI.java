package com.example.team.team.net;

import com.example.team.team.Bean.TeamData;
import com.example.team.team.Bean.UserTeam;
import com.example.team.team.Bean.UsersTeam;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface TeamAPI {
    /**
     * 加入团队
     */
    //加入团队
    @POST("team/paticipation")
    Call<TeamData> joinTeam(@Header("token")String token, @Body TeamData.DataDTO team_data);

    //创建团队
    //传入头像
    @Multipart
    @POST("team")
    Call<TeamData> createTeam(@Header("token") String token, @Part MultipartBody.Part file,@PartMap Map<String, RequestBody> files);

    //查看用户加入的团队
    @GET("team")
    Call<UserTeam> userTeam(@Header("token")String token);

    //查看团队
    @GET("team/{team_id}")
    Call<UsersTeam> usersTeam(@Header("token") String token,@Path("team_id")int team_id);
}
