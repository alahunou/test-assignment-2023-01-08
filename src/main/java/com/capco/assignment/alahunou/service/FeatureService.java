package com.capco.assignment.alahunou.service;

import com.capco.assignment.alahunou.model.Feature;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class FeatureService {

    public Feature create(String name) {
        // TODO actually create a feature
        return new Feature(new Random().nextLong(), name, true);
    }
}
