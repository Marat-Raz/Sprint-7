import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import client.ListOfOrdersClient;

import java.util.ArrayList;
import java.util.Collections;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class GetListOfOrdersTest {
    private ListOfOrdersClient listOfOrdersClient;
    @BeforeClass
    public static void globalSetUp() {
        RestAssured.filters(
                new RequestLoggingFilter(), new ResponseLoggingFilter(),
                new AllureRestAssured());
    }

    @Before
    public void setUp() {
        listOfOrdersClient = new ListOfOrdersClient();
    }
    @Test
    @DisplayName("Getting a list of orders")
    public void getListOrdersTest(){
        ValidatableResponse response = listOfOrdersClient.getOrdersList();
        int statusCode = response.extract().statusCode();
        assertEquals("Status code is incorrect", SC_OK, statusCode);
        ArrayList<String> ordersList = response.extract().path("orders");
        assertNotEquals("List is empty", Collections.EMPTY_LIST, ordersList);
    }
}
