package softuni.cardealer.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.cardealer.domain.dtos.xml.exportDtos.CustomerOrderExportDto;
import softuni.cardealer.domain.dtos.xml.exportDtos.CustomerOrderRootDto;
import softuni.cardealer.domain.dtos.xml.importDtos.CustomImportRootDto;
import softuni.cardealer.domain.dtos.xml.importDtos.CustomerImportDto;
import softuni.cardealer.domain.entities.Customer;
import softuni.cardealer.domain.repositories.CustomerRepository;
import softuni.cardealer.services.CustomerService;
import softuni.cardealer.utils.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final String CUSTOMER_EXPORT_ORDERED = "src/main/resources/exported/ordered-customers.xml";
    private static final String CUSTOMER_XML = "src/main/resources/xmls/customers.xml";
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final XmlParser xmlParser;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, Gson gson, XmlParser xmlParser) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedCustomers() throws IOException, JAXBException {
        CustomImportRootDto customImportRootDto = this.xmlParser.parseXml(CustomImportRootDto.class, CUSTOMER_XML);
        for (CustomerImportDto dto : customImportRootDto.getCustomerImportDtos()) {
            this.customerRepository.saveAndFlush(this.modelMapper.map(dto, Customer.class));
        }

    }

    @Override
    public String orderedCustomers() {
        return null;
    }

    @Override
    public void exportOrdered() throws JAXBException {
        List<CustomerOrderExportDto> collect = this.customerRepository.findAllAndSort()
                .stream()
                .map(c -> this.modelMapper.map(c, CustomerOrderExportDto.class))
                .collect(Collectors.toList());
        CustomerOrderRootDto root = new CustomerOrderRootDto();
        root.setCustomers(collect);

        this.xmlParser.exportToXml(root, CustomerOrderRootDto.class, CUSTOMER_EXPORT_ORDERED);
    }
}
