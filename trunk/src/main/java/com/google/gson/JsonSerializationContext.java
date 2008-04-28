package com.google.gson;

import java.lang.reflect.Type;

import com.google.gson.reflect.ObjectNavigator;
import com.google.gson.reflect.ObjectNavigatorFactory;

final class JsonSerializationContext implements JsonSerializer.Context {
  
  private final ObjectNavigatorFactory factory;
  private final ParameterizedTypeHandlerMap<JsonSerializer<?>> serializers;

  JsonSerializationContext(ObjectNavigatorFactory factory, 
      ParameterizedTypeHandlerMap<JsonSerializer<?>> serializers) {
    this.factory = factory;
    this.serializers = serializers;
  }
  
  public JsonElement serialize(Object src) {
    return serialize(src, src.getClass());
  }
  
  public JsonElement serialize(Object src, Type typeOfSrc) {
    ObjectNavigator on = factory.create(src, typeOfSrc);
    JsonSerializationVisitor visitor = new JsonSerializationVisitor(factory, serializers, this);
    on.accept(visitor);
    return visitor.getJsonElement();
  }
}
