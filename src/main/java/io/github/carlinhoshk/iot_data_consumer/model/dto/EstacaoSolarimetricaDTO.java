package io.github.carlinhoshk.iot_data_consumer.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EstacaoSolarimetricaDTO {

    private String sn;
    private String tsleitura;
    private String tpLei;

    @JsonProperty("IrGHI") private Object irGHI;
    @JsonProperty("IrPOA") private Object irPOA;
    @JsonProperty("Umid")  private Object umid;
    private Object tempAmb;
    private Object tempMedMod;
    private Object velVento;
    private Object dirVento;
    private Object chuTotal;

    @JsonProperty("IrDay")   private Object irDay;
    @JsonProperty("IrTotal") private Object irTotal;

}
