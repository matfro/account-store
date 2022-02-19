package pl.matfro.common.components.jpa;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import pl.matfro.common.components.resources.IdAndVersion;

@RequiredArgsConstructor
public abstract class AbstractPersistentJpaService<T, I> {

  private final ModelMapper modelMapper;

  public abstract T fetch(I id);

  protected <E extends IdAndVersion<I>> T mapEntity(E entity, T resource) {
    modelMapper.map(entity, resource);

    return resource;
  }
}
