package pl.matfro.account.store.data.orm;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import pl.matfro.common.components.resources.IdAndVersion;

@Getter
@Setter
@EqualsAndHashCode(of = {"id", "version"})
public abstract class UuidAndVersionResource implements IdAndVersion<UUID> {

  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(
          length = 36,
          unique = true,
          nullable = false,
          updatable = false,
          columnDefinition = "uuid DEFAULT RANDOM_UUID()")
  @Type(type = "uuid-char")
  @Id
  private UUID id;

  @Version
  @Column(nullable = false)
  private Long version;

}
