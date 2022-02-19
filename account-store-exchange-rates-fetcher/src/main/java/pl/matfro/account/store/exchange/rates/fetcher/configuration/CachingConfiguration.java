package pl.matfro.account.store.exchange.rates.fetcher.configuration;

import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
@RequiredArgsConstructor
public class CachingConfiguration extends CachingConfigurerSupport {

  public static final String CACHE_NAME_EXCHANGE_RATES = "exchangeRates";

  @Bean
  @Override
  public CacheManager cacheManager() {
    SimpleCacheManager cacheManager = new SimpleCacheManager();
    cacheManager.setCaches(
            Collections.singletonList(new ConcurrentMapCache(CACHE_NAME_EXCHANGE_RATES)));
    return cacheManager;
  } //TODO implement disableable cache to always fetch exchange rate between 11:45 and 12:15
}
