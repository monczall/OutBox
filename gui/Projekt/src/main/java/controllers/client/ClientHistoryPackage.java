package main.java.controllers.client;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
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
import main.java.features.Charts;
import main.java.features.Preference;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientHistoryPackage implements Initializable {

    @FXML
    private AnchorPane mainPane;

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

        informationAlert.setTranslateY(-850);
        moreInformationPane.setTranslateX(+850);
        btnBack.setVisible(false);

        // Loading date into the dynamic objects from db query
        loadPackages(packageFirst);
    }


    /**
     * <p>
     *  Method used to display all the packages in order from the db by HQL query
     *  (history)
     * </p>
     * @param userId used to show packages that client registered
     * @param userEmail used to show packages that are 'coming' to actual client
     * @return filled List of type PopulatePackageItem it contains info about statuses
     */
    public static List<PopulatePackageItem> loadPackagesList(int userId, String userEmail){

        List<PackagesDTO> listOfPackages = ClientTrackPackage.translateLastStatus(PackagesDAO.readHistoryByID(userId, userEmail));

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

    // Method that leads to list of all history packages
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
     * This method create panes with buttons dynamically
     * Number of panes depends on size of the List
     * It takes List with type of PopulatePackageItem object
     *
     * @param list
     */
    private void loadPackages(List<PopulatePackageItem> list){

        ClientTrackPackage clientTrackPackage = new ClientTrackPackage();

        for(int i=0; i<list.size(); i++){
            FXMLLoader fxmlLoader = new FXMLLoader();

            if(pref.readPreference("language").equals("english")) {
                bundle = ResourceBundle.getBundle("main.resources.languages.lang_en");
            }
            else {
                bundle = ResourceBundle.getBundle("main.resources.languages.lang_pl");
            }

            fxmlLoader.setLocation(getClass().getResource("../../../resources/view/client/packageItem.fxml"));
            fxmlLoader.setResources(bundle);

            try {
                Pane pane = fxmlLoader.load();
                PackageItem packageItem = fxmlLoader.getController();       //Loading controller of packageItem.fxml

                packageItem.setText(list.get(i).getType());

                pane.setPadding(new Insets(70,0,100,70));       //Adjusting padding of pane

                Button showMore = new Button("Więcej");

                showMore.setLayoutX(549);        //Setting layout where button should be and width + height
                showMore.setLayoutY(115.5);
                showMore.setPrefWidth(136);
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

                        List<PackageHistory> statuses = clientTrackPackage.translateStatuses(packageItem.getId());

                        // Creating statuses depending on database information
                        for(int i = 0; i < statuses.size(); i++) {
                            if(i == statuses.size()-1) {
                                if(i != 0) {
                                    clientTrackPackage.createStep(4, statusesVBox);
                                }

                                String date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(statuses.get(i).getDate());
                                clientTrackPackage.createCurrentStatus(date,statuses.get(i).getStatus(),
                                        clientTrackPackage.addDescription(statuses.get(i).getStatus()), statusesVBox);
                            }
                            else {
                                if(i != 0){
                                    clientTrackPackage.createStep(2, statusesVBox);
                                }

                                String date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(statuses.get(i).getDate());
                                clientTrackPackage.createStatus(date, statuses.get(i).getStatus(), statusesVBox);
                            }
                        }
                        Animations.fadeAway(toggleFromClient,0.2,1,0,false);
                        Animations.fadeAway(toggleToClient,0.2,1,0,false);
                    }
                });

                Button fullInfo = new Button();

                fullInfo.setLayoutX(500);        //Setting layout where button should be and width + height
                fullInfo.setLayoutY(115.5);
                fullInfo.setPrefWidth(39);
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
