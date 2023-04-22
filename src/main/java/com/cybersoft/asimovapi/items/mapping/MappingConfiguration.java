package com.cybersoft.asimovapi.items.mapping;

import com.cybersoft.asimovapi.items.mapping.ItemMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("itemMappingConfiguration")
public class MappingConfiguration {
  @Bean
  public ItemMapper itemMapper() { return new ItemMapper(); }
}
