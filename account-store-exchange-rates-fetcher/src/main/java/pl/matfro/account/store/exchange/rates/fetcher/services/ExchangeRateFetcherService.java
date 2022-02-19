package pl.matfro.account.store.exchange.rates.fetcher.services;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.matfro.account.store.exchange.rates.fetcher.configuration.AccountStoreExchangeRatesFetcherConfigurationProperties;
import pl.matfro.account.store.exchange.rates.fetcher.configuration.CachingConfiguration;
import pl.matfro.account.store.exchange.rates.fetcher.exceptions.CurrencyNotSupportedException;
import pl.matfro.account.store.exchange.rates.fetcher.model.ExchangeRateModel;

@Service
@RequiredArgsConstructor
public class ExchangeRateFetcherService {

  private final RestTemplate exchangeRateRegistryRestTemplate;

  private final AccountStoreExchangeRatesFetcherConfigurationProperties properties;

  @Cacheable(
          cacheNames = {CachingConfiguration.CACHE_NAME_EXCHANGE_RATES},
          key = "'${pl.matfro.account.store.exchange.rates.fetcher.currency.default-currency}'")
  public BigDecimal getExchangeRate() {
    Currency currency = properties.getCurrency().getDefaultCurrency();
    final String table = properties.getCurrency().getCurrencyTableMap().get(currency);
    return fetchExchangeRate(table, currency);
  }

  @Cacheable(
          cacheNames = {CachingConfiguration.CACHE_NAME_EXCHANGE_RATES},
          key = "#currency")
  public BigDecimal getExchangeRate(Currency currency) {
    final String table = properties.getCurrency().getCurrencyTableMap().get(currency);
    return fetchExchangeRate(table, currency);
  }

  private BigDecimal fetchExchangeRate(String table, Currency currency) {

    if (table == null) {
      throw new CurrencyNotSupportedException(String.format("Currency %s not supported", currency));
    }

    return Objects.requireNonNull(
            exchangeRateRegistryRestTemplate.getForObject(
                    properties.getEndpoint() + "/{table}/{currency}/",
                    ExchangeRateModel.class,
                    table,
                    currency))
            .getRates()
            .get(0)
            .getMid()
            .round(new MathContext(5, RoundingMode.HALF_EVEN));
  }
}
