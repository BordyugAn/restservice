package server.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import server.domain.PersonEntity;

import java.util.List;

public interface PersonRepo extends CrudRepository<PersonEntity, Long> {
    List<PersonEntity> findAllByPlaceofbirth(String place);
    List<PersonEntity> findAllByLocation(String location);
    List<PersonEntity> findAllByName(String name);

    @Transactional
    void deleteById(int id);


    PersonEntity findById(int id);
}
