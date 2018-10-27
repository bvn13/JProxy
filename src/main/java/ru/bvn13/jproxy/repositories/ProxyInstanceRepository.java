package ru.bvn13.jproxy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bvn13.jproxy.entities.ProxyInstance;

import java.util.Optional;

/**
 * Created by bvn13 on 24.10.2018.
 */
@Repository
public interface ProxyInstanceRepository extends JpaRepository<ProxyInstance, Long> {

    Optional<ProxyInstance> getById(Long id);

}
