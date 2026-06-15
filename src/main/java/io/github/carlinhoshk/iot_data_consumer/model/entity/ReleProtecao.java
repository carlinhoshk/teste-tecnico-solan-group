package io.github.carlinhoshk.iot_data_consumer.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "rele_protecao", indexes = {
    @Index(name = "idx_rele_sn", columnList = "sn")
})
public class ReleProtecao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sn;
    private LocalDateTime tsleitura;
    private String tpLei;

    private Double rFreq;
    private Double rIfaseA;
    private Double rIfaseB;
    private Double rIfaseC;
    private Double rVfaseA;
    private Double rVfaseB;
    private Double rVfaseC;
    private Double rpac;
    private Double rpac1;
    private Double rpac2;
    private Double rpac3;
    private Double rtempinterno;

    // flags: 0 = normal, 1 = disparado
    private Integer r25;
    private Integer r27A;
    private Integer r27B;
    private Integer r27C;
    private Integer r32A;
    private Integer r32B;
    private Integer r32C;
    private Integer r37A;
    private Integer r37B;
    private Integer r37C;
    private Integer r47;
    private Integer r50A;
    private Integer r50B;
    private Integer r50C;
    private Integer r50N;
    private Integer r51A;
    private Integer r51B;
    private Integer r51C;
    private Integer r51N;
    private Integer r59A;
    private Integer r59B;
    private Integer r59C;
    private Integer r59N;
    private Integer r67A;
    private Integer r67B;
    private Integer r67C;
    private Integer r67N;
    private Integer r78;
    private Integer r81;
    private Integer r86;
}
