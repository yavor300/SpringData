package Homework_Databases.BillsPaymentSystemDatabase;

import Homework_Databases.BillsPaymentSystemDatabase.base.BaseEntity;
import Homework_Databases.BillsPaymentSystemDatabase.base.BillingDetail;
import Homework_Databases.SalesDatabase.Sale;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @OneToMany(mappedBy = "user")
    private List<CreditCard> creditCards;
    @OneToMany(mappedBy = "user")
    private List<BankAccount> bankAccounts;
}
