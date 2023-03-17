package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import client.base.Client;

import static io.restassured.RestAssured.given;

public class ListOfOrdersClient extends Client {

    private static final String ORDERS_URI = BASE_URL + "orders";

    @Step("Getting a list of orders {ListOfOrders}")
    public ValidatableResponse getOrdersList() {
        return given()
                .header("Content-type", "application/json")
                .when()
                .get(ORDERS_URI)
                .then();
    }


/*
    given()
                .header("Content-type", "application/json")
                .log().all()
                .get("/api/v1/orders")
                .then()
                .assertThat()
                .statusCode(200);
    ListOfOrders listOfOrders = given()
            .header("Content-type", "application/json")
            .log().all()
            .get("/api/v1/orders")
            .body()
            .as(ListOfOrders.class);
        Assert.assertThat(listOfOrders.getOrders(), Matchers.not(Matchers.empty()));*/
}