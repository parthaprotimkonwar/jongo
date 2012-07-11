/*
 * Copyright (C) 2011 Benoit GUEROUT <bguerout at gmail dot com> and Yves AMSELLEM <amsellem dot yves at gmail dot com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jongo.marshall.jackson;


import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import de.undercouch.bson4jackson.BsonParser;

class BackwardDateDeserializer extends JsonDeserializer<Date> {

    private final EmbeddedObjectDeserializer deserializer;
    private final NativeDeserializer nativeDeserializer;

    public BackwardDateDeserializer(EmbeddedObjectDeserializer deserializer, NativeDeserializer nativeDeserializer) {
        this.deserializer = deserializer;
        this.nativeDeserializer = nativeDeserializer;
    }

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        if(jp instanceof BsonParser){
           return (Date)deserializer.deserialize(jp, ctxt);
        }
        return new Date((Long) nativeDeserializer.deserialize(jp, ctxt));
    }

}
