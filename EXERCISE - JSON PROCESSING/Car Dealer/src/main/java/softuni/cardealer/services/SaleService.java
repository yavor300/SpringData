package softuni.cardealer.services;

public interface SaleService {
    void seedSales();

    String totalSalesByCustomer();

    String salesWithAppliedDiscount();
}
