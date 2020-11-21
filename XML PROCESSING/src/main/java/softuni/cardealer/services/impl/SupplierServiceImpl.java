package softuni.cardealer.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.cardealer.domain.dtos.xml.importDtos.SupplierImportDto;
import softuni.cardealer.domain.dtos.xml.importDtos.SupplierImportRootDto;
import softuni.cardealer.domain.entities.Supplier;
import softuni.cardealer.domain.repositories.SupplierRepository;
import softuni.cardealer.services.SupplierService;
import softuni.cardealer.utils.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SupplierServiceImpl implements SupplierService {
    private final static String SUPPLIER_XML = "src/main/resources/xmls/suppliers.xml";
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final XmlParser xmlParser;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper, Gson gson, XmlParser xmlParser) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedSupplier() throws JAXBException {
        SupplierImportRootDto supplierImportRootDto = this.xmlParser.parseXml(SupplierImportRootDto.class, SUPPLIER_XML);
        for (SupplierImportDto supplier : supplierImportRootDto.getSuppliers()) {
            this.supplierRepository.saveAndFlush(this.modelMapper.map(supplier, Supplier.class));
        }

    }

    @Override
    public String localSuppliers() {
        return null;
    }
}
