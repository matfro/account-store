package pl.matfro.common.mapping;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.matfro.common.mapping.mappers.ModelMapperCustomizer;
import pl.matfro.common.mapping.mappers.ModelMapperFactory;

@Configuration
@ComponentScan
public class CommonMappingAutoConfiguration {

  @Bean
  public ModelMapper modelMapper(List<ModelMapperCustomizer> customizers) {
    ModelMapper modelMapper = new ModelMapperFactory().newModelMapper();

    for (ModelMapperCustomizer customizer : customizers) {
      customizer.customize(modelMapper);
    }

    return modelMapper;
  }
}
