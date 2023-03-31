import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Client {
    private JTable table1;
    private JTable table2;

    public JTable getTable1() {
        return table1;
    }

    public JTable getTable2() {
        return table2;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private JButton addButton;
    private JPanel mainPanel;
    private JLabel total;
    private JButton deleteButton;
    private JButton submitButton;
    private JTextField searchTitle;
    private JTextField searchPrice;
    private JTextField searchRating;
    private JTextField searchCalories;
    private JTextField searchProtein;
    private JTextField searchFat;
    private JTextField searchSodium;
    private JButton button1;

    public JTextField getSearchTitle() {
        return searchTitle;
    }

    public JTextField getSearchPrice() {
        return searchPrice;
    }

    public JTextField getSearchRating() {
        return searchRating;
    }

    public JTextField getSearchCalories() {
        return searchCalories;
    }

    public JTextField getSearchProtein() {
        return searchProtein;
    }

    public JTextField getSearchFat() {
        return searchFat;
    }

    public JTextField getSearchSodium() {
        return searchSodium;
    }

    public JButton getButton1() {
        return button1;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getSubmitButton() {
        return submitButton;
    }

    public JLabel getTotal() {
        return total;
    }

    public void createTable(DeliveryService deliveryService) {

        String[][] data = new String[deliveryService.getMenu().size()][7];
        int i = 0, j;
        for (MenuItem c :
                deliveryService.getMenu()) {
            j = 0;
            data[i][j++] = c.getTitle();
            data[i][j++] = String.valueOf((c).getRating());
            data[i][j++] = String.valueOf((c).getCalories());
            data[i][j++] = String.valueOf((c).getProtein());
            data[i][j++] = String.valueOf((c).getFat());
            data[i][j++] = String.valueOf((c).getSodium());
            data[i][j] = String.valueOf((c).getPrice());
            i++;
        }
        String[] columnNames = {"Title", "Rating", "Calories", "Protein", "Fat", "Sodium", "Price"};

        table1.setModel(new DefaultTableModel(
                data, columnNames
        ));
    }


}
