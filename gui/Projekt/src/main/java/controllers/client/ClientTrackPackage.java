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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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
    private Label test;

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
                        test.setText(packageItem.getNumber());
                        btnBack.setVisible(true);
                        btnBack.setOpacity(1);
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
        Animations.fadeAway(btnBack,0.5);
        Animations.changePane(moreInformationPane,trackPackagePane,+850,0.5);
    }
}
