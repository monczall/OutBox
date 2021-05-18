package main.java.controllers.client;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.java.controllers.auth.Login;
import main.java.dao.PackageHistoryDAO;
import main.java.dao.PackagesDAO;
import main.java.entity.PackageHistory;
import main.java.entity.Packages;
import main.java.entity.PackagesDTO;
import main.java.features.Animations;
import main.java.features.Preference;
import org.controlsfx.control.CheckComboBox;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ClientTrackPackage implements Initializable {

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

    List<PopulatePackageItem> packageFirst = new ArrayList<>(loadPackagesList(Login.getUserID(), Login.getUserEmail()));;


    private static Preference pref = new Preference();
    private static ResourceBundle bundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        toggleToClient.setSelected(true);
        toggleFromClient.setSelected(true);

        informationAlert.setTranslateY(-850);
        moreInformationPane.setTranslateX(+850);
        btnBack.setVisible(false);

        //Loading date into the dynamic objects from db query
        loadPackages(packageFirst);
    }

    /**
     * This method create panes with buttons dynamically
     * Number of panes depends on size of the List
     * It takes List with type of PopulatePackageItem object
     *
     * @param list
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

                showMore.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        Animations.changePane(trackPackagePane,moreInformationPane,-850,0.5);

                        btnBack.setVisible(true);
                        btnBack.setOpacity(1);

                        List<PackageHistory> statuses = PackageHistoryDAO.getDateAndStatusById(packageItem.getId());

                        //Filling from DB
                        for(int i = 0; i < statuses.size(); i++){
                            if(i == statuses.size()-1){

                                if(i != 0)
                                    createStep(4);
                                String date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(statuses.get(i).getDate());
                                createCurrentStatus(date,statuses.get(i).getStatus(),"Jakis opis");
                            }
                            else{
                                if(i != 0)
                                    createStep(2);
                                String date = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(statuses.get(i).getDate());
                                createStatus(date,statuses.get(i).getStatus());
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
     */
    private void createStatus(String date, String status){

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

        statusesVBox.getChildren().add(0,statusHBox);
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
    private void createStep(int steps){
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

            statusesVBox.getChildren().add(0,stepBox);
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
    private void createCurrentStatus(String date, String status, String desc){

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

        statusesVBox.getChildren().add(0,statusHBox);
    }

    /**
     * Method used to display all the statuses in order from the db by HQL query
     * @return filled List of type PopulatePackageItem it contains info about statuses
     */
    public static List<PopulatePackageItem> loadPackagesList(int userId, String userEmail){

        List<PackagesDTO> listOfPackages = PackagesDAO.readPackagesByID(userId, userEmail);

        List<PopulatePackageItem> packageItems = new ArrayList<>();

        for(int i = 0; i < listOfPackages.size(); i++){

            PopulatePackageItem populatePackageItem = new PopulatePackageItem();

            populatePackageItem.setPackageNumber(listOfPackages.get(i).getPackageNumber());
            populatePackageItem.setSender(listOfPackages.get(i).getName());
            populatePackageItem.setStatus(listOfPackages.get(i).getStatus());
            populatePackageItem.setId(listOfPackages.get(i).getPackagesId());

            if(listOfPackages.get(i).getEmail().equals(Login.getUserEmail()))
                populatePackageItem.setType("Nadawca");
            else
                populatePackageItem.setType("Odbiorca");

            packageItems.add(populatePackageItem);
        }

        return packageItems;
    }

    @FXML
    void backToTrackPackage(ActionEvent event) throws IOException {
        Animations.fadeAway(btnBack,0.2,1,0,false);
        Animations.fadeAway(toggleFromClient,0.2,0,1,true);
        Animations.fadeAway(toggleToClient,0.2,0,1,true);
        Animations.changePane(moreInformationPane,trackPackagePane,+850,0.5);

        //Need rework
        statusesVBox.getChildren().clear();
    }

    @FXML
    public void closeInfoAlert(javafx.scene.input.MouseEvent mouseEvent) {
        Animations.moveByY(informationAlert,-850,0.5);
    }

    /**
     *
     * @param event
     */
    @FXML
    void loadFromClient(ActionEvent event) {
        List<PopulatePackageItem> list = new ArrayList<>();
        if(!toggleFromClient.isSelected() && !toggleToClient.isSelected()) {
            packageLayout.getChildren().clear();
        }
        else if(!toggleFromClient.isSelected() && toggleToClient.isSelected()) {
            packageLayout.getChildren().clear();
            for(PopulatePackageItem ppI : packageFirst) {
                if(ppI.getType().equals("Nadawca")) {
                    list.add(ppI);
                }
            }
            loadPackages(list);
        }
        else {
            for(PopulatePackageItem ppI : packageFirst) {
                if(ppI.getType().equals("Odbiorca")) {
                    list.add(ppI);
                }
            }
            loadPackages(list);
        }
    }

    /**
     *
     * @param event
     */
    @FXML
    void loadToClient(ActionEvent event) {
        List<PopulatePackageItem> list = new ArrayList<>();
        if(!toggleFromClient.isSelected() && !toggleToClient.isSelected()) {
            packageLayout.getChildren().clear();
        }
        else if(!toggleToClient.isSelected() && toggleFromClient.isSelected()) {
            packageLayout.getChildren().clear();
            for(PopulatePackageItem ppI : packageFirst) {
                if(ppI.getType().equals("Odbiorca")) {
                    list.add(ppI);
                }
            }
            loadPackages(list);
        }
        else {
            for(PopulatePackageItem ppI : packageFirst) {
                if(ppI.getType().equals("Nadawca")) {
                    list.add(ppI);
                }
            }
            loadPackages(list);
        }
    }

}
