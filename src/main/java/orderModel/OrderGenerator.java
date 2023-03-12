package orderModel;
import com.github.javafaker.Faker;

import java.util.Random;

public class OrderGenerator {

    public static Faker faker = new Faker();
    public static Random random = new Random();
    public static String[] color;


    public static Order getOrder(String[] color) {
         String firstName = faker.name().firstName();
         String lastName = faker.name().lastName();
         String address = faker.address().fullAddress();
         int metroStation = random.nextInt(237)+1; //по количеству станций метро
         String phone = "+7 800 355 35 35";
         int rentTime = random.nextInt(10)+1;
         String deliveryDate = "2024-06-01";
         String comment = "Saske, come back to Konoha";

        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, color, comment);
    }

}