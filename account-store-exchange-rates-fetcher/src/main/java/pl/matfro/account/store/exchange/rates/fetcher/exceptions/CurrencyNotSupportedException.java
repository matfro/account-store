package pl.matfro.account.store.exchange.rates.fetcher.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CurrencyNotSupportedException extends IllegalArgumentException {

  private static final long serialVersionUID = -5803908438358312002L;

  public CurrencyNotSupportedException(String message) {
    super(message);
  }
}
