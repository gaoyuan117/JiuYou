/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014. Ray
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.jiuyou.network.response.OtherResponse;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public enum Gender {

    FEMALE(0) {
        @Override
        public String toString() {
            return "女";
        }
    },
    MALE(1) {
        @Override
        public String toString() {
            return "男";
        }
    };

    private int mGender;

    Gender(int gender) {
        mGender = gender;
    }

    int getGender() {
        return mGender;
    }

    public static class GenderAdapter implements JsonDeserializer<Gender> {

        @Override
        public Gender deserialize(JsonElement json, Type typeOfT,
                                  JsonDeserializationContext context)
                throws JsonParseException {
            final String gender = json.getAsString();

            try {
                final int genderInt = Integer.parseInt(gender);
                switch (genderInt) {
                    case 0:
                        return Gender.FEMALE;
                    case 1:
                        return Gender.MALE;
                    default:
                        return Gender.FEMALE;
                }
            } catch (NumberFormatException e) {
                if (Gender.MALE.name().equals(gender)) {
                    return Gender.MALE;
                } else {
                    return Gender.FEMALE;
                }
            }
        }
    }

}
