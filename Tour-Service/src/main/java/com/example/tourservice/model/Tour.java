package com.example.tourservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "myTour")
public class Tour {
    @Id
    private String _id;
    private String name;
    private String province;
    private Double price;
    private String img;
    private String detail_img;
    private Object detail;
    private Integer people;
    private String schedule;
    private Integer max_tourist;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date_first;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date_second;
}
