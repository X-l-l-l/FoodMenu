import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Admin {
    private JPanel mainPanel;
    private JTable table1;
    private JTextField priceField;
    private JTextField sodiumField;
    private JTextField fatField;
    private JTextField proteinField;
    private JTextField caloriesField;
    private JTextField ratingField;
    private JTextField titleField;
    private JButton modifyButton;
    private JTable table2;
    private JButton addButton;
    private JButton createButton;
    private JButton createButton1;
    private JTextField textField1;
    private JButton createRap2;
    private JButton createRap4;
    private JButton createRap3;
    private JButton createRap1;
    private JTextField time1;
    private JTextField time2;
    private JTextField minNr;
    private JTextField minNrCl;
    private JTextField minValCl;
    private JTextField day;

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JPanel getPanel1() {
        return mainPanel;
    }

    public JTable getTable1() {
        return table1;
    }

    public JTextField getPriceField() {
        return priceField;
    }

    public JTextField getSodiumField() {
        return sodiumField;
    }

    public JTextField getFatField() {
        return fatField;
    }

    public JTextField getProteinField() {
        return proteinField;
    }

    public JTextField getCaloriesField() {
        return caloriesField;
    }

    public JTextField getRatingField() {
        return ratingField;
    }

    public JTextField getTitleField() {
        return titleField;
    }

    public JButton getModifyButton() {
        return modifyButton;
    }

    public JTable getTable2() {
        return table2;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public JButton getCreateButton1() {
        return createButton1;
    }

    public JTextField getTextField1() {
        return textField1;
    }

    public JButton getCreateRap2() {
        return createRap2;
    }

    public JButton getCreateRap4() {
        return createRap4;
    }

    public JButton getCreateRap3() {
        return createRap3;
    }

    public JButton getCreateRap1() {
        return createRap1;
    }

    public JTextField getTime1() {
        return time1;
    }

    public JTextField getTime2() {
        return time2;
    }

    public JTextField getMinNr() {
        return minNr;
    }

    public JTextField getMinNrCl() {
        return minNrCl;
    }

    public JTextField getMinValCl() {
        return minValCl;
    }

    public JTextField getDay() {
        return day;
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
