/*
 * Decompiled with CFR 0.152.
 */
package com.badlogic.gdx.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Null;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.StreamUtils;
import com.badlogic.gdx.utils.StringBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;

public class JsonSkimmer {
    private static final byte[] _json_actions = JsonSkimmer.init__json_actions_0();
    private static final short[] _json_key_offsets = JsonSkimmer.init__json_key_offsets_0();
    private static final char[] _json_trans_keys = JsonSkimmer.init__json_trans_keys_0();
    private static final byte[] _json_single_lengths = JsonSkimmer.init__json_single_lengths_0();
    private static final byte[] _json_range_lengths = JsonSkimmer.init__json_range_lengths_0();
    private static final short[] _json_index_offsets = JsonSkimmer.init__json_index_offsets_0();
    private static final byte[] _json_indicies = JsonSkimmer.init__json_indicies_0();
    private static final byte[] _json_trans_targs = JsonSkimmer.init__json_trans_targs_0();
    private static final byte[] _json_trans_actions = JsonSkimmer.init__json_trans_actions_0();
    private static final byte[] _json_eof_actions = JsonSkimmer.init__json_eof_actions_0();
    static final int json_start = 1;
    static final int json_first_final = 35;
    static final int json_error = 0;
    static final int json_en_object = 5;
    static final int json_en_array = 23;
    static final int json_en_main = 1;
    private boolean stop;

    public void parse(String json) {
        char[] data = json.toCharArray();
        this.parse(data, 0, data.length);
    }

    public void parse(Reader reader) {
        char[] data = new char[1024];
        int offset = 0;
        try {
            int length;
            while ((length = reader.read(data, offset, data.length - offset)) != -1) {
                if (length == 0) {
                    char[] newData = new char[data.length * 2];
                    System.arraycopy(data, 0, newData, 0, data.length);
                    data = newData;
                    continue;
                }
                offset += length;
            }
        }
        catch (IOException ex) {
            throw new SerializationException("Error reading input.", ex);
        }
        finally {
            StreamUtils.closeQuietly(reader);
        }
        this.parse(data, 0, offset);
    }

    public void parse(InputStream input) {
        InputStreamReader reader;
        try {
            reader = new InputStreamReader(input, "UTF-8");
        }
        catch (Exception ex) {
            throw new SerializationException("Error reading stream.", ex);
        }
        this.parse(reader);
    }

    public void parse(FileHandle file) {
        Reader reader;
        try {
            reader = file.reader("UTF-8");
        }
        catch (Exception ex) {
            throw new SerializationException("Error reading file: " + file, ex);
        }
        try {
            this.parse(reader);
        }
        catch (Exception ex) {
            throw new SerializationException("Error parsing file: " + file, ex);
        }
    }

