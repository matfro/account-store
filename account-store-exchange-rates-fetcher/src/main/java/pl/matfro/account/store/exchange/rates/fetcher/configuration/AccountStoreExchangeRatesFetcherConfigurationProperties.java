package pl.matfro.account.store.exchange.rates.fetcher.configuration;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "pl.matfro.account.store.exchange.rates.fetcher")
public class AccountStoreExchangeRatesFetcherConfigurationProperties {

  private String baseUrl;

  private String endpoint;

  private AccountStoreCurrency currency = new AccountStoreCurrency();

  @Getter
  @Setter
  public static class AccountStoreCurrency {

    Map<Currency, String> currencyTableMap = new HashMap<>();

    Currency defaultCurrency = Currency.getInstance("EUR");

  }
}
