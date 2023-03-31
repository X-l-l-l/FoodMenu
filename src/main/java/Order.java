import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private int orderId;
    private int clientId;
    private Date orderDate;

    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public int getClientId() {
        return clientId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", clientId=" + clientId +
                ", orderDate=" + orderDate +
                '}';
    }

    public Order(int clientId, int orderId) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.orderDate = new Date();
    }


    public int hashCode() {
        return orderId + clientId + orderDate.hashCode();
    }
}
