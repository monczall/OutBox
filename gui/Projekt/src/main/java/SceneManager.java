package main.java;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.controllers.courier.Courier;
import main.java.features.Preference;
import java.io.IOException;
import java.util.Hashtable;
import java.util.ResourceBundle;


public class SceneManager {

    private static Stage stage;
    private static Hashtable<String,String> view = new Hashtable<>();

    private static Preference pref = new Preference();
    private static ResourceBundle bundle;

    public static void addScene(String name,String path) throws IOException {
        view.put(name,path);
    }

    public static void loadScene(String path, AnchorPane anchorPane) throws IOException {

        //Reading preferences about language and deciding in which language app should start
        if(pref.readPreference("language").equals("english"))
            bundle = ResourceBundle.getBundle("main.resources.languages.lang_en");
        else
            bundle = ResourceBundle.getBundle("main.resources.languages.lang_pl");

        Node newLoadedPane = FXMLLoader.load(Courier.class.getResource(path),bundle);
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(newLoadedPane);
    }

    public static void removeScene(String name){
        view.remove(name);
    }

    public static void renderScene(String name){
        String path="";
        try{
            path=view.get(name);

            //Reading preferences about language and deciding in which language app should start
            if(pref.readPreference("language").equals("english"))
                bundle = ResourceBundle.getBundle("main.resources.languages.lang_en");
            else
                bundle = ResourceBundle.getBundle("main.resources.languages.lang_pl");

            Parent root = FXMLLoader.load(SceneManager.class.getResource(path),bundle);
            Scene scene = new Scene(root);


            //Reading preferences about color and deciding in which main color theme app should start
            if(pref.readPreference("color").equals("red")) {
                scene.getRoot().setStyle("-fx-main-color: #d82020;" +
                        "-fx-second-color: #ffffff;");
            }
            else if(pref.readPreference("color").equals("orange")) {
                scene.getRoot().setStyle("-fx-main-color: #ffa500;" +
                        "-fx-second-color: #000000;");
            }
            else{
                scene.getRoot().setStyle("-fx-main-color: #ffffff;" +
                        "-fx-second-color: #000000;");
            }


            stage.setScene(scene);
            stage.setTitle("OutBox");
            stage.show();
          //stage.setResizable(false);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Brak danego pliku XML");
        }catch(RuntimeException e){
            System.err.println("Podano błędną scieżkę pliku");
        }
    }

    public static void setStage(Stage _stage){
        stage = _stage;
    }

    public static Stage getStage() {
        return stage;
    }
}
