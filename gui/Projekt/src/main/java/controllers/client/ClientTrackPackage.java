package main.java.controllers.client;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.java.controllers.auth.Login;
import main.java.dao.PackageHistoryDAO;
import main.java.dao.PackagesDAO;
import main.java.entity.PackageHistory;
import main.java.entity.PackagesDTO;
import main.java.features.Animations;
import main.java.features.Preference;

import java.awt.event.MouseEvent;
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

    private static Preference pref = new Preference();
    private static ResourceBundle bundle;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        moreInformationPane.setTranslateX(+850);
        btnBack.setVisible(false);

        //Testing how ClientTrackPackage view will look like with example data
        List<PopulatePackageItem> list = new ArrayList<>(packageTest());
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

                        //Testing dynamically created statuses from list that's gonna be filled with db rows
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
                    }
                });
                pane.getChildren().add(1,showMore);
                packageItem.setData(list.get(i));
                packageLayout.getChildren().add(pane);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

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

    //Filing list with example data
    private List<PopulatePackageItem> packageTest(){

        List<PackagesDTO> listOfPackages = PackagesDAO.addTable();

        List<PopulatePackageItem> packageItems = new ArrayList<>();

        for(int i = 0; i < listOfPackages.size(); i++){
            PopulatePackageItem populatePackageItem = new PopulatePackageItem();
            populatePackageItem.setPackageNumber(listOfPackages.get(i).getPackageNumber());
            populatePackageItem.setSender(listOfPackages.get(i).getName());
            populatePackageItem.setStatus(listOfPackages.get(i).getStatus());
            populatePackageItem.setId(listOfPackages.get(i).getPackagesId());
            packageItems.add(populatePackageItem);
        }

        return packageItems;
    }

    @FXML
    void backToTrackPackage(ActionEvent event) throws IOException {
        Animations.fadeAway(btnBack,0.5,1,0,false);
        Animations.changePane(moreInformationPane,trackPackagePane,+850,0.5);

        //Need rework
        statusesVBox.getChildren().clear();
    }
}
