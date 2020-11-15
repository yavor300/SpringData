package softuni.cardealer.services;

import softuni.cardealer.domain.entities.Supplier;

import java.io.IOException;
import java.util.Set;

public interface SupplierService {
    void seedSupplier() throws IOException;

    String localSuppliers();
}
