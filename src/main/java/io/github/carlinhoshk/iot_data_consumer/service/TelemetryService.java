package io.github.carlinhoshk.iot_data_consumer.service;

import io.github.carlinhoshk.iot_data_consumer.client.ApiClient;
import io.github.carlinhoshk.iot_data_consumer.exception.ApiUnavailableException;
import io.github.carlinhoshk.iot_data_consumer.exception.DadoInvalidoException;
import io.github.carlinhoshk.iot_data_consumer.model.dto.EstacaoSolarimetricaDTO;
import io.github.carlinhoshk.iot_data_consumer.model.dto.InversorDTO;
import io.github.carlinhoshk.iot_data_consumer.model.dto.ReleProtecaoDTO;
import io.github.carlinhoshk.iot_data_consumer.model.entity.EstacaoSolarimetrica;
import io.github.carlinhoshk.iot_data_consumer.model.entity.Inversor;
import io.github.carlinhoshk.iot_data_consumer.model.entity.ReleProtecao;
import io.github.carlinhoshk.iot_data_consumer.repository.EstacaoSolarimetricaRepository;
import io.github.carlinhoshk.iot_data_consumer.repository.InversorRepository;
import io.github.carlinhoshk.iot_data_consumer.repository.ReleProtecaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Slf4j
@Service
public class TelemetryService {

    private final ApiClient apiClient;
    private final InversorRepository inversorRepository;
    private final ReleProtecaoRepository releProtecaoRepository;
    private final EstacaoSolarimetricaRepository estacaoRepository;

    private static final DateTimeFormatter FORMATO_DATA =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public TelemetryService(ApiClient apiClient,
                            InversorRepository inversorRepository,
                            ReleProtecaoRepository releProtecaoRepository,
                            EstacaoSolarimetricaRepository estacaoRepository) {
        this.apiClient = apiClient;
        this.inversorRepository = inversorRepository;
        this.releProtecaoRepository = releProtecaoRepository;
        this.estacaoRepository = estacaoRepository;
    }

    // ─────────────────────────────────────────
    // PONTO DE ENTRADA — chamado pelo Scheduler
    // ─────────────────────────────────────────

    public void coletarEPersistirDados() {
        processarInversor();
        processarReleProtecao();
        processarEstacaoSolarimetrica();
    }

    // ─────────────────────────────────────────
    // INVERSOR
    // essential: sn, tsleitura, Pac
    // ─────────────────────────────────────────

    private void processarInversor() {
        try {
            InversorDTO dto = apiClient.buscarInversor();

            if (!inversorValido(dto)) {
                log.warn("[inversor] descartado na validação inicial");
                return;
            }

            Inversor entity = converterInversor(dto);
            inversorRepository.save(entity);
            log.info("[inversor] salvo — sn: {}", entity.getSn());

        } catch (DadoInvalidoException e) {
            log.warn("[inversor] descartado — {}", e.getMessage());
        } catch (ApiUnavailableException e) {
            log.error("[inversor] API indisponível — pulando ciclo: {}", e.getMessage());
        }
    }

    private boolean inversorValido(InversorDTO dto) {
        if (dto == null)                return false;
        if (dto.getSn() == null)        return false;
        if (dto.getTsleitura() == null) return false;
        if (dto.getPac() == null)       return false;
        if (!isNumero(dto.getPac()))    return false;
        Double pac = toDouble(dto.getPac());
        Double uac = toDouble(dto.getUac());
        if ((pac == null || pac == 0.0)
                && (uac == null || uac == 0.0)) return false;
        return true;
    }

    private Inversor converterInversor(InversorDTO dto) {
        Inversor e = new Inversor();
        e.setSn(dto.getSn());
        e.setTsleitura(parsarDataObrigatoria(dto.getTsleitura()));
        e.setPac(toDoubleStrict(dto.getPac(), "Pac")); // essential
        e.setPac1(toDouble(dto.getPac1()));
        e.setPac2(toDouble(dto.getPac2()));
        e.setPac3(toDouble(dto.getPac3()));
        e.setUac(toDouble(dto.getUac()));
        e.setUac1(toDouble(dto.getUac1()));
        e.setUac2(toDouble(dto.getUac2()));
        e.setUac3(toDouble(dto.getUac3()));
        e.setIac(toDouble(dto.getIac()));
        e.setIac1(toDouble(dto.getIac1()));
        e.setIac2(toDouble(dto.getIac2()));
        e.setIac3(toDouble(dto.getIac3()));
        e.setUpv1(toDouble(dto.getUpv1()));
        e.setUpv2(toDouble(dto.getUpv2()));
        e.setUpv3(toDouble(dto.getUpv3()));
        e.setIpv1(toDouble(dto.getIpv1()));
        e.setIpv2(toDouble(dto.getIpv2()));
        e.setIpv3(toDouble(dto.getIpv3()));
        e.setEday(toDouble(dto.getEday()));
        e.setEtotal(toDouble(dto.getEtotal()));
        e.setTemp(toDouble(dto.getTemp()));
        e.setFac(toDouble(dto.getFac()));
        e.setCos(toDouble(dto.getCos()));
        return e;
    }

