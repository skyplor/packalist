package com.skypayjm.thack.packalist.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by user on 6/13/2015.
 */
public class AmadeusJSONParser<T> {
    //T stands for "Type"
    private Class<T> targetClass;

    private ArrayList<T> list;
    private T object;

    public AmadeusJSONParser(Class<T> clazz) {
        this.targetClass = clazz;
    }

    public ArrayList<T> unmarshalList(String json) {
        ObjectMapper mapper = new ObjectMapper();
        AnnotationIntrospector introspector = new JacksonAnnotationIntrospector();

        mapper.getDeserializationConfig().with(introspector);

        mapper.getSerializationConfig().with(introspector);
        JavaType type = mapper.getTypeFactory().constructCollectionType(ArrayList.class, targetClass);
        try {
            list = (ArrayList<T>) mapper.readValue(json, type);

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public T unmarshal(String json) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            object = mapper.readValue(json, targetClass);

        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return object;
    }
}
