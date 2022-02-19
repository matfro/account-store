package pl.matfro.account.store.exchange.rates.fetcher;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import pl.matfro.account.store.exchange.rates.fetcher.configuration.AccountStoreExchangeRatesFetcherConfigurationProperties;

@Configuration
@ComponentScan
@EnableScheduling
@EnableCaching
@EnableConfigurationProperties(AccountStoreExchangeRatesFetcherConfigurationProperties.class)
public class AccountStoreExchangeRatesFetcherAutoConfiguration {

  @Bean
  public RestTemplate exchangeRateRegistryRestTemplate(
          RestTemplateBuilder restTemplateBuilder,
          AccountStoreExchangeRatesFetcherConfigurationProperties properties) {
    return restTemplateBuilder.rootUri(properties.getBaseUrl()).build();
  }

}
