package com.cybersoft.asimovapi.announcements.mapping;

import com.cybersoft.asimovapi.announcements.domain.model.entity.Announcement;
import com.cybersoft.asimovapi.announcements.resource.AnnouncementResource;
import com.cybersoft.asimovapi.announcements.resource.CreateAnnouncementResource;
import com.cybersoft.asimovapi.announcements.resource.UpdateAnnouncementResource;
import com.cybersoft.asimovapi.shared.mapping.EnhancedModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public class AnnouncementMapper implements Serializable {

    @Autowired
    EnhancedModelMapper mapper;

    public AnnouncementResource toResource(Announcement model) { return mapper.map(model, AnnouncementResource.class); }

    public Page<AnnouncementResource> modelListToPage(List<Announcement> modelList, Pageable pageable) {
        return new PageImpl<>(mapper.mapList(modelList, AnnouncementResource.class), pageable, modelList.size());
    }

    public List<AnnouncementResource> modelListToResource(List<Announcement> modelList){return mapper.mapList(modelList, AnnouncementResource.class); }

    public Announcement toModel(CreateAnnouncementResource resource) {
        return mapper.map(resource, Announcement.class);
    }

    public Announcement toModel(UpdateAnnouncementResource resource) {
        return mapper.map(resource, Announcement.class);
    }

}
