package com.boss.Listners;

import com.boss.models.SearchApiResponse;

public interface SearchResponseListners {
    void onFetch(SearchApiResponse responseListners, String message);
    void onError(String message);
}
