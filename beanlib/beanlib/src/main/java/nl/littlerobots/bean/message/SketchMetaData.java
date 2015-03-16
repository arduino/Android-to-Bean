/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Little Robots
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package nl.littlerobots.bean.message;

import android.os.Parcelable;

import java.nio.charset.Charset;
import java.util.Date;

import auto.parcel.AutoParcel;
import okio.Buffer;

@AutoParcel
public abstract class SketchMetaData implements Parcelable {
    public static SketchMetaData fromPayload(Buffer buffer) {
        int size = buffer.readIntLe();
        int crc = buffer.readIntLe();
        long timestamp = (buffer.readIntLe() & 0xffffffffL) * 1000L;
        int nameSize = buffer.readByte() & 0xff;
        String name = "";
        if (nameSize > 0 && nameSize <= 20) {
            name = buffer.readString(nameSize, Charset.forName("UTF-8"));
        }
        return new AutoParcel_SketchMetaData(size, crc, new Date(timestamp), name);
    }

    public abstract int size();

    public abstract int crc();

    public abstract Date timestamp();

    public abstract String name();
}
