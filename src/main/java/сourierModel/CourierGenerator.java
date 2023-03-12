package сourierModel;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {

    public static String login = RandomStringUtils.randomAlphabetic(8);
    public static String password = RandomStringUtils.randomAlphabetic(8);
    public static String firstName = RandomStringUtils.randomAlphabetic(8);

    public static Сourier getCourier() {
        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Сourier(login, password, firstName);
    }

    public static Сourier getFirstExistingCourier() {
        return new Сourier("existing_Courier", password, firstName);
    }
    public static Сourier getSecondExistingCourier() {
        return new Сourier("existing_Courier", password+2, firstName+2);
    }

    public static Сourier getCourierWithoutLogin() {
        return new Сourier("", password, firstName);
    }

    public static Сourier getCourierWithoutPassword() {
        return new Сourier(login, "", firstName);
    }

    public static Сourier getCourierWithoutFirstName() {
        return new Сourier(login, password, "");
    }
}