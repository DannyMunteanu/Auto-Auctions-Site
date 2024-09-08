package com.github.grngoo.autoauctions.controllers;

import com.github.grngoo.autoauctions.dtos.ModelDto;
import com.github.grngoo.autoauctions.models.Model;
import com.github.grngoo.autoauctions.services.ModelService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoints for model functionality.
 *
 * @author danielmunteanu
 */
@RestController
@RequestMapping("/api/model")
public class ModelController {

  @Autowired ModelService modelService;

  /**
   * Search and filter model based on given parameters. If no filters given every model is returned.
   * If filters given, a filtered response is given. I.e. all model within a certain reserve range.
   *
   * @param modelDto DTO containing make, series name, displacement, cylinders params(filters).
   * @return A result of all desired models based on filters.
   */
  @GetMapping("/search")
  public ResponseEntity<List<Model>> searchModels(@Valid @RequestBody ModelDto modelDto) {
    try {
      return new ResponseEntity<>(modelService.filterModels(modelDto), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }

  /**
   * Add a new model.
   *
   * @param modelDto Data Transmissible Object to contain request body.
   * @return Response with newly added model and status code.
   */
  @PostMapping("/add")
  public ResponseEntity<Model> addModel(@Valid @RequestBody ModelDto modelDto) {
    try {
      return new ResponseEntity<>(modelService.saveModel(modelDto), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
  }
}
