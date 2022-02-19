package pl.matfro.account.store.data.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.matfro.account.model.Account;
import pl.matfro.account.model.Client;
import pl.matfro.account.store.data.orm.AccountDb;
import pl.matfro.account.store.data.orm.ClientDb;
import pl.matfro.common.mapping.mappers.ModelMapperCustomizer;

@Configuration
public class ModelMapperConfiguration {

  @Bean
  public ModelMapperCustomizer modelMapperCustomizer() {
    return modelMapper -> {
      modelMapper.typeMap(AccountDb.class, Account.class);
      modelMapper.typeMap(Account.class, AccountDb.class);

      modelMapper.typeMap(ClientDb.class, Client.class);
      modelMapper.typeMap(Client.class, ClientDb.class);
    };
  }
}
