package softuni.cardealer.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface CustomerService {
    void seedCustomers() throws IOException, JAXBException;

    String orderedCustomers();

    void exportOrdered() throws JAXBException;


}
