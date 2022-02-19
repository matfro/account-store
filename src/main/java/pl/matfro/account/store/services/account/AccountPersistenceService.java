package pl.matfro.account.store.services.account;

import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.matfro.account.model.Account;
import pl.matfro.account.store.data.orm.AccountDb;
import pl.matfro.account.store.exceptions.ResourceNotFoundException;
import pl.matfro.account.store.repositories.AccountRepository;
import pl.matfro.common.components.jpa.AbstractPersistentJpaService;

@Service
public class AccountPersistenceService extends AbstractPersistentJpaService<Account, UUID> {

  private final AccountRepository accountRepository;

  public AccountPersistenceService(AccountRepository accountRepository, ModelMapper modelMapper) {
    super(modelMapper);
    this.accountRepository = accountRepository;
  }

  @Override
  public Account fetch(UUID id) {
    return mapEntity(fetchDb(id), new Account());
  }

  public Account fetchByAccountNumber(String accountNumber) {
    return mapEntity(
            accountRepository
                    .findByAccountNumber(accountNumber)
                    .orElseThrow(() -> new ResourceNotFoundException("Account number " + accountNumber)),
            new Account());
  }

  private AccountDb fetchDb(UUID id) {
    return accountRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(id)));
  }
}
