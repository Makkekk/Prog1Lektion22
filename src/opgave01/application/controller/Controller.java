package opgave01.application.controller;


import opgave01.application.model.Company;
import opgave01.application.model.Customer;
import opgave01.application.model.Employee;
import opgave01.storage.Storage;

import java.util.ArrayList;

public class Controller {
    /**
     * Creates a new Company.<br />
     * Requires: hours >= 0.
     *
     * @param name  name of the company
     * @param hours number of weekly work hours
     */
    public static Company createCompany(String name, int hours) {
        Company company = new Company(name, hours);
        Storage.addCompany(company);
        return company;
    }

    /**
     * Deletes the company.<br />
     * Requires: The company has no employees.
     *
     * @param company The company to delete.
     */
    public static void deleteCompany(Company company) {
        if (company.hasEmployees()) {
            return;
        }
        Storage.removeCompany(company);
    }

    /**
     * Updates the company.<br />
     * Requires: hours >= 0.
     */
    public static void updateCompany(Company company, String name, int hours) {
        company.setName(name);
        company.setHours(hours);
    }

    /**
     * Get all the companies
     */
    public static ArrayList<Company> getCompanies() {
        return Storage.getCompanies();
    }

    // -------------------------------------------------------------------------

    /**
     * Creates a new employee.<br />
     * Requires: wage >= 0.
     */
    public static Employee createEmployee(String name, int wage, int employmentYear) {
        Employee employee = new Employee(name, wage, employmentYear);
        Storage.addEmployee(employee);
        return employee;
    }

    /**
     * Creates a new employee.<br />
     * Requires: wage >= 0, company!=null.
     */
    public static Employee createEmployee(String name, int wage, Company company, int employtmentYear) {
        Employee employee = createEmployee(name, wage, employtmentYear);
        company.addEmployee(employee);
        return employee;
    }

    /**
     * Deletes the employee.
     */
    public static void deleteEmployee(Employee employee) {
        Company company = employee.getCompany();
        if (company != null) {
            company.removeEmployee(employee);
        }
        Storage.removeEmployee(employee);
    }

    /**
     * Updates the employee.<br />
     * Requires: wage >= 0.
     */
    public static void updateEmployee(Employee employee, String name, int wage, int employmentYear) {
        employee.setName(name);
        employee.setWage(wage);
        employee.setEmploymentYear(employmentYear);
    }

    /**
     * Adds the employee to the company. Removes the employee from the old company if present.
     */
    public static void addEmployeeToCompany(Employee employee, Company company) {
        company.addEmployee(employee);

    }

    /**
     * Removes the employee from the old company if not null.
     *
     * @param company  The old company. Can be null.
     * @param employee The employee to remove.
     */
    public static void removeEmployeeFromCompany(Employee employee, Company company) {
        if (company != null) {
            company.removeEmployee(employee);
        }
    }

    /**
     * Get all the employees.
     */
    public static ArrayList<Employee> getEmployees() {
        return Storage.getEmployees();
    }


    // -------------------------------------------------------------------------

    /**
     * * Creates a costumer without a company
     * @param name
     * @return customer
     */


    public static Customer createCustomer(String name, Company company) {
        Customer customer = new Customer(name);
        customer.setCompany(company);
        Storage.addCustomer(customer);
        return customer;
    }
    /**
     * Updates the Customer.<br />
     */
    public static void updateCustomer(Customer customer, String name, Company selectedCompany) {
        customer.setName(name);

    }
    /**
     * Creates a customer.<br />
     */

    public static void addCustomerToCompany(Customer customer, Company company) {
        if (company != null && customer != null) {
            company.addCustomer(customer);
        }
    }

    public static void removeCustomerFromCompany(Customer customer, Company company) {
        if (company != null) {
            company.removeCustomer(customer);
        }
    }

    public static ArrayList<Customer> getCustomers() {
        return Storage.getCustomers();
    }


}



