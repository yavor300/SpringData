package Homework_Databases.GringottsDatabase;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "wizzard_deposits")
public class WizardDeposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name", length = 50)
    private String firstName;
    @Column(name = "last_name", length = 60, nullable = false)
    private String lastName;
    @Column(name = "notes", columnDefinition = "TEXT", length = 1000)
    private String notes;
    @Column(name = "age", nullable = false)
    @Positive
    private int age;
    @Column(name = "magic_wand_creator", length = 100)
    private String magicWandCreator;
    @Column(name = "magic_wand_size", columnDefinition = "SMALLINT")
    private int magicWandSize;
    @Column(name = "deposit_group", length = 20)
    private String depositGroup;
    @Column(name = "deposit_start_date", columnDefinition = "DATETIME")
    private Date depositStartDate;
    @Column(name = "deposit_amount")
    private BigDecimal depositAmount;
    @Column(name = "deposit_interest")
    private BigDecimal depositInterest;
    @Column(name = "deposit_charge")
    private BigDecimal depositCharge;
    @Column(name = "deposit_expiration_date", columnDefinition = "DATETIME")
    private Date depositExpirationDate;
    @Column(name = "is_deposit_expired")
    private Boolean isDepositExpired;
}
