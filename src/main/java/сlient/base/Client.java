package сlient.base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Client {
    public static final String BASE_URL = "http://qa-scooter.praktikum-services.ru/api/v1/";
    protected RequestSpecification getBaseSpec() {
                return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri(BASE_URL)
                .build();
    }
}
