/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c482.View_Controller;

import c482.Model.InHouse;
import c482.Model.Inventory;
import c482.Model.Outsourced;
import c482.ValidateData;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
public class AddPartController implements Initializable {

    @FXML
    private RadioButton addPartInHouseRadioBtn;

    @FXML
    private RadioButton addPartOutsourcedRadioBtn;

    @FXML
    private TextField addPartIdTextField;

    @FXML
    private TextField addPartNameTextField;

    @FXML
    private TextField addPartInvTextField;

    @FXML
    private TextField addPartPriceTextField;

    @FXML
    private TextField addPartMaxTextField;

    @FXML
    private TextField addPartCompanyNameTextField;

    @FXML
    private Text addPartCompanyNameText;

    @FXML
    private TextField addPartMinTextField;

    @FXML
    private Button addPartSaveBtn;

    @FXML
    private Button addPartCancelBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addPartInHouseRadioBtn.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    addPartCompanyNameTextField.setText("");
                    addPartCompanyNameTextField.setPromptText("Machine ID");
                    addPartCompanyNameText.setText("Machine ID");
                    addPartOutsourcedRadioBtn.setSelected(false);
                } else if (wasPreviouslySelected) {
                    addPartCompanyNameTextField.setPromptText("Company Name");
                    addPartCompanyNameText.setText("Company Name");
                    addPartInHouseRadioBtn.setSelected(false);
                }
            }
        });

        addPartOutsourcedRadioBtn.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    String name = addPartNameTextField.getText();
                    addPartCompanyNameTextField.setText("");
                    addPartCompanyNameTextField.setPromptText("Company Name");
                    addPartCompanyNameText.setText("Company Name");
                    addPartInHouseRadioBtn.setSelected(false);
                } else if (wasPreviouslySelected) {
                    addPartCompanyNameTextField.setPromptText("Machine ID");
                    addPartCompanyNameText.setText("Machine ID");
                    addPartOutsourcedRadioBtn.setSelected(false);
                }
            }
        });
    }

    @FXML
    void handleAddPartCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Cancel");
        alert.setContentText("Cancel this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage stage;
            Parent root;
            stage = (Stage) addPartCancelBtn.getScene().getWindow();
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
    void handleAddPartSave(ActionEvent event) throws IOException {
        String nameField = addPartNameTextField.getText();
        String priceField = addPartPriceTextField.getText();
        String stockField = addPartInvTextField.getText();
        String minField = addPartMinTextField.getText();
        String maxField = addPartMaxTextField.getText();
        String variableTextField = addPartCompanyNameTextField.getText();

        String name = "";
        double price = 0.00;
        int stock = 0;
        int min = 0;
        int max = 0;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Input Error");

        Random randomId = new Random();
        int id = randomId.nextInt(1000) + 1;
        boolean isEmpty = ValidateData.isEmptyCheck(nameField, priceField, stockField, minField, maxField, variableTextField);
        String validationMessage = "";

        if (!isEmpty) {
            name = nameField;
            price = Double.parseDouble(addPartPriceTextField.getText());
            stock = Integer.parseInt(addPartInvTextField.getText());
            min = Integer.parseInt(addPartMinTextField.getText());
            max = Integer.parseInt(addPartMaxTextField.getText());

            try {
                validationMessage = ValidateData.validationMessage(name, stock, price, max, min);
                if (validationMessage.length() < 1) {
                    if (addPartInHouseRadioBtn.isSelected()) {
                        int machineId = Integer.parseInt(addPartCompanyNameTextField.getText());
                        InHouse inHousePart = new InHouse();
                        inHousePart.setId(id);
                        inHousePart.setName(name);
                        inHousePart.setPrice(price);
                        inHousePart.setMin(min);
                        inHousePart.setMax(max);
                        inHousePart.setStock(stock);
                        inHousePart.setMachineId(machineId);
                        Inventory.addPart(inHousePart);
                    } else if (addPartOutsourcedRadioBtn.isSelected()) {
                        String companyName = addPartCompanyNameTextField.getText();
                        Outsourced outsourcedPart = new Outsourced();
                        outsourcedPart.setId(id);
                        outsourcedPart.setName(name);
                        outsourcedPart.setPrice(price);
                        outsourcedPart.setMin(min);
                        outsourcedPart.setMax(max);
                        outsourcedPart.setStock(stock);
                        outsourcedPart.setCompanyName(companyName);
                        Inventory.addPart(outsourcedPart);
                    }

                    alert.setTitle("Part Saved");
                    alert.setHeaderText("Part Saved!");
                    alert.setContentText("Successfully saved " + name);
                    alert.show();

                    Stage stage;
                    Parent root;
                    stage = (Stage) addPartCancelBtn.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource(
                            "Main.fxml"));
                    root = loader.load();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Inventory Management System");
                    stage.show();
                } else {
                    alert.setContentText(ValidateData.validationMessage(name, stock, price, max, min));
                    alert.show();
                }
            } catch (NumberFormatException e) {

            }
        } else {
            alert.setContentText("There is an empty field!");
            alert.show();
        }
    }
}
