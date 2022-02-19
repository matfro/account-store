package pl.matfro.common.mapping.mappers;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;

public class ModelMapperFactory {

  public static ModelMapper createModelMapper() {
    return new ModelMapperFactory().newModelMapper();
  }

  public ModelMapper newModelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    Configuration configuration = modelMapper.getConfiguration();
    configuration.setMatchingStrategy(MatchingStrategies.STRICT);
    return modelMapper;
  }
}
