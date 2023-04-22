package com.cybersoft.asimovapi.items.service;

import com.cybersoft.asimovapi.courses.domain.persistence.CourseRepository;
import com.cybersoft.asimovapi.items.domain.model.entity.Item;
import com.cybersoft.asimovapi.items.domain.persistence.ItemRepository;

import com.cybersoft.asimovapi.items.domain.service.ItemService;
import com.cybersoft.asimovapi.shared.exception.ResourceNotFoundException;
import com.cybersoft.asimovapi.shared.exception.ResourceValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private CourseRepository courseRepository;

    private static final String ENTITY = "Item";
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private Validator validator;


    @Override
    public List<Item> getAllByCourseId(Long courseId) {
        var existingCourse =  courseRepository.findById(courseId);
        if(existingCourse.isEmpty())
            throw new ResourceNotFoundException("Course",courseId);
        return itemRepository.findByCourseId(courseId);
    }

    @Override
    public Page<Item> getAllByCourseId(Long courseId, Pageable pageable) {
        return itemRepository.findByCourseId(courseId,pageable);
    }

    @Override
    public Item getById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException(ENTITY, itemId));
    }

    @Override
    public Item create(Item item, Long courseId) {
        Set<ConstraintViolation<Item>> violations = validator.validate(item);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);
        return courseRepository.findById(courseId).map(course -> {
            item.setCourse(course);
            return itemRepository.save(item);
        }).orElseThrow(()->new ResourceNotFoundException("Course ",courseId));
    }

    @Override
    public Item update(Long itemId, Item request) {
        Set<ConstraintViolation<Item>> violations = validator.validate(request);
        if (!violations.isEmpty())
            throw new ResourceValidationException(ENTITY, violations);

        return itemRepository.findById(itemId).map(item ->
                itemRepository.save(
                        item.withName(request.getName())
                                .withState(request.getState()).withDescription(request.getDescription()))
                ).orElseThrow(() -> new ResourceNotFoundException(ENTITY, itemId));
    }

    @Override
    public ResponseEntity<?> delete(Long itemId) {
        return itemRepository.findById(itemId).map(item -> {
            itemRepository.delete(item);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException(ENTITY, itemId));
    }
}
