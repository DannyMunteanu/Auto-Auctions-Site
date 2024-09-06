package com.github.grngoo.AutoAuctions.Services;

import com.github.grngoo.AutoAuctions.Models.Manufacturer;
import com.github.grngoo.AutoAuctions.Models.Model;
import com.github.grngoo.AutoAuctions.Repositories.ManufacturerRepository;
import com.github.grngoo.AutoAuctions.Repositories.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.grngoo.AutoAuctions.DTOs.ModelDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service for Model.
 *
 * @author danielmunteanu
 */
@Service
public class ModelService {

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    /**
     * Find all models and filter based on input.
     * If there is no input then return all models.
     * Otherwise, returns an intersection set of filtered results from repository.
     * Note: requires displacement(cc) and cylinder(number of) ranges.
     * User filter only applied if specified.
     *
     * @param modelDto Contains all request param for search filters
     * @return A set of models filtered or unfiltered.
     */
    public List<Model> filterModels(ModelDto modelDto) {
        List<Model> modelSet = modelRepository.findAll();
        List<List<Model>> allFilterSet = new ArrayList<>();
        allFilterSet.add(modelRepository.findByDisplacementBetween(modelDto.getDisplacement()[0], modelDto.getDisplacement()[1]));
        allFilterSet.add(modelRepository.findByCylindersBetween(modelDto.getCylinders()[0], modelDto.getCylinders()[1]));
        if (modelDto.getMake() != null) {
            allFilterSet.add(modelRepository.findByManufacturerMake(modelDto.getMake()));
        }
        if (modelDto.getName() != null) {
            allFilterSet.add(modelRepository.findByModelname(modelDto.getName()));
        }
        if (modelDto.getSeries() != null) {
            allFilterSet.add(modelRepository.findBySeries(modelDto.getSeries()));
        }
        for (List<Model> filterSet : allFilterSet) {
            modelSet.retainAll(filterSet);
        }
        return modelSet;
    }

    /**
     * Add a new model.
     *
     * @param modelDto contains all params for constructing new model entity.
     * @return Newly created entity.
     */
    public Model saveModel(ModelDto modelDto) {
        Optional<Manufacturer> manufacturerOptional = manufacturerRepository.findById(modelDto.getMake());
        Model model = new Model(
                manufacturerOptional.get(),
                modelDto.getSeries(),
                modelDto.getName(),
                modelDto.getDisplacement()[0],
                modelDto.getCylinders()[0]
        );
        return modelRepository.save(model);
    }
}
