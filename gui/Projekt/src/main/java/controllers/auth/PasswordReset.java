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
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import main.java.SceneManager;
import main.java.features.Alerts;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    private Circle passwordResetEmailCircle;

    @FXML
    private TextField passwordResetVerificationCodeField;

    @FXML
    private Circle passwordResetVerificationCodeCircle;

    @FXML
    private TextField passwordResetPasswordField;

    @FXML
    private Circle passwordResetPasswordCircle;

    @FXML
    private TextField passwordResetConfirmPasswordField;

    @FXML
    private Circle passwordResetConfirmPasswordCircle;

    @FXML
    private Label passwordResetSendCodeLabel;

    @FXML
    private Button passwordResetSendCodeButton;

    @FXML
    private Label passwordResetVerifyCodeLabel;

    @FXML
    private Button passwordResetVerifyCodeButton;

    @FXML
    private Button passwordResetSetNewPasswordButton;

    @FXML
    private Label passwordResetSixCharsRequirement;

    @FXML
    private Label passwordResetSmallLetterRequirement;

    @FXML
    private Label passwordResetBigLetterRequirement;

    @FXML
    private Label passwordResetNumberRequirement;

    @FXML
    private Label passwordResetSpecialCharRequirement;

    @FXML
    private Label passwordResetSamePasswordsRequirement;


    private String verificationCode = "";


    public void handleSendVerificationCode(ActionEvent actionEvent) throws MessagingException {
        handleSendEmail();
    }

    public void handleSendVerificationCodesOnEnterPressed(KeyEvent keyEvent) throws MessagingException {
        if(keyEvent.getCode() == KeyCode.ENTER)
        {
            handleSendEmail();
        }
    }

    public void handleSendEmail() throws MessagingException {
        if(isEmail(passwordResetEmailField.getText())){

            //TUTAJ SPRAWDZENIE MAILA Z BAZA DANYCH

            String firstName = "Lukasz";
            //Generating verification code
            Random rand = new Random();
            int vCode1 = rand.nextInt(9);
            int vCode2 = rand.nextInt(9);
            int vCode3 = rand.nextInt(9);
            int vCode4 = rand.nextInt(9);
            verificationCode = "" + vCode1 + vCode2 + vCode3 + vCode4 + "";
            sendEmail(passwordResetEmailField.getText(),firstName,verificationCode);

            //Blocking email field and send code button
            passwordResetEmailField.setDisable(true);
            passwordResetEmailCircle.getStyleClass().clear();
            passwordResetEmailCircle.getStyleClass().add("circleCorrect");

            passwordResetEmailField.getStyleClass().clear();
            passwordResetEmailField.getStyleClass().add("textFieldsCorrect");

            passwordResetSendCodeLabel.setVisible(false);

            passwordResetSendCodeButton.setDisable(true);
            passwordResetSendCodeButton.setVisible(false);

            //Unblocking verification code field and verify button
            passwordResetVerificationCodeField.setDisable(false);
            passwordResetVerificationCodeCircle.getStyleClass().clear();
            passwordResetVerificationCodeCircle.getStyleClass().add("circle");

            passwordResetVerifyCodeLabel.setVisible(true);

            passwordResetVerifyCodeButton.setDisable(false);
        }else{
            errorOnEmail();
            Alerts.createCustomAlert(loginRightPaneAnchorPane, passwordResetSetNewPasswordButton,"WARNING","Wprowadzony mail jest błędny", 293, 86, "alertFailure");
        }
    }

    private boolean isEmail(String email){
        Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
        Matcher mat = pattern.matcher(email);

        if(mat.matches()){
            return true;
        }else{
            return false;
        }
    }

    public static void sendEmail(String recipient,String firstName, String verificationCode) throws MessagingException {
        System.out.println("Starting process of sending email");
        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String outBoxEmailAccount = "outbox2137@gmail.com";
        String outBoxEmailPassword = "zaq1@WSX";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(outBoxEmailAccount, outBoxEmailPassword);
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

    public void handleVerifyCodeOnEnterPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER)
        {
            verifyCode();
        }
    }

    public void handleVerify(ActionEvent actionEvent) {
        verifyCode();
    }

    public void verifyCode(){
        if(passwordResetVerificationCodeField.getText().equals(verificationCode)){
            Alerts.createCustomAlert(loginRightPaneAnchorPane, passwordResetSetNewPasswordButton,"CHECK","Weryfikacja pomyślna", 293, 86, "alertSuccess");

            //Block verification code field and verify button
            passwordResetVerificationCodeField.setDisable(true);
            passwordResetVerificationCodeCircle.getStyleClass().clear();
            passwordResetVerificationCodeCircle.getStyleClass().add("circleCorrect");

            passwordResetVerificationCodeField.getStyleClass().clear();
            passwordResetVerificationCodeField.getStyleClass().add("textFieldsCorrect");

            passwordResetVerifyCodeLabel.setVisible(false);

            passwordResetVerifyCodeButton.setDisable(true);
            passwordResetVerifyCodeButton.setVisible(false);

            //Unblock password fields and set password button
            passwordResetPasswordField.setDisable(false);
            passwordResetPasswordCircle.getStyleClass().clear();
            passwordResetPasswordCircle.getStyleClass().add("circle");

            passwordResetConfirmPasswordField.setDisable(false);
            passwordResetConfirmPasswordCircle.getStyleClass().clear();
            passwordResetConfirmPasswordCircle.getStyleClass().add("circle");

            passwordResetSetNewPasswordButton.setDisable(false);

        }else{
            errorOnVerificationCode();
            Alerts.createCustomAlert(loginRightPaneAnchorPane, passwordResetSetNewPasswordButton,"WARNING","Wprowadzony kod jest błędny", 293, 86, "alertFailure");
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

    public void passwordReset(){
        if(isValid(passwordResetPasswordField.getText(), passwordResetConfirmPasswordField.getText())){
            System.out.println("Zresetowano");
            System.out.println("Nowe hasło:" + passwordResetPasswordField.getText());
        }
    }
    private boolean isValid(String password1, String password2){
        boolean passwordError = false, passwordNotTheSameError = false;
        int error = 0;

        Pattern patternPassword = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$");

        Matcher matchPassword = patternPassword.matcher(password1);

        if(!matchPassword.matches()){
            errorOnPassword();
            passwordError = true;
            errorOnConfirmPassword();
            error++;
        }else{
            if(!password1.equals(password2)){
                passwordNotTheSameError = true;
                errorOnConfirmPassword();
                error++;
            }
        }

        if(error <= 0){
            return true;
        }else if(error == 1) {
            if(passwordError){
                Alerts.createCustomAlert(loginRightPaneAnchorPane, passwordResetSetNewPasswordButton,"WARNING","Podany format hasła jest błędny", 350, 86, "alertFailure");
            }
            if(passwordNotTheSameError){
                Alerts.createCustomAlert(loginRightPaneAnchorPane, passwordResetSetNewPasswordButton,"WARNING","Hasła nie są takie same", 350, 86, "alertFailure");
            }
            return false;
        }else{

            Alerts.createCustomAlert(loginRightPaneAnchorPane, passwordResetSetNewPasswordButton,"WARNING","Popraw błędy na zaznaczonych polach", 350, 86, "alertFailure");
            return false;
        }
    }

    private void passwordRequirements(){
        Pattern sixChars = Pattern.compile(".{6,}");
        Pattern smallLetter = Pattern.compile(".*[a-z]+.*");
        Pattern bigLetter = Pattern.compile(".*[A-Z]+.*");
        Pattern number = Pattern.compile(".*[0-9]+.*");
        Pattern specialChar = Pattern.compile(".*[!@#$%^&*()_\\-+=]+.*");

        Matcher matchSixChars = sixChars.matcher(passwordResetPasswordField.getText());
        Matcher matchSmallLetter = smallLetter.matcher(passwordResetPasswordField.getText());
        Matcher matchBigLetter = bigLetter.matcher(passwordResetPasswordField.getText());
        Matcher matchNumber = number.matcher(passwordResetPasswordField.getText());
        Matcher matchSpecialChar = specialChar.matcher(passwordResetPasswordField.getText());

        if(matchSixChars.matches()){
            passwordResetSixCharsRequirement.getStyleClass().clear();
            passwordResetSixCharsRequirement.getStyleClass().add("successText");
        }else{
            passwordResetSixCharsRequirement.getStyleClass().clear();
            passwordResetSixCharsRequirement.getStyleClass().add("errorText");
        }

        if(matchSmallLetter.matches()){
            passwordResetSmallLetterRequirement.getStyleClass().clear();
            passwordResetSmallLetterRequirement.getStyleClass().add("successText");
        }else{
            passwordResetSmallLetterRequirement.getStyleClass().clear();
            passwordResetSmallLetterRequirement.getStyleClass().add("errorText");
        }

        if(matchBigLetter.matches()){
            passwordResetBigLetterRequirement.getStyleClass().clear();
            passwordResetBigLetterRequirement.getStyleClass().add("successText");
        }else{
            passwordResetBigLetterRequirement.getStyleClass().clear();
            passwordResetBigLetterRequirement.getStyleClass().add("errorText");
        }

        if(matchNumber.matches()){
            passwordResetNumberRequirement.getStyleClass().clear();
            passwordResetNumberRequirement.getStyleClass().add("successText");
        }else{
            passwordResetNumberRequirement.getStyleClass().clear();
            passwordResetNumberRequirement.getStyleClass().add("errorText");
        }

        if(matchSpecialChar.matches()){
            passwordResetSpecialCharRequirement.getStyleClass().clear();
            passwordResetSpecialCharRequirement.getStyleClass().add("successText");
        }else{
            passwordResetSpecialCharRequirement.getStyleClass().clear();
            passwordResetSpecialCharRequirement.getStyleClass().add("errorText");
        }

        if(passwordResetConfirmPasswordField.getText().equals(passwordResetPasswordField.getText())){
            passwordResetSamePasswordsRequirement.getStyleClass().clear();
            passwordResetSamePasswordsRequirement.getStyleClass().add("successText");
        }else{
            passwordResetSamePasswordsRequirement.getStyleClass().clear();
            passwordResetSamePasswordsRequirement.getStyleClass().add("errorText");
        }
    }

    public void checkRequirements(KeyEvent keyEvent) {
        passwordRequirements();
    }


    public void errorOnEmail(){
        //EmailField
        passwordResetEmailField.getStyleClass().clear();
        passwordResetEmailField.getStyleClass().add("textFieldsError");
        //EmailCircle
        passwordResetEmailCircle.getStyleClass().clear();
        passwordResetEmailCircle.getStyleClass().add("circleError");
    }

    public void clearErrorsOnEmail(KeyEvent keyEvent) {
        //EmailField
        passwordResetEmailField.getStyleClass().clear();
        passwordResetEmailField.getStyleClass().add("textFields");
        //EmailCircle
        passwordResetEmailCircle.getStyleClass().clear();
        passwordResetEmailCircle.getStyleClass().add("circle");
    }

    public void errorOnVerificationCode(){
        //VerificationCodeField
        passwordResetVerificationCodeField.getStyleClass().clear();
        passwordResetVerificationCodeField.getStyleClass().add("textFieldsError");
        //VerificationCodeCircle
        passwordResetVerificationCodeCircle.getStyleClass().clear();
        passwordResetVerificationCodeCircle.getStyleClass().add("circleError");
    }

    public void clearErrorsOnVerificationCode(KeyEvent keyEvent) {
        //VerificationCodeField
        passwordResetVerificationCodeField.getStyleClass().clear();
        passwordResetVerificationCodeField.getStyleClass().add("textFields");
        //VerificationCodeCircle
        passwordResetVerificationCodeCircle.getStyleClass().clear();
        passwordResetVerificationCodeCircle.getStyleClass().add("circle");
    }

    public void errorOnPassword(){
        //PasswordField
        passwordResetPasswordField.getStyleClass().clear();
        passwordResetPasswordField.getStyleClass().add("textFieldsError");
        //PasswordCircle
        passwordResetPasswordCircle.getStyleClass().clear();
        passwordResetPasswordCircle.getStyleClass().add("circleError");
    }

    public void clearErrorsOnPassword(KeyEvent keyEvent) {
        //PasswordField
        passwordResetPasswordField.getStyleClass().clear();
        passwordResetPasswordField.getStyleClass().add("textFields");
        //PasswordCircle
        passwordResetPasswordCircle.getStyleClass().clear();
        passwordResetPasswordCircle.getStyleClass().add("circle");
    }

    public void errorOnConfirmPassword(){
        //ConfirmPasswordField
        passwordResetConfirmPasswordField.getStyleClass().clear();
        passwordResetConfirmPasswordField.getStyleClass().add("textFieldsError");
        //ConfirmPasswordCircle
        passwordResetConfirmPasswordCircle.getStyleClass().clear();
        passwordResetConfirmPasswordCircle.getStyleClass().add("circleError");
    }

    public void clearErrorsOnConfirmPassword(KeyEvent keyEvent) {
        //ConfirmPasswordField
        passwordResetConfirmPasswordField.getStyleClass().clear();
        passwordResetConfirmPasswordField.getStyleClass().add("textFields");
        //ConfirmPasswordCircle
        passwordResetConfirmPasswordCircle.getStyleClass().clear();
        passwordResetConfirmPasswordCircle.getStyleClass().add("circle");
    }

    public void handleReturn(ActionEvent actionEvent) {
        SceneManager.renderScene("login");
    }

    public void handleExit(ActionEvent actionEvent) {
        Stage stage = (Stage) passwordResetReturnButtonButton.getScene().getWindow();
        stage.close();
    }
}
