package main.java;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.controllers.courier.Courier;
import main.java.features.Preference;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.ResourceBundle;


public class SceneManager {

    private static Stage stage;
    private static final Hashtable<String, String> view = new Hashtable<>();
    private static final Preference pref = new Preference();
    private static ResourceBundle bundle;
    private static int counter = 0;
    private static double yOffset = 0;
    private static double xOffset = 0;

    public static void addScene(String name, String path) throws IOException {
        view.put(name, path);
    }

    public static void loadScene(String path, AnchorPane anchorPane) throws IOException {

        //Reading preferences about language and deciding in which language app should start
        if (Preference.readPreference("language").equals("english"))
            bundle = ResourceBundle.getBundle("main.resources.languages.lang_en");
        else
            bundle = ResourceBundle.getBundle("main.resources.languages.lang_pl");

        Node newLoadedPane = FXMLLoader.load(Courier.class.getResource(path), bundle);
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(newLoadedPane);
    }

    public static void removeScene(String name) {
        view.remove(name);
    }

    public static void renderScene(String name) {
        String path = "";
        Parent root = null;

        try {
            path = view.get(name);

            //Reading preferences about language and deciding in which language app should start
            if (Preference.readPreference("language").equals("english"))
                bundle = ResourceBundle.getBundle("main.resources.languages.lang_en");
            else
                bundle = ResourceBundle.getBundle("main.resources.languages.lang_pl");

            root = FXMLLoader.load(SceneManager.class.getResource(path), bundle);
            Scene scene = new Scene(root);


            //Reading preferences about color and deciding in which main color theme app should start
            if (Preference.readPreference("color").equals("red")) {
                scene.getRoot().setStyle("-fx-main-color: #d82020;" +
                        "-fx-second-color: #ffffff;");
            } else if (Preference.readPreference("color").equals("orange")) {
                scene.getRoot().setStyle("-fx-main-color: #ffa500;" +
                        "-fx-second-color: #000000;");
            } else {
                scene.getRoot().setStyle("-fx-main-color: #ffffff;" +
                        "-fx-second-color: #000000;");
            }

            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() - xOffset);
                    stage.setY(event.getScreenY() - yOffset);
                }
            });

            stage.setScene(scene);
            stage.getIcons().add(new Image("main/resources/images/appIcon.png"));
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Brak danego pliku XML");
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.err.println("Błędna ścieżka do pliku");
        }
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage _stage) {
        stage = _stage;
    }
}
