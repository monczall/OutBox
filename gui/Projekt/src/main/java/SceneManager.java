package main.java;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.java.controllers.Courier;

import java.io.IOException;
import java.security.AccessController;
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
            //stage.getIcons().add(new Image("/resources/images/icon.png"));
            stage.setScene(scene);

            stage.setTitle("OutBox");
            stage.show();
//            stage.setResizable(false);

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
