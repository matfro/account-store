package pl.matfro.account.store.controllers;

import java.util.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.matfro.account.model.result.ClientAccountsAmountResult;
import pl.matfro.account.store.services.client.ClientService;

@RestController
@RequiredArgsConstructor
public class ClientController implements ClientApi {

  private final ClientService clientService;

  @Override
  public ResponseEntity<ClientAccountsAmountResult> getClientAccountsAmounts(
          String clientIdentifier) {
    return ResponseEntity.ok(clientService.getClientsAccountsAmounts(clientIdentifier));
  }

  @Override
  public ResponseEntity<ClientAccountsAmountResult> getAmount(
          String clientIdentifier, String currency) {
    return ResponseEntity.ok(
            clientService.getClientsAccountsAmounts(clientIdentifier, Currency.getInstance(currency)));
  }
}
