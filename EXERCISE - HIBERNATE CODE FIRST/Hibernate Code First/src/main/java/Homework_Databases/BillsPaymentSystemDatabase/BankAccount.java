package Homework_Databases.BillsPaymentSystemDatabase;

import Homework_Databases.BillsPaymentSystemDatabase.base.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "bank_accouts")
public class BankAccount extends BaseEntity {
    @Column(name = "bank_name")
    private String bankName;
    @Column(name = "swift_code")
    private String SwiftCode;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
