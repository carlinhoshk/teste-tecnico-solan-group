package io.github.carlinhoshk.iot_data_consumer.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "estacao_solarimetrica", indexes = {
    @Index(name = "idx_estacao_sn", columnList = "sn")
})
public class EstacaoSolarimetrica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sn;
    private LocalDateTime tsleitura;
    private String tpLei;

    // convertidos de String para Double no Service
    private Double irGHI;
    private Double irPOA;
    private Double umid;
    private Double tempAmb;
    private Double tempMedMod;
    private Double velVento;
    private Double dirVento;
    private Double chuTotal;

    private Double irDay;
    private Double irTotal;
}
