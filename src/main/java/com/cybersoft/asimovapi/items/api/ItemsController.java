package com.cybersoft.asimovapi.items.api;

import com.cybersoft.asimovapi.items.mapping.ItemMapper;
import com.cybersoft.asimovapi.items.resource.ItemResource;
import com.cybersoft.asimovapi.items.domain.service.ItemService;
import com.cybersoft.asimovapi.items.resource.CreateItemResource;
import com.cybersoft.asimovapi.items.resource.UpdateItemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@PreAuthorize("hasRole('DIRECTOR') or hasRole('TEACHER')")
public class ItemsController {

    private final ItemService itemService;

    private final ItemMapper mapper;

    public ItemsController(ItemService itemService, ItemMapper mapper) {
        this.itemService = itemService;
        this.mapper = mapper;
    }

    @GetMapping("courses/{courseId}/items")
    public List<ItemResource> getAllByCourseID(@PathVariable Long courseId) {
        return mapper.modelListToResource(itemService.getAllByCourseId(courseId));
    }

    @PostMapping("courses/{courseId}/items")
    public ItemResource createItem(@RequestBody CreateItemResource request, @PathVariable Long courseId) {
        return mapper.toResource(itemService.create(mapper.toModel(request),courseId));
    }


    @PutMapping("items/{itemId}")
    public ItemResource updateItem(@PathVariable Long itemId, @RequestBody UpdateItemResource request) {
        return mapper.toResource(itemService.update(itemId, mapper.toModel(request)));
    }

    @DeleteMapping("items/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable Long itemId) {
        return itemService.delete(itemId);
    }
}
