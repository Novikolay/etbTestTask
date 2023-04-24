package com.etb.testtask.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "bills")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Bill {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "customerId")
    private int customerId;

    @Column(name = "amount")
    private long amount;

}
