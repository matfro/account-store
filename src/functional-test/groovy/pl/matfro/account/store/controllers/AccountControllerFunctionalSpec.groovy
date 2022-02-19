package pl.matfro.account.store.controllers

import java.math.RoundingMode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import pl.matfro.account.model.result.AmountResult
import pl.matfro.account.store.AbstractFunctionalSpec
import pl.matfro.account.store.data.orm.AccountDb
import pl.matfro.account.store.data.orm.ClientDb
import pl.matfro.account.store.repositories.AccountRepository
import pl.matfro.account.store.repositories.ClientRepository

class AccountControllerFunctionalSpec extends AbstractFunctionalSpec {

  @Autowired private ClientRepository clientRepository

  @Autowired private AccountRepository accountRepository

  void setup() {
    clientRepository.deleteAll()
    accountRepository.deleteAll()
    wireMockServer.resetAll()
  }

  void 'account amount can be fetched in EUR without specified currency'() {
    given:
    AccountDb account = setupAccount()
    def headers = createHttpHeaders(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON)
    stubFor(wireMockServer)

    when:
    ResponseEntity<AmountResult> response = testRestTemplate.exchange('/accounts/{accountNumber}/amount', HttpMethod.GET,
            new HttpEntity<>(headers), AmountResult, account.accountNumber)

    then:
    response.statusCode == HttpStatus.OK
    response.body.result == account.amount.divide(euroExchangeRate, 2, RoundingMode.HALF_DOWN)
  }

  void 'account amount can be fetched in a specified currency'() {
    given:
    AccountDb account = setupAccount()
    def headers = createHttpHeaders(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON)
    stubFor(wireMockServer)

    when:
    ResponseEntity<AmountResult> response = testRestTemplate.exchange('/accounts/{accountNumber}/amount?currency={currency}',
            HttpMethod.GET, new HttpEntity<>(headers), AmountResult, account.accountNumber, 'EUR')

    then:
    response.statusCode == HttpStatus.OK
    response.body.result == account.amount.divide(euroExchangeRate, 2, RoundingMode.HALF_DOWN)
  }

  void '404 is returned for non-existing account'() {
    given:
    def headers = createHttpHeaders(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON)
    stubFor(wireMockServer)

    when:
    ResponseEntity<AmountResult> response = testRestTemplate.exchange('/accounts/{accountNumber}/amount?currency={currency}',
            HttpMethod.GET, new HttpEntity<>(headers), AmountResult, '1234567890', 'EUR')

    then:
    response.statusCode == HttpStatus.NOT_FOUND
  }

  void '400 is returned for a non-mapped currency'() {
    given:
    AccountDb account = setupAccount()
    def headers = createHttpHeaders(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON)
    stubFor(wireMockServer)

    when:
    ResponseEntity<AmountResult> response = testRestTemplate.exchange('/accounts/{accountNumber}/amount?currency={currency}',
            HttpMethod.GET, new HttpEntity<>(headers), AmountResult, account.accountNumber, 'USD')

    then:
    response.statusCode == HttpStatus.BAD_REQUEST
  }

  private AccountDb setupAccount(BigDecimal amount = 500, String accountNumber = '1234567890') {
    clientRepository.save(new ClientDb(identifier: '', accounts: Set.of(new AccountDb(accountNumber: accountNumber, amount: amount)))).accounts[0]
  }
}
