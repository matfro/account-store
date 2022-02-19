package pl.matfro.account.store.exchange.rates.fetcher.health;

import java.util.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;
import pl.matfro.account.store.exchange.rates.fetcher.configuration.AccountStoreExchangeRatesFetcherConfigurationProperties;
import pl.matfro.account.store.exchange.rates.fetcher.services.ExchangeRateFetcherService;

@Component("exchangeRateRegistry")
@RequiredArgsConstructor
public class ExchangeRateRegistryHealthIndicator extends AbstractHealthIndicator {

  private final AccountStoreExchangeRatesFetcherConfigurationProperties properties;

  private final ExchangeRateFetcherService exchangeRateFetcherService;

  @Override
  protected void doHealthCheck(Health.Builder builder) {
    builder.withDetail(
            "message",
            String.format(
                    "ExchangeRateRegistry @ %s%s", properties.getBaseUrl(), properties.getEndpoint()));
    exchangeRateFetcherService.getExchangeRate(Currency.getInstance("EUR"));
    builder.up();
  }
}
