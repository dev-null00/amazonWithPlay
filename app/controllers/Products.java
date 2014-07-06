package controllers;

/**
 * Created by perf on 7/3/14.
 */
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import models.hub_products;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.List;
import java.util.Map;

public class Products extends Controller{

    private static final Form<hub_products> productForm = Form.form(hub_products.class);

    public static Result list() {
        List<hub_products> hubproductses = hub_products.findAll();
        return ok(hubproductses.get(0).toString());
    }

    public static Result index() {
        List<Map<String, AttributeValue>> rows = hub_products.scanTheTable();

        return ok(rows.toString());
    }
}
