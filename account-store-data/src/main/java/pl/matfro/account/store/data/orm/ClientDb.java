package pl.matfro.account.store.data.orm;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "client", indexes = @Index(name = "ix_client_identifier", columnList = "identifier"))
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ClientDb extends UuidAndVersionResource {

  @Column(nullable = false)
  private String identifier;

  @OneToMany(targetEntity = AccountDb.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
  @JoinColumn(name = "client_id", nullable = false)
  private Set<AccountDb> accounts;
}
