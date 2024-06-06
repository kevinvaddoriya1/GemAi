package com.techbuddys.appui.interfaces;

import com.techbuddys.appui.model.ConversationModel;
import com.techbuddys.appui.model.Image;
import com.techbuddys.appui.model.InsertMessagesModel;
import com.techbuddys.appui.model.LikeModel;
import com.techbuddys.appui.model.LoginModel;
import com.techbuddys.appui.model.RegisterModel;
import com.techbuddys.appui.model.TopicModel;
import com.techbuddys.appui.model.InsertTopicModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterModel> setUserData(
            @Field("name") String name,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginModel> checkLoginCredentials(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("topic.php")
    Call<List<TopicModel>> getTopics(
            @Field("u_id") String u_id
    );

    @FormUrlEncoded
    @POST("fetchConversation.php")
    Call<List<ConversationModel>> getConversation(
            @Field("u_id") String u_id,
            @Field("topic_id") String topic_id
    );

    @FormUrlEncoded
    @POST("insertTopic.php")
    Call<InsertTopicModel> insertTopic(
            @Field("topic_name") String topic_name
    );

    @FormUrlEncoded
    @POST("insertMessages.php")
    Call<InsertMessagesModel> setMessages(
            @Field("topic_id") int topic_id,
            @Field("u_id") String u_id,
            @Field("content") String content,
            @Field("bot_resp") boolean bot_resp
    );

    @FormUrlEncoded
    @POST("updateLikes.php")
    Call<LikeModel> updateLikes(
            @Field("image_url") String imgUrl,
            @Field("user_id") String userId,
            @Field("isLiked") int isLiked
            );

    @FormUrlEncoded
    @POST("saveImage.php")
    Call<LikeModel> saveImage(
            @Field("image_url") String imgUrl,
            @Field("user_id") String userId,
            @Field("isLiked") int isLiked
    );
    @FormUrlEncoded
    @POST("fetchImages.php")
    Call<List<Image>> getLikedImages(
            @Field("u_id") String userId,
            @Field("isLiked") int isLiked
    );
}
