package com.example.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PATCH;
import retrofit2.http.DELETE;
import retrofit2.http.Query;

public interface ApiService {

    @Headers({
        "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InNtbnJlY3Zja2lhb3luaGRxemJsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzY3OTc1MTksImV4cCI6MjA5MjM3MzUxOX0.ntsel2g2Osm-YgBPxnuCz0NyTsj6jouNjztULvMiA20",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InNtbnJlY3Zja2lhb3luaGRxemJsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzY3OTc1MTksImV4cCI6MjA5MjM3MzUxOX0.ntsel2g2Osm-YgBPxnuCz0NyTsj6jouNjztULvMiA20",
        "Accept-Profile: akademik",
        "Content-Type: application/json"
    })
    @GET("rest/v1/Dosen")
    Call<List<Dosen>> getDosen();

    @Headers({
        "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InNtbnJlY3Zja2lhb3luaGRxemJsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzY3OTc1MTksImV4cCI6MjA5MjM3MzUxOX0.ntsel2g2Osm-YgBPxnuCz0NyTsj6jouNjztULvMiA20",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InNtbnJlY3Zja2lhb3luaGRxemJsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzY3OTc1MTksImV4cCI6MjA5MjM3MzUxOX0.ntsel2g2Osm-YgBPxnuCz0NyTsj6jouNjztULvMiA20",
        "Content-Profile: akademik",
        "Content-Type: application/json",
        "Prefer: return=minimal"
    })
    @POST("rest/v1/Dosen")
    Call<Void> addDosen(@Body Dosen dosen);

    @Headers({
        "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InNtbnJlY3Zja2lhb3luaGRxemJsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzY3OTc1MTksImV4cCI6MjA5MjM3MzUxOX0.ntsel2g2Osm-YgBPxnuCz0NyTsj6jouNjztULvMiA20",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InNtbnJlY3Zja2lhb3luaGRxemJsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzY3OTc1MTksImV4cCI6MjA5MjM3MzUxOX0.ntsel2g2Osm-YgBPxnuCz0NyTsj6jouNjztULvMiA20",
        "Content-Profile: akademik",
        "Content-Type: application/json",
        "Prefer: return=minimal"
    })
    @PATCH("rest/v1/Dosen")
    Call<Void> updateDosen(@Query("nip") String nip, @Body Dosen dosen);

    @Headers({
        "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InNtbnJlY3Zja2lhb3luaGRxemJsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzY3OTc1MTksImV4cCI6MjA5MjM3MzUxOX0.ntsel2g2Osm-YgBPxnuCz0NyTsj6jouNjztULvMiA20",
        "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InNtbnJlY3Zja2lhb3luaGRxemJsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzY3OTc1MTksImV4cCI6MjA5MjM3MzUxOX0.ntsel2g2Osm-YgBPxnuCz0NyTsj6jouNjztULvMiA20",
        "Content-Profile: akademik",
        "Content-Type: application/json"
    })
    @DELETE("rest/v1/Dosen")
    Call<Void> deleteDosen(@Query("nip") String nip);
}