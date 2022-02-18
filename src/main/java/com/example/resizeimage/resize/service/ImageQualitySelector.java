package com.example.resizeimage.resize.service;

import org.imgscalr.Scalr;

import java.util.Arrays;

public enum ImageQualitySelector {
    AUTOMATIC("automatic", Scalr.Method.AUTOMATIC),
    BALANCED("balanced", Scalr.Method.BALANCED),
    QUALITY("quality", Scalr.Method.QUALITY);

    private final String quality;
    private final Scalr.Method method;

    ImageQualitySelector(String quality, Scalr.Method method) {
        this.quality = quality;
        this.method = method;
    }

    public static Scalr.Method getMethod(String quality) {
        ImageQualitySelector imageQualitySelector = Arrays.stream(values())
                .filter(method -> method.quality.equalsIgnoreCase(quality))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Selected Quality Not Supported"));
        return imageQualitySelector.method;
    }
}
