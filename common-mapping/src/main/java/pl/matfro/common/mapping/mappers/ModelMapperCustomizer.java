package pl.matfro.common.mapping.mappers;

import org.modelmapper.ModelMapper;

@FunctionalInterface
public interface ModelMapperCustomizer {

  void customize(ModelMapper modelMapper);
}
