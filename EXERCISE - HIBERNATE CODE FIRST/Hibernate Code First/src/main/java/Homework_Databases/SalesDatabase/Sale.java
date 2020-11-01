package Homework_Databases.SalesDatabase;

import Homework_Databases.SalesDatabase.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "sale")
public class Sale extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "store_location_id", referencedColumnName = "id")
    private StoreLocation storeLocation;
}
