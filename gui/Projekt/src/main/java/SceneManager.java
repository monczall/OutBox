package main.java;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.controllers.courier.Courier;
import main.java.preferences.Preference;
import java.io.IOException;
import java.util.Hashtable;


public class SceneManager {
    private static Stage stage;
    private static Hashtable<String,String> view = new Hashtable<>();


    public static void addScene(String name,String path)throws IOException {
        view.put(name,path);
    }

    public static void loadScene(String path, AnchorPane anchorPane) throws IOException {
        Node newLoadedPane = FXMLLoader.load(Courier.class.getResource(path));
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
            Parent root = FXMLLoader.load(SceneManager.class.getResource(path));
            Scene scene = new Scene(root);

            // Preferences are being loaded from registry.
            Preference pref = new Preference();
            String color = pref.readPreference("color");

            if(color.equals("red")){
                scene.getStylesheets().add(SceneManager.class.getResource("../resources/css/clientRed.css").toExternalForm());
            }
            else{
                scene.getStylesheets().add(SceneManager.class.getResource("../resources/css/client.css").toExternalForm());
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
}
