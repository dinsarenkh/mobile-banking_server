package com.dinsaren.mobilebankingserver.models;

import com.dinsaren.mobilebankingserver.constants.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "mb_user_account")
@DynamicUpdate()
@Data
public class UserAccount extends BaseEntity{
    private static final long serialVersionUID = 4489397646584896516L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "user_account_id")
    private String userAccountId;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "account_ccy")
    private String accountCcy;
    @Column(name = "total_balance")
    private Double totalBalance;
    private String status;
    @Column(name = "account_default")
    private String accountDefault;
    @Column(name = "account_type")
    private String accountType = Constants.SAVING_ACCOUNT;

}