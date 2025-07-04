package com.zach.funnyutils.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ImageParam {
    private int width;
    private int height;
    private double scale;

    @JsonCreator
    public ImageParam(
            @JsonProperty("width") int width,
            @JsonProperty("height") int height,
            @JsonProperty("scale") double scale) {
        this.width = width;
        this.height = height;
        this.scale = scale;
    }
}
