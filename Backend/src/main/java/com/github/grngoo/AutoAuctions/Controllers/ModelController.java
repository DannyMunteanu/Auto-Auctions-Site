package com.github.grngoo.AutoAuctions.Controllers;

import com.github.grngoo.AutoAuctions.DTOs.ModelDto;
import com.github.grngoo.AutoAuctions.Models.Manufacturer;
import com.github.grngoo.AutoAuctions.Models.Model;
import com.github.grngoo.AutoAuctions.Services.ManufacturerService;
import com.github.grngoo.AutoAuctions.Services.ModelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Endpoints for model functionality.
 *
 * @author danielmunteanu
 */
@RestController
@RequestMapping("/api/model")
public class ModelController {

    @Autowired
    ModelService modelService;

    @Autowired
    ManufacturerService manufacturerService;

    /**
     * Find all models by make(manufacturer name)
     *
     * @param modelDto Data Transmissible Object to contain request body.
     * @return All models of same make.
     */
    @GetMapping("/make")
    public ResponseEntity<List<Model>> findModelByMake(@Valid @RequestBody ModelDto modelDto) {
        return new ResponseEntity<List<Model>>(modelService.findByMake(modelDto.getMake()), HttpStatus.OK);
    }

    /**
     * Find all models by name
     *
     * @param modelDto Data Transmissible Object to contain request body.
     * @return all models with same name.
     */
    @GetMapping("/name")
    public ResponseEntity<List<Model>> findModelByName(@Valid @RequestBody ModelDto modelDto) {
        return new ResponseEntity<List<Model>>(modelService.findByName(modelDto.getName()), HttpStatus.OK);
    }

    /**
     * Find all models of same series.
     *
     * @param modelDto Data Transmissible Object to contain request body.
     * @return all Models of same series.
     */
    @GetMapping("/series")
    public ResponseEntity<List<Model>> findModelBySeries(@Valid @RequestBody ModelDto modelDto) {
        return new ResponseEntity<List<Model>>(modelService.findBySeries(modelDto.getSeries()), HttpStatus.OK);
    }

    /**
     * Find all models within a displacemnt range.
     *
     * @param modelDto Data Transmissible Object to contain request body.
     * @return models within range
     */
    @GetMapping("/displacement")
    public ResponseEntity<List<Model>> findModelByDisplacment(@Valid @RequestBody ModelDto modelDto) {
        List<Model> models = modelService.findByDislacement(modelDto.getMinDisplacement(), modelDto.getMaxDisplacement());
        return new ResponseEntity<List<Model>>(models, HttpStatus.OK);
    }

    /**
     * Find all models of certain cylinder count.
     *
     * @param modelDto Data Transmissible Object to contain request body.
     * @return all models with certain cylinder count.
     */
    @GetMapping("/cylinders")
    public ResponseEntity<List<Model>> findModelByCylinders(@Valid @RequestBody ModelDto modelDto) {
        return new ResponseEntity<List<Model>>(modelService.findByCylinders(modelDto.getCylinders()), HttpStatus.OK);
    }

    /**
     * Add a new model.
     *
     * @param modelDto Data Transmissible Object to contain request body.
     * @return Response with newly added model and status code.
     */
    @PostMapping("/add")
    public ResponseEntity<Model> addModel(@Valid @RequestBody ModelDto modelDto) {
        Optional<Manufacturer> mOpt = manufacturerService.findManufacturer(modelDto.getMake());
        Model model = new Model(
                mOpt.get(),
                modelDto.getSeries(),
                modelDto.getName(),
                modelDto.getMaxDisplacement(),
                modelDto.getCylinders()
        );
        return new ResponseEntity<Model>(modelService.saveModel(model), HttpStatus.OK);
    }
}
