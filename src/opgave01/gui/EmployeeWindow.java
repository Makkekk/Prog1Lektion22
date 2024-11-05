package opgave01.gui;

import javafx.beans.value.ChangeListener;
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
import opgave01.application.model.Employee;

public class EmployeeWindow extends Stage {
    private Employee employee;
    private TextField nameTextField;
    private TextField wageTextField;
    private CheckBox addCompanyCheckBox;
    private ComboBox<Company> selectCompanyComboBox;
    private Label errorLabel;
    private TextField employmentYearTextfield;


    public EmployeeWindow(String title, Employee employee) {
        initStyle(StageStyle.UTILITY);
        initModality(Modality.APPLICATION_MODAL);
        setResizable(false);
        this.employee = employee;
        setTitle(title);
        GridPane pane = new GridPane();
        initContent(pane);
        Scene scene = new Scene(pane);
        setScene(scene);
    }

    public EmployeeWindow(String title) {
        this(title, null);
    }

    private void initContent(GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setGridLinesVisible(false);
        Label lblName = new Label("Name");
        pane.add(lblName, 0, 0);
        nameTextField = new TextField();
        pane.add(nameTextField, 0, 1);
        nameTextField.setPrefWidth(200);
        Label lblHours = new Label("Hourly Wage");
        pane.add(lblHours, 0, 2);
        wageTextField = new TextField();
        pane.add(wageTextField, 0, 4);
        addCompanyCheckBox = new CheckBox("Company");
        pane.add(addCompanyCheckBox, 0, 7);
        ChangeListener<Boolean> listener = (ov, oldValue, newValue) -> selectedCompanyChanged(newValue);
        addCompanyCheckBox.selectedProperty().addListener(listener);

        Label lblEmploymentYear = new Label("Employment Year");
        pane.add(lblEmploymentYear,0,5);
        employmentYearTextfield = new TextField();
        pane.add(employmentYearTextfield,0,6);

        selectCompanyComboBox = new ComboBox<>();
        pane.add(selectCompanyComboBox, 0, 8);
        selectCompanyComboBox.getItems().addAll(Controller.getCompanies());
        selectCompanyComboBox.setDisable(true);
        Button btnCancel = new Button("Cancel");
        pane.add(btnCancel, 0, 9);
        GridPane.setHalignment(btnCancel, HPos.LEFT);
        btnCancel.setOnAction(event -> cancelAction());
        Button btnOK = new Button("OK");
        pane.add(btnOK, 0, 9);
        GridPane.setHalignment(btnOK, HPos.RIGHT);
        btnOK.setOnAction(event -> okAction());
        errorLabel = new Label();
        pane.add(errorLabel, 0, 10);
        errorLabel.setStyle("-fx-text-fill: red");
        initControls();
    }

    private void initControls() {
        if (employee != null) {
            nameTextField.setText(employee.getName());
            wageTextField.setText("" + employee.getWage());
            employmentYearTextfield.setText("" + employee.getEmploymentYear());
            if (employee.getCompany() != null) {
                addCompanyCheckBox.setSelected(true);
                selectCompanyComboBox.getSelectionModel().select(employee.getCompany());
            } else {
                selectCompanyComboBox.getSelectionModel().select(0);
            }
        } else {
            nameTextField.clear();
            wageTextField.clear();
            employmentYearTextfield.clear();
            selectCompanyComboBox.getSelectionModel().select(0);
        }
    }

    private void cancelAction() {
        hide();
    }

    private void okAction() {
        String name = nameTextField.getText().trim();
        if (name.isEmpty()) {
            errorLabel.setText("Name is empty");
            return;
        }
        int wage = -1;
        int employmentYear;
        try {
            wage = Integer.parseInt(wageTextField.getText().trim());
            employmentYear = Integer.parseInt(employmentYearTextfield.getText().trim());
        } catch (NumberFormatException ex) {
            errorLabel.setText("Enter a valid number of hours");
            return;
        }
        if (wage < 0) {
            errorLabel.setText("Wage is not a positive number");
            return;
        }
        boolean companyIsSelected = addCompanyCheckBox.isSelected();
        Company newCompany = selectCompanyComboBox.getSelectionModel().getSelectedItem();

        // Call application.controller methods
        if (employee != null) {
            Controller.updateEmployee(employee, name, wage,employmentYear);
            if (companyIsSelected) {
                Controller.addEmployeeToCompany(employee, newCompany);
            } else {
                Controller.removeEmployeeFromCompany(employee, employee.getCompany());
            }
        } else {
            if (companyIsSelected) {
                Controller.createEmployee(name, wage, newCompany, employmentYear);
            } else {
                Controller.createEmployee(name, wage, employmentYear);
            }
        }
        hide();
    }

    private void selectedCompanyChanged(boolean checked) {
        selectCompanyComboBox.setDisable(!checked);
    }
}