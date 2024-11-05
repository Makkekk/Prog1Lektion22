package opgave01.gui;

import javafx.application.Application;
import opgave01.application.controller.Controller;
import opgave01.application.model.Company;

public class App {
    public static void main(String[] args) {
        initStorage();
        Application.launch(StartWindow.class);
    }
    /**
     * Initializes the storage with some objects.
     */
    public static void initStorage() {
        Company ibm = Controller.createCompany("IBM", 37);
        Company amd = Controller.createCompany("AMD", 40);
        Controller.createCompany("NVIDIA", 45);
        Controller.createCompany("GOOGLE", 32);

        Controller.createEmployee("Bob Dole", 210, amd, 2022);
        Controller.createEmployee("Alice Schmidt", 250, ibm,2024);
        Controller.createEmployee("George Down", 150, amd, 2002);
        Controller.createEmployee("Rita Uphill", 300,amd,2001);

        Controller.createCustomer("Karen Thæne");
        Controller.createCustomer("Anne Dam");
    }
}
