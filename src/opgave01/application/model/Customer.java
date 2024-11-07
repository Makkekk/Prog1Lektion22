package opgave01.application.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Customer {
    private String name;

    // link to company class (--> 0..1)
    private Company company;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return  name;

    }

    public void add(Customer customer) {
    }


    public void setCompany(Company company) {
            if (this.company != company) {
                Company oldCompany = this.company;
                if (oldCompany != null) {
                    oldCompany.removeCustomer(this);
                }
                this.company = company;
                if (company != null)
                    company.addCustomer(this);
            }
        }

    public Company getCompany() {
        return company;
    }
}
