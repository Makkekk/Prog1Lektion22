package opgave01.gui;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import opgave01.application.controller.Controller;
import opgave01.application.model.Company;
import opgave01.application.model.Customer;
import java.util.List;

public class SelectCustomerWindow extends Stage {
    private ListView<Customer> customerListView; // ListView for selecting customers
    private Company company;
    private Label errorLabel;

    // Constructor for choosing customers to add to a company
    public SelectCustomerWindow(String title, Company company) {
        this.company = company;
        this.setTitle(title);
        this.setResizable(false);

        // Set the modal window properties
        this.initStyle(StageStyle.UTILITY);
        this.initModality(Modality.APPLICATION_MODAL);

        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);

        // Label and ListView for selecting customers
        pane.add(new Label("Select Customers"), 0, 0);
        customerListView = new ListView<>();
        customerListView.getItems().addAll(Controller.getCustomers());
        customerListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // Allow multiple selections
        pane.add(customerListView, 0, 1);
        customerListView.setPrefHeight(200);
        customerListView.setPrefWidth(200);

        // Error label
        errorLabel = new Label();
        pane.add(errorLabel, 0, 2);
        errorLabel.setStyle("-fx-text-fill: red");

        // Buttons
        Button cancelButton = new Button("Cancel");
        pane.add(cancelButton, 0, 3);
        GridPane.setHalignment(cancelButton, HPos.LEFT);
        cancelButton.setOnAction(event -> this.cancelAction());

        Button okButton = new Button("OK");
        pane.add(okButton, 0, 3);
        GridPane.setHalignment(okButton, HPos.RIGHT);
        okButton.setOnAction(event -> this.okAction());
    }

    private void cancelAction() {
        this.hide();
    }

    private void okAction() {
        // Get the selected customers from the ListView
        List<Customer> selectedCustomers = customerListView.getSelectionModel().getSelectedItems();
        if (selectedCustomers.isEmpty()) {
            errorLabel.setText("No customers selected");
            return;
        }

        // Add selected customers to the company
        for (Customer customer : selectedCustomers) {
            Controller.addCustomerToCompany(customer, company);
        }

        this.hide();
    }
}
