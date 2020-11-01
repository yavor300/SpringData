package Homework_Databases.BillsPaymentSystemDatabase.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BillingDetail extends BaseEntity {
    @Column(name = "number")
    private String number;
    @Column(name = "owner")
    private String owner;
}
