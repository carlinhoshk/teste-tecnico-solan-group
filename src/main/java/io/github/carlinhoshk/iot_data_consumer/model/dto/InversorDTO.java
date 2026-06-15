package io.github.carlinhoshk.iot_data_consumer.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // se a API mandar um campo a mais ou a menos não quebra
public class InversorDTO {

    private String sn;
    private String tsleitura;

    @JsonProperty("Pac")  private Object pac;
    @JsonProperty("Pac1") private Object pac1;
    @JsonProperty("Pac2") private Object pac2;
    @JsonProperty("Pac3") private Object pac3;

    @JsonProperty("Uac")  private Object uac;
    @JsonProperty("Uac1") private Object uac1;
    @JsonProperty("Uac2") private Object uac2;
    @JsonProperty("Uac3") private Object uac3;

    @JsonProperty("Iac")  private Object iac;
    @JsonProperty("Iac1") private Object iac1;
    @JsonProperty("Iac2") private Object iac2;
    @JsonProperty("Iac3") private Object iac3;

    @JsonProperty("Upv1") private Object upv1;
    @JsonProperty("Upv2") private Object upv2;
    @JsonProperty("Upv3") private Object upv3;

    @JsonProperty("Ipv1") private Object ipv1;
    @JsonProperty("Ipv2") private Object ipv2;
    @JsonProperty("Ipv3") private Object ipv3;

    @JsonProperty("Eday")   private Object eday;
    @JsonProperty("Etotal") private Object etotal;
    @JsonProperty("Temp")   private Object temp;

    private Object fac;
    private Object cos;
}
