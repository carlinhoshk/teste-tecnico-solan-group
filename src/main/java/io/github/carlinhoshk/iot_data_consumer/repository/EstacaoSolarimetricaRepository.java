package io.github.carlinhoshk.iot_data_consumer.repository;

import io.github.carlinhoshk.iot_data_consumer.model.entity.EstacaoSolarimetrica;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstacaoSolarimetricaRepository extends JpaRepository<EstacaoSolarimetrica, Long> {
}
