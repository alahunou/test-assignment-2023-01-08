package com.capco.assignment.alahunou.service;

import com.capco.assignment.alahunou.model.Feature;
import com.capco.assignment.alahunou.view.FeatureStatusResponse;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static java.util.Arrays.asList;

@Service
public class UserService {

    public void switchFeature(long userId, long featureId, boolean disabled) {
        // TODO actually switch feature
    }

    public Pair<List<Feature>, List<Feature>> getFeatures(long userId) {
        Random random = new Random();

        return Pair.of(
                asList(
                        new Feature(random.nextLong(), UUID.randomUUID().toString(), random.nextBoolean()),
                        new Feature(random.nextLong(), UUID.randomUUID().toString(), random.nextBoolean()),
                        new Feature(random.nextLong(), UUID.randomUUID().toString(), random.nextBoolean())
                ),
                asList(
                        new Feature(random.nextLong(), UUID.randomUUID().toString(), random.nextBoolean()),
                        new Feature(random.nextLong(), UUID.randomUUID().toString(), random.nextBoolean()),
                        new Feature(random.nextLong(), UUID.randomUUID().toString(), random.nextBoolean())
                )
        );
    }
}
