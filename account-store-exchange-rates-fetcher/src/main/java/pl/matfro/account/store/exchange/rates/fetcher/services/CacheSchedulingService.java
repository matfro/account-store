package pl.matfro.account.store.exchange.rates.fetcher.services;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CacheSchedulingService {

  private final CachingService cachingService;

  @Scheduled(cron = "${pl.matfro.account.store.exchange.rates.fetcher.cache.eviction-cron}")
  public void clearCache() {
    cachingService.clearCache();
  }
}