    /*
     * Unable to fully structure code
     */
    public void parse(char[] data, int offset, int length) {
        this.stop = false;
        p = offset;
        eof = pe = length;
        top = 0;
        stack = new int[4];
        s = 0;
        name = null;
        needsUnescape = false;
        stringIsName = false;
        stringIsUnquoted = false;
        parseRuntimeEx = null;
        debug = false;
        if (debug) {
            System.out.println();
        }
        try {
            cs = 1;
            top = 0;
            _trans = 0;
            _goto_targ = 0;
            block36: while (true) {
                switch (_goto_targ) {
                    case 0: {
                        if (p == pe) {
                            _goto_targ = 4;
                            continue block36;
                        }
                        if (cs == 0) {
                            _goto_targ = 5;
                            continue block36;
                        }
                    }
                    case 1: {
                        _keys = JsonSkimmer._json_key_offsets[cs];
                        _trans = JsonSkimmer._json_index_offsets[cs];
                        _klen = JsonSkimmer._json_single_lengths[cs];
                        if (_klen <= 0) ** GOTO lbl48
                        _lower = _keys;
                        _upper = _keys + _klen - 1;
                        while (_upper >= _lower) {
                            _mid = _lower + (_upper - _lower >> 1);
                            if (data[p] < JsonSkimmer._json_trans_keys[_mid]) {
                                _upper = _mid - 1;
                                continue;
                            }
                            if (data[p] > JsonSkimmer._json_trans_keys[_mid]) {
                                _lower = _mid + 1;
                                continue;
                            }
                            _trans += _mid - _keys;
                            ** GOTO lbl62
                        }
                        _keys += _klen;
                        _trans += _klen;
lbl48:
                        // 2 sources

                        if ((_klen = JsonSkimmer._json_range_lengths[cs]) > 0) {
                            _lower = _keys;
                            _upper = _keys + (_klen << 1) - 2;
                            while (_upper >= _lower) {
                                _mid = _lower + (_upper - _lower >> 1 & -2);
                                if (data[p] < JsonSkimmer._json_trans_keys[_mid]) {
                                    _upper = _mid - 2;
                                    continue;
                                }
                                if (data[p] > JsonSkimmer._json_trans_keys[_mid + 1]) {
                                    _lower = _mid + 2;
                                    continue;
                                }
                                _trans += _mid - _keys >> 1;
                                ** GOTO lbl62
                            }
                            _trans += _klen;
                        }
lbl62:
                        // 5 sources

                        _trans = JsonSkimmer._json_indicies[_trans];
                        cs = JsonSkimmer._json_trans_targs[_trans];
                        if (JsonSkimmer._json_trans_actions[_trans] == 0) ** GOTO lbl218
                        _acts = JsonSkimmer._json_trans_actions[_trans];
                        _nacts = JsonSkimmer._json_actions[_acts++];
                        block39: while (_nacts-- > 0) {
                            switch (JsonSkimmer._json_actions[_acts++]) {
                                case 0: {
                                    stringIsName = true;
                                    break;
                                }
                                case 1: {
                                    value = new String(data, s, p - s);
                                    if (needsUnescape) {
                                        value = this.unescape(value);
                                    }
                                    if (stringIsName) {
                                        stringIsName = false;
                                        if (debug) {
                                            System.out.println("name: " + value);
                                        }
                                        name = value;
                                    } else {
                                        if (debug) {
                                            System.out.println("value: " + name + "=" + value);
                                        }
                                        this.value(name, value, stringIsUnquoted);
                                        name = null;
                                    }
                                    if (this.stop) {
                                        return;
                                    }
                                    stringIsUnquoted = false;
                                    s = p;
                                    break;
                                }
                                case 2: {
                                    if (debug) {
                                        System.out.println("startObject: " + name);
                                    }
                                    this.push(name, true);
                                    if (this.stop) {
                                        return;
                                    }
                                    name = null;
                                    if (top == stack.length) {
                                        stack = Arrays.copyOf(stack, stack.length * 2);
                                    }
                                    stack[top++] = cs;
                                    cs = 5;
                                    _goto_targ = 2;
                                    continue block36;
                                }
                                case 3: {
                                    if (debug) {
                                        System.out.println("endObject");
                                    }
                                    this.pop();
                                    if (this.stop) {
                                        return;
                                    }
                                    cs = stack[--top];
                                    _goto_targ = 2;
                                    continue block36;
                                }
                                case 4: {
                                    if (debug) {
                                        System.out.println("startArray: " + name);
                                    }
                                    this.push(name, false);
                                    if (this.stop) {
                                        return;
                                    }
                                    name = null;
                                    if (top == stack.length) {
                                        stack = Arrays.copyOf(stack, stack.length * 2);
                                    }
                                    stack[top++] = cs;
                                    cs = 23;
                                    _goto_targ = 2;
                                    continue block36;
                                }
                                case 5: {
                                    if (debug) {
                                        System.out.println("endArray");
                                    }
                                    this.pop();
                                    if (this.stop) {
                                        return;
                                    }
                                    cs = stack[--top];
                                    _goto_targ = 2;
                                    continue block36;
                                }
                                case 6: {
                                    start = p - 1;
                                    if (data[p++] == '/') {
                                        while (p != eof && data[p] != '\n') {
                                            ++p;
                                        }
                                        --p;
                                    } else {
                                        while (p + 1 < eof && data[p] != '*' || data[p + 1] != '/') {
                                            ++p;
                                        }
                                        ++p;
                                    }
                                    if (!debug) continue block39;
                                    System.out.println("comment " + new String(data, start, p - start));
                                    break;
                                }
                                case 7: {
                                    if (debug) {
                                        System.out.println("unquotedChars");
                                    }
                                    s = p;
                                    needsUnescape = false;
                                    stringIsUnquoted = true;
                                    if (!stringIsName) ** GOTO lbl177
                                    block42: while (true) {
                                        switch (data[p]) {
                                            case '\\': {
                                                needsUnescape = true;
                                                ** GOTO lbl171
                                            }
                                            case '/': {
                                                if (p + 1 == eof) ** GOTO lbl171
                                                c = data[p + 1];
                                                if (c == '/') ** GOTO lbl193
                                                if (c == '*') {
                                                    break block42;
                                                }
                                                ** GOTO lbl171
                                            }
                                            case '\n': 
                                            case '\r': 
                                            case ':': {
                                                break block42;
                                            }
lbl171:
                                            // 4 sources

                                            default: {
                                                if (!debug) continue block42;
                                                System.out.println("unquotedChar (name): '" + data[p] + "'");
                                                if (++p != eof) continue block42;
                                                break block42;
                                            }
                                        }
                                        break;
                                    }
                                    ** GOTO lbl193
lbl177:
                                    // 1 sources

                                    block43: while (true) {
                                        switch (data[p]) {
                                            case '\\': {
                                                needsUnescape = true;
                                                ** GOTO lbl188
                                            }
                                            case '/': {
                                                if (p + 1 != eof && ((c = data[p + 1]) == '/' || c == '*')) {
                                                    break block43;
                                                }
                                                ** GOTO lbl188
                                            }
                                            case '\n': 
                                            case '\r': 
                                            case ',': 
                                            case ']': 
                                            case '}': {
                                                break block43;
                                            }
lbl188:
                                            // 3 sources

                                            default: {
                                                if (!debug) continue block43;
                                                System.out.println("unquotedChar (value): '" + data[p] + "'");
                                                if (++p != eof) continue block43;
                                                break block43;
                                            }
                                        }
                                        break;
                                    }
lbl193:
                                    // 5 sources

                                    --p;
                                    while (Character.isSpace(data[p])) {
                                        --p;
                                    }
                                    break;
                                }
                                case 8: {
                                    if (debug) {
                                        System.out.println("quotedChars");
                                    }
                                    s = ++p;
                                    needsUnescape = false;
                                    block45: while (true) {
                                        switch (data[p]) {
                                            case '\\': {
                                                needsUnescape = true;
                                                ++p;
                                                ** GOTO lbl211
                                            }
                                            case '\"': {
                                                break block45;
                                            }
lbl211:
                                            // 2 sources

                                            default: {
                                                if (!debug) continue block45;
                                                System.out.println("quotedChar: '" + data[p] + "'");
                                                if (++p != eof) continue block45;
                                                break block45;
                                            }
                                        }
                                        break;
                                    }
                                    --p;
                                }
                            }
                        }
                    }
lbl218:
                    // 3 sources

                    case 2: {
                        if (cs == 0) {
                            _goto_targ = 5;
                            continue block36;
                        }
                        if (++p != pe) {
                            _goto_targ = 1;
                            continue block36;
                        }
                    }
                    case 4: {
                        if (p != eof) break block36;
                        __acts = JsonSkimmer._json_eof_actions[cs];
                        __nacts = JsonSkimmer._json_actions[__acts++];
                        while (__nacts-- > 0) {
                            switch (JsonSkimmer._json_actions[__acts++]) {
                                case 1: {
                                    value = new String(data, s, p - s);
                                    if (needsUnescape) {
                                        value = this.unescape(value);
                                    }
                                    if (stringIsName) {
                                        stringIsName = false;
                                        if (debug) {
                                            System.out.println("name: " + value);
                                        }
                                        name = value;
                                    } else {
                                        if (debug) {
                                            System.out.println("value: " + name + "=" + value);
                                        }
                                        this.value(name, value, stringIsUnquoted);
                                        name = null;
                                    }
                                    if (this.stop) {
                                        return;
                                    }
                                    stringIsUnquoted = false;
                                    s = p;
                                }
                            }
                        }
                        break block36;
                    }
                }
                break;
            }
        }
        catch (RuntimeException ex) {
            parseRuntimeEx = ex;
        }
        if (p < pe) {
            lineNumber = 1;
            for (i = 0; i < p; ++i) {
                if (data[i] != '\n') continue;
                ++lineNumber;
            }
            start = Math.max(0, p - 32);
            throw new SerializationException("Error parsing JSON on line " + lineNumber + " near: " + new String(data, start, p - start) + "*ERROR*" + new String(data, p, Math.min(64, pe - p)), parseRuntimeEx);
        }
        if (parseRuntimeEx != null) {
            throw new SerializationException("Error parsing JSON: " + new String(data), parseRuntimeEx);
        }
    }

