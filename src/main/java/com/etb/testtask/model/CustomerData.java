package com.etb.testtask.model;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "CustomerData")
@Getter
@Setter
public class CustomerData {

    @XmlElement(name = "CustomerList")
    List<CustomerScheme> customerList;

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "CustomerList", propOrder = {

    })
    @Getter
    @Setter
    public static class CustomerScheme {
        @XmlElement(name = "Id", required = true)
        private int id;

        @XmlElement(name = "Name", required = true)
        private String name;

        @XmlElement(name = "BillList")
        List<BillScheme> billList;

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "BillList", propOrder = {

        })
        @Getter
        @Setter
        public static class BillScheme {
            @XmlElement(name = "Id")
            private int id;

            @XmlElement(name = "Amount")
            private long amount;
        }
    }
}
