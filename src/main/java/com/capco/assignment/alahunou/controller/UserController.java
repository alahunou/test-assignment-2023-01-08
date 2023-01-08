package com.capco.assignment.alahunou.controller;

import com.capco.assignment.alahunou.converter.FeatureConverter;
import com.capco.assignment.alahunou.model.Feature;
import com.capco.assignment.alahunou.service.UserService;
import com.capco.assignment.alahunou.view.FeatureDto;
import com.capco.assignment.alahunou.view.FeatureStatusResponse;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/user", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
public class UserController {

    private UserService userService;
    private FeatureConverter featureConverter;

    public UserController(UserService userService, FeatureConverter featureConverter) {
        this.userService = userService;
        this.featureConverter = featureConverter;
    }

    @PutMapping(path = "/{userId}/feature")
    public void switchFeature(@PathVariable("userId") Long userId, @RequestBody FeatureDto request) {
        // TODO validate input parameters ?
        userService.switchFeature(userId, request.getId(), request.getDisabled());
    }

    @GetMapping(path = "/{userId}/feature")
    public FeatureStatusResponse getFeatures(@PathVariable("userId") Long userId) {
        Pair<List<Feature>, List<Feature>> featuresForUser = userService.getFeatures(userId);

        return new FeatureStatusResponse(
                featuresForUser.getFirst().stream().map(featureConverter::toDto).toList(),
                featuresForUser.getSecond().stream().map(featureConverter::toDto).toList()
        );
    }
}
