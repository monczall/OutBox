package main.java.controllers.auth;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.SceneManager;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class PasswordReset {

    @FXML
    private AnchorPane loginLeftPaneAnchorPane;

    @FXML
    private ImageView passwordResetLogoImageView;

    @FXML
    private Label passwordResetSloganLabel;

    @FXML
    private Label passwordResetWelcomeLabel;

    @FXML
    private Label passwordResetInfoLabel;

    @FXML
    private Button passwordResetExitButtonButton;

    @FXML
    private Button passwordResetReturnButtonButton;

    @FXML
    private AnchorPane loginRightPaneAnchorPane;

    @FXML
    private Label passwordResetResetPasswordLabel;

    @FXML
    private TextField passwordResetEmailField;

    @FXML
    private TextField passwordResetVerificationCodeField;

    @FXML
    private TextField passwordResetPasswordField;

    @FXML
    private TextField passwordResetRepeatPasswordField;

    @FXML
    private Button passwordResetSetNewPasswordButton;

    public void handleSendEmail() throws MessagingException {
        String firstName = "Lukasz";
        String verificationCode = "2137";
        sendEmail(passwordResetEmailField.getText(),firstName,verificationCode);
    }

    public void handleVerifyCodeOnEnterPressed(KeyEvent keyEvent) {
    }

    public void handleVerify(ActionEvent actionEvent) {
    }

    public void passwordReset(){
        System.out.println("Zresetowano");
    }

    public static void sendEmail(String recipient,String firstName, String verificationCode) throws MessagingException {
        System.out.println("Starting process of sending email");
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String outBoxEmailAccount = "outbox2137@gmail.com";
        String outBoxEamilPassword = "zaq1@WSX";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(outBoxEmailAccount, outBoxEamilPassword);
            }
        });

        Message message = prepareMessage(session, outBoxEmailAccount, recipient, firstName, verificationCode);

        Transport.send(message);
        System.out.println("Message sent successfully");
    }

    private static Message prepareMessage(Session session, String outBoxEmailAccount, String recipient, String firstName, String verificationCode) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("OutBox_Support"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("Password Reset Request");
            message.setText("Hello "+ firstName +",\n" +
                    "we have received request to reset your password.\n" +
                    "Here is your verification code to use in our application:\n" +
                    "\n" + verificationCode + "\n\n" +
                    "Please note that this code is valid until you close password reset page.\n" +
                    "If you closed password reset page by accident you can start over.\n" +
                    "\n" +
                    "If you didn't request password reset, please ignore this e-mail.\n" +
                    "Best regards,");
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void clearErrorsOnEmail(KeyEvent keyEvent) {
    }

    public void clearErrorsOnVerificationCode(KeyEvent keyEvent) {
    }

    public void clearErrorsOnPassword(KeyEvent keyEvent) {
    }

    public void clearErrorsOnRepeatPassword(KeyEvent keyEvent) {
    }

    public void handleSendVerificationCode(ActionEvent actionEvent) throws MessagingException {
        handleSendEmail();
    }

    public void handleSendVerificationCodesOnEnterPressed(KeyEvent keyEvent) throws MessagingException {
        if(keyEvent.getCode() == KeyCode.ENTER)
        {
        handleSendEmail();
        }
    }

    public void handleSetNewPassword(ActionEvent actionEvent) {
        passwordReset();
    }

    public void handlePasswordResetOnEnterPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)
        {
            passwordReset();
        }
    }

    public void handleReturn(ActionEvent actionEvent) {
        SceneManager.renderScene("login");
    }

    public void handleExit(ActionEvent actionEvent) {
        Stage stage = (Stage) passwordResetReturnButtonButton.getScene().getWindow();
        stage.close();
    }

}
