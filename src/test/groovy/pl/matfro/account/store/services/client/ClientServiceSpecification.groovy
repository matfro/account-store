package pl.matfro.account.store.services.client

import java.math.RoundingMode
import pl.matfro.account.model.Account
import pl.matfro.account.model.Client
import pl.matfro.account.store.exchange.rates.fetcher.services.ExchangeRateFetcherService
import spock.lang.Specification

class ClientServiceSpecification extends Specification {

  private ClientPersistenceService clientPersistenceService = Mock()

  private ExchangeRateFetcherService exchangeRateFetcherService = Mock()

  private ClientService clientService = new ClientService(clientPersistenceService, exchangeRateFetcherService)

  void 'clients accounts amounts are returned in specified currency'() {
    given:
    String clientIdentifier = '1234567890'
    String accountNumber = '1234567890'

    def currency = Currency.getInstance('EUR')
    def exchangeRate = new BigDecimal(4)
    def amount = new BigDecimal(500)

    when:
    def result = clientService.getClientsAccountsAmounts(clientIdentifier, currency).results

    then:
    1 * clientPersistenceService.fetchByIdentifier(clientIdentifier) >> Client.builder()
            .identifier(clientIdentifier)
            .accounts(Set.of(Account.builder()
                    .accountNumber(accountNumber)
                    .amount(amount)
                    .build()))
            .build()
    1 * exchangeRateFetcherService.getExchangeRate(currency) >> exchangeRate
    result[0].amount == amount.divide(exchangeRate, 2, RoundingMode.HALF_DOWN)
    result[0].accountNumber == accountNumber
  }

  void 'clients accounts amounts are returned in default currency'() {
    given:
    String clientIdentifier = '1234567890'
    String accountNumber = '1234567890'

    def exchangeRate = new BigDecimal(4)
    def amount = new BigDecimal(500)

    when:
    def result = clientService.getClientsAccountsAmounts(clientIdentifier).results

    then:
    1 * clientPersistenceService.fetchByIdentifier(clientIdentifier) >> Client.builder()
            .identifier(clientIdentifier)
            .accounts(Set.of(Account.builder()
                    .accountNumber(accountNumber)
                    .amount(amount)
                    .build()))
            .build()
    1 * exchangeRateFetcherService.getExchangeRate() >> exchangeRate
    result[0].amount == amount.divide(exchangeRate, 2, RoundingMode.HALF_DOWN)
    result[0].accountNumber == accountNumber
  }
}