    // ─────────────────────────────────────────
    // RELÉ DE PROTEÇÃO
    // essential: sn, tsleitura, tpLei
    // ─────────────────────────────────────────

    private void processarReleProtecao() {
        try {
            ReleProtecaoDTO dto = apiClient.buscarReleProtecao();

            if (!releValido(dto)) {
                log.warn("[rele-protecao] descartado na validação inicial");
                return;
            }

            ReleProtecao entity = converterRele(dto);
            releProtecaoRepository.save(entity);
            log.info("[rele-protecao] salvo — sn: {}", entity.getSn());

        } catch (DadoInvalidoException e) {
            log.warn("[rele-protecao] descartado — {}", e.getMessage());
        } catch (ApiUnavailableException e) {
            log.error("[rele-protecao] API indisponível — pulando ciclo: {}", e.getMessage());
        }
    }

    private boolean releValido(ReleProtecaoDTO dto) {
        if (dto == null)                return false;
        if (dto.getSn() == null)        return false;
        if (dto.getTsleitura() == null) return false;
        if (dto.getTpLei() == null)     return false;
        Double rFREQ = toDouble(dto.getRFREQ());
        Double rVfaseA = toDouble(dto.getRVfaseA());
        if ((rFREQ == null || rFREQ == 0.0)
                && (rVfaseA == null || rVfaseA == 0.0)) return false;
        return true;
    }

    private ReleProtecao converterRele(ReleProtecaoDTO dto) {
        ReleProtecao e = new ReleProtecao();
        e.setSn(dto.getSn());
        e.setTsleitura(parsarDataObrigatoria(dto.getTsleitura()));
        e.setTpLei(dto.getTpLei());
        e.setRFreq(toDouble(dto.getRFREQ()));
        e.setRIfaseA(toDouble(dto.getRIfaseA()));
        e.setRIfaseB(toDouble(dto.getRIfaseB()));
        e.setRIfaseC(toDouble(dto.getRIfaseC()));
        e.setRVfaseA(toDouble(dto.getRVfaseA()));
        e.setRVfaseB(toDouble(dto.getRVfaseB()));
        e.setRVfaseC(toDouble(dto.getRVfaseC()));
        e.setRpac(toDouble(dto.getRpac()));
        e.setRpac1(toDouble(dto.getRpac1()));
        e.setRpac2(toDouble(dto.getRpac2()));
        e.setRpac3(toDouble(dto.getRpac3()));
        e.setRtempinterno(toDouble(dto.getRtempinterno()));
        e.setR25(toInteiro(dto.getR25()));
        e.setR27A(toInteiro(dto.getR27A()));
        e.setR27B(toInteiro(dto.getR27B()));
        e.setR27C(toInteiro(dto.getR27C()));
        e.setR32A(toInteiro(dto.getR32A()));
        e.setR32B(toInteiro(dto.getR32B()));
        e.setR32C(toInteiro(dto.getR32C()));
        e.setR37A(toInteiro(dto.getR37A()));
        e.setR37B(toInteiro(dto.getR37B()));
        e.setR37C(toInteiro(dto.getR37C()));
        e.setR47(toInteiro(dto.getR47()));
        e.setR50A(toInteiro(dto.getR50A()));
        e.setR50B(toInteiro(dto.getR50B()));
        e.setR50C(toInteiro(dto.getR50C()));
        e.setR50N(toInteiro(dto.getR50N()));
        e.setR51A(toInteiro(dto.getR51A()));
        e.setR51B(toInteiro(dto.getR51B()));
        e.setR51C(toInteiro(dto.getR51C()));
        e.setR51N(toInteiro(dto.getR51N()));
        e.setR59A(toInteiro(dto.getR59A()));
        e.setR59B(toInteiro(dto.getR59B()));
        e.setR59C(toInteiro(dto.getR59C()));
        e.setR59N(toInteiro(dto.getR59N()));
        e.setR67A(toInteiro(dto.getR67A()));
        e.setR67B(toInteiro(dto.getR67B()));
        e.setR67C(toInteiro(dto.getR67C()));
        e.setR67N(toInteiro(dto.getR67N()));
        e.setR78(toInteiro(dto.getR78()));
        e.setR81(toInteiro(dto.getR81()));
        e.setR86(toInteiro(dto.getR86()));
        return e;
    }

