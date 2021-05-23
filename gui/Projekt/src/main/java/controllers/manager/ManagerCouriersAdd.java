package main.java.controllers.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import main.java.App;
import main.java.controllers.auth.Encryption;
import main.java.controllers.auth.Login;
import main.java.dao.AreasDAO;
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
    private ComboBox<String> regionName;

    List<UserInfos> dataUserInfos;
    List<Users> dataUser;
    UserInfos ui = UserInfosDAO.getUserInfoByID(Login.getUserInfoID()).get(0);

    private ObservableList<String> regions = FXCollections.observableArrayList("Rzeszów","Rzeszów Rejtana");

    public void addCourier(MouseEvent mouseEvent) {

        if(name.getText().toString().equals("") ||
                surname.getText().toString().equals("") ||
                street.getText().toString().equals("") ||
                city.getText().toString().equals("") ||
                email.getText().toString().equals("") ||
                numberPhone.getText().toString().equals("")){
            Alerts.createAlert(appWindow, addCourierButton,"WARNING",
                    App.getLanguageProperties("completeAllFields"));
        }
        else {
            if (!validation()) {
                Alerts.createAlert(appWindow, addCourierButton, "WARNING",
                        App.getLanguageProperties("correctFields"));
            }
            else{
                String nameString = name.getText();
                String emailString = email.getText();
                String phoneString = numberPhone.getText();
                String streetString = street.getText();
                String surnameString = surname.getText();
                String cityString = city.getText();
                String role = "Kurier";

                String password = new Random().ints(10, 33, 122)
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

                try {
                    sendEmail(emailString,nameString,password);
                } catch (MessagingException e) {
                    e.printStackTrace();
                    Alerts.createAlert(appWindow, addCourierButton,"WARNING",
                            App.getLanguageProperties("errorEmail"));
                }

                UserInfos ui = UserInfosDAO.getUserInfoByID(Login.getUserInfoID()).get(0);
                dataUser = UsersDAO.getUsersId(ui.getId());
                int areaId = dataUser.get(0).getAreaId();
                UserInfosDAO.addUserInfo(nameString, surnameString, emailString, phoneString, streetString, cityString,
                        ui.getVoivodeship(), Encryption.encrypt(password), role, areaId);

                alertPane.setVisible(true);
            }
        }
    }

    boolean validation(){

        boolean status = true;

        if (email.getText().matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}"))
        {
            goodValidation(email);
        }
        else
        {
            status = false;
            errorValidation(email);
        }
        if (name.getText().matches("[a-zA-Z]+"))
        {
            goodValidation(name);
        }
        else
        {
            status = false;
            errorValidation(name);
        }
        if (surname.getText().toString().matches("[a-zA-Z]+"))
        {
            goodValidation(surname);
        }
        else
        {
            status = false;
            errorValidation(surname);
        }
        if (city.getText().matches("[A-Za-z]+"))
        {
            goodValidation(city);
        }
        else
        {
            status = false;
            errorValidation(city);
        }
        if (street.getText().matches("[A-Za-z]{0,2}\\.?\\s?[A-Za-z]{2,40}\\s?\\-?[A-Za-z]{0,40}?\\s?" +
                "\\-?[A-Za-z]{0,40}?\\s[0-9]{1,4}\\s?[A-Za-z]?\\s?\\/?\\s?[0-9]{0,5}"))
        {
            goodValidation(street);
        }
        else {
            status = false;
            errorValidation(street);
        }
        if (numberPhone.getText().matches("[0-9]*") && numberPhone.getText().length() == 9)
        {
            goodValidation(numberPhone);
        }
        else
        {
            status = false;
            errorValidation(numberPhone);
        }

        return status;
    }

    void goodValidation(TextField name){
        name.getStyleClass().clear();
        name.getStyleClass().add("inputBoxCourier");
    }

    void errorValidation(TextField name){
        name.getStyleClass().clear();
        name.getStyleClass().add("inputBoxCourierError");
    }

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

    public static void sendEmail(String recipient,
                                 String firstName,
                                 String password
    ) throws MessagingException {
        System.out.println("Starting process of sending email");

        Properties properties = new Properties();

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

        Message message = prepareMessage(session,
                outBoxEmailAccount,
                recipient,
                firstName,
                password);

        Transport.send(message);
        System.out.println("Message sent successfully");
    }

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        goodValidation(name);
        goodValidation(surname);
        goodValidation(street);
        goodValidation(city);
        goodValidation(numberPhone);
        goodValidation(email);

        regionName.setItems(regions);
        regionName.setValue(regions.get(0));
    }
}
