package pl.matfro.account.store.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.matfro.account.store.data.orm.ClientDb;

@Repository
public interface ClientRepository extends CrudRepository<ClientDb, UUID> {

  Optional<ClientDb> findByIdentifier(String identifier);
}
