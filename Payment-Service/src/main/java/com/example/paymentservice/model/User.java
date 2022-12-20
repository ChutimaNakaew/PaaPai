package com.example.paymentservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "myUser")
public class User {
    @Id
    private String id;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private String annotation;
    private Integer total_tourist;
    private Integer total_price;
    private String tour_name;
    private Double tour_price;
    private String province;
    private String status;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @NotNull
    private Binary slip;
    private String bank;
}
