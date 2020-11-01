package Homework_Databases.BillsPaymentSystemDatabase;

import Homework_Databases.BillsPaymentSystemDatabase.base.BaseEntity;
import Homework_Databases.SalesDatabase.StoreLocation;

import javax.persistence.*;
import java.time.Month;
import java.time.Year;

@Entity
@Table(name = "credit_cards")
public class CreditCard extends BaseEntity {
    @Column(name = "type")
    private String type;
    @Column(name = "expiration_month")
    private Month expirationMonth;
    @Column(name = "expiration_year")
    private Year expirationYear;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
