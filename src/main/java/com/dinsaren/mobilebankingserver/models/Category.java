package com.dinsaren.mobilebankingserver.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "mb_category")
@DynamicUpdate()
@Data
public class Category{
    private static final long serialVersionUID = 4489397646584896516L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "name_kh")
    private String nameKh;
    private String route;
    private int index;
    @Column(name = "image_url")
    private String imageUrl;
    private String status;
    @Column(name = "category_code")
    private String categoryCode;

}