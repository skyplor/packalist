package com.skypayjm.thack.packalist.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skypayjm.thack.packalist.model.Airport;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by user on 6/13/2015.
 */
public class AmadeusJSONParser {
    //T stands for"Type"
//    private Class<T> targetClass;

    private ArrayList<Airport> airports;

    public AmadeusJSONParser() {
    }

    public ArrayList<Airport> unmarshal(String json) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            airports = mapper.readValue(json, new TypeReference<ArrayList<Airport>>() {
            });

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return airports;
    }
}
