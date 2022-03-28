package com.execise.estore.estore.utils;

import java.util.List;

import lombok.Getter;

@Getter
public class RestMessage {
    private String message;
    private List<String> messages;
    /*
     * Why not using a lombok annotation to create the constructors?
     * As of the time of writing, no feature provided by Lombok 
     * creates separate constructors for each property. 
     * Therefore, we needed to add the code by ourselves, 
     * but at least we could take advantage of the @Getter annotation
     */
    public RestMessage(List<String> messages) {
        this.messages = messages;
    }

    public RestMessage(String message) {
        this.message = message;
    }
}
