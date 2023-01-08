package com.capco.assignment.alahunou.controller;

import com.capco.assignment.alahunou.converter.FeatureConverter;
import com.capco.assignment.alahunou.model.Feature;
import com.capco.assignment.alahunou.service.FeatureService;
import com.capco.assignment.alahunou.view.FeatureDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/feature", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class FeatureController {

    private final FeatureService featureService;
    private final FeatureConverter featureConverter;

    public FeatureController(FeatureService featureService, FeatureConverter featureConverter) {
        this.featureService = featureService;
        this.featureConverter = featureConverter;
    }

    @PostMapping
    public FeatureDto create(FeatureDto featureDTO) {
        Feature feature = featureService.create(featureDTO.getName());
        return featureConverter.toDto(feature);
    }
}
