package pl.matfro.account.store

import com.github.tomakehurst.wiremock.WireMockServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import pl.matfro.test.support.WireMockInitializer
import spock.lang.Shared
import spock.lang.Specification

import static com.github.tomakehurst.wiremock.client.WireMock.get
import static com.github.tomakehurst.wiremock.client.WireMock.ok
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo

@ActiveProfiles("functional-test")
@ContextConfiguration(initializers = WireMockInitializer)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = [FunctionalTestConfiguration])
abstract class AbstractFunctionalSpec extends Specification {

  @Autowired protected TestRestTemplate testRestTemplate

  @Autowired protected WireMockServer wireMockServer

  @Shared protected BigDecimal euroExchangeRate = new BigDecimal(4.5256)

  protected static HttpHeaders createHttpHeaders(
          MediaType requestMediaType,
          MediaType responseMediaType) {
    HttpHeaders httpHeaders = new HttpHeaders()
    httpHeaders.setContentType(requestMediaType)
    httpHeaders.setAccept(List.of(responseMediaType))
    httpHeaders
  }

  protected static void stubFor(WireMockServer wireMockServer, String endpoint = '/api/exchangerates/rates/A/EUR/') {
    wireMockServer.stubFor(get(urlPathEqualTo(endpoint))
            .willReturn(ok()
                    .withBodyFile('nbp-eur-response.json')
                    .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)))
  }

}
