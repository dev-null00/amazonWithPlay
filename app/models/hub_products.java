package models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import play.data.validation.Constraints;
import play.libs.F;
import play.mvc.PathBindable;
import play.mvc.QueryStringBindable;
import utils.dynamodbClientObject;
import java.util.List;
import java.util.Map;

@DynamoDBTable(tableName="hub_products")
public class hub_products implements PathBindable<hub_products>,QueryStringBindable<hub_products> {
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

    public static List<hub_products> findAll(){
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        dynamodbClientObject dbClient = new dynamodbClientObject();
        return dbClient.mapper.scan(hub_products.class,scanExpression);
    }

    public static hub_products findByID(long productid){
        dynamodbClientObject dbClient = new dynamodbClientObject();
        return dbClient.mapper.load(hub_products.class, productid);
    }

    @Override
    public hub_products bind(String key, String value) {
        return findByID(Long.parseLong(value));
    }

    @Override
    public F.Option<hub_products> bind(String key, Map<String, String[]> data) {
        return F.Option.Some(findByID(Long.parseLong(data.get("productid")[0])));
    }

    @Override
    public String unbind(String s) {
        return Long.toString(this.productid);
    }

    @Override
    public String javascriptUnbind() {
        return Long.toString(this.productid);
    }
}


