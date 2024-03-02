package Model;

import java.time.LocalDateTime;

/**
 * @Author Andreea Onaci
 * This is a record which has the following data given as parameters: id, localDateTime, idProduct, idClient, quantity,
 * priceUnit and totalPrice which is the equal to quantity*priceUnit
 */

public record Bill(int id, LocalDateTime localDateTime, int idProduct, int idClient, int quantity, double priceUnit, double totalPrice) {

}
