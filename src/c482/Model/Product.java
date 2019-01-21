/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c482.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author john6
 */
public class Product {
    
    ObservableList<Part> associatedParts = FXCollections.observableArrayList();;
    protected int id;
    protected String name;
    protected double price;
    protected int stock;
    protected int min;
    protected int max;
    
    public Product() {
        
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public void setStock(int stock) {
        this.stock = stock;
    }
    
    public void setMin(int min) {
        this.min = min;
    }
    
    public void setMax(int max) {
        this.max = max;
    }
    
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }
    
    public int getStock() {
        return stock;
    }
    
    public int getMin() {
        return min;
    }
    
    public int getMax() {
        return max;
    }
    
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
