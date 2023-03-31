import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IDeliveryServiceProcessing {

    /**
     * @pre true
     * @post menu.size() > 0
     */
    void populateMenu();

    /**
     * @pre title != null && items.size() > 0
     * @post menu.size() = menu.size() + 1
     */
    void addCompProduct(String title, List<MenuItem> items);

    /**
     * @pre title != null && rating >= 0 && calories >= 0 && protein >= 0 && fat >= 0 && sodium >= 0 && price >= 0
     * @post menu.size() = menu.size()
     */
    void modify(String title, double rating, int calories, int protein, int fat, int sodium, int price, int index);

    /**
     * @pre title != null && rating >= 0 && calories >= 0 && protein >= 0 && fat >= 0 && sodium >= 0 && price >= 0
     * @post menu.size() = menu.size() + 1
     */
    void addBaseProduct(String title, double rating, int calories, int protein, int fat, int sodium, int price);

    /**
     * @pre clientId > -1 && products.size() > 0
     * @post orders.size() = orders.size()@pre + 1
     */
    void createOrder(int clientId, List<MenuItem> products);

    /**
     * @pre min > 0 && max > min
     * @post @nochange
     */
    List<Order> rap1(int min, int max);

    /**
     * @pre y > 0
     * @post @nochange
     */
    List<String> rap2(int y);

    /**
     * @pre q > 0 && z > 0
     * @post @nochange
     */
    List<Integer> rap3(int q, int z);

    /**
     * @pre date != null
     * @post @nochange
     */
    Map rap4(String date);

}
