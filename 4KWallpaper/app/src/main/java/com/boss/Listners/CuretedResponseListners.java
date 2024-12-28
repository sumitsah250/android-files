package com.boss.Listners;

import com.boss.models.CuretedApiResponse;

public interface CuretedResponseListners {

    void onFetch(CuretedApiResponse response, String message);
    void onError(String message);

    }
