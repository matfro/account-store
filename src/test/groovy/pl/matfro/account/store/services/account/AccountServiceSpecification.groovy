package pl.matfro.account.store.services.account

import java.math.RoundingMode
import pl.matfro.account.model.Account
import pl.matfro.account.store.exchange.rates.fetcher.services.ExchangeRateFetcherService
import pl.matfro.account.store.services.account.AccountPersistenceService
import pl.matfro.account.store.services.account.AccountService
import spock.lang.Specification

class AccountServiceSpecification extends Specification {

  private AccountPersistenceService accountPersistenceService = Mock()

  private ExchangeRateFetcherService exchangeRateFetcherService = Mock()

  private AccountService accountService = new AccountService(accountPersistenceService, exchangeRateFetcherService)

  void 'account amount is returned in specified currency'() {
    given:
    String accountNumber = '1234567890'
    def currency = Currency.getInstance('EUR')
    def exchangeRate = new BigDecimal(4)
    def amount = new BigDecimal(500)

    when:
    def result = accountService.getAmount(accountNumber, currency).result

    then:
    1 * accountPersistenceService.fetchByAccountNumber(accountNumber) >> Account.builder().amount(amount).build()
    1 * exchangeRateFetcherService.getExchangeRate(currency) >> exchangeRate
    result == amount.divide(exchangeRate, 2, RoundingMode.HALF_DOWN)
  }

  void 'account amount is returned in default currency'() {
    given:
    String accountNumber = '1234567890'
    def exchangeRate = new BigDecimal(4)
    def amount = new BigDecimal(500)

    when:
    def result = accountService.getAmount(accountNumber).result

    then:
    accountPersistenceService.fetchByAccountNumber(accountNumber) >> Account.builder().amount(amount).build()
    exchangeRateFetcherService.getExchangeRate() >> exchangeRate
    result == amount.divide(exchangeRate, 2, RoundingMode.HALF_DOWN)
  }
}
