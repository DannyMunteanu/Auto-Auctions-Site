package com.github.grngoo.AutoAuctions.Services;

import com.github.grngoo.AutoAuctions.Models.Model;
import com.github.grngoo.AutoAuctions.Repositories.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    /**
     * Retrieve model by unique id.
     * @param modelId unique model value.
     * @return specified model entity.
     */
    public Optional<Model> findModel(Long modelId) {
        return modelRepository.findById(modelId);
    }

    /**
     *
     * @param make
     * @return
     */
    public List<Model> findByMake(String make) {
        return modelRepository.findByManufacturerMake(make);
    }

    /**
     * Get all models of the same name (ie all Focus models)
     *
     * @param name of model.
     * @return group of model entities with same name.
     */
    public List<Model> findByName(String name) {
        return modelRepository.findByModelname(name);
    }

    /**
     * Return all models produced under same series.
     * @param series manufacturing code for generations.
     * @return all models with same series.
     */
    public List<Model> findBySeries(String series) {
        return modelRepository.findBySeries(series);
    }

    /**
     * Find all models between a displacement range.
     *
     * @param minDisplacement upperbound.
     * @param maxDisplacemnt lowerbound.
     * @return all models with engines within range.
     */
    public List<Model> findByDislacement(BigDecimal minDisplacement, BigDecimal maxDisplacemnt) {
        return modelRepository.findByDisplacementBetween(minDisplacement, maxDisplacemnt);
    }

    /**
     * Find by number of cylinders
     *
     * @param cylinders engine pistons.
     * @return All models with certain cylinder count.
     */
    public List<Model> findByCylinders(Integer cylinders) {
        return modelRepository.findByCylinders(cylinders);
    }

    /**
     * Add a new model.
     *
     * @param model Spring entity.
     * @return Newly created entity.
     */
    public Model saveModel(Model model) {
        return modelRepository.save(model);
    }
}
