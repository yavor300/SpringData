package softuni.cardealer.services.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
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
