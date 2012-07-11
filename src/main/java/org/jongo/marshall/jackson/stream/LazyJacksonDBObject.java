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

package org.jongo.marshall.jackson.stream;

import java.io.IOException;

import org.bson.LazyBSONCallback;
import org.jongo.marshall.MarshallingException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.mongodb.LazyDBObject;
import de.undercouch.bson4jackson.BsonFactory;

public class LazyJacksonDBObject<T> extends LazyDBObject {

    private final JsonParser jsonParser;
    private ObjectCodec codec;

    public LazyJacksonDBObject(byte[] data, int offset, LazyBSONCallback cbk, BsonFactory bsonFactory) {
        super(data, offset, cbk);
        this.codec = bsonFactory.getCodec();
        try {
            this.jsonParser = bsonFactory.createJsonParser(data, offset, data.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public T as(Class<T> clazz) {
        try {
            return codec.readValue(jsonParser, clazz);
        } catch (IOException e) {
            String message = String.format("Unable to unmarshall to class %s from content %s", clazz, this.toString());
            throw new MarshallingException(message, e);
        }
    }
}