    // ─────────────────────────────────────────
    // ESTAÇÃO SOLARIMÉTRICA
    // essential: sn, tsleitura, tpLei
    // ─────────────────────────────────────────

    private void processarEstacaoSolarimetrica() {
        try {
            EstacaoSolarimetricaDTO dto = apiClient.buscarEstacaoSolarimetrica();

            if (!estacaoValida(dto)) {
                log.warn("[estacao-solarimetrica] descartado na validação inicial");
                return;
            }

            EstacaoSolarimetrica entity = converterEstacao(dto);
            estacaoRepository.save(entity);
            log.info("[estacao-solarimetrica] salvo — sn: {}", entity.getSn());

        } catch (DadoInvalidoException e) {
            log.warn("[estacao-solarimetrica] descartado — {}", e.getMessage());
        } catch (ApiUnavailableException e) {
            log.error("[estacao-solarimetrica] API indisponível — pulando ciclo: {}", e.getMessage());
        }
    }

    private boolean estacaoValida(EstacaoSolarimetricaDTO dto) {
        if (dto == null)                      return false;
        if (dto.getSn() == null)              return false;
        if (dto.getTsleitura() == null)       return false;
        if (dto.getTpLei() == null)           return false;
        if (!isDoubleValido(dto.getIrGHI()))  return false;
        Double irGHI = parsarStringDouble(dto.getIrGHI());
        Double irPOA = parsarStringDouble(dto.getIrPOA());
        if (irGHI != null && irGHI == 0.0
                && (irPOA == null || irPOA == 0.0)) return false; // zeroed
        return true;
    }

    private EstacaoSolarimetrica converterEstacao(EstacaoSolarimetricaDTO dto) {
        EstacaoSolarimetrica e = new EstacaoSolarimetrica();
        e.setSn(dto.getSn());
        e.setTsleitura(parsarDataObrigatoria(dto.getTsleitura()));
        e.setTpLei(dto.getTpLei());
        e.setIrGHI(parsarStringDouble(dto.getIrGHI()));
        e.setIrPOA(parsarStringDouble(dto.getIrPOA()));
        e.setUmid(parsarStringDouble(dto.getUmid()));
        e.setTempAmb(parsarStringDouble(dto.getTempAmb()));
        e.setTempMedMod(parsarStringDouble(dto.getTempMedMod()));
        e.setVelVento(parsarStringDouble(dto.getVelVento()));
        e.setDirVento(parsarStringDouble(dto.getDirVento()));
        e.setChuTotal(parsarStringDouble(dto.getChuTotal()));
        e.setIrDay(toDouble(dto.getIrDay()));
        e.setIrTotal(toDouble(dto.getIrTotal()));
        return e;
    }

    // ─────────────────────────────────────────
    // UTILITÁRIOS DE CONVERSÃO
    // ─────────────────────────────────────────

    // essential — lança exceção se inválido
    private Double toDoubleStrict(Object valor, String campo) {
        if (valor == null)
            throw new DadoInvalidoException("Campo essential nulo: " + campo);
        if (valor instanceof Number)
            return ((Number) valor).doubleValue();
        try {
            return Double.parseDouble(valor.toString().trim());
        } catch (NumberFormatException e) {
            throw new DadoInvalidoException("Tipo inválido em " + campo + ": " + valor);
        }
    }

    // não-essential — retorna null se inválido
    private Double toDouble(Object valor) {
        if (valor == null) return null;
        if (valor instanceof Number) return ((Number) valor).doubleValue();
        try {
            return Double.parseDouble(valor.toString().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // flags do relé — não-essential
    private Integer toInteiro(Object valor) {
        if (valor == null) return null;
        if (valor instanceof Number) return ((Number) valor).intValue();
        try {
            return Integer.parseInt(valor.toString().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // campos String/Object da estação — não-essential
    private Double parsarStringDouble(Object valor) {
        if (valor == null) return null;
        try {
            return Double.parseDouble(valor.toString().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // data é sempre essential — sem ela o registro não tem sentido temporal
    private LocalDateTime parsarDataObrigatoria(String valor) {
        if (valor == null)
            throw new DadoInvalidoException("Campo essential nulo: tsleitura");
        try {
            return LocalDateTime.parse(valor, FORMATO_DATA);
        } catch (DateTimeParseException e) {
            throw new DadoInvalidoException("Data inválida: " + valor);
        }
    }

    private boolean isNumero(Object valor) {
        if (valor == null) return false;
        if (valor instanceof Number) return true;
        try {
            Double.parseDouble(valor.toString().trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isDoubleValido(Object valor) {
        if (valor == null) return false;
        try {
            Double.parseDouble(valor.toString().trim());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}