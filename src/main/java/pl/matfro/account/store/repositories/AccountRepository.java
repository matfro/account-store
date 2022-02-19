package pl.matfro.account.store.repositories;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.matfro.account.store.data.orm.AccountDb;

@Repository
public interface AccountRepository extends CrudRepository<AccountDb, UUID> {

  Optional<AccountDb> findByAccountNumber(String accountNumber);
}
