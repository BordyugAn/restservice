package server.repos;

import server.domain.JSONEntity;
import org.springframework.data.repository.CrudRepository;

public interface JSONRepo extends CrudRepository<JSONEntity, Long> {
    JSONEntity findAllById(int id);
}
