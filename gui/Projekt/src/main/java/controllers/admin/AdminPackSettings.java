package main.java.controllers.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.java.dao.PackageTypeDAO;
import main.java.entity.PackageType;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminPackSettings implements Initializable {

    @FXML
    private TextField smallSize;
    @FXML
    private TextField medSize;
    @FXML
    private TextField bigSize;
    @FXML
    private TextField smallWeight;
    @FXML
    private TextField medWeight;
    @FXML
    private TextField bigWeight;
    @FXML
    private TextField smallPrice;
    @FXML
    private TextField medPrice;
    @FXML
    private TextField bigPrice;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<PackageType> listOfTypeInfo = PackageTypeDAO.getTypeInfo();
        smallSize.setText(listOfTypeInfo.get(0).getSize());
        medSize.setText(listOfTypeInfo.get(1).getSize());
        bigSize.setText(listOfTypeInfo.get(2).getSize());

        smallWeight.setText(listOfTypeInfo.get(0).getWeight());
        medWeight.setText(listOfTypeInfo.get(1).getWeight());
        bigWeight.setText(listOfTypeInfo.get(2).getWeight());

        smallPrice.setText(listOfTypeInfo.get(0).getPrice());
        medPrice.setText(listOfTypeInfo.get(1).getPrice());
        bigPrice.setText(listOfTypeInfo.get(2).getPrice());

    }

    @FXML
    public void saveChanges(ActionEvent actionEvent) {

        if(!isEmpty()){

                PackageTypeDAO.updatePackageType(1,smallSize.getText(), smallWeight.getText(), smallPrice.getText());

                PackageTypeDAO.updatePackageType(2,medSize.getText(), medWeight.getText(), medPrice.getText());

                PackageTypeDAO.updatePackageType(3,bigSize.getText(), bigWeight.getText(), bigPrice.getText());

        }




    }

    private boolean isEmpty() {
        int error = 0;
        if (smallSize.getText().isEmpty()) {
            errorOnSmallSize();
            error++;
        }
        if (smallSize.getText().isEmpty()) {
            errorOnSmallSize();
            error++;
        }
        if (smallSize.getText().isEmpty()) {
            errorOnSmallSize();
            error++;
        }
        if(error > 0){
            return true;
        }else{
            return false;
        }
    }

    private void errorOnSmallSize() {
    }

    private boolean isValid(String size, String weight, String price) {
        int error = 0;
        Pattern patternSize = Pattern.compile("[0-9]{1,3}[x][0-9]{1,3}[x][0-9]{1,3}");
        Pattern patternWeight = Pattern.compile("0-9]{1,3}");
        Pattern patternPrice = Pattern.compile("0-9]{1,3}");

        Matcher matchSize = patternSize.matcher(size);
        Matcher matchWeight = patternWeight.matcher(weight);
        Matcher matchPrice = patternPrice.matcher(price);

        if(!matchSize.matches()){

            error++;
        }

        if(!matchWeight.matches()){

            error++;
        }

        if(!matchPrice.matches()){

            error++;
        }

        if(error > 0){
            return false;
        }else{
            return true;
        }
    }
}
