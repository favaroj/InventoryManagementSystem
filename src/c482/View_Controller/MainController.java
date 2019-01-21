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
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author john6
 */
public class MainController implements Initializable {

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private Pane mainProductPane;

    @FXML
    private HBox mainProductHBox;

    @FXML
    private Text mainProductHeader;

    @FXML
    private Button mainProductSearchBtn;

    @FXML
    private Button mainProductClearBtn;

    @FXML
    private TextField mainProductTextField;

    @FXML
    private Button mainProductAddBtn;

    @FXML
    private Button mainModifyProductBtn;

    @FXML
    private Button mainDeleteProductBtn;

    @FXML
    private TableView<Product> mainProductTableView;

    @FXML
    private TableColumn<Product, Integer> mainProductTableViewIdColumn;

    @FXML
    private TableColumn<Product, String> mainProductTableViewNameColumn;

    @FXML
    private TableColumn<Product, Integer> mainProductTableViewInventoryColumn;

    @FXML
    private TableColumn<Product, Double> mainProductTableViewPriceColumn;

    @FXML
    private Pane mainPartPane;

    @FXML
    private HBox mainPartHBox;

    @FXML
    private Text mainPartHeader;

    @FXML
    private Button mainPartSearchBtn;

    @FXML
    private TextField mainPartTextField;

    @FXML
    private Button mainPartAddBtn;

    @FXML
    private Button mainModifyPartBtn;

    @FXML
    private Button mainDeletePartBtn;

    @FXML
    private TableView<Part> mainPartTableView;

    @FXML
    private TableColumn<Part, Integer> mainPartTableViewIdColumn;

    @FXML
    private TableColumn<Part, String> mainPartTableViewNameColumn;

    @FXML
    private TableColumn<Part, Integer> mainPartTableViewInventoryColumn;

    @FXML
    private TableColumn<Part, Double> mainPartTableViewCostColumn;

    @FXML
    private Button mainExitBtn;

    @FXML
    private Button mainPartClearBtn;

    static Part part;

    static Product product;

    static int partIndex;

    static int productIndex;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainPartTableViewIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainPartTableViewNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainPartTableViewInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainPartTableViewCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        mainPartTableViewCostColumn.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("0.00")));

        mainProductTableViewIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainProductTableViewNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainProductTableViewInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        mainProductTableViewPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        mainProductTableViewPriceColumn.setCellFactory(new DecimalColumnFactory<>(new DecimalFormat("0.00")));

        updateMainPartTableView();
        updateMainProductTableView();
    }

    @FXML
    void handleMainExit(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void handleMainPartAdd(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) mainPartAddBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "AddPart.fxml"));
        root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void handleMainPartDelete(ActionEvent event) {
        Part selectedPart = mainPartTableView.getSelectionModel().getSelectedItem();
        if (selectedPart != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete Part");
            alert.setContentText("Delete this Part?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Inventory.deletePart(selectedPart);
            } else {

            }
        }
    }

    @FXML
    void handleMainPartModify(ActionEvent event) throws IOException {
        part = mainPartTableView.getSelectionModel().getSelectedItem();
        if (part != null) {
            partIndex = Inventory.getAllParts().indexOf(part);
            Parent modifyPartParent = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
            Scene modifyPartScene = new Scene(modifyPartParent);
            Stage modifyPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            modifyPartStage.setScene(modifyPartScene);
            modifyPartStage.show();
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error!");
            alert.setContentText("No part selected to modify.");
            alert.showAndWait();
        }
    }

    @FXML
    void handleMainPartSearch(ActionEvent event) {
        String searchItem = mainPartTextField.getText();
        Inventory.lookupPart(searchItem);
        int partIndex = -1;

        if (Inventory.lookupPart(searchItem) == -1) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error!");
            alert.setContentText("Part not found");
            alert.showAndWait();
        } else {
            partIndex = Inventory.lookupPart(searchItem);
            Part foundPart = Inventory.getAllParts().get(partIndex);
            ObservableList<Part> filteredParts = FXCollections.observableArrayList();
            filteredParts.add(foundPart);
            mainPartTableView.setItems(filteredParts);
        }
    }

    @FXML
    void handleMainPartClearBtnPress(ActionEvent event) {
        mainPartTextField.setText("");
        updateMainPartTableView();
    }

    @FXML
    void handleMainProductAdd(ActionEvent event) throws IOException {
        Parent modifyPartParent = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
        Scene modifyPartScene = new Scene(modifyPartParent);
        Stage modifyPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        modifyPartStage.setScene(modifyPartScene);
        modifyPartStage.show();
    }

    @FXML
    void handleMainProductDelete(ActionEvent event) {
        Product selectedProduct = mainProductTableView.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete Product");
            alert.setContentText("Delete this Product?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                Inventory.deleteProduct(selectedProduct);
            } else {

            }
        }
    }

    @FXML
    void handleMainProductModify(ActionEvent event) throws IOException {
        product = mainProductTableView.getSelectionModel().getSelectedItem();
        if (product != null) {
            productIndex = Inventory.getAllProducts().indexOf(product);

            FXMLLoader loader = new FXMLLoader(getClass().getResource(
                    "ModifyPart.fxml"));
            Parent modifyPartParent = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
            Scene modifyPartScene = new Scene(modifyPartParent);

            Stage modifyPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            modifyPartStage.setScene(modifyPartScene);
            modifyPartStage.show();

        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error!");
            alert.setContentText("No product selected to modify.");
            alert.showAndWait();
        }
    }

    @FXML
    void handleMainProductSearch(ActionEvent event) {
        String searchItem = mainProductTextField.getText();
        Inventory.lookupProduct(searchItem);
        int productIndex = -1;

        if (Inventory.lookupProduct(searchItem) == -1) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error!");
            alert.setContentText("Product not found");
            alert.showAndWait();
        } else {
            productIndex = Inventory.lookupProduct(searchItem);
            Product foundProduct = Inventory.getAllProducts().get(productIndex);
            ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
            filteredProducts.add(foundProduct);
            mainProductTableView.setItems(filteredProducts);
        }
    }

    @FXML
    void handleMainProductClearBtnPress(ActionEvent event) {
        mainProductTextField.setText("");
        updateMainProductTableView();
    }

    private void updateMainPartTableView() {
        mainPartTableView.setItems(Inventory.getAllParts());
    }

    private void updateMainProductTableView() {
        mainProductTableView.setItems(Inventory.getAllProducts());
    }
}
