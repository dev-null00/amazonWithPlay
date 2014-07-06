package utils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBAsyncClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import play.Play;

/**
 * Created by perf on 7/6/14.
 */
public class dynamodbClientObject {
    private final String AWS_SECRET_KEY = Play.application().configuration().getString("aws.secret.key");
    private final String AWS_ACCESS_KEY = Play.application().configuration().getString("aws.access.key");
    private final AWSCredentials awsCredentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
    public AmazonDynamoDBAsyncClient client = new AmazonDynamoDBAsyncClient(awsCredentials);
    public DynamoDBMapper mapper = new DynamoDBMapper(client);
}
