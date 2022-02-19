package pl.matfro.account.store.exchange.rates.fetcher.services

import com.github.tomakehurst.wiremock.WireMockServer
import java.math.MathContext
import java.math.RoundingMode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import pl.matfro.account.store.exchange.rates.fetcher.ExchangeRatesFetcherFunctionalApplication
import pl.matfro.test.support.WireMockInitializer
import spock.lang.Shared
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.ok
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo

@ActiveProfiles("functional-test")
@ContextConfiguration(initializers = WireMockInitializer)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [ExchangeRatesFetcherFunctionalApplication])
class ExchangeRateFetcherServiceFunctionalSpec extends Specification {

  @Autowired private WireMockServer wireMockServer

  @Autowired private ExchangeRateFetcherService exchangeRateFetcherService

  @Shared private BigDecimal euroExchangeRate = new BigDecimal(4.5256).round(new MathContext(5, RoundingMode.HALF_EVEN))

  void setup() {
    wireMockServer.resetAll()
  }

  void 'specific currency exchange rate can be obtained'() {
    given:
    stubFor(wireMockServer)

    when:
    def rate = exchangeRateFetcherService.getExchangeRate(Currency.getInstance('EUR'))

    then:
    rate == euroExchangeRate
  }

  void 'default currency exchange rate can be obtained'() {
    given:
    stubFor(wireMockServer)

    when:
    def rate = exchangeRateFetcherService.getExchangeRate()

    then:
    rate == euroExchangeRate
  }

  private static void stubFor(WireMockServer wireMockServer, String endpoint = '/api/exchangerates/rates/A/EUR/') {
    wireMockServer.stubFor(get(urlPathEqualTo(endpoint))
            .willReturn(ok()
                    .withBodyFile('nbp-eur-response.json')
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)))
  }
}
