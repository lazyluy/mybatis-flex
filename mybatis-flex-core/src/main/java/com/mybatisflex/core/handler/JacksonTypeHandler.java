/*
 *  Copyright (c) 2022-2023, Mybatis-Flex (fuhai999@gmail.com).
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.mybatisflex.core.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mybatisflex.core.exception.FlexExceptions;

import java.io.IOException;

public class JacksonTypeHandler extends BaseJsonTypeHandler<Object> {

    private static ObjectMapper objectMapper;
    private final Class<?> propertyType;

    public JacksonTypeHandler(Class<?> propertyType) {
        this.propertyType = propertyType;
    }

    @Override
    protected Object parseJson(String json) {
        try {
            return getObjectMapper().readValue(json, propertyType);
        } catch (IOException e) {
            throw FlexExceptions.wrap(e, "Can not parseJson by JacksonTypeHandler: " + json);
        }
    }

    @Override
    protected String toJson(Object object) {
        try {
            return getObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw FlexExceptions.wrap(e, "Can not convert object to Json by JacksonTypeHandler: " + object);
        }
    }

    public static ObjectMapper getObjectMapper() {
        if (null == objectMapper) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }

    public static void setObjectMapper(ObjectMapper objectMapper) {
        JacksonTypeHandler.objectMapper = objectMapper;
    }

}
