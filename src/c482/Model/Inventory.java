/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c482.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author john6
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public Inventory() {

    }

    public static void addPart(Part part) {
        allParts.add(part);
    }

    public static int lookupPart(String searchItem) {
        boolean partFound = false;
        int partIndex = 0;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Error!");
        alert.setContentText("Part not found");
     
        try {
            for (int i = 0; i < allParts.size(); i++) {
                double price = allParts.get(i).getPrice();
                double searchPrice = Double.valueOf(searchItem);
                
                if (price == searchPrice) {
                    partFound = true;
                    partIndex = i;
                }
                if (searchItem == null || searchItem.isEmpty()) {
                    partFound = true;
                    partIndex = i;
                }
                if (allParts.get(i).getId() == Integer.parseInt(searchItem)) {
                    partFound = true;
                    partIndex = i;
                } else if (allParts.get(i).getStock() == Integer.parseInt(searchItem)) {
                    partFound = true;
                    partIndex = i;
                }
            }
        } catch (NumberFormatException e) {
            for (int i = 0; i < allParts.size(); i++) {
                double price = allParts.get(i).getPrice();
                String result = String.format("%.2f", price);
                System.out.println("Formatted Price: " + result);

                if (searchItem == null || searchItem.isEmpty()) {
                    partFound = true;
                    partIndex = i;
                }

                String lowerCaseFilter = searchItem.toLowerCase();
                System.out.println("Search Item: " + lowerCaseFilter);

                if (allParts.get(i).getName().toLowerCase().contains(lowerCaseFilter)) {
                    partFound = true;
                    partIndex = i;
                } else if (result.equals(searchItem)) {
                    partFound = true;
                    partIndex = i;
                }
            }
        }
        if (partFound) {
            return partIndex;
        } else {
            return -1;
        }
    }
    
    public static void updatePart(int index, Part part) {
        allParts.set(index, part);
    }
    
    public static void deletePart(Part part) {
        allParts.remove(part);
    }
    
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static void addProduct(Product product) {
        allProducts.add(product);
    }
    
    public static int lookupProduct(String searchItem) {
        boolean productFound = false;
        int productIndex = 0;

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("Error!");
        alert.setContentText("Product not found");
     
        try {
            for (int i = 0; i < allProducts.size(); i++) {
                double price = allProducts.get(i).getPrice();
                double searchPrice = Double.valueOf(searchItem);
                
                if (price == searchPrice) {
                    productFound = true;
                    productIndex = i;
                }
                if (searchItem == null || searchItem.isEmpty()) {
                    productFound = true;
                    productIndex = i;
                }
                if (allProducts.get(i).getId() == Integer.parseInt(searchItem)) {
                    productFound = true;
                    productIndex = i;
                } else if (allProducts.get(i).getStock() == Integer.parseInt(searchItem)) {
                    productFound = true;
                    productIndex = i;
                }
            }
        } catch (NumberFormatException e) {
            for (int i = 0; i < allProducts.size(); i++) {
                double price = allProducts.get(i).getPrice();
                String result = String.format("%.2f", price);
                System.out.println("Formatted Price: " + result);

                if (searchItem == null || searchItem.isEmpty()) {
                    productFound = true;
                    productIndex = i;
                }

                String lowerCaseFilter = searchItem.toLowerCase();
                System.out.println("Search Item: " + lowerCaseFilter);

                if (allProducts.get(i).getName().toLowerCase().contains(lowerCaseFilter)) {
                    productFound = true;
                    productIndex = i;
                } else if (result.equals(searchItem)) {
                    productFound = true;
                    productIndex = i;
                }
            }
        }
        if (productFound) {
            return productIndex;
        } else {
            return -1;
        }
    }
    
    public static void updateProduct(int index, Product product) {
        allProducts.set(index, product);
    }
    
    public static void deleteProduct(Product product) {
        allProducts.remove(product);
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
