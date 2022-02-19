package pl.matfro.account.store.exchange.rates.fetcher.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import pl.matfro.account.store.exchange.rates.fetcher.configuration.CachingConfiguration;

@Slf4j
@Service
public class CachingService {

  @CacheEvict(cacheNames = {CachingConfiguration.CACHE_NAME_EXCHANGE_RATES}, allEntries = true)
  public void clearCache() {
    log.info("Cache {} evicted", CachingConfiguration.CACHE_NAME_EXCHANGE_RATES);
  }
}
