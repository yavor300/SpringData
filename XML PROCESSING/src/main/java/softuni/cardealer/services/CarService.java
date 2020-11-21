package softuni.cardealer.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface CarService {
    void seedCars() throws Exception;

    void carsAndParts() throws JAXBException;
}
