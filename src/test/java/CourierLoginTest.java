import client.CourierClient;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import courierModel.CourierCredentials;
import courierModel.CourierGenerator;
import courierModel.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CourierLoginTest {
    private Courier courier;
    private CourierClient courierClient;
    private int id;

    @BeforeClass
    public static void globalSetUp() {
        RestAssured.filters(
                new RequestLoggingFilter(), new ResponseLoggingFilter(),
                new AllureRestAssured()
        );
    }

    @Before
    public void setUp() {
        courier = CourierGenerator.getCourier();
        courierClient = new CourierClient();
        ValidatableResponse response = courierClient.creatingCourier(courier);
    }
    @After
    public void clearData() {
        ValidatableResponse loginResponse = courierClient.courierLogin(CourierCredentials.from(courier));
        id = loginResponse.extract().path("id");
        courierClient.deleteCourier(id);
    }

    @Test
    @DisplayName("Курьер может авторизоваться; для авторизации нужно передать все обязательные поля; успешный запрос возвращает id")
    public void courierСanLogInЕTest() {
        ValidatableResponse loginResponse = courierClient.courierLogin(CourierCredentials.from(courier));
        int statusCode = loginResponse.extract().statusCode();
        id = loginResponse.extract().path("id");

        assertEquals("Status code is incorrect",SC_OK, statusCode);
        assertTrue("Courier ID is not created", id != 0);
    }
    @Test
    @DisplayName("Система вернёт ошибку, если неправильно указать логин или пароль *неверно указан пароль")
    public void errorIfPasswordIncorrectTest() {
        CourierCredentials courierWithoutPassword = new CourierCredentials(courier.getLogin(), "1234");
        ValidatableResponse loginResponse = courierClient.courierLogin(courierWithoutPassword);
        int statusCode = loginResponse.extract().statusCode();

        assertEquals("Status code is incorrect",SC_NOT_FOUND, statusCode);

    }
    @Test
    @DisplayName("Система вернёт ошибку, если неправильно указать логин или пароль *неверно указан логин")
    public void errorIfLoginIncorrectTest() {
        CourierCredentials courierWithoutPassword = new CourierCredentials("existing_Courier", courier.getPassword());
        ValidatableResponse loginResponse = courierClient.courierLogin(courierWithoutPassword);
        int statusCode = loginResponse.extract().statusCode();

        assertEquals("Status code is incorrect",SC_NOT_FOUND, statusCode);

    }
    @Test
    @DisplayName("Система вернёт ошибку, если не указать логин или пароль *не указан логин")
    public void errorIfLoginAbsentTest() {
        CourierCredentials courierWithoutPassword = new CourierCredentials("", courier.getPassword());
        ValidatableResponse loginResponse = courierClient.courierLogin(courierWithoutPassword);
        int statusCode = loginResponse.extract().statusCode();

        assertEquals("Status code is incorrect",SC_BAD_REQUEST, statusCode);

    }
    @Test
    @DisplayName("Система вернёт ошибку, если не указать логин или пароль *не указан пароль")
    public void errorIfPasswordAbsentTest() {
        CourierCredentials courierWithoutPassword = new CourierCredentials(courier.getLogin(), "");
        ValidatableResponse loginResponse = courierClient.courierLogin(courierWithoutPassword);
        int statusCode = loginResponse.extract().statusCode();

        assertEquals("Status code is incorrect",SC_BAD_REQUEST, statusCode);

    }
    @Test
    @DisplayName("Система вернёт ошибку, если авторизоваться под несуществующим пользователем")
    public void errorIfNonExistentUserTest() {
        CourierCredentials courierWithoutPassword = new CourierCredentials("Господин Никто", "Password");
        ValidatableResponse loginResponse = courierClient.courierLogin(courierWithoutPassword);
        int statusCode = loginResponse.extract().statusCode();

        assertEquals("Status code is incorrect",SC_NOT_FOUND, statusCode);

    }
}
