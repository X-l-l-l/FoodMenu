import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.Observable;
import java.util.Observer;

public class Employee implements Observer {
    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JTree getTree() {
        return tree;
    }

    private JPanel mainPanel;
    private JTree tree;

    public void createTree(DeliveryService deliveryService)
    {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Orders");
        for(Order order:deliveryService.getOrders().keySet())
        {
            DefaultMutableTreeNode n = new DefaultMutableTreeNode("Client Id: "+order.getClientId()+" Order Id: "+order.getOrderId());
            root.add(n);
            for(MenuItem m : deliveryService.getOrders().get(order))
            {
                n.add(new DefaultMutableTreeNode(m.getTitle()));
            }
        }
        DefaultTreeModel t = new DefaultTreeModel(root,true);
        tree.setModel(t);
    }

    @Override
    public void update(Observable o, Object arg) {
        DeliveryService deliveryService = (DeliveryService) arg;
        createTree(deliveryService);
        JOptionPane.showMessageDialog(null, "New Order!", "PopUp", JOptionPane.INFORMATION_MESSAGE);

    }
}