    private static byte[] init__json_actions_0() {
        return new byte[]{0, 1, 1, 1, 2, 1, 3, 1, 4, 1, 5, 1, 6, 1, 7, 1, 8, 2, 0, 7, 2, 0, 8, 2, 1, 3, 2, 1, 5};
    }

    private static short[] init__json_key_offsets_0() {
        return new short[]{0, 0, 11, 13, 14, 16, 25, 31, 37, 39, 50, 57, 64, 73, 74, 83, 85, 87, 96, 98, 100, 101, 103, 105, 116, 123, 130, 141, 142, 153, 155, 157, 168, 170, 172, 174, 179, 184, 184};
    }

    private static char[] init__json_trans_keys_0() {
        return new char[]{'\r', ' ', '\"', ',', '/', ':', '[', ']', '{', '\t', '\n', '*', '/', '\"', '*', '/', '\r', ' ', '\"', ',', '/', ':', '}', '\t', '\n', '\r', ' ', '/', ':', '\t', '\n', '\r', ' ', '/', ':', '\t', '\n', '*', '/', '\r', ' ', '\"', ',', '/', ':', '[', ']', '{', '\t', '\n', '\t', '\n', '\r', ' ', ',', '/', '}', '\t', '\n', '\r', ' ', ',', '/', '}', '\r', ' ', '\"', ',', '/', ':', '}', '\t', '\n', '\"', '\r', ' ', '\"', ',', '/', ':', '}', '\t', '\n', '*', '/', '*', '/', '\r', ' ', '\"', ',', '/', ':', '}', '\t', '\n', '*', '/', '*', '/', '\"', '*', '/', '*', '/', '\r', ' ', '\"', ',', '/', ':', '[', ']', '{', '\t', '\n', '\t', '\n', '\r', ' ', ',', '/', ']', '\t', '\n', '\r', ' ', ',', '/', ']', '\r', ' ', '\"', ',', '/', ':', '[', ']', '{', '\t', '\n', '\"', '\r', ' ', '\"', ',', '/', ':', '[', ']', '{', '\t', '\n', '*', '/', '*', '/', '\r', ' ', '\"', ',', '/', ':', '[', ']', '{', '\t', '\n', '*', '/', '*', '/', '*', '/', '\r', ' ', '/', '\t', '\n', '\r', ' ', '/', '\t', '\n', '\u0000'};
    }

