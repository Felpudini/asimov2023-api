package com.cybersoft.asimovapi.announcements.mapping;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("announcementMappingConfiguration")
public class MappingConfiguration {
    @Bean
    public AnnouncementMapper announcementMapper() { return new AnnouncementMapper(); }
}
