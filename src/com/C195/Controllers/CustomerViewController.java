package com.C195.Controllers;

import com.C195.Database.QueryCustomers;
import com.C195.Models.Country;
import com.C195.Models.Customer;
import com.C195.Models.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import static com.C195.Database.JDBC.closeConnection;
import static com.C195.Database.JDBC.openConnection;
import static com.C195.Database.QueryCountries.queryCountries;
import static com.C195.Database.QueryCustomers.*;
import static com.C195.Database.QueryDivisions.queryDivisions;

/**
 * This class is responsible for controlling all form functionality for the customerView scene.
 * @author Brady Bassett
 */
public class CustomerViewController extends FormController implements Initializable {
    public boolean newCustomer = true;
    @FXML private TextField customerIdField;
    @FXML private TextField customerNameField;
    @FXML private TextField customerAddressField;
    @FXML private TextField customerPostalCodeField;
    @FXML private TextField customerPhoneField;
    @FXML private ComboBox<Country> customerCountryBox;
    @FXML private ComboBox<Division> customerDivisionBox;

    /**
     * Initializes all text and prompt texts values, as well as the items for each ComboBox.
     * @param url The url to the customerView.fxml file.
     * @param resourceBundle This parameter contains all locale-specific objects.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initButtons();
        try {
            openConnection();
            customerIdField.setText(String.valueOf(QueryCustomers.queryMaxId() + 1));
        } catch (SQLException e) {
            showAlert(e);
        } finally {
            closeConnection();
        }
        customerNameField.setPromptText(bundle.getString("field.name"));
        customerAddressField.setPromptText(bundle.getString("field.address"));
        customerPostalCodeField.setPromptText(bundle.getString("field.postalCode"));
        customerPhoneField.setPromptText(bundle.getString("field.phone"));
        customerCountryBox.setPromptText(bundle.getString("field.country"));
        customerDivisionBox.setPromptText(bundle.getString("field.division"));
        initCountries();
    }

    /**
     * This function is called to initialize the countries ComboBox.
     */
    private void initCountries() {
        ObservableList<Country> countries;
        try {
            openConnection();
            countries = FXCollections.observableArrayList(queryCountries());
        } catch (SQLException e) {
            showAlert(e);
            return;
        } finally {
            closeConnection();
        }

        // Displays the country name value instead of the memory address
        customerCountryBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Country country) {
                return country.getCountry();
            }

            @Override
            public Country fromString(String s) {
                return null;
            }
        });

        customerCountryBox.setItems(countries);
    }

    /**
     * This function will initialize the divisions ComboBox with all divisions associated with a given countryId.
     * @param countryId The country that contains all relevant divisions.
     */
    private void initDivisions(int countryId) {
        ObservableList<Division> divisions;
        try {
            openConnection();
            divisions = FXCollections.observableArrayList(queryDivisions(countryId));
        } catch (SQLException e) {
            showAlert(e);
            return;
        } finally {
            closeConnection();
        }

        // Displays the division name value instead of the memory address
        customerDivisionBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Division division) {
                return division.getDivision();
            }

            @Override
            public Division fromString(String s) {
                return null;
            }
        });

        customerDivisionBox.setItems(divisions);
    }

    /**
     * This function is called whenever the modify button is selected on the mainView, and passes the selected
     * customer information into all fields.
     * @param customer The customer that is being loaded.
     */
    public void initCustomerData(Customer customer) {
        customerIdField.setText(String.valueOf(customer.getCustomerId()));
        customerNameField.setText(customer.getCustomerName());
        customerAddressField.setText(customer.getAddress());
        customerPostalCodeField.setText(customer.getPostalCode());
        customerPhoneField.setText(customer.getPhone());
        customerCountryBox.setValue(customer.getDivision().getCountry());
        customerDivisionBox.setValue(customer.getDivision());
        customerDivisionBox.setDisable(false);
    }

    /**
     * Whenever a country value is selected, query all divisions related to it and enable the user to select the
     * customerDivisionBox.
     */
    @FXML private void handleCountrySelected() {
        initDivisions(customerCountryBox.getValue().getCountryId());
        customerDivisionBox.setDisable(false);
        customerDivisionBox.setValue(null);
    }

    /**
     * This function saves the customer data and either creates or modifies an appointment in the database.
     * @param event The ActionEvent to pass down the current window to the main view.
     */
    @FXML private void handleSave(ActionEvent event) {
        try {
            validateItems();
            openConnection();
            Customer customer = new Customer(Integer.parseInt(customerIdField.getText()), customerNameField.getText(),
                    customerAddressField.getText(), customerPostalCodeField.getText(), customerPhoneField.getText(),
                    customerDivisionBox.getValue());
            if (newCustomer){
                createNewCustomer(customer, convertLocalToUTC(Timestamp.valueOf(LocalDateTime.now())));
            } else {
                modifyCustomer(customer, convertLocalToUTC(Timestamp.valueOf(LocalDateTime.now())));
            }
        } catch (NullPointerException | IllegalArgumentException | SQLException exception) {
            showAlert(exception);
            return;
        } finally {
            closeConnection();
        }
        showView(event, "../Views/mainView.fxml");
    }

    /**
     * This function is responsible for validating that every user inputted field is a valid input.
     */
    private void validateItems() {
        validateFieldNotEmpty(customerNameField.getText(), bundle.getString("error.nullNameField"));
        validateFieldNotEmpty(customerAddressField.getText(), bundle.getString("error.nullAddressField"));
        // REGEX - digits followed by a space followed by words and spaces
        validateField("\\d+\\s[\\w\\s]+", customerAddressField.getText(), bundle.getString("error.invalidAddress"));
        validateFieldNotEmpty(customerPostalCodeField.getText(), bundle.getString("error.nullPostalCodeField"));
        // REGEX - 5 character sequence of characters or digits
        validateField("[a-zA-Z\\d]{5}", customerPostalCodeField.getText(), bundle.getString("error.invalidPostal"));
        validateFieldNotEmpty(customerPhoneField.getText(), bundle.getString("error.nullPhoneField"));
        // REGEX - Various different methods of displaying a phone number (including international numbers)
        validateField("^(\\+?\\d{1,3}-?( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$",
                customerPhoneField.getText(), bundle.getString("error.invalidPhone"));

        if (customerCountryBox.getValue() == null)
            throw new NullPointerException(bundle.getString("error.nullCountryBox"));
        if (customerDivisionBox.getValue() == null)
            throw new NullPointerException(bundle.getString("error.nullDivisionBox"));
    }
}
