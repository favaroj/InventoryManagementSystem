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

public class AddProductController implements Initializable {

    @FXML
    private Button addProductSaveBtn;

    @FXML
    private Button addProductCancelBtn;

    @FXML
    private Button addProductDeleteBtn;

    @FXML
    private TableView<Part> addProductPartDeleteTableView;

    @FXML
    private TableColumn<Part, Integer> addProductPartIdDeleteColumn;

    @FXML
    private TableColumn<Part, String> addProductPartNameDeleteColumn;

    @FXML
    private TableColumn<Part, Integer> addProductInvLevelDeleteColumn;

    @FXML
    private TableColumn<Part, Double> addProductPartPriceDeleteColumn;

    @FXML
    private Button addProductAddBtn;

    @FXML
    private Button addProductSearchBtn;

    @FXML
    private Button clearProductAddPartSearchBtn;

    @FXML
    private TextField addProductSearchTextField;

    @FXML
    private TableView<Part> addProductPartTableView;

    @FXML
    private TableColumn<Part, Integer> addProductPartIdColumn;

    @FXML
    private TableColumn<Part, String> addProductPartNameColumn;

    @FXML
    private TableColumn<Part, Integer> addProductPartInvLevelColumn;

    @FXML
    private TableColumn<Part, Double> addProductPartPriceColumn;

    @FXML
    private TextField addProductIdTextField;

    @FXML
    private TextField addProductNameTextField;

    @FXML
    private TextField addProductInvTextField;

    @FXML
    private TextField addProductPriceTextField;

    @FXML
    private TextField addProductMaxTextField;

    @FXML
    private TextField addProductMinTextField;

    private ObservableList<Part> currentProductParts = FXCollections.observableArrayList();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addProductPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductPartInvLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        addProductPartPriceColumn.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("0.00")));

        addProductPartIdDeleteColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductPartNameDeleteColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInvLevelDeleteColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPartPriceDeleteColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        addProductPartPriceDeleteColumn.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("0.00")));

        updateProductPartAddTableView();
        updateProductPartDeleteTableView();
    }

    @FXML
    void handleAddPartToProduct(ActionEvent event) {
        Part selectedPart = addProductPartTableView.getSelectionModel().getSelectedItem();
        currentProductParts.add(selectedPart);
        System.out.println("Selected Parent: " + selectedPart.getName());
        updateProductPartDeleteTableView();
    }

    @FXML
    void handleAddProductCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Cancel");
        alert.setContentText("Cancel this?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage stage;
            Parent root;
            stage = (Stage) addProductCancelBtn.getScene().getWindow();
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
    void handleAddProductDelete(ActionEvent event) {
        Part selectedPart = addProductPartDeleteTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete Part");
            alert.setContentText("Delete this Part from Product?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                currentProductParts.remove(selectedPart);
            } else {

            }
        }
    }

    @FXML
    void handleAddProductSave(ActionEvent event) throws IOException {
        String nameField = addProductNameTextField.getText();
        String priceField = addProductPriceTextField.getText();
        String stockField = addProductInvTextField.getText();
        String minField = addProductMinTextField.getText();
        String maxField = addProductMaxTextField.getText();

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
            name = addProductNameTextField.getText();
            price = Double.parseDouble(addProductPriceTextField.getText());
            stock = Integer.parseInt(addProductInvTextField.getText());
            min = Integer.parseInt(addProductMinTextField.getText());
            max = Integer.parseInt(addProductMaxTextField.getText());

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

                    if (currentProductParts.size() > 0) {
                        for (Part part : currentProductParts) {
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
                            Inventory.addProduct(newProduct);

                            alert.setTitle("Product Saved");
                            alert.setHeaderText("Product Saved!");
                            alert.setContentText("Successfully saved " + name);
                            alert.show();

                            Stage stage;
                            Parent root;
                            stage = (Stage) addProductCancelBtn.getScene().getWindow();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                                    "Main.fxml"));
                            root = loader.load();
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.setTitle("Inventory Management System");
                            stage.show();
                        }
                    } else {
                        alert.setHeaderText("Product Save Error!");
                        alert.setContentText("Product must contain at least one Part.");
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
    void handleAddProductSearch(ActionEvent event) {
        String searchItem = addProductSearchTextField.getText();
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
            addProductPartTableView.setItems(filteredParts);
        }
    }

    @FXML
    void handleClearProductAddPartSearch(ActionEvent event) {
        addProductSearchTextField.setText("");
        updateProductPartAddTableView();
    }

    public void updateProductPartAddTableView() {
        addProductPartTableView.setItems(Inventory.getAllParts());
    }

    public void updateProductPartDeleteTableView() {
        addProductPartDeleteTableView.setItems(currentProductParts);
    }
}
