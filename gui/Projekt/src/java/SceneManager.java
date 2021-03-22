package java;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Hashtable;

public class SceneManager {
    private static Stage stage;
    private static Hashtable<String,String> view = new Hashtable<>();


    public static void addScene(String name,String path)throws IOException {
        view.put(name,path);
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
            stage.setResizable(false);

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
