package com.wms.task.http.api;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiData {
    @POST("/microsaasapi/datainterface/api3/httpEntrance/json/WCS/WCS_NOTICE")
    Call<Object> apiNotice(
            @Body Object o
    );

    @POST("/microsaasapi/datainterface/api3/httpEntrance/json/WCS/WCS_GET_INOUTDATA")
    Call<Object> apiData(
            @Body Object o
    );
}