    private static byte[] init__json_single_lengths_0() {
        return new byte[]{0, 9, 2, 1, 2, 7, 4, 4, 2, 9, 7, 7, 7, 1, 7, 2, 2, 7, 2, 2, 1, 2, 2, 9, 7, 7, 9, 1, 9, 2, 2, 9, 2, 2, 2, 3, 3, 0, 0};
    }

    private static byte[] init__json_range_lengths_0() {
        return new byte[]{0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0};
    }

    private static short[] init__json_index_offsets_0() {
        return new short[]{0, 0, 11, 14, 16, 19, 28, 34, 40, 43, 54, 62, 70, 79, 81, 90, 93, 96, 105, 108, 111, 113, 116, 119, 130, 138, 146, 157, 159, 170, 173, 176, 187, 190, 193, 196, 201, 206, 207};
    }

    private static byte[] init__json_indicies_0() {
        return new byte[]{1, 1, 2, 3, 4, 3, 5, 3, 6, 1, 0, 7, 7, 3, 8, 3, 9, 9, 3, 11, 11, 12, 13, 14, 3, 15, 11, 10, 16, 16, 17, 18, 16, 3, 19, 19, 20, 21, 19, 3, 22, 22, 3, 21, 21, 24, 3, 25, 3, 26, 3, 27, 21, 23, 28, 29, 29, 28, 30, 31, 32, 3, 33, 34, 34, 33, 13, 35, 15, 3, 34, 34, 12, 36, 37, 3, 15, 34, 10, 16, 3, 36, 36, 12, 3, 38, 3, 3, 36, 10, 39, 39, 3, 40, 40, 3, 13, 13, 12, 3, 41, 3, 15, 13, 10, 42, 42, 3, 43, 43, 3, 28, 3, 44, 44, 3, 45, 45, 3, 47, 47, 48, 49, 50, 3, 51, 52, 53, 47, 46, 54, 55, 55, 54, 56, 57, 58, 3, 59, 60, 60, 59, 49, 61, 52, 3, 60, 60, 48, 62, 63, 3, 51, 52, 53, 60, 46, 54, 3, 62, 62, 48, 3, 64, 3, 51, 3, 53, 62, 46, 65, 65, 3, 66, 66, 3, 49, 49, 48, 3, 67, 3, 51, 52, 53, 49, 46, 68, 68, 3, 69, 69, 3, 70, 70, 3, 8, 8, 71, 8, 3, 72, 72, 73, 72, 3, 3, 3, 0};
    }

