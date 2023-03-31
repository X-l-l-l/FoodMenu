import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @invariant isWellFormed()
 */

public class DeliveryService extends Observable implements IDeliveryServiceProcessing {

    DeliveryService deliveryService = this;
    private List<MenuItem> menu = new ArrayList<>();
    private List<MenuItem> searchMenu = new ArrayList<>();
    private HashMap<Order, List<MenuItem>> orders;

    protected boolean isWellFormed() {
        int n = 0;
        for (MenuItem item : menu) {
            n++;
            if (Objects.equals(item.getTitle(), ""))
                return false;
            if (item.getRating() < 0)
                return false;
            if (item.getCalories() < 0)
                return false;
            if (item.getProtein() < 0)
                return false;
            if (item.getFat() < 0)
                return false;
            if (item.getSodium() < 0)
                return false;
            if (item.getPrice() < 0)
                return false;
        }
        return n == menu.size();
    }

    public List<MenuItem> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuItem> menu) {
        this.menu = menu;
    }

    public void setOrder(HashMap<Order, List<MenuItem>> orders) {
        this.orders = orders;
    }

    public DeliveryService() {
        orders = new HashMap<>();
    }

    public void populateMenu() {
        char c = '"';
        var filePath = "src/main/resources/products.csv";
        try {
            menu = Files.lines(Paths.get(filePath))
                    .skip(1).map(s -> s.replace(String.valueOf(c), "").split(",")).map(strings -> new BaseProduct(strings[0], Double.parseDouble(strings[1]), Integer.parseInt(strings[2]), Integer.parseInt(strings[3]), Integer.parseInt(strings[4]), Integer.parseInt(strings[5]), Integer.parseInt(strings[6]))).collect(Collectors.toList());
            menu = menu.stream().distinct().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assert isWellFormed();
        assert menu.size() > 0;
    }

    public void addCompProduct(String title, List<MenuItem> items) {
        assert title != null && items.size() > 0;
        int sz1 = menu.size();
        CompositeProduct cp = new CompositeProduct(title, items);
        menu.add(cp);
        int sz2 = menu.size();
        assert isWellFormed();
        assert sz1 == sz2;
    }

    public void modify(String title, double rating, int calories, int protein, int fat, int sodium, int price, int index) {

        assert title != null && rating >= 0 && calories >= 0 && protein >= 0 && fat >= 0 && sodium >= 0 && price >= 0;
        int sz1 = menu.size();
        deliveryService.getMenu().get(index).setTitle(title);
        deliveryService.getMenu().get(index).setRating(rating);
        deliveryService.getMenu().get(index).setCalories(calories);
        deliveryService.getMenu().get(index).setProtein(protein);
        deliveryService.getMenu().get(index).setFat(fat);
        deliveryService.getMenu().get(index).setSodium(sodium);
        deliveryService.getMenu().get(index).setPrice(price);
        int sz2 = menu.size();
        assert isWellFormed();
        assert sz1 == sz2;

    }


    public void addBaseProduct(String title, double rating, int calories, int protein, int fat, int sodium, int price) {
        assert title != null && rating >= 0 && calories >= 0 && protein >= 0 && fat >= 0 && sodium >= 0 && price >= 0;
        int sz1 = menu.size();
        MenuItem item = new BaseProduct(title, rating, calories, protein, fat, sodium, price);
        menu.add(item);
        int sz2 = menu.size();
        assert sz1 == sz2;
        assert isWellFormed();
    }

    public HashMap<Order, List<MenuItem>> getOrders() {
        return orders;
    }

    public void createOrder(int clientId, List<MenuItem> products) {
        Order order = new Order(clientId, orders.size() + 1);
        orders.put(order, products);

        assert clientId > -1 && products.size() > 0;
        int sz1 = menu.size();

        setChanged();
        notifyObservers(deliveryService);

        BufferedWriter writer;
        int price = 0;
        try {
            String billName = "Bill " + order.getOrderId();
            String str = "Client " + clientId + "\nProducts:\n";
            for (MenuItem menuItem : products) {
                str += menuItem.getTitle() + ", ";
                price += menuItem.getPrice();
            }
            StringBuffer sb = new StringBuffer(str);
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
            str = sb + "\nPrice " + price;
            writer = new BufferedWriter(new FileWriter(billName));
            writer.write(str);
            writer.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        int sz2 = menu.size();
        assert sz1 == sz2;
    }

    public void serialize(String filename, Object obj) {
        try {
            FileOutputStream fileOut =
                    new FileOutputStream("src/main/resources/" + filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(obj);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public void setSearchMenu(List<MenuItem> searchMenu) {
        this.searchMenu = searchMenu;
    }

    public void setOrders(HashMap<Order, List<MenuItem>> orders) {
        this.orders = orders;
    }

    public Object deserialize(String filename) throws IOException, ClassNotFoundException {

        Object obj;
        FileInputStream fileIn = new FileInputStream("src/main/resources/" + filename);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        obj = in.readObject();
        in.close();
        fileIn.close();

        return obj;
    }

    public List<Order> rap1(int min, int max) {
        assert min > 0 && max > min;
        return orders.keySet().stream().filter(order -> order.getOrderDate().getHours() >= min).filter(order -> order.getOrderDate().getHours() < max).collect(Collectors.toList());
    }

    public List<String> rap2(int y) {
        assert y > 0;
        List<MenuItem> list = orders.values().stream().flatMap(List::stream).toList();
        Map<String, Long> counts =
                list.stream().collect(Collectors.groupingBy(MenuItem::getTitle, Collectors.counting()));
        return counts.keySet().stream().filter(x -> counts.get(x) > y).collect(Collectors.toList());
    }

    public static int orderVal(List<MenuItem> list) {

        return list.stream().mapToInt(MenuItem::getPrice).sum();
    }

    public List<Integer> rap3(int q, int z) {
        assert q > 0 && z > 0;
        List<Order> list = orders.keySet().stream().toList();
        List<Order> llist = list.stream().filter(x -> orderVal(orders.get(x)) > z).toList();
        Map<Integer, Long> counts =
                llist.stream().collect(Collectors.groupingBy(Order::getClientId, Collectors.counting()));
        return counts.keySet().stream().filter(x -> counts.get(x) > q).collect(Collectors.toList());
    }

    public Map rap4(String date) {
        assert date != null;
        List<Order> list = orders.keySet().stream().filter(x -> Objects.equals(new SimpleDateFormat("dd/MM/yyyy").format(x.getOrderDate()), date)).toList();
        List<MenuItem> menuItems = list.stream().map(x -> orders.get(x)).toList().stream().flatMap(List::stream).toList();
        Map<String, Long> counts =
                menuItems.stream().collect(Collectors.groupingBy(MenuItem::getTitle, Collectors.counting()));
        counts.keySet().stream();
        return counts;
    }

    public List<MenuItem> search(Client ui) {
        List<MenuItem> filtered = menu;
        if (!ui.getSearchTitle().getText().equals("")) {
            filtered = filtered.stream().filter(menuItem -> menuItem.getTitle().contains(ui.getSearchTitle().getText())).toList();
        }
        if (!ui.getSearchRating().getText().equals("")) {
            filtered = filtered.stream().filter(menuItem -> String.valueOf(menuItem.getRating()).equals(ui.getSearchRating().getText())).toList();
        }
        if (!ui.getSearchCalories().getText().equals("")) {
            filtered = filtered.stream().filter(menuItem -> String.valueOf(menuItem.getCalories()).equals(ui.getSearchCalories().getText())).toList();
        }
        if (!ui.getSearchProtein().getText().equals("")) {
            filtered = filtered.stream().filter(menuItem -> String.valueOf(menuItem.getProtein()).equals(ui.getSearchProtein().getText())).toList();
        }
        if (!ui.getSearchFat().getText().equals("")) {
            filtered = filtered.stream().filter(menuItem -> String.valueOf(menuItem.getFat()).equals(ui.getSearchFat().getText())).toList();
        }
        if (!ui.getSearchSodium().getText().equals("")) {
            filtered = filtered.stream().filter(menuItem -> String.valueOf(menuItem.getSodium()).equals(ui.getSearchSodium().getText())).toList();
        }
        if (!ui.getSearchPrice().getText().equals("")) {
            filtered = filtered.stream().filter(menuItem -> String.valueOf(menuItem.getPrice()).equals(ui.getSearchPrice().getText())).toList();
        }
        return filtered;
    }
}
