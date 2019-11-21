package io.github.burnieliang.metro.dao;


import com.couchbase.client.deps.com.fasterxml.jackson.annotation.JsonInclude;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.couchbase.client.java.document.JsonDocument;


import com.couchbase.client.java.document.json.JsonObject;
import io.github.burnieliang.metro.vo.baidu.query.ResponseVO;

public class CouchbaseDaoImpl implements BaseDao {

    private Cluster cluster = null;
    private Bucket bucket;
    private ObjectMapper objectMapper;


    public CouchbaseDaoImpl(String ip, String userName, String password, String bucketName) {
        cluster = CouchbaseCluster.create(ip);
        cluster.authenticate(userName, password);
        bucket = cluster.openBucket(bucketName);
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    @Override
    public void upsert(ResponseVO responseVO, String cityName) {
        if (responseVO != null) {
            try {
                JsonObject jsonObject = JsonObject.fromJson(objectMapper.writeValueAsString(responseVO));
                bucket.upsert(JsonDocument.create(cityName, jsonObject));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public JsonDocument get(String cityName) {
        return bucket.get(cityName);
    }
}
