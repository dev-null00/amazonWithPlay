package models;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import play.Play;
import play.data.validation.Constraints;

import java.util.List;
import java.util.Map;

@DynamoDBTable(tableName="hub_products")
public class hub_products {
    public static final String AWS_SECRET_KEY = Play.application().configuration().getString("aws.secret.key");
    public static final String AWS_ACCESS_KEY = Play.application().configuration().getString("aws.access.key");
    public static final AWSCredentials awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);


    @Constraints.Required
    private long productid;
    @Constraints.Required
    private String loadDate;
    private String recordSource;

    @DynamoDBHashKey(attributeName="productid")
    public long getProductid() { return productid;}
    public void setProductid(long productid) {this.productid = productid;}

    @DynamoDBAttribute(attributeName="loadDate")
    public String getLoadDate() {return loadDate; }
    public void setLoadDate(String loadDate) { this.loadDate = loadDate; }

    @DynamoDBAttribute(attributeName="recordSource")
    public String getRecordSource() { return recordSource; }
    public void setRecordSource(String recordSource) { this.recordSource = recordSource; }

    public static List<hub_products> findAll() {
        AmazonDynamoDBAsyncClient client = new AmazonDynamoDBAsyncClient(awsCredentials);
        DynamoDBMapper mapper = new DynamoDBMapper(client);
        hub_products hashKeyValues = new hub_products();
        long productID=1;
        hashKeyValues.setProductid(productID);
        DynamoDBQueryExpression<hub_products> queryExpression = new DynamoDBQueryExpression<hub_products>().withHashKeyValues(hashKeyValues);
        queryExpression.setHashKeyValues(hashKeyValues);
        List<hub_products> itemList = mapper.query(hub_products.class, queryExpression);
        return itemList;
    }

    public static List<Map<String, AttributeValue>> scanTheTable(){
        AmazonDynamoDBAsyncClient client = new AmazonDynamoDBAsyncClient(awsCredentials);
        ScanResult result =null;
        ScanRequest req = new ScanRequest();
        req.setTableName("hub_products");

        if(result != null){
            req.setExclusiveStartKey(result.getLastEvaluatedKey());
        }

        result = client.scan(req);

        List<Map<String, AttributeValue>> rows = result.getItems();
        return rows;
    }
}


