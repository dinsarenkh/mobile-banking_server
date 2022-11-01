package com.dinsaren.mobilebankingserver.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "M_OTP_LOG")
@DynamicUpdate()
@Data
public class OtpLog extends BaseEntity{
    private static final long serialVersionUID = 4489397646584896516L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;
    private String sendTo;
    private String otp;
    private String status;

}