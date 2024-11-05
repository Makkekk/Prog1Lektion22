package opgave01.application.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Customer {
    private String name;

    // A arraylist that this costumer is associated with many-to-many
    private ArrayList<Company> companies;

    public Customer(String name) {
        this.name = name;
        this.companies = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name:'" + name + '\'' +
                '}';
    }

    public void add(Customer customer) {
    }

    public void setCompany(Company company) {

    }

    public void addCompany(Company company) {
        if (!companies.contains(company)) {
            companies.add(company); // Add the company to this customer's list
            company.addCustomer(this);
        }
    }

}