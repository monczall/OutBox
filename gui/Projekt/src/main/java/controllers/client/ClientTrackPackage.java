package main.java.controllers.client;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.java.App;
import main.java.controllers.auth.Login;
import main.java.dao.PackageHistoryDAO;
import main.java.dao.PackagesDAO;
import main.java.entity.PackageHistory;
import main.java.entity.Packages;
import main.java.entity.PackagesDTO;
import main.java.features.Animations;
import main.java.features.Preference;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientTrackPackage implements Initializable {

    public String arrayOfDescriptions[] = {
            App.getLanguageProperties("statusOne"),
            App.getLanguageProperties("statusTwo"),
            App.getLanguageProperties("statusThree"),
            App.getLanguageProperties("statusFour"),
            App.getLanguageProperties("statusFive"),
            App.getLanguageProperties("statusSix"),
            App.getLanguageProperties("statusSeven"),
            App.getLanguageProperties("statusEight"),
            App.getLanguageProperties("statusNine"),
            App.getLanguageProperties("statusTen"),
            App.getLanguageProperties("statusEleven"),
            App.getLanguageProperties("statusTwelve"),
    };

    @FXML
    private AnchorPane trackPackagePane;

    @FXML
    private VBox packageLayout;

    @FXML
    private Button btnBack;

    @FXML
    private AnchorPane moreInformationPane;

    @FXML
    private VBox statusesVBox;

    @FXML
    private Pane informationAlert;

    @FXML
    private Text packageNumber;

    @FXML
    private Text senderName;

    @FXML
    private Text senderSurname;

    @FXML
    private Text senderTelephone;

    @FXML
    private Text senderStreet;

    @FXML
    private Text senderCity;

    @FXML
    private Text senderVoivodeship;

    @FXML
    private Text recipientName;

    @FXML
    private Text recipientSurname;

    @FXML
    private Text recipientTelephone;

    @FXML
    private Text recipientStreet;

    @FXML
    private Text recipientCity;

    @FXML
    private Text recipientVoivodeship;

    @FXML
    private Text timeOfDelivery;

    @FXML
    private ToggleButton toggleFromClient;

    @FXML
    private ToggleButton toggleToClient;

    private List<PopulatePackageItem> packageFirst = new ArrayList<>(loadPackagesList(Login.getUserID(), Login.getUserEmail()));;

    private static Preference pref = new Preference();
    private static ResourceBundle bundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        toggleToClient.setSelected(true);
        toggleFromClient.setSelected(true);

        // Moving panes for animation purposes
        informationAlert.setTranslateY(-850);
        moreInformationPane.setTranslateX(+850);

        btnBack.setVisible(false);

        // Loading date into the dynamic objects from db query
        loadPackages(packageFirst);
    }

    public String addDescription(String currentStatus){
                if(currentStatus.equals("Zarejestrowana") || currentStatus.equals("Registered")) {
                    return arrayOfDescriptions[0];
                }
                else if(currentStatus.equals("Odebrana Od Klienta") || currentStatus.equals("Received From Client")) {
                    return arrayOfDescriptions[1];
                }
                else if(currentStatus.equals("W Transporcie") || currentStatus.equals("In Transport")) {
                    return arrayOfDescriptions[2];
                }
                else if(currentStatus.equals("W Lokalnej Sortowni") || currentStatus.equals("In Local Hub")) {
                    return arrayOfDescriptions[3];
                }
                else if(currentStatus.equals("W Głównej Sortowni") || currentStatus.equals("In Main Hub")) {
                    return arrayOfDescriptions[4];
                }
                else if(currentStatus.equals("Przekazana Do Doręczenia") || currentStatus.equals("Handed Over For Delivery")) {
                    return arrayOfDescriptions[5];
                }
                else if(currentStatus.equals("Dostarczona") || currentStatus.equals("Delivered")) {
                    return arrayOfDescriptions[6];
                }
                else if(currentStatus.equals("Nieobecność Odbiorcy") || currentStatus.equals("Recipient's Absence")) {
                    return arrayOfDescriptions[7];
                }
                else if(currentStatus.equals("Ponowna Próba Doręczenia") || currentStatus.equals("Retry Delivery")) {
                    return arrayOfDescriptions[8];
                }
                else if(currentStatus.equals("Do Odebrania W Odziale") || currentStatus.equals("To Be Picked In Hub")) {
                    return arrayOfDescriptions[9];
                }
                else if(currentStatus.equals("Zwrot Do Nadawcy") || currentStatus.equals("Returning To The Sender")) {
                    return arrayOfDescriptions[10];
                }
                else {
                    return arrayOfDescriptions[11];
                }
    }

    public static List<PackageHistory> translateStatuses(int id){
        List<PackageHistory> statuses = PackageHistoryDAO.getDateAndStatusById(id);

        if(Preference.readPreference("language").equals("english")) {
            for(int i = 0; i < statuses.size(); i++) {
                if(statuses.get(i).getStatus().equals("Zarejestrowana")) {
                    statuses.get(i).setStatus("Registered");
                }
                else if(statuses.get(i).getStatus().equals("Odebrana Od Klienta")) {
                    statuses.get(i).setStatus("Received From Client");
                }
                else if(statuses.get(i).getStatus().equals("W Transporcie")) {
                    statuses.get(i).setStatus("In Transport");
                }
                else if(statuses.get(i).getStatus().equals("W Lokalnej Sortowni")) {
                    statuses.get(i).setStatus("In Local Hub");
                }
                else if(statuses.get(i).getStatus().equals("W Głównej Sortowni")) {
                    statuses.get(i).setStatus("In Main Hub");
                }
                else if(statuses.get(i).getStatus().equals("Przekazana Do Doręczenia")) {
                    statuses.get(i).setStatus("Handed Over For Delivery");
                }
                else if(statuses.get(i).getStatus().equals("Dostarczona")) {
                    statuses.get(i).setStatus("Delivered");
                }
                else if(statuses.get(i).getStatus().equals("Nieobecność Odbiorcy")) {
                    statuses.get(i).setStatus("Recipient's Absence");
                }
                else if(statuses.get(i).getStatus().equals("Ponowna Próba Doręczenia")) {
                    statuses.get(i).setStatus("Retry Delivery");
                }
                else if(statuses.get(i).getStatus().equals("Do Odebrania W Odziale")) {
                    statuses.get(i).setStatus("To Be Picked In Hub");
                }
                else if(statuses.get(i).getStatus().equals("Zwrot Do Nadawcy")) {
                    statuses.get(i).setStatus("Returning To The Sender");
                }
                else if(statuses.get(i).getStatus().equals("Zwrócona Do Nadawcy")) {
                    statuses.get(i).setStatus("Returned To The Sender");
                }
            }
        }
        else {
            return statuses;
        }
        return statuses;
    }


    /**
     * <p>
     *     Method that always create status (small gray square)
     *     HBox is created and splited into few Panes (dataPane, squarePane, grayPane, statusPane)
     *     dataPane contains a label with DATE (that is one of the arguments)
     *     squarePane contains a grayPane (is has css that make this pane gray)
     *     statusPane that contains status name (second argument)
     *     At the end everything is added into Vbox
     * </p>
     * @param date date of a status
     * @param status name of status
     * @param createPlace where status will be created
     */
    public void createStatus(String date, String status, VBox createPlace){

        HBox statusHBox = new HBox();

        Pane dataPane = new Pane();
        Pane squarePane = new Pane();
        Pane grayPane = new Pane();
        Pane statusPane = new Pane();

        Label dataLabel = new Label();
        Label statusName = new Label();

        dataLabel.setText(date);
        dataLabel.getStyleClass().add("textOption");
        dataLabel.setLayoutX(8);
        dataLabel.setLayoutY(3);

        statusName.setText(status + ".");
        statusName.getStyleClass().add("optionGroup");

        grayPane.setPrefWidth(30);
        grayPane.setPrefHeight(27);
        grayPane.setLayoutX(32);
        grayPane.getStyleClass().add("grayColor");

        squarePane.setPrefWidth(85);
        squarePane.getChildren().add(grayPane);

        statusPane.getChildren().add(statusName);

        dataPane.setPrefWidth(147);
        dataPane.getChildren().add(dataLabel);

        statusHBox.setPrefHeight(36);
        statusHBox.getChildren().add(dataPane);
        statusHBox.getChildren().add(squarePane);
        statusHBox.getChildren().add(statusPane);

        createPlace.getChildren().add(0,statusHBox);
    }

    /**
     * <p>
     *     Method that always create a small gray square it indicate a progress step
     *     HBox is created and splited into three Panes (emptyPane, squarePane, grayPane)
     *     emptyPane it is just a empty pane that helps to arrane HBox
     *     squarePane contains a grayPane (is has css that make this pane gray)
     *     gray that contains status name (second argument)
     * </p>
     * @param steps how much steps need to be created
     */
    public void createStep(int steps, VBox createPlace){
        for(int i = 0 ; i < steps; i ++) {
            HBox stepBox = new HBox();

            Pane emptyPane = new Pane();
            Pane squarePane = new Pane();
            Pane grayPane = new Pane();

            emptyPane.setPrefWidth(165);

            grayPane.setPrefWidth(9);
            grayPane.setPrefHeight(9);
            grayPane.setLayoutX(24);
            grayPane.setLayoutY(1);
            grayPane.getStyleClass().add("grayColor");

            squarePane.getChildren().add(grayPane);

            stepBox.setPrefHeight(18);
            stepBox.getChildren().add(emptyPane);
            stepBox.getChildren().add(squarePane);

            createPlace.getChildren().add(0,stepBox);
        }
    }

    /**
     * <p>
     *     Method that always create status (small gray square)
     *     HBox is created and splited into few Panes (dataPane, squarePane, grayPane, currentPane)
     *     dataPane contains a label with DATE (that is one of the arguments)
     *     squarePane contains a grayPane (is has css that make this pane gray)
     *     currentPane is the biggest square that indicates that this is current status
     *     statusPane that contains status name (second argument)
     *     At the end everything is added into Vbox
     * </p>
     * @param date date of a status
     * @param status name of status
     * @param desc description of current status (the newest in terms of date)
     */
    public void createCurrentStatus(String date, String status, String desc, VBox createPlace){

        HBox statusHBox = new HBox();

        Pane dataPane = new Pane();
        Pane squarePane = new Pane();
        Pane currentPane = new Pane();
        Pane statusPane = new Pane();

        Label dataLabel = new Label();
        Label statusName = new Label();

        Text descriptionOfStatus = new Text(desc);

        descriptionOfStatus.setWrappingWidth(450);
        descriptionOfStatus.getStyleClass().add("textOption");
        descriptionOfStatus.setLayoutX(6);
        descriptionOfStatus.setLayoutY(46);

        dataLabel.setText(date);
        dataLabel.getStyleClass().add("textOption");
        dataLabel.setLayoutX(8);
        dataLabel.setLayoutY(27);

        statusName.setText(status + ".");
        statusName.getStyleClass().add("optionGroup");
        statusName.setLayoutX(5);

        currentPane.setPrefWidth(57);
        currentPane.setPrefHeight(51);
        currentPane.setLayoutX(18);
        currentPane.setLayoutY(11);
        currentPane.getStyleClass().add("sideBackground");

        squarePane.setPrefWidth(85);
        squarePane.getChildren().add(currentPane);

        statusPane.getChildren().add(statusName);
        statusPane.getChildren().add(descriptionOfStatus);

        dataPane.setPrefWidth(147);
        dataPane.getChildren().add(dataLabel);

        statusHBox.setPrefHeight(73);
        statusHBox.getChildren().add(dataPane);
        statusHBox.getChildren().add(squarePane);
        statusHBox.getChildren().add(statusPane);

        createPlace.getChildren().add(0,statusHBox);
    }

    /**
     * <p>
     *  Method used to display all the statuses in order from the db by HQL query
     * </p>
     * @param userId used to show packages that client registered
     * @param userEmail used to show packages that are 'coming' to actual client
     * @return filled List of type PopulatePackageItem it contains info about statuses
     */
    public static List<PopulatePackageItem> loadPackagesList(int userId, String userEmail){

        List<PackagesDTO> listOfPackages = PackagesDAO.readPackagesByID(userId, userEmail);

        List<PopulatePackageItem> packageItems = new ArrayList<>();

        for(int i = 0; i < listOfPackages.size(); i++){

            PopulatePackageItem populatePackageItem = new PopulatePackageItem();

            populatePackageItem.setPackageNumber(listOfPackages.get(i).getPackageNumber());
            populatePackageItem.setStatus(listOfPackages.get(i).getStatus());
            populatePackageItem.setId(listOfPackages.get(i).getPackagesId());

            if(listOfPackages.get(i).getEmail().equals(Login.getUserEmail())) {
                populatePackageItem.setType(App.getLanguageProperties("clientSender"));
                populatePackageItem.setSender(listOfPackages.get(i).getName());
            }
            else {
                populatePackageItem.setType(App.getLanguageProperties("clientRecipient"));
                populatePackageItem.setSender(listOfPackages.get(i).getRecipentName());
            }

            packageItems.add(populatePackageItem);
        }

        return packageItems;
    }

    // Method that leads to list of all active packages
    @FXML
    void backToTrackPackage(ActionEvent event) throws IOException {
        Animations.fadeAway(btnBack,0.2,1,0,false);
        Animations.fadeAway(toggleFromClient,0.2,0,1,true);
        Animations.fadeAway(toggleToClient,0.2,0,1,true);
        Animations.changePane(moreInformationPane,trackPackagePane,+850,0.5);
    }

    // Method handle event on icon that is closing alert
    @FXML
    void closeInfoAlert(javafx.scene.input.MouseEvent mouseEvent) {
        Animations.moveByY(informationAlert,-850,0.5);
    }

    // Method handles showing only packages that client registered
    @FXML
    void loadFromClient(ActionEvent event) {
        List<PopulatePackageItem> list = new ArrayList<>();

        /* If both buttons are not selected then VBox is
         cleared (and shows nothing)*/
        if(!toggleFromClient.isSelected() && !toggleToClient.isSelected()) {
            packageLayout.getChildren().clear();
        }
        else if(!toggleFromClient.isSelected() && toggleToClient.isSelected()) {
            packageLayout.getChildren().clear();
            for(PopulatePackageItem ppI : packageFirst) {
                if(ppI.getType().equals(App.getLanguageProperties("clientSender"))) {
                    list.add(ppI);
                }
            }
            loadPackages(list);
        }
        else {
            for(PopulatePackageItem ppI : packageFirst) {
                if(ppI.getType().equals(App.getLanguageProperties("clientRecipient"))) {
                    list.add(ppI);
                }
            }
            loadPackages(list);
        }
    }

    // Method handles showing only packages that are going to actual client
    @FXML
    void loadToClient(ActionEvent event) {
        List<PopulatePackageItem> list = new ArrayList<>();

        /* If both buttons are not selected then VBox is
         cleared (and shows nothing)*/
        if(!toggleFromClient.isSelected() && !toggleToClient.isSelected()) {
            packageLayout.getChildren().clear();
        }
        else if(!toggleToClient.isSelected() && toggleFromClient.isSelected()) {
            packageLayout.getChildren().clear();
            for(PopulatePackageItem ppI : packageFirst) {
                if(ppI.getType().equals(App.getLanguageProperties("clientRecipient"))) {
                    list.add(ppI);
                }
            }
            loadPackages(list);
        }
        else {
            for(PopulatePackageItem ppI : packageFirst) {
                if(ppI.getType().equals(App.getLanguageProperties("clientSender"))) {
                    list.add(ppI);
                }
            }
            loadPackages(list);
        }
    }

    /**
     * <p>
     * This method create panes with buttons - dynamically
     * Number of panes depends on size of the List
     * It takes List with type of PopulatePackageItem object
     * </p>
     * @param list list of information needed to generate blocks
     */
    private void loadPackages(List<PopulatePackageItem> list){

        for(int i=0; i<list.size(); i++){
            FXMLLoader fxmlLoader = new FXMLLoader();

            if(pref.readPreference("language").equals("english"))
                bundle = ResourceBundle.getBundle("main.resources.languages.lang_en");
            else
                bundle = ResourceBundle.getBundle("main.resources.languages.lang_pl");

            fxmlLoader.setLocation(getClass().getResource("../../../resources/view/client/packageItem.fxml"));
            fxmlLoader.setResources(bundle);

            try {
                Pane pane = fxmlLoader.load();
                PackageItem packageItem = fxmlLoader.getController();       // Loading controller of packageItem.fxml

                packageItem.setText(list.get(i).getType());

                pane.setPadding(new Insets(70,0,100,70));       // Adjusting padding of pane

                Button showMore = new Button("Więcej");

                showMore.setLayoutX(549);        // Setting layouts where button should be
                showMore.setLayoutY(115.5);
                showMore.setPrefWidth(136);      // Setting width + height
                showMore.setPrefHeight(39);
                showMore.getStyleClass().add("btnNext");
                showMore.setContentDisplay(ContentDisplay.RIGHT);

                FontAwesomeIconView arrow = new FontAwesomeIconView();      //Creating icon
                arrow.setGlyphName("LONG_ARROW_RIGHT");
                arrow.setSize("23");
                arrow.getStyleClass().add("iconNext");
                showMore.setGraphic(arrow);      // Adding icon into the button

                /* Adding function to handle click event on the button
                 This Event leads user to the pane that show statuses*/
                showMore.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        statusesVBox.getChildren().clear();
                        Animations.changePane(trackPackagePane,moreInformationPane,-850,0.5);

                        btnBack.setVisible(true);
                        btnBack.setOpacity(1);


                        List<PackageHistory> statuses = translateStatuses(packageItem.getId());

                        // Creating statuses depending on database information
                        for(int i = 0; i < statuses.size(); i++) {

                            if(i == statuses.size()-1) {
                                if(i != 0){
                                    createStep(4, statusesVBox);
                                }

                                String date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(
                                        statuses.get(i).getDate());

                                createCurrentStatus(date,statuses.get(i).getStatus(),
                                        addDescription(statuses.get(i).getStatus()),statusesVBox);
                            }
                            else {
                                if(i != 0) {
                                    createStep(2, statusesVBox);
                                }

                                String date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(statuses.get(i).getDate());
                                createStatus(date,statuses.get(i).getStatus(), statusesVBox);
                            }
                        }

                        Animations.fadeAway(toggleFromClient,0.2,1,0,false);
                        Animations.fadeAway(toggleToClient,0.2,1,0,false);
                    }
                });

                Button fullInfo = new Button();

                fullInfo.setLayoutX(500);        //Setting layout where button should be
                fullInfo.setLayoutY(115.5);
                fullInfo.setPrefWidth(39);      //Setting width + height
                fullInfo.setPrefHeight(39);
                fullInfo.getStyleClass().add("btnBack");
                fullInfo.setContentDisplay(ContentDisplay.RIGHT);

                FontAwesomeIconView infoIcon = new FontAwesomeIconView();      //Creating icon
                infoIcon.setGlyphName("INFO_CIRCLE");
                infoIcon.setSize("23");
                infoIcon.getStyleClass().add("backIcon");
                fullInfo.setGraphic(infoIcon);

                /* Adding function to handle click event on the button
                 This Event shows user a pane with full information
                 about package*/
                fullInfo.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        List<Packages> infoAboutPackage = PackagesDAO.getPackagesById(packageItem.getId());

                        packageNumber.setText(infoAboutPackage.get(0).getPackageNumber());

                        recipientCity.setText(infoAboutPackage.get(0).getUserInfosByUserInfoId().getCity());
                        recipientStreet.setText(infoAboutPackage.get(0).getUserInfosByUserInfoId().getStreetAndNumber());
                        recipientVoivodeship.setText(infoAboutPackage.get(0).getUserInfosByUserInfoId().getVoivodeship());
                        recipientName.setText(infoAboutPackage.get(0).getUserInfosByUserInfoId().getName());
                        recipientSurname.setText(infoAboutPackage.get(0).getUserInfosByUserInfoId().getSurname());
                        recipientTelephone.setText(infoAboutPackage.get(0).getUserInfosByUserInfoId().getPhoneNumber());

                        senderCity.setText(infoAboutPackage.get(0).getUsersByUserId().getUserInfosByUserInfoId().getCity());
                        senderStreet.setText(infoAboutPackage.get(0).getUsersByUserId().getUserInfosByUserInfoId().getStreetAndNumber());
                        senderVoivodeship.setText(infoAboutPackage.get(0).getUsersByUserId().getUserInfosByUserInfoId().getVoivodeship());
                        senderName.setText(infoAboutPackage.get(0).getUsersByUserId().getUserInfosByUserInfoId().getName());
                        senderSurname.setText(infoAboutPackage.get(0).getUsersByUserId().getUserInfosByUserInfoId().getSurname());
                        senderTelephone.setText(infoAboutPackage.get(0).getUsersByUserId().getUserInfosByUserInfoId().getPhoneNumber());

                        timeOfDelivery.setText(infoAboutPackage.get(0).getTimeOfPlannedDelivery());

                        Animations.moveByY(informationAlert,+850,0.5);
                    }
                });

                pane.getChildren().add(1,showMore);
                pane.getChildren().add(2,fullInfo);
                packageItem.setData(list.get(i));
                packageLayout.getChildren().add(pane);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
