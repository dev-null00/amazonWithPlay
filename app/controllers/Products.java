package controllers;

/**
 * Created by perf on 7/3/14.
 */
import com.google.gson.Gson;
import models.hub_products;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.List;
import utils.dynamodbClientObject;
import org.joda.time.DateTime;

public class Products extends Controller{

    private static final Form<hub_products> productForm = Form.form(hub_products.class);

    public static Result index() {
        List<hub_products> rows = hub_products.findAll();
        String json = new Gson().toJson(rows);
        return ok(json);
    }

    public static Result addProduct() {
        dynamodbClientObject DBmapper = new dynamodbClientObject();
        Form<hub_products> boundForm = productForm.bindFromRequest();
        if(boundForm.hasErrors()) {
            flash("error", "Please correct the form below.");
            return badRequest(boundForm.errorsAsJson());
        }
        hub_products productToAdd = boundForm.get();
        DateTime dt = DateTime.now();
        productToAdd.setLoadDate(dt.toString());
        DBmapper.mapper.save(productToAdd);
        return ok();
    }

    public static Result getProduct(hub_products product) {
        if (product == null)
        {
            return badRequest("no such product");
        }
        Form<hub_products> filledForm = productForm.fill(product);
        if(filledForm.hasErrors()) {
            flash("error", "Please correct the form below.");
            return badRequest("error");
        }
        String json = new Gson().toJson(filledForm.value());
        return ok(json);
    }
}
