package softuni.cardealer.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.cardealer.domain.dtos.exportDtos.CustomerExportDto;
import softuni.cardealer.domain.dtos.importDtos.CustomerSeedDto;
import softuni.cardealer.domain.entities.Customer;
import softuni.cardealer.domain.repositories.CustomerRepository;
import softuni.cardealer.services.CustomerService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CustomerServiceImpl implements CustomerService {
    private static final String CUSTOMER_PATH = "src/main/resources/jsons/customers.json";
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, Gson gson) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void seedCustomers() throws IOException {
        String content = String.join("", Files.readAllLines(Path.of(CUSTOMER_PATH)));
        CustomerSeedDto[] customerSeedDtos = this.gson.fromJson(content, CustomerSeedDto[].class);
        for (CustomerSeedDto customerSeedDto : customerSeedDtos) {
            Customer customer = this.modelMapper.map(customerSeedDto, Customer.class);
            this.customerRepository.saveAndFlush(customer);
        }
    }

    @Override
    public String orderedCustomers() {
        Set<Customer> allByOrderByYoungDriverAscBirthDateAsc = this.customerRepository.getAllByOrderByBirthDateAscYoungDriverAsc();
        List<CustomerExportDto> toExport = new ArrayList<>();
        for (Customer customer : allByOrderByYoungDriverAscBirthDateAsc) {
            CustomerExportDto customerExportDto  = this.modelMapper.map(customer, CustomerExportDto.class);
            toExport.add(customerExportDto);
        }
        return this.gson.toJson(toExport);
    }
}
