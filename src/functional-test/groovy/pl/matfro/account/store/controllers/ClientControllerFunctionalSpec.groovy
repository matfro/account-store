package pl.matfro.account.store.controllers

import java.math.RoundingMode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import pl.matfro.account.model.result.AmountResult
import pl.matfro.account.model.result.ClientAccountsAmountResult
import pl.matfro.account.store.AbstractFunctionalSpec
import pl.matfro.account.store.data.orm.AccountDb
import pl.matfro.account.store.data.orm.ClientDb
import pl.matfro.account.store.repositories.AccountRepository
import pl.matfro.account.store.repositories.ClientRepository

class ClientControllerFunctionalSpec extends AbstractFunctionalSpec {

  @Autowired private ClientRepository clientRepository

  @Autowired private AccountRepository accountRepository

  void setup() {
    clientRepository.deleteAll()
    accountRepository.deleteAll()
    wireMockServer.resetAll()
  }

  void 'clients accounts amounts can be fetched in EUR without specified currency'() {
    given:
    ClientDb client = setupClient()
    def headers = createHttpHeaders(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON)
    stubFor(wireMockServer)

    when:
    ResponseEntity<ClientAccountsAmountResult> response = testRestTemplate.exchange('/clients/{clientIdentifier}/accounts/amount', HttpMethod.GET,
            new HttpEntity<>(headers), ClientAccountsAmountResult, client.identifier)

    then:
    response.statusCode == HttpStatus.OK
    response.body.results[0].amount == client.accounts[0].amount.divide(euroExchangeRate, 2, RoundingMode.HALF_DOWN)
    response.body.results[0].accountNumber == client.accounts[0].accountNumber
  }

  void 'clients accounts amounts can be fetched in specified currency'() {
    given:
    ClientDb client = setupClient()
    def headers = createHttpHeaders(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON)
    stubFor(wireMockServer)

    when:
    ResponseEntity<ClientAccountsAmountResult> response = testRestTemplate.exchange('/clients/{clientIdentifier}/accounts/amount?currency={currency}', HttpMethod.GET,
            new HttpEntity<>(headers), ClientAccountsAmountResult, client.identifier, 'EUR')

    then:
    response.statusCode == HttpStatus.OK
    response.body.results[0].amount == client.accounts[0].amount.divide(euroExchangeRate, 2, RoundingMode.HALF_DOWN)
    response.body.results[0].accountNumber == client.accounts[0].accountNumber
  }

  void '404 is returned for non-existing client'() {
    given:
    def headers = createHttpHeaders(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON)
    stubFor(wireMockServer)

    when:
    ResponseEntity<AmountResult> response = testRestTemplate.exchange('/clients/{clientIdentifier}/accounts/amount',
            HttpMethod.GET, new HttpEntity<>(headers), AmountResult, '1234567890')

    then:
    response.statusCode == HttpStatus.NOT_FOUND
  }

  private ClientDb setupClient(String clientIdentifier = '1234567890', BigDecimal amount = 500, String accountNumber = '1234567890') {
    clientRepository.save(new ClientDb(identifier: clientIdentifier, accounts: Set.of(new AccountDb(accountNumber: accountNumber, amount: amount))))
  }
}
