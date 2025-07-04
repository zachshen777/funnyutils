package com.zach.funnyutils.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ImageParam {
    private String charType;
    private String charInput;
    private double scale;

    @JsonCreator
    public ImageParam (
            @JsonProperty("charType") String charType,
            @JsonProperty("charInput") String charInput,
            @JsonProperty("scale") double scale) {
        this.charType = charType;
        this.charInput = charInput;
        this.scale = scale;
    }
}
