package io.github.carlinhoshk.iot_data_consumer.scheduler;

import io.github.carlinhoshk.iot_data_consumer.service.TelemetryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Slf4j
@Component
@EnableScheduling
public class CollectionScheduler {

    private final TelemetryService telemetryService;
    private final Instant inicio = Instant.now();

    private static final long DURACAO_MAXIMA_MINUTOS = 60;

    public CollectionScheduler(TelemetryService telemetryService) {
        this.telemetryService = telemetryService;
    }

    @Scheduled(fixedDelay = 300_000) // 5 minutos em ms
    public void executarColeta() {
        long minutosDecorridos = Duration.between(inicio, Instant.now()).toMinutes();

        if (minutosDecorridos >= DURACAO_MAXIMA_MINUTOS) {
            log.info("60 minutos concluídos — encerrando coletas.");
            return;
        }

        log.info("Iniciando coleta — minuto {} de {}", minutosDecorridos, DURACAO_MAXIMA_MINUTOS);
        telemetryService.coletarEPersistirDados();
        log.info("Coleta concluída — próxima em 5 minutos.");
    }
}