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
import main.java.features.Animations;

import java.io.IOException;
import java.net.URL;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        moreInformationPane.setTranslateX(+850);
        btnBack.setVisible(false);

        //Testing how ClientTrackPackage view will look like with example data
        List<PackageTest> list = new ArrayList<>(packageTest());
        for(int i=0; i<list.size(); i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("../../../resources/view/client/packageItem.fxml"));

            try {
                Pane pane = fxmlLoader.load();
                PackageItem packageItem = fxmlLoader.getController();       //Loading controler of packageItem.fxml

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

                        //Testing dynamically created statuses from list that's gonna be filled with db rows
                        createStatus("28 Mar 2021 23:00", "Przesyłka zarejestrowana");
                        createStep(2);
                        createStatus("28 Mar 2021 23:00", "Odebrana od klienta");
                        createStep(2);
                        createStatus("28 Mar 2021 23:00", "Przyjęta w oddziale");
                        createStep(2);
                        createStatus("28 Mar 2021 23:00", "W transporcie");
                        createStep(2);
                        createStatus("28 Mar 2021 23:00", "W doręczeniu");
                        createStep(4);
                        createCurrentStatus("28 Mar 2021 23:00", "Dostarczona - " + packageItem.getNumber(),"Podróż przesyłki od Nadawcy do Obiorcy zakończyła się. Dziękujemy!");
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
    private List<PackageTest> packageTest(){
        List<PackageTest> ls = new ArrayList<>();
        PackageTest tes = new PackageTest();

        tes.setPackageNumber("3232323232");
        tes.setSender("FILIP");
        tes.setStatus("ZAREJESTROWANA");
        ls.add(tes);

        tes = new PackageTest();
        tes.setPackageNumber("121212121");
        tes.setSender("ANDRZEJ");
        tes.setStatus("W TRANSPORCIE");
        ls.add(tes);

        tes = new PackageTest();
        tes.setPackageNumber("5151515151");
        tes.setSender("STEVE");
        tes.setStatus("W ODDZIALE");
        ls.add(tes);

        return ls;
    }

    @FXML
    void backToTrackPackage(ActionEvent event) throws IOException {
        Animations.fadeAway(btnBack,0.5,1,0,false);
        Animations.changePane(moreInformationPane,trackPackagePane,+850,0.5);

        //Need rework
        statusesVBox.getChildren().clear();
    }
}
