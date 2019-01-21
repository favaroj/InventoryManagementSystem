/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c482.View_Controller;

import c482.DecimalColumnFactory;
import c482.Model.Inventory;
import c482.Model.Part;
import c482.Model.Product;
import c482.ValidateData;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author john6
 */
public class ModifyProductController implements Initializable {

    @FXML
    private Button modifyProductSaveBtn;

    @FXML
    private Button modifyProductCancelBtn;

    @FXML
    private Button modifyProductDeleteBtn;

    @FXML
    private TableView<Part> modifyProductPartDeleteTableView;

    @FXML
    private TableColumn<Part, Integer> modifyProductPartIdDeleteColumn;

    @FXML
    private TableColumn<Part, String> modifyProductPartNameDeleteColumn;

    @FXML
    private TableColumn<Part, Integer> modifyProductPartStockDeleteColumn;

    @FXML
    private TableColumn<Part, Double> modifyProductPartPriceDeleteColumn;

    @FXML
    private Button modifyProductAddBtn;

    @FXML
    private Button modifyProductSearchBtn;

    @FXML
    private TextField modifyProductSearchTextField;

    @FXML
    private TableView<Part> modifyProductPartTableView;

    @FXML
    private TableColumn<Part, Integer> modifyProductPartIdAddColumn;

    @FXML
    private TableColumn<Part, String> modifyProductPartNameAddColumn;

    @FXML
    private TableColumn<Part, Integer> modifyProductPartStockAddColumn;

    @FXML
    private TableColumn<Part, Double> modifyProductPartPriceAddColumn;

    @FXML
    private Button clearProductAddPartSearchBtn;

    @FXML
    private TextField modifyProductIdTextField;

    @FXML
    private TextField modifyProductNameTextField;

    @FXML
    private TextField modifyProductInvTextField;

    @FXML
    private TextField modifyProductPriceTextField;

    @FXML
    private TextField modifyProductMaxTextField;

    @FXML
    private TextField modifyProductMinTextField;

    private final Product selectedProduct = MainController.product;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        modifyProductPartIdAddColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductPartNameAddColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductPartStockAddColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPartPriceAddColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyProductPartPriceAddColumn.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("0.00")));

        modifyProductPartIdDeleteColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductPartNameDeleteColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductPartStockDeleteColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPartPriceDeleteColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        modifyProductPartPriceDeleteColumn.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("0.00")));

        modifyProductIdTextField.setText(String.valueOf(selectedProduct.getId()));
        modifyProductNameTextField.setText(selectedProduct.getName());
        modifyProductInvTextField.setText(String.valueOf(selectedProduct.getStock()));
        modifyProductPriceTextField.setText(String.valueOf(selectedProduct.getPrice()));
        modifyProductMaxTextField.setText(String.valueOf(selectedProduct.getMax()));
        modifyProductMinTextField.setText(String.valueOf(selectedProduct.getMin()));

        updateProductPartAddTableView();
        updateProductPartDeleteTableView();
    }

    @FXML
    void handleModifyProductAdd(ActionEvent event) {
        Part selectedPart = modifyProductPartTableView.getSelectionModel().getSelectedItem();
        selectedProduct.addAssociatedPart(selectedPart);
        System.out.println("Selected Parent: " + selectedPart.getName());
        updateProductPartDeleteTableView();
    }

    @FXML
    void handleModifyProductCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Cancel");
        alert.setContentText("Cancel this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage stage;
            Parent root;
            stage = (Stage) modifyProductCancelBtn.getScene().getWindow();
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
    void handleModifyProductDelete(ActionEvent event) {
        Part selectedPart = modifyProductPartDeleteTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete Part");
            alert.setContentText("Delete this Part from Product?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                selectedProduct.getAllAssociatedParts().remove(selectedPart);
            } else {

            }
        }
    }

    @FXML
    void handleModifyProductSave(ActionEvent event) throws IOException {
        String nameField = modifyProductNameTextField.getText();
        String priceField = modifyProductPriceTextField.getText();
        String stockField = modifyProductInvTextField.getText();
        String minField = modifyProductMinTextField.getText();
        String maxField = modifyProductMaxTextField.getText();

        String name = "";
        Double price = 0.00;
        int stock = 0;
        int min = 0;
        int max = 0;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error");
        alert.setHeaderText("Input Error");

        Random randomId = new Random();
        int id = randomId.nextInt(1000) + 1;
        boolean isEmpty = ValidateData.isEmptyCheck(nameField, priceField, stockField, minField, maxField, "Field");
        String validationMessage = "";

        if (!isEmpty) {
            name = modifyProductNameTextField.getText();
            price = Double.parseDouble(modifyProductPriceTextField.getText());
            stock = Integer.parseInt(modifyProductInvTextField.getText());
            min = Integer.parseInt(modifyProductMinTextField.getText());
            max = Integer.parseInt(modifyProductMaxTextField.getText());

            Product newProduct = new Product();

            try {
                validationMessage = ValidateData.validationMessage(name, stock, price, max, min);
                if (validationMessage.length() < 1) {
                    newProduct.setId(id);
                    newProduct.setName(name);
                    newProduct.setPrice(price);
                    newProduct.setMin(min);
                    newProduct.setMax(max);
                    newProduct.setStock(stock);
                    int partTotal = 0;

                    if (selectedProduct.getAllAssociatedParts().size() > 0) {
                        for (Part part : selectedProduct.getAllAssociatedParts()) {
                            newProduct.addAssociatedPart(part);
                            System.out.println("Product Part: " + part.getName());

                            partTotal += part.getPrice();
                        }
                        if (partTotal > price) {
                            alert.setTitle("Error");
                            alert.setHeaderText("Product Save Error!");
                            alert.setContentText("The product must not cost less than the total cost of the parts.");
                            alert.show();
                        } else {
                            Inventory.updateProduct(MainController.productIndex, newProduct);

                            alert.setTitle("Product Saved");
                            alert.setHeaderText("Product Saved!");
                            alert.setContentText("Successfully saved " + name);
                            alert.show();

                            Stage stage;
                            Parent root;
                            stage = (Stage) modifyProductCancelBtn.getScene().getWindow();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                                    "Main.fxml"));
                            root = loader.load();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.setTitle("Inventory Management System");
                            stage.show();
                        }
                    } else {
                        alert.setContentText("Product must contain at least one Part!");
                        alert.show();
                    }
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

    @FXML
    void handleModifyProductSearch(ActionEvent event) {
        String searchItem = modifyProductSearchTextField.getText();
        Inventory.lookupPart(searchItem);
        int partIndex = -1;

        if (Inventory.lookupPart(searchItem) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error!");
            alert.setContentText("Part not found");
            alert.showAndWait();
        } else {
            partIndex = Inventory.lookupPart(searchItem);
            Part foundPart = Inventory.getAllParts().get(partIndex);
            ObservableList<Part> filteredParts = FXCollections.observableArrayList();
            filteredParts.add(foundPart);
            modifyProductPartTableView.setItems(filteredParts);
        }
    }

    @FXML
    void handleClearProductAddPartSearch(ActionEvent event) {
        modifyProductSearchTextField.setText("");
        modifyProductPartTableView.setItems(Inventory.getAllParts());
    }

    public void updateProductPartAddTableView() {
        modifyProductPartTableView.setItems(Inventory.getAllParts());
    }

    public void updateProductPartDeleteTableView() {
        modifyProductPartDeleteTableView.setItems(selectedProduct.getAllAssociatedParts());
    }
}
