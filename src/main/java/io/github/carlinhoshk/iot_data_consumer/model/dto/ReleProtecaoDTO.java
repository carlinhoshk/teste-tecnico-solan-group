package io.github.carlinhoshk.iot_data_consumer.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReleProtecaoDTO {

    private String sn;
    private String tsleitura;
    private String tpLei;

    private Object rFREQ;
    private Object rIfaseA;
    private Object rIfaseB;
    private Object rIfaseC;
    private Object rVfaseA;
    private Object rVfaseB;
    private Object rVfaseC;
    private Object rpac;
    private Object rpac1;
    private Object rpac2;
    private Object rpac3;
    private Object rtempinterno;

    // flags de disparo 0 ou 1
    private Object r25;
    private Object r27A;
    private Object r27B;
    private Object r27C;
    private Object r32A;
    private Object r32B;
    private Object r32C;
    private Object r37A;
    private Object r37B;
    private Object r37C;
    private Object r47;
    private Object r50A;
    private Object r50B;
    private Object r50C;
    private Object r50N;
    private Object r51A;
    private Object r51B;
    private Object r51C;
    private Object r51N;
    private Object r59A;
    private Object r59B;
    private Object r59C;
    private Object r59N;
    private Object r67A;
    private Object r67B;
    private Object r67C;
    private Object r67N;
    private Object r78;
    private Object r81;
    private Object r86;

}
