import javax.swing.*;

public class Login {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton createAccountButton;
    private JButton loginButton;
    private JPanel mainPanel;

    public JTextField getTextField1() {
        return textField1;
    }

    public void setTextField1(JTextField textField1) {
        this.textField1 = textField1;
    }

    public JPasswordField getPasswordField1() {
        return passwordField1;
    }

    public void setPasswordField1(JPasswordField passwordField1) {
        this.passwordField1 = passwordField1;
    }

    public JButton getCreateAccountButton() {
        return createAccountButton;
    }

    public void setCreateAccountButton(JButton createAccountButton) {
        this.createAccountButton = createAccountButton;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(JButton loginButton) {
        this.loginButton = loginButton;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }
}
