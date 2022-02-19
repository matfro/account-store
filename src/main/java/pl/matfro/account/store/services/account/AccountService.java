package pl.matfro.account.store.services.account;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.matfro.account.model.Account;
import pl.matfro.account.model.result.AmountResult;
import pl.matfro.account.store.exchange.rates.fetcher.services.ExchangeRateFetcherService;

@Service
@RequiredArgsConstructor
public class AccountService {

  private final AccountPersistenceService accountPersistenceService;

  private final ExchangeRateFetcherService exchangeRateFetcherService;

  public AmountResult getAmount(String accountNumber) {
    final BigDecimal exchangeRate = exchangeRateFetcherService.getExchangeRate();
    return AmountResult.builder().result(calculateAmount(accountNumber, exchangeRate)).build();
  }

  public AmountResult getAmount(String accountNumber, Currency currency) {
    final BigDecimal exchangeRate = exchangeRateFetcherService.getExchangeRate(currency);
    return AmountResult.builder().result(calculateAmount(accountNumber, exchangeRate)).build();
  }

  private BigDecimal calculateAmount(String accountNumber, BigDecimal exchangeRate) {
    final Account account = accountPersistenceService.fetchByAccountNumber(accountNumber);
    return account.getAmount().divide(exchangeRate, 2, RoundingMode.HALF_EVEN);
  }
}
