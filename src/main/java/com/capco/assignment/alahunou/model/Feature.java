package com.capco.assignment.alahunou.model;

public class Feature {

    private Long id;
    private String name;
    private Boolean disabled;

    public Feature() {
    }

    public Feature(Long id, String name, Boolean disabled) {
        this.id = id;
        this.name = name;
        this.disabled = disabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