    private static byte[] init__json_trans_targs_0() {
        return new byte[]{35, 1, 3, 0, 4, 36, 36, 36, 36, 1, 6, 5, 13, 17, 22, 37, 7, 8, 9, 7, 8, 9, 7, 10, 20, 21, 11, 11, 11, 12, 17, 19, 37, 11, 12, 19, 14, 16, 15, 14, 12, 18, 17, 11, 9, 5, 24, 23, 27, 31, 34, 25, 38, 25, 25, 26, 31, 33, 38, 25, 26, 33, 28, 30, 29, 28, 26, 32, 31, 25, 23, 2, 36, 2};
    }

    private static byte[] init__json_trans_actions_0() {
        return new byte[]{13, 0, 15, 0, 0, 7, 3, 11, 1, 11, 17, 0, 20, 0, 0, 5, 1, 1, 1, 0, 0, 0, 11, 13, 15, 0, 7, 3, 1, 1, 1, 1, 23, 0, 0, 0, 0, 0, 0, 11, 11, 0, 11, 11, 11, 11, 13, 0, 15, 0, 0, 7, 9, 3, 1, 1, 1, 1, 26, 0, 0, 0, 0, 0, 0, 11, 11, 0, 11, 11, 11, 1, 0, 0};
    }

    private static byte[] init__json_eof_actions_0() {
        return new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0};
    }

    public void stop() {
        this.stop = true;
    }

    public boolean isStopped() {
        return this.stop;
    }

    protected String unescape(String value) {
        int length = value.length();
        StringBuilder buffer = new StringBuilder(length + 16);
        int i = 0;
        while (i < length) {
            char c;
            if ((c = value.charAt(i++)) != '\\') {
                buffer.append(c);
                continue;
            }
            if (i == length) break;
            if ((c = value.charAt(i++)) == 'u') {
                buffer.append(Character.toChars(Integer.parseInt(value.substring(i, i + 4), 16)));
                i += 4;
                continue;
            }
            switch (c) {
                case '\"': 
                case '/': 
                case '\\': {
                    break;
                }
                case 'b': {
                    c = '\b';
                    break;
                }
                case 'f': {
                    c = '\f';
                    break;
                }
                case 'n': {
                    c = '\n';
                    break;
                }
                case 'r': {
                    c = '\r';
                    break;
                }
                case 't': {
                    c = '\t';
                    break;
                }
                default: {
                    throw new SerializationException("Illegal escaped character: \\" + c);
                }
            }
            buffer.append(c);
        }
        return buffer.toString();
    }

    protected void push(@Null String name, boolean object) {
    }

    protected void pop() {
    }

    protected void value(@Null String name, String value, boolean unquoted) {
    }
}

