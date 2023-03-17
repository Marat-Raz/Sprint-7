import client.CourierClient;
import io.qameta.allure.junit4.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import courierModel.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class CourierClientTest {
    private CourierClient courierClient;
    private int id;
    Courier courier;
    ValidatableResponse response;

    @BeforeClass
    public static void globalSetUp() {
        RestAssured.filters(
                new RequestLoggingFilter(), new ResponseLoggingFilter(),
                new AllureRestAssured());
    }

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getCourier();
        response = courierClient.creatingCourier(courier);
        ValidatableResponse loginResponse = courierClient.courierLogin(CourierCredentials.from(courier));
        id = loginResponse.extract().path("id");
    }
    @After
    public void cleanUp() {

        courierClient.deleteCourier(id);
    }

    @Test
    @DisplayName("Курьера можно создать")
    public void canCreateCourierTest() {
        int statusCode = response.extract().statusCode();
        boolean isCourierCreated = response.extract().path("ok");

        assertEquals("Status code is incorrect", SC_CREATED, statusCode);
        assertTrue("Courier is not created", isCourierCreated);
        assertTrue("Courier ID is not created", id != 0);
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void cannotCreateTwoIdenticalCouriersTest() {
        ValidatableResponse secondResponse = courierClient.creatingCourier(courier);
        int statusCode = secondResponse.extract().statusCode();

        assertEquals("Status code is incorrect", SC_CONFLICT, statusCode);
    }

    @Test
    @DisplayName("Если одного из полей нет при создании курьера, запрос возвращает ошибку *отсутстие поля login")
    public void cannotCreateCourierWithoutLoginTest() {
        Courier courierWithoutLogin = CourierGenerator.getCourierWithoutLogin();
        ValidatableResponse response = courierClient.creatingCourier(courierWithoutLogin);
        int statusCode = response.extract().statusCode();

        assertEquals("Status code is incorrect", SC_BAD_REQUEST, statusCode);
    }

    @Test
    @DisplayName("Если одного из полей нет при создании курьера, запрос возвращает ошибку *отсутстие поля password")
    public void cannotCreateCourierWithoutPasswordTest() {
        Courier courierWithoutPassword = CourierGenerator.getCourierWithoutPassword();
        ValidatableResponse response = courierClient.creatingCourier(courierWithoutPassword);
        int statusCode = response.extract().statusCode();

        assertEquals("Status code is incorrect", SC_BAD_REQUEST, statusCode);
    }

    @Test
    @DisplayName("Если одного из полей нет при создании курьера, запрос возвращает ошибку *отсутстие поля firstName")
    public void cannotCreateCourierWithoutFirstNameTest() {
        Courier courierWithoutFirstName = CourierGenerator.getCourierWithoutFirstName();
        ValidatableResponse response = courierClient.creatingCourier(courierWithoutFirstName);
        int statusCode = response.extract().statusCode();

        assertEquals("Status code is incorrect", SC_BAD_REQUEST, statusCode);
    }

    @Test
    @DisplayName("Если создать пользователя с логином, который уже есть, возвращается ошибка")
    public void cannotCreateCourierIfLoginExistsTest() {
        ValidatableResponse secondResponse = courierClient.creatingCourier(courier);
        int statusCode = secondResponse.extract().statusCode();

        assertEquals("Status code is incorrect", SC_CONFLICT, statusCode);
    }
}
