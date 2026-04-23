/*
 * Decompiled with CFR 0.152.
 */
package com.badlogic.gdx.utils;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.StringBuilder;
import java.math.BigDecimal;
import java.math.BigInteger;

public class JsonString {
    final StringBuilder buffer;
    private final Array<JsonObject> stack = new Array();
    private JsonObject current;
    private boolean named;
    private JsonWriter.OutputType outputType = JsonWriter.OutputType.json;
    private boolean quoteLongValues = false;

    public JsonString() {
        this.buffer = new StringBuilder();
    }

    public StringBuilder getBuffer() {
        return this.buffer;
    }

    public void setOutputType(JsonWriter.OutputType outputType) {
        this.outputType = outputType;
    }

    public void setQuoteLongValues(boolean quoteLongValues) {
        this.quoteLongValues = quoteLongValues;
    }

    public JsonString name(String name) {
        if (this.current == null || this.current.array) {
            throw new IllegalStateException("Current item must be an object.");
        }
        if (!this.current.needsComma) {
            this.current.needsComma = true;
        } else {
            this.buffer.append(',');
        }
        this.buffer.append(this.outputType.quoteName(name));
        this.buffer.append(':');
        this.named = true;
        return this;
    }

    public JsonString object() {
        this.requireCommaOrName();
        this.current = new JsonObject(false);
        this.stack.add(this.current);
        return this;
    }

    public JsonString array() {
        this.requireCommaOrName();
        this.current = new JsonObject(true);
        this.stack.add(this.current);
        return this;
    }

    public JsonString value(@Null Object value) {
        if (this.quoteLongValues && (value instanceof Long || value instanceof Double || value instanceof BigDecimal || value instanceof BigInteger)) {
            value = value.toString();
        } else if (value instanceof Number) {
            Number number = (Number)value;
            long longValue = number.longValue();
            if (number.doubleValue() == (double)longValue) {
                value = longValue;
            }
        }
        this.requireCommaOrName();
        this.buffer.append(this.outputType.quoteValue(value));
        return this;
    }

    public JsonString json(String json) {
        this.requireCommaOrName();
        this.buffer.append(json);
        return this;
    }

    private void requireCommaOrName() {
        if (this.current == null) {
            return;
        }
        if (this.current.array) {
            if (!this.current.needsComma) {
                this.current.needsComma = true;
            } else {
                this.buffer.append(',');
            }
        } else {
            if (!this.named) {
                throw new IllegalStateException("Name must be set.");
            }
            this.named = false;
        }
    }

    public JsonString object(String name) {
        return this.name(name).object();
    }

    public JsonString array(String name) {
        return this.name(name).array();
    }

    public JsonString set(String name, Object value) {
        return this.name(name).value(value);
    }

    public JsonString json(String name, String json) {
        return this.name(name).json(json);
    }

    public JsonString pop() {
        if (this.named) {
            throw new IllegalStateException("Expected an object, array, or value since a name was set.");
        }
        this.stack.pop().close();
        this.current = this.stack.size == 0 ? null : this.stack.peek();
        return this;
    }

    public JsonString close() {
        while (this.stack.size > 0) {
            this.pop();
        }
        return this;
    }

    public void reset() {
        this.buffer.clear();
        this.stack.clear();
        this.current = null;
        this.named = false;
    }

    public String toString() {
        return this.buffer.toString();
    }

    private class JsonObject {
        final boolean array;
        boolean needsComma;

        JsonObject(boolean array) {
            this.array = array;
            JsonString.this.buffer.append(array ? (char)'[' : '{');
        }

        void close() {
            JsonString.this.buffer.append(this.array ? (char)']' : '}');
        }
    }
}

