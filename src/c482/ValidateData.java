/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c482;

/**
 *
 * @author john6
 */
public class ValidateData {

    public static String validationMessage(String name, int stock, double price, int max, int min) {
        String alertString = "";
        if (min > max) {
            alertString += "Min must be less than max!\nMax must be less than min!\n";
        }
        if (stock > max || stock < min) {
            alertString += "Inv must be greater than or equal to min and less than or equal to max!";
        }
        return alertString;
    }
    
    public static boolean isEmptyCheck(String nameField, String priceField, String stockField, String minField, String maxField, String variableTextField) {
        if (!nameField.isEmpty() && !priceField.isEmpty() && !stockField.isEmpty() && !minField.isEmpty() && !maxField.isEmpty() && !variableTextField.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }
}
