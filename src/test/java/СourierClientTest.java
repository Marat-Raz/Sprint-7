import сlient.СourierClient;
import io.qameta.allure.junit4.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.ValidatableResponse;
import сourierModel.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class СourierClientTest {
    // private Сourier courier;
    private СourierClient сourierClient;
    private int id;

    @BeforeClass
    public static void globalSetUp() {
        RestAssured.filters(
                new RequestLoggingFilter(), new ResponseLoggingFilter(),
                new AllureRestAssured());
    }

    @Before
    public void setUp() {
        сourierClient = new СourierClient();
    }

    @Test
    @DisplayName("Курьера можно создать")
    public void canCreateCourierTest() {
        Сourier courier = CourierGenerator.getCourier();
        ValidatableResponse response = сourierClient.creatingСourier(courier);
        int statusCode = response.extract().statusCode();
        boolean isCourierCreated = response.extract().path("ok");
        assertEquals("Status code is incorrect", SC_CREATED, statusCode);
        assertTrue("Courier is not created", isCourierCreated);

        ValidatableResponse loginResponse = сourierClient.courierLogin(CourierCredentials.from(courier));
        id = loginResponse.extract().path("id");
        assertTrue("Courier ID is not created", id != 0);

        сourierClient.deleteCourier(id);

    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void cannotСreateTwoIdenticalCouriersTest() {
        Сourier courier = CourierGenerator.getCourier();
        сourierClient.creatingСourier(courier);
        ValidatableResponse secondResponse = сourierClient.creatingСourier(courier);
        int statusCode = secondResponse.extract().statusCode();
        assertEquals("Status code is incorrect", SC_CONFLICT, statusCode);

        ValidatableResponse loginResponse = сourierClient.courierLogin(CourierCredentials.from(courier));
        сourierClient.deleteCourier(loginResponse.extract().path("id"));

    }

    @Test
    @DisplayName("Если одного из полей нет при создании курьера, запрос возвращает ошибку *отсутстие поля login")
    public void cannotCreateCourierWithoutLoginTest() {
        Сourier courierWithoutLogin = CourierGenerator.getCourierWithoutLogin();
        ValidatableResponse response = сourierClient.creatingСourier(courierWithoutLogin);
        int statusCode = response.extract().statusCode();

        assertEquals("Status code is incorrect", SC_BAD_REQUEST, statusCode);

    }

    @Test
    @DisplayName("Если одного из полей нет при создании курьера, запрос возвращает ошибку *отсутстие поля password")
    public void cannotCreateCourierWithoutPasswordTest() {
        Сourier courierWithoutPassword = CourierGenerator.getCourierWithoutPassword();
        ValidatableResponse response = сourierClient.creatingСourier(courierWithoutPassword);
        int statusCode = response.extract().statusCode();

        assertEquals("Status code is incorrect", SC_BAD_REQUEST, statusCode);

    }

    @Test
    @DisplayName("Если одного из полей нет при создании курьера, запрос возвращает ошибку *отсутстие поля firstName")
    public void cannotCreateCourierWithoutFirstNameTest() {
        Сourier courierWithoutFirstName = CourierGenerator.getCourierWithoutFirstName();
        ValidatableResponse response = сourierClient.creatingСourier(courierWithoutFirstName);
        int statusCode = response.extract().statusCode();

        assertEquals("Status code is incorrect", SC_BAD_REQUEST, statusCode);

    }

    @Test
    @DisplayName("Если создать пользователя с логином, который уже есть, возвращается ошибка")
    public void cannotCreateCourierIfloginExistsTest() {
        Сourier firstCourier = CourierGenerator.getFirstExistingCourier();
        Сourier secondCourierWithExistingLogin = CourierGenerator.getSecondExistingCourier();
        ValidatableResponse firstResponse = сourierClient.creatingСourier(firstCourier);
        ValidatableResponse secondResponse = сourierClient.creatingСourier(secondCourierWithExistingLogin);
        int statusCode = secondResponse.extract().statusCode();

        assertEquals("Status code is incorrect", SC_CONFLICT, statusCode);

        ValidatableResponse loginResponse = сourierClient.courierLogin(CourierCredentials.from(firstCourier));
        id = loginResponse.extract().path("id");
        сourierClient.deleteCourier(id);

    }
}
