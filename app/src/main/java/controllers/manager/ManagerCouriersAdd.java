package main.java.controllers.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.java.App;
import main.java.controllers.auth.Encryption;
import main.java.controllers.auth.Login;
import main.java.dao.UserInfosDAO;
import main.java.dao.UsersDAO;
import main.java.entity.UserInfos;
import main.java.entity.Users;
import main.java.features.Alerts;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

public class ManagerCouriersAdd implements Initializable {

    @FXML
    private final ObservableList<String> role = FXCollections.observableArrayList("Kurier", "Kurier Miedzyoddzialowy");
    String roleString;
    List<Users> dataUser;
    Users uu = UsersDAO.getUsersId(Login.getUserID()).get(0);
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField street;
    @FXML
    private TextField city;
    @FXML
    private TextField email;
    @FXML
    private TextField numberPhone;
    @FXML
    private AnchorPane appWindow;
    @FXML
    private Button addCourierButton;
    @FXML
    private Pane alertPane;
    @FXML
    private ComboBox<String> comboRole;

    /**
     * The method responsible for sending the generated password to the e-mail address provided
     *
     * @param recipient e-mail recipient
     * @param firstName name recipient
     * @param password  password recipient
     * @throws MessagingException
     */
    public static void sendEmail(String recipient,
                                 String firstName,
                                 String password
    ) throws MessagingException {

        Properties properties = new Properties();

        //Email setting
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String outBoxEmailAccount = "outbox2137@gmail.com";
        String outBoxEmailPassword = "zaq1@WSX";


        Session session = Session.getInstance(properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(outBoxEmailAccount,
                                outBoxEmailPassword);
                    }
                });

        //Message setting
        Message message = prepareMessage(session,
                outBoxEmailAccount,
                recipient,
                firstName,
                password);

        Transport.send(message);
    }

    /**
     * The method responsible for preparing the message
     *
     * @param session
     * @param outBoxEmailAccount sender e-mail
     * @param recipient          e-mail recipient
     * @param firstName          name recipient
     * @param password           password recipient
     * @return null
     */
    private static Message prepareMessage(Session session,
                                          String outBoxEmailAccount,
                                          String recipient,
                                          String firstName,
                                          String password) {
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("OutBox_Support"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject("New Password");
            message.setText("Your new password is: " + password);
            return message;
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Choosing the role of the courier
     */
    @FXML
    void changeRole(ActionEvent event) {
        if (comboRole.getValue().equals("Kurier")) {
            roleString = "Kurier";
        } else {
            roleString = "Kurier Miedzyoddzialowy";
        }
    }

    /**
     * The method responsible for adding the courier to the database
     */
    public void addCourier(MouseEvent mouseEvent) {
        if (name.getText().equals("") ||
                surname.getText().equals("") ||
                street.getText().equals("") ||
                city.getText().equals("") ||
                email.getText().equals("") ||
                numberPhone.getText().equals("")) {
            Alerts.createAlert(appWindow, addCourierButton, "WARNING",
                    App.getLanguageProperties("completeAllFields"));
        } else {
            if (!validation()) {
                Alerts.createAlert(appWindow, addCourierButton, "WARNING",
                        App.getLanguageProperties("correctFields"));
            } else {
                String nameString = name.getText();
                String emailString = email.getText();
                String phoneString = numberPhone.getText();
                String streetString = street.getText();
                String surnameString = surname.getText();
                String cityString = city.getText();

                String password = new Random().ints(10, 33, 122)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

                try {
                    sendEmail(emailString, nameString, password);
                } catch (MessagingException e) {
                    e.printStackTrace();
                    Alerts.createAlert(appWindow, addCourierButton, "WARNING",
                            App.getLanguageProperties("errorEmail"));
                }

                UserInfos ui = UserInfosDAO.getUserInfoByID(Login.getUserInfoID()).get(0);
                dataUser = UsersDAO.getUsersId(ui.getId());
                int areaId = dataUser.get(0).getAreaId();
                UserInfosDAO.addUserInfo(nameString, surnameString, emailString, phoneString, streetString, cityString,
                        ui.getVoivodeship(), Encryption.encrypt(password), roleString, uu.getAreaId());

                alertPane.setVisible(true);
            }
        }
    }

    /**
     * The method validates the entered data
     * true - all fields are correct
     * false - at least one field is incorrect
     *
     * @return boolean
     */
    boolean validation() {

        boolean status = true;

        if (email.getText().matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}")) {
            goodValidation(email);
        } else {
            status = false;
            errorValidation(email);
        }
        if (name.getText().matches("[a-zA-Z]+")) {
            goodValidation(name);
        } else {
            status = false;
            errorValidation(name);
        }
        if (surname.getText().matches("[a-zA-Z]+")) {
            goodValidation(surname);
        } else {
            status = false;
            errorValidation(surname);
        }
        if (city.getText().matches("[A-Za-z]+")) {
            goodValidation(city);
        } else {
            status = false;
            errorValidation(city);
        }
        if (street.getText().matches("[A-Za-z]{0,2}\\.?\\s?[A-Za-z]{2,40}\\s?\\-?[A-Za-z]{0,40}?\\s?" +
                "\\-?[A-Za-z]{0,40}?\\s[0-9]{1,4}\\s?[A-Za-z]?\\s?\\/?\\s?[0-9]{0,5}")) {
            goodValidation(street);
        } else {
            status = false;
            errorValidation(street);
        }
        if (numberPhone.getText().matches("[0-9]*") && numberPhone.getText().length() == 9) {
            goodValidation(numberPhone);
        } else {
            status = false;
            errorValidation(numberPhone);
        }

        return status;
    }

    /**
     * If textfield is valid it is set to the default style
     *
     * @param name textfield
     */
    void goodValidation(TextField name) {
        name.getStyleClass().clear();
        name.getStyleClass().add("inputBoxCourier");
    }

    /**
     * If textfield is incorrect it is set to error style
     *
     * @param name textfield
     */
    void errorValidation(TextField name) {
        name.getStyleClass().clear();
        name.getStyleClass().add("inputBoxCourierError");
    }

    /**
     * Button that closes the window confirming adding a courier
     */
    @FXML
    void confirmButton(MouseEvent event) {
        name.setText("");
        surname.setText("");
        street.setText("");
        city.setText("");
        email.setText("");
        numberPhone.setText("");
        alertPane.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //set textfields to defualt
        goodValidation(name);
        goodValidation(surname);
        goodValidation(street);
        goodValidation(city);
        goodValidation(numberPhone);
        goodValidation(email);


        comboRole.setItems(role);
        comboRole.setValue(role.get(0));
        roleString = "Kurier";
    }

}
