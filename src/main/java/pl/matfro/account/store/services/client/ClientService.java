package pl.matfro.account.store.services.client;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matfro.account.model.result.AccountResult;
import pl.matfro.account.model.result.ClientAccountsAmountResult;
import pl.matfro.account.store.exchange.rates.fetcher.services.ExchangeRateFetcherService;

@Service
@RequiredArgsConstructor
public class ClientService {

  private final ClientPersistenceService clientPersistenceService;

  private final ExchangeRateFetcherService exchangeRateFetcherService;

  public ClientAccountsAmountResult getClientsAccountsAmounts(String clientIdentifier) {
    final BigDecimal exchangeRate = exchangeRateFetcherService.getExchangeRate();
    return calculateAmounts(clientIdentifier, exchangeRate);
  }

  public ClientAccountsAmountResult getClientsAccountsAmounts(
          String clientIdentifier, Currency currency) {
    final BigDecimal exchangeRate = exchangeRateFetcherService.getExchangeRate(currency);
    return calculateAmounts(clientIdentifier, exchangeRate);
  }

  private ClientAccountsAmountResult calculateAmounts(
          String clientIdentifier, BigDecimal exchangeRate) {

    final List<AccountResult> accountResults =
            clientPersistenceService.fetchByIdentifier(clientIdentifier).getAccounts().stream()
                    .map(
                            account ->
                                    AccountResult.builder()
                                            .accountNumber(account.getAccountNumber())
                                            .amount(account.getAmount().divide(exchangeRate, 2, RoundingMode.HALF_EVEN))
                                            .build())
                    .collect(Collectors.toList());

    return new ClientAccountsAmountResult(accountResults);
  }
}
