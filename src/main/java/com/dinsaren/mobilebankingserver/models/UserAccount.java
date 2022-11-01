package com.dinsaren.mobilebankingserver.models;

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
    private String accountNumber;
    private String accountCcy;
    private Double totalBalance;
    private String status;
    @Column(name = "account_default")
    private String accountDefault;

}