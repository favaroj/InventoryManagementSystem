/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c482.View_Controller;

import c482.Model.InHouse;
import c482.Model.Inventory;
import c482.Model.Outsourced;
import c482.Model.Part;
import c482.ValidateData;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author john6
 */
public class ModifyPartController implements Initializable {

    @FXML
    private RadioButton modifyPartInHouseRadioBtn;

    @FXML
    private RadioButton modifyPartOutsourcedRadioBtn;

    @FXML
    private TextField modifyPartIdTextField;

    @FXML
    private TextField modifyPartNameTextField;

    @FXML
    private TextField modifyPartInvTextField;

    @FXML
    private TextField modifyPartPriceTextField;

    @FXML
    private TextField modifyPartMaxTextField;

    @FXML
    private TextField modifyPartCompanyNameTextField;

    @FXML
    private Text modifyPartCompanyNameText;

    @FXML
    private TextField modifyPartMinTextField;

    @FXML
    private Button modifyPartSaveBtn;

    @FXML
    private Button modifyPartCancelBtn;

    private String machineId;

    private String companyName;

    private Part part = MainController.part;

    private boolean isOutsourced;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modifyPartIdTextField.setText(String.valueOf(MainController.part.getId()));
        modifyPartNameTextField.setText(MainController.part.getName());
        modifyPartInvTextField.setText(String.valueOf(MainController.part.getStock()));
        modifyPartPriceTextField.setText(String.valueOf(MainController.part.getPrice()));
        modifyPartMaxTextField.setText(String.valueOf(MainController.part.getMax()));
        modifyPartMinTextField.setText(String.valueOf(MainController.part.getMin()));

        if (part instanceof InHouse) {
            System.out.println("InHouse");
            modifyPartCompanyNameText.setText("Machine ID");
            modifyPartInHouseRadioBtn.setSelected(true);
            modifyPartCompanyNameTextField.setText(Integer.toString(((InHouse) part).getMachineId()));
        } else {
            System.out.println("Company Name");
            modifyPartCompanyNameText.setText("Company Name");
            modifyPartOutsourcedRadioBtn.setSelected(true);
            modifyPartCompanyNameTextField.setText(((Outsourced) part).getCompanyName());
            isOutsourced = true;
        }
    }

    @FXML
    void handleModifyPartOutsourcedRadioBtn(ActionEvent event) {
        isOutsourced = true;
        modifyPartCompanyNameTextField.setText(companyName);
        modifyPartCompanyNameText.setText("Company Name");
        modifyPartInHouseRadioBtn.setSelected(false);
    }

    @FXML
    void handleModifyPartInHouseRadioBtn(ActionEvent event) {
        isOutsourced = false;
        modifyPartCompanyNameTextField.setText("");
        modifyPartCompanyNameText.setText("Machine ID");
        modifyPartOutsourcedRadioBtn.setSelected(false);
    }

    @FXML
    void handleModifyPartCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Cancel");
        alert.setContentText("Cancel this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage stage;
            Parent root;
            stage = (Stage) modifyPartCancelBtn.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "Main.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Inventory Management System");
            stage.show();
        } else {

        }
    }

    @FXML
    void handleModifyPartSave(ActionEvent event) throws IOException {
        String idField = modifyPartIdTextField.getText();
        String nameField = modifyPartNameTextField.getText();
        String priceField = modifyPartPriceTextField.getText();
        String stockField = modifyPartInvTextField.getText();
        String minField = modifyPartMinTextField.getText();
        String maxField = modifyPartMaxTextField.getText();
        String variableTextField = modifyPartCompanyNameTextField.getText();

        int id = Integer.parseInt(idField);
        String name = "";
        Double price = 0.00;
        int stock = 0;
        int min = 0;
        int max = 0;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Input Error");

        boolean isEmpty = ValidateData.isEmptyCheck(nameField, priceField, stockField, minField, maxField, variableTextField);
        String validationMessage = "";

        if (!isEmpty) {
            name = nameField;
            price = Double.parseDouble(modifyPartPriceTextField.getText());
            stock = Integer.parseInt(modifyPartInvTextField.getText());
            min = Integer.parseInt(modifyPartMinTextField.getText());
            max = Integer.parseInt(modifyPartMaxTextField.getText());

            try {
                validationMessage = ValidateData.validationMessage(name, stock, price, max, min);
                System.out.println(isOutsourced);
                if (validationMessage.length() < 1) {
                    if (!isOutsourced) {
                        InHouse newInHouse = new InHouse();
                        newInHouse.setId(id);
                        newInHouse.setName(name);
                        newInHouse.setPrice(price);
                        newInHouse.setMin(min);
                        newInHouse.setMax(max);
                        newInHouse.setStock(stock);
                        newInHouse.setMachineId(Integer.valueOf(modifyPartCompanyNameTextField.getText()));
                        Inventory.updatePart(MainController.partIndex, newInHouse);
                    } else {
                        Outsourced newOutsourced = new Outsourced();
                        newOutsourced.setId(id);
                        newOutsourced.setName(name);
                        newOutsourced.setPrice(price);
                        newOutsourced.setMin(min);
                        newOutsourced.setMax(max);
                        newOutsourced.setStock(stock);
                        newOutsourced.setCompanyName(variableTextField);
                        Inventory.updatePart(MainController.partIndex, newOutsourced);
                    }

                    alert.setTitle("Part Saved");
                    alert.setHeaderText("Part Saved!");
                    alert.setContentText("Successfully saved " + name);
                    alert.show();

                    Stage stage;
                    Parent root;
                    stage = (Stage) modifyPartCancelBtn.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(
                            "Main.fxml"));
                    root = loader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    alert.setContentText(ValidateData.validationMessage(name, stock, price, max, min));
                    alert.show();
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            alert.setContentText("There is an empty field!");
            alert.show();
        }
    }
}
