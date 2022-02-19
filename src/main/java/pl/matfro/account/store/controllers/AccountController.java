package pl.matfro.account.store.controllers;

import java.util.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.matfro.account.model.result.AmountResult;
import pl.matfro.account.store.services.account.AccountService;

@RestController
@RequiredArgsConstructor
public class AccountController implements AccountApi {

  private final AccountService accountService;

  @Override
  public ResponseEntity<AmountResult> getAmount(String accountNumber) {
    return ResponseEntity.ok(accountService.getAmount(accountNumber));
  }

  @Override
  public ResponseEntity<AmountResult> getAmount(String accountNumber, String currency) {
    return ResponseEntity.ok(
            accountService.getAmount(accountNumber, Currency.getInstance(currency)));
  }
}
