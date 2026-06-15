package io.github.carlinhoshk.iot_data_consumer.repository;

import io.github.carlinhoshk.iot_data_consumer.model.entity.ReleProtecao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReleProtecaoRepository extends JpaRepository<ReleProtecao, Long> {
}
