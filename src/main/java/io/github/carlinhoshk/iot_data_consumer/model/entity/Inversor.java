package io.github.carlinhoshk.iot_data_consumer.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "inversor", indexes = {
    @Index(name = "idx_inversor_sn", columnList = "sn")
})
public class Inversor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sn;
    private LocalDateTime tsleitura;

    private Double pac;
    private Double pac1;
    private Double pac2;
    private Double pac3;

    private Double uac;
    private Double uac1;
    private Double uac2;
    private Double uac3;

    private Double iac;
    private Double iac1;
    private Double iac2;
    private Double iac3;

    private Double upv1;
    private Double upv2;
    private Double upv3;

    private Double ipv1;
    private Double ipv2;
    private Double ipv3;

    private Double eday;
    private Double etotal;
    private Double temp;
    private Double fac;
    private Double cos;

}
