package pl.matfro.account.store.exchange.rates.fetcher.model;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExchangeRateModel {

  private List<Rate> rates;

  @Getter
  @Setter
  public static class Rate {
    private BigDecimal mid;
  }
}
