package opgave01.gui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import opgave01.application.controller.Controller;
import opgave01.application.model.Company;
import opgave01.application.model.Customer;

public class CustomerWindow extends Stage {
    private Customer customer;
    private TextField customerTextField;
    private Company company;
    private Label errorLabel;
    private ComboBox<Company> selectCompanyComboBox;

    public CustomerWindow(String title, Customer customer) {
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        this.customer = customer;

        this.setTitle(title);
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    public CustomerWindow(String title) {
        this(title, null);
    }


    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        pane.add(new Label("Kundes navn"), 0, 0);
        customerTextField = new TextField();
        pane.add(customerTextField, 0, 1);
        customerTextField.setPrefWidth(200);

        pane.add(new Label("Vælg Virksomhed"),0,2);
        selectCompanyComboBox = new ComboBox<>();
        selectCompanyComboBox.getItems().addAll(Controller.getCompanies());
        selectCompanyComboBox.setPrefWidth(200);
        selectCompanyComboBox.setDisable(false);
        pane.add(selectCompanyComboBox,0,3);


        Button cancelButton = new Button("Cancel");
        pane.add(cancelButton, 0, 4);
        GridPane.setHalignment(cancelButton, HPos.LEFT);
        cancelButton.setOnAction(event -> this.cancelAction());

        Button okButton = new Button("OK");
        pane.add(okButton, 0, 4);
        GridPane.setHalignment(okButton, HPos.RIGHT);
        okButton.setOnAction(event -> this.okAction());

        errorLabel = new Label();
        pane.add(errorLabel, 0, 5);
        errorLabel.setStyle("-fx-text-fill: red");

        this.initControls();
    }

    private void initControls() {
        if (customer != null) {
            // If updating an existing customer, pre-fill the customer name
            customerTextField.setText(customer.getName());
            // Select the associated company in the ComboBox
            selectCompanyComboBox.getSelectionModel().select(customer.getCompany());
        } else {
            // Clear fields if creating a new customer
            customerTextField.clear();
            selectCompanyComboBox.getSelectionModel().select(0); // Default to the first company
        }
    }

    private void cancelAction() {
        this.hide();
    }

    private void okAction() {
        String name = customerTextField.getText().trim();
        if (name.isEmpty()) {
            errorLabel.setText("Name is empty");
            return;
        }
        Company selectedCompany = selectCompanyComboBox.getSelectionModel().getSelectedItem();
        if (selectedCompany == null) {
            errorLabel.setText("Please select a company");
            return;
        }

        if (customer != null) {
            // Update the existing customer
            Controller.updateCustomer(customer, name, selectedCompany);
        } else {
            // Create a new customer
            Controller.createCustomer(name, selectedCompany);
        }

        this.hide(); // Close the window after the action
    }
}
