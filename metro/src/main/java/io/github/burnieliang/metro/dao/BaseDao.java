package io.github.burnieliang.metro.dao;

import com.couchbase.client.java.document.JsonDocument;
import io.github.burnieliang.metro.vo.baidu.query.ResponseVO;

public interface BaseDao {

    void upsert(ResponseVO responseVO, String cityName);

    JsonDocument get(String cityName);

}
