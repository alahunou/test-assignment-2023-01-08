package com.capco.assignment.alahunou.view;

import java.util.List;

public class FeatureStatusResponse {

    private List<FeatureDto> allFeatures;
    private List<FeatureDto> userFeatures;

    public FeatureStatusResponse(List<FeatureDto> allFeatures, List<FeatureDto> userFeatures) {
        this.allFeatures = allFeatures;
        this.userFeatures = userFeatures;
    }

    public List<FeatureDto> getAllFeatures() {
        return allFeatures;
    }

    public void setAllFeatures(List<FeatureDto> allFeatures) {
        this.allFeatures = allFeatures;
    }

    public List<FeatureDto> getUserFeatures() {
        return userFeatures;
    }

    public void setUserFeatures(List<FeatureDto> userFeatures) {
        this.userFeatures = userFeatures;
    }
}
