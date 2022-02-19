package pl.matfro.account.store.services.client;

import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.matfro.account.model.Client;
import pl.matfro.account.store.data.orm.ClientDb;
import pl.matfro.account.store.exceptions.ResourceNotFoundException;
import pl.matfro.account.store.repositories.ClientRepository;
import pl.matfro.common.components.jpa.AbstractPersistentJpaService;

@Service
public class ClientPersistenceService extends AbstractPersistentJpaService<Client, UUID> {

  private final ClientRepository clientRepository;

  public ClientPersistenceService(ClientRepository clientRepository, ModelMapper modelMapper) {
    super(modelMapper);
    this.clientRepository = clientRepository;
  }

  @Override
  public Client fetch(UUID id) {
    return mapEntity(fetchDb(id), new Client());
  }

  public Client fetchByIdentifier(String identifier) {
    return mapEntity(
            clientRepository
                    .findByIdentifier(identifier)
                    .orElseThrow(() -> new ResourceNotFoundException("Client identifier " + identifier)),
            new Client());
  }

  private ClientDb fetchDb(UUID id) {
    return clientRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(String.valueOf(id)));
  }
}
