package сlient;

import сlient.base.Client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import сourierModel.*;

import static io.restassured.RestAssured.given;

public class СourierClient extends Client {
    public static final String COURIER_URI = BASE_URL + "courier/";
    public static final String COURIER_LOGIN = COURIER_URI + "login/";

    @Step("Create courier {courier}")
    public ValidatableResponse creatingСourier(Сourier courier){
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post(COURIER_URI)
                .then();

    }
    @Step("Login as {courierCredentials}")
    public ValidatableResponse courierLogin(CourierCredentials courierCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(courierCredentials)
                .when()
                .post(COURIER_LOGIN)
                .then();

    }

    @Step("Delete courier {id}")
    public ValidatableResponse deleteCourier(int id) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_URI + id)
                .then();

    }

}
