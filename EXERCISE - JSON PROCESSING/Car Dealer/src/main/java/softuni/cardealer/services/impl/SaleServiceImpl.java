package softuni.cardealer.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.cardealer.domain.dtos.exportDtos.CustomerSalesExportDto;
import softuni.cardealer.domain.dtos.exportDtos.SaleDetailedDto;
import softuni.cardealer.domain.entities.Car;
import softuni.cardealer.domain.entities.Customer;
import softuni.cardealer.domain.entities.Part;
import softuni.cardealer.domain.entities.Sale;
import softuni.cardealer.domain.repositories.CarRepository;
import softuni.cardealer.domain.repositories.CustomerRepository;
import softuni.cardealer.domain.repositories.SaleRepository;
import softuni.cardealer.services.SaleService;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SaleServiceImpl implements SaleService {
    private final SaleRepository saleRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;

    public SaleServiceImpl(SaleRepository saleRepository, CarRepository carRepository, CustomerRepository customerRepository, Gson gson, ModelMapper modelMapper) {
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedSales() {
        for (int i = 0; i < 3; i++) {
            Sale sale = new Sale();
            sale.setDiscount(getRandomDiscount());
            sale.setCar(getRandomCar());
            sale.setCustomer(getRandomCustomer());
            this.saleRepository.saveAndFlush(sale);
        }
    }

    @Override
    public String totalSalesByCustomer() {
        List<Sale> all = this.saleRepository.findAll();

        Set<Long> customersIds = new HashSet<>();
        for (Sale sale : all) {
            customersIds.add(sale.getCustomer().getId());
        }

        List<CustomerSalesExportDto> toExport = new ArrayList<>();
        for (long customerId : customersIds) {
            Customer customer = this.customerRepository.findById(customerId).get();
            CustomerSalesExportDto customerSalesExportDto = new CustomerSalesExportDto();
            customerSalesExportDto.setFullName(customer.getName());
            customerSalesExportDto.setBoughtCars(customer.getSales().size());
            BigDecimal spentMoney = new BigDecimal(0);
            for (Sale sale : customer.getSales()) {
                for (Part part : sale.getCar().getParts()) {
                    spentMoney = spentMoney.add(part.getPrice());
                }
            }
            customerSalesExportDto.setSpentMoney(spentMoney);
            toExport.add(customerSalesExportDto);
        }

        List<CustomerSalesExportDto> sortedExport = toExport.stream().sorted((f, s) -> {
            int firsComparison = s.getSpentMoney().compareTo(f.getSpentMoney());
            if (firsComparison == 0) {
                return Integer.compare(s.getBoughtCars(), f.getBoughtCars());
            }
            return firsComparison;
        }).collect(Collectors.toList());


        return this.gson.toJson(sortedExport);
    }

    @Override
    public String salesWithAppliedDiscount() {
        List<Sale> sales = this.saleRepository.findAll();
        List<SaleDetailedDto> toExport = new ArrayList<>();

        for (Sale sale : sales) {
            SaleDetailedDto saleDetailedDto = this.modelMapper.map(sale, SaleDetailedDto.class);
            BigDecimal spentMoney = new BigDecimal(0);

            for (Part part : sale.getCar().getParts()) {
                spentMoney = spentMoney.add(part.getPrice());
            }
            saleDetailedDto.setPrice(spentMoney);

            BigDecimal percentageDiscount = BigDecimal.valueOf(sale.getDiscount() / 100.0);

            saleDetailedDto.setPriceWithDiscount(spentMoney.subtract(spentMoney.multiply(percentageDiscount)));

            toExport.add(saleDetailedDto);
        }

        return this.gson.toJson(toExport);

    }

    private int getRandomDiscount() {
        int[] discounts = new int[] {0, 5, 10, 15, 20, 30, 40, 50};
        Random random = new Random();
        return discounts[random.nextInt(discounts.length) + 1];
    }

    private Customer getRandomCustomer() {
        Random random = new Random();
        long id = (long) random.nextInt((int) this.customerRepository.count()) + 1;
        return this.customerRepository.findById(id).get();
    }

    private Car getRandomCar() {
        Random random = new Random();
        long id = (long) random.nextInt((int) this.carRepository.count()) + 1;
        return this.carRepository.findById(id).get();
    }
}
