package io.github.carlinhoshk.iot_data_consumer.repository;

import io.github.carlinhoshk.iot_data_consumer.model.entity.Inversor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InversorRepository extends JpaRepository<Inversor, Long> {
}
