import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {

    private static HashMap<UserData, Integer> users = new HashMap<>();
    private static final DeliveryService deliveryService = new DeliveryService();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Controller::createGUI);
    }


    private static void createGUI() {
        try {
            users = (HashMap<UserData, Integer>) deliveryService.deserialize("users.ser");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("nu");
        }

        try {
            deliveryService.setOrder((HashMap<Order, List<MenuItem>>) deliveryService.deserialize("orders.ser"));
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("nu");
        }

        try {
            deliveryService.setMenu((List<MenuItem>) deliveryService.deserialize("menu.ser"));
        } catch (Exception e) {
            deliveryService.populateMenu();
        }

        Login ui = new Login();
        JPanel root = ui.getMainPanel();
        JFrame frame = new JFrame();
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        ui.getCreateAccountButton().addActionListener(e -> {
            if (ui.getTextField1().getText().equals("") || ui.getPasswordField1().getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Empty fields!", "Error!", JOptionPane.INFORMATION_MESSAGE);
            }else if(userExists(ui.getTextField1().getText())){
                JOptionPane.showMessageDialog(null, "User exists!", "Error!", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                users.put(new UserData(ui.getTextField1().getText(), ui.getPasswordField1().getText(), users.size() + 1), 2);
                ui.getTextField1().setText("");
                ui.getPasswordField1().setText("");
                JOptionPane.showMessageDialog(null, "Account Created Successfully!", "Created!", JOptionPane.INFORMATION_MESSAGE);
            }
            deliveryService.serialize("users.ser", users);
        });

        ui.getLoginButton().addActionListener(e -> {
            int[] type_id = checkUser(ui);
            ui.getTextField1().setText("");
            ui.getPasswordField1().setText("");
            if (type_id[0] == 2) {
                Client ui1 = new Client();
                JPanel root1 = ui1.getMainPanel();
                JFrame frame1 = new JFrame();
                frame1.setContentPane(root1);
                frame1.pack();
                frame1.setLocationRelativeTo(null);
                frame1.setVisible(true);

                ui1.createTable(deliveryService);

                String[] columns = {"Title", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price"};
                DefaultTableModel model = new DefaultTableModel(columns, 0);
                ui1.getTable2().setModel(model);

                ui1.getAddButton().addActionListener(e1 -> {
                    Integer total;
                    int n = ui1.getTable1().getColumnCount();
                    String[] data = new String[7];
                    for (int i = 0; i < n; i++) {
                        data[i] = (ui1.getTable1().getValueAt(ui1.getTable1().getSelectedRow(), i).toString());
                    }
                    model.addRow(data);
                    total = Integer.valueOf(ui1.getTotal().getText());
                    total += Integer.parseInt(data[6]);
                    ui1.getTotal().setText(String.valueOf(total));
                });

                ui1.getDeleteButton().addActionListener(e12 -> {
                    Integer total;

                    total = Integer.valueOf(ui1.getTotal().getText());
                    total -= Integer.parseInt(ui1.getTable2().getValueAt(ui1.getTable2().getSelectedRow(), 6).toString());
                    model.removeRow(ui1.getTable2().getSelectedRow());
                    ui1.getTotal().setText(String.valueOf(total));
                });

                ui1.getSubmitButton().addActionListener(e15 -> {
                    List<MenuItem> items = new ArrayList<>();
                    for (int i = 0; i < ui1.getTable2().getRowCount(); i++) {
                        int n = ui1.getTable1().getColumnCount();
                        String[] data = new String[7];
                        for (int j = 0; j < n; j++) {
                            data[j] = (ui1.getTable2().getValueAt(i, j).toString());
                        }
                        MenuItem menuItem = new BaseProduct(data[0], Double.parseDouble(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]));
                        items.add(menuItem);
                    }
                    deliveryService.createOrder(type_id[1], items);
                    System.out.println(deliveryService.getOrders());
                    while (model.getRowCount() != 0)
                        model.removeRow(0);
                    ui1.getTotal().setText("0");
                    deliveryService.serialize("orders.ser", deliveryService.getOrders());
                });

                ui1.getButton1().addActionListener(e14 -> {
                    DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
                    ui1.getTable1().setModel(tableModel);
                    for (MenuItem item : deliveryService.search(ui1)) {
                        String[] str = {item.getTitle(), String.valueOf(item.getRating()), String.valueOf(item.getCalories()), String.valueOf(item.getProtein()), String.valueOf(item.getFat()), String.valueOf(item.getSodium()), String.valueOf(item.getPrice())};
                        tableModel.addRow(str);
                    }
                    ui1.getTable1().setModel(tableModel);
                });

            } else if (type_id[0] == 1) {
                Employee ui1 = new Employee();
                deliveryService.addObserver(ui1);
                JPanel root1 = ui1.getMainPanel();
                JFrame frame1 = new JFrame();
                frame1.setContentPane(root1);
                frame1.pack();
                frame1.setLocationRelativeTo(null);
                frame1.setVisible(true);
                frame1.setSize(600, 700);

                ui1.createTree(deliveryService);
            } else if (type_id[0] == 0) {
                Admin ui1 = new Admin();
                JPanel root1 = ui1.getMainPanel();
                JFrame frame1 = new JFrame();
                frame1.setContentPane(root1);
                frame1.pack();
                frame1.setLocationRelativeTo(null);
                frame1.setVisible(true);

                ui1.createTable(deliveryService);

                String[] columns = {"Title", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price"};
                DefaultTableModel model = new DefaultTableModel(columns, 0);
                ui1.getTable2().setModel(model);

                ui1.getAddButton().addActionListener(e13 -> {
                    String[] data = new String[7];
                    for (int i = 0; i < 7; i++) {
                        data[i] = (ui1.getTable1().getValueAt(ui1.getTable1().getSelectedRow(), i).toString());
                    }
                    model.addRow(data);
                });

                ui1.getCreateButton().addActionListener(e16 -> {
                    List<MenuItem> items = new ArrayList<>();
                    for (int i = 0; i < ui1.getTable2().getRowCount(); i++) {
                        String[] data = new String[7];
                        for (int j = 0; j < 7; j++) {
                            data[j] = (ui1.getTable2().getValueAt(i, j).toString());
                        }
                        MenuItem product = new BaseProduct(data[0], Double.parseDouble(data[1]), Integer.parseInt(data[2]), Integer.parseInt(data[3]), Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]));
                        items.add(product);
                    }
                    deliveryService.addCompProduct(ui1.getTextField1().getText(),items);
                    ui1.createTable(deliveryService);
                    while (model.getRowCount() != 0)
                        model.removeRow(0);
                    deliveryService.serialize("menu.ser", deliveryService.getMenu());
                });

                ui1.getCreateButton1().addActionListener(e17 -> {
                    try {
                        deliveryService.addBaseProduct(ui1.getTitleField().getText(), Double.parseDouble(ui1.getRatingField().getText()), Integer.parseInt(ui1.getCaloriesField().getText()), Integer.parseInt(ui1.getProteinField().getText()), Integer.parseInt(ui1.getFatField().getText()), Integer.parseInt(ui1.getSodiumField().getText()), Integer.parseInt(ui1.getPriceField().getText()));
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, "Could not insert product.", "Error", JOptionPane.INFORMATION_MESSAGE);
                    }
                    ui1.createTable(deliveryService);
                    ui1.getTitleField().setText("");
                    ui1.getRatingField().setText("");
                    ui1.getCaloriesField().setText("");
                    ui1.getFatField().setText("");
                    ui1.getSodiumField().setText("");
                    ui1.getPriceField().setText("");
                    ui1.getProteinField().setText("");

                    deliveryService.serialize("menu.ser", deliveryService.getMenu());
                });

                ui1.getModifyButton().addActionListener(e18 -> {

                    String title;
                    double rating;
                    int calories, protein, fat, sodium, price;

                    int row = ui1.getTable1().getSelectedRow();

                    if (!ui1.getTitleField().getText().equals(""))
                        title = ui1.getTitleField().getText();
                    else title = ui1.getTable1().getValueAt(row, 0).toString();

                    if (!ui1.getRatingField().getText().equals(""))
                        rating = Double.parseDouble(ui1.getRatingField().getText());
                    else rating = Double.parseDouble(ui1.getTable1().getValueAt(row, 1).toString());

                    if (!ui1.getCaloriesField().getText().equals(""))
                        calories = Integer.parseInt(ui1.getCaloriesField().getText());
                    else calories = Integer.parseInt(ui1.getTable1().getValueAt(row, 2).toString());

                    if (!ui1.getProteinField().getText().equals(""))
                        protein = Integer.parseInt(ui1.getProteinField().getText());
                    else protein = Integer.parseInt(ui1.getTable1().getValueAt(row, 3).toString());

                    if (!ui1.getFatField().getText().equals(""))
                        fat = Integer.parseInt(ui1.getFatField().getText());
                    else fat = Integer.parseInt(ui1.getTable1().getValueAt(row, 4).toString());

                    if (!ui1.getSodiumField().getText().equals(""))
                        sodium = Integer.parseInt(ui1.getSodiumField().getText());
                    else sodium = Integer.parseInt(ui1.getTable1().getValueAt(row, 5).toString());

                    if (!ui1.getPriceField().getText().equals(""))
                        price = Integer.parseInt(ui1.getPriceField().getText());
                    else price = Integer.parseInt(ui1.getTable1().getValueAt(row, 6).toString());

                    if (rating >= 0 && calories >= 0 && protein >= 0 && fat >= 0 && sodium >= 0 && price >= 0) {
                        deliveryService.modify(title,rating,calories,protein,fat,sodium,price,row);
                        ui1.createTable(deliveryService);
                    } else
                        JOptionPane.showMessageDialog(null, "Invalid input!", "Error", JOptionPane.INFORMATION_MESSAGE);

                    deliveryService.serialize("menu.ser", deliveryService.getMenu());
                });

                ui1.getCreateRap1().addActionListener(e19 -> {
                    String str = "";
                    try {
                        int min = Integer.parseInt(ui1.getTime1().getText());
                        int max = Integer.parseInt(ui1.getTime2().getText());
                        List<Order> rez = deliveryService.rap1(min, max);
                        for (Order o : rez) {
                            str += o.getOrderId() + " " + o.getClientId() + " " + o.getOrderDate() + "\n";
                        }
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, "Invalid input!", "Error", JOptionPane.INFORMATION_MESSAGE);
                    }

                    BufferedWriter writer;
                    try {
                        writer = new BufferedWriter(new FileWriter("Raport1"));
                        writer.write(str);
                        writer.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                ui1.getCreateRap2().addActionListener(e19 -> {
                    String str = "";
                    try {
                        int min = Integer.parseInt(ui1.getMinNr().getText());
                        List<String> rez = deliveryService.rap2(min);
                        for (String s : rez) {
                            str += s + "\n";
                        }
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, "Invalid input!", "Error", JOptionPane.INFORMATION_MESSAGE);
                    }

                    BufferedWriter writer = null;
                    try {
                        writer = new BufferedWriter(new FileWriter("Raport2"));
                        writer.write(str);
                        writer.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                ui1.getCreateRap3().addActionListener(e19 -> {
                    String str = "";
                    try {
                        int minCount = Integer.parseInt(ui1.getMinNrCl().getText());
                        int minVal = Integer.parseInt(ui1.getMinValCl().getText());
                        List<Integer> rez = deliveryService.rap3(minCount, minVal);
                        for (Integer o : rez) {
                            str += "Client " + o + "\n";
                        }
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, "Invalid input!", "Error", JOptionPane.INFORMATION_MESSAGE);
                    }
                    BufferedWriter writer;
                    try {
                        writer = new BufferedWriter(new FileWriter("Raport3"));
                        writer.write(str);
                        writer.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

                ui1.getCreateRap4().addActionListener(e19 -> {
                    String str = "";
                    try {
                        String day = ui1.getDay().getText();
                        Map rez = deliveryService.rap4(day);
                        for (Object o : rez.keySet()) {
                            str += o + " " + rez.get(o) + "\n";
                        }
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, "Invalid input!", "Error", JOptionPane.INFORMATION_MESSAGE);
                    }
                    BufferedWriter writer;
                    try {
                        writer = new BufferedWriter(new FileWriter("Raport4"));
                        writer.write(str);
                        writer.close();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });

            }
        });
    }

    private static int[] checkUser(Login login) {
        users.put(new UserData("ana", "are", 0), 2);
        users.put((new UserData("admin", "admin", 0)), 0);
        users.put((new UserData("emp", "emp", 0)), 1);
        int[] rez = {0, 0};
        for (UserData cr : users.keySet()) {
            if (cr.username.equals(login.getTextField1().getText()) && cr.password.equals(login.getPasswordField1().getText())) {
                rez[0] = users.get(cr);
                rez[1] = cr.id;
                return rez;
            }
        }
        JOptionPane.showMessageDialog(null, "Account doesn't exist!", "Error", JOptionPane.INFORMATION_MESSAGE);
        return new int[]{-1, -1};
    }

    private static boolean userExists(String user)
    {

        for(UserData data:users.keySet())
        {
            if(data.username.equals(user))
                return false;
        }

        return true;
    }

}
