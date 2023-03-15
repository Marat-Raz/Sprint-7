package сourierModel;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {

    public static String login = RandomStringUtils.randomAlphabetic(8);
    public static String password = RandomStringUtils.randomAlphabetic(8);
    public static String firstName = RandomStringUtils.randomAlphabetic(8);

    public static Courier getCourier() {
        String login = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphabetic(10);
        String firstName = RandomStringUtils.randomAlphabetic(10);
        return new Courier(login, password, firstName);
    }

    public static Courier getFirstExistingCourier() {
        return new Courier("existing_Courier", password, firstName);
    }
    public static Courier getSecondExistingCourier() {
        return new Courier("existing_Courier", password+2, firstName+2);
    }

    public static Courier getCourierWithoutLogin() {
        return new Courier("", password, firstName);
    }

    public static Courier getCourierWithoutPassword() {
        return new Courier(login, "", firstName);
    }

    public static Courier getCourierWithoutFirstName() {
        return new Courier(login, password, "");
    }
}