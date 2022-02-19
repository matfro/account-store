package pl.matfro.account.store.data.orm;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(
        name = "account",
        indexes = {
                @Index(name = "ix_account_client_id", columnList = "client_id"),
                @Index(name = "ix_account_account_number", columnList = "accountNumber")
        })
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AccountDb extends UuidAndVersionResource {

  @Column(nullable = false)
  private String accountNumber;

  @Column(nullable = false)
  private BigDecimal amount;
}
