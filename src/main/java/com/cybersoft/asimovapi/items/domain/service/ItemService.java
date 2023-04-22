package com.cybersoft.asimovapi.items.domain.service;

import com.cybersoft.asimovapi.items.domain.model.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ItemService {
    List<Item> getAllByCourseId(Long courseId);
    Page<Item> getAllByCourseId(Long courseId,Pageable pageable);
    Item getById(Long itemId);
    Item create(Item item, Long courseId);
    Item update(Long itemId, Item request);
    ResponseEntity<?> delete(Long itemId);
}
