package com.capco.assignment.alahunou.converter;

import com.capco.assignment.alahunou.model.Feature;
import com.capco.assignment.alahunou.view.FeatureDto;
import org.springframework.stereotype.Component;

@Component
public class FeatureConverter {

    public FeatureDto toDto(Feature feature) {
        return new FeatureDto(feature.getId(), feature.getName(), feature.getDisabled());
    }
}
