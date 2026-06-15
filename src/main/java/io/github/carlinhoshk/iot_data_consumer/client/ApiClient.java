package io.github.carlinhoshk.iot_data_consumer.client;

import io.github.carlinhoshk.iot_data_consumer.exception.ApiUnavailableException;
import io.github.carlinhoshk.iot_data_consumer.model.dto.EstacaoSolarimetricaDTO;
import io.github.carlinhoshk.iot_data_consumer.model.dto.InversorDTO;
import io.github.carlinhoshk.iot_data_consumer.model.dto.ReleProtecaoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class ApiClient {

    private final RestTemplate restTemplate;

    private static final String BASE_URL = "http://localhost:5050";

    public ApiClient(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public InversorDTO buscarInversor(){
        return buscar(BASE_URL + "/inversor", InversorDTO.class, "inversor");
    }

    public ReleProtecaoDTO buscarReleProtecao(){
        return buscar(BASE_URL + "/rele-protecao", ReleProtecaoDTO.class, "rele-protecao");
    }

    public EstacaoSolarimetricaDTO buscarEstacaoSolarimetrica(){
        return buscar(BASE_URL + "/estacao-solarimetrica", EstacaoSolarimetricaDTO.class, "estacao-solarimetrica");
    }

    private <T> T buscar(String url, Class<T> tipo, String nomeEndpoint) {
        try {
            ResponseEntity<T> resposta = restTemplate.getForEntity(url, tipo);

            if (!resposta.getStatusCode().is2xxSuccessful() || resposta.getBody() == null) {
                log.warn("[{}] resposta inválida — status: {}", nomeEndpoint, resposta.getStatusCode());
                return null;
            }
            return resposta.getBody();
        }catch (HttpClientErrorException e){
        log.warn("[{}] erro HTTP {}: {}", nomeEndpoint, e.getStatusCode(), e.getMessage());
        return null;

        } catch (RestClientException e) {
            // API fora do ar, timeout, ou retornou texto puro (não JSON)
            log.error("[{}] falha na requisição: {}", nomeEndpoint, e.getMessage());
            throw new ApiUnavailableException("API indisponível no endpoint: " + nomeEndpoint, e);
        }
    }
}