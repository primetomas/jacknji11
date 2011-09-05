/*
 * Copyright 2010-2011 Joel Hockey (joel.hockey@gmail.com). All rights reserved.
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

package com.joelhockey.jacknji11;

import java.util.Map;

import com.sun.jna.NativeLong;
import com.sun.jna.Structure;

/**
 * JNA wrapper for PKCS#11 CK_INFO struct.  It sets align type to {@link Structure#ALIGN_NONE}
 * since the ULONGS (NativeLongs) dont line up on a 4 byte boundary.  You wouldn't care to know
 * how painful that learning experience was.
 * @author Joel Hockey (joel.hockey@gmail.com)
 */
public class CK_INFO extends Structure {

    /** Maps from int value to String description (variable name). */
    private static final Map<Integer, String> I2S = C.createI2SMap(CK_INFO.class);
    /**
     * Convert int constant value to name.
     * @param ckf value
     * @return name
     */
    public static final String I2S(int ckf) { return C.i2s(I2S, "CKF", ckf); }
    /**
     * Convert flags to string.
     * @param flags flags
     * @return string format
     */
    public static String f2s(int flags) { return C.f2s(I2S, flags); }

    public CK_VERSION cryptokiVersion;
    public byte[] manufacturerID = new byte[32];
    public NativeLong flags;
    public byte[] libraryDescription = new byte[32];
    public CK_VERSION libraryVersion;

    /**
     * Default constructor.
     * need to set alignment to none since 'flags' is not
     * correctly aligned to a 4 byte boundary
     */
    public CK_INFO() {
        setAlignType(ALIGN_NONE);
    }

    /** @return string */
    public String toString() {
        return String.format("(\n  version=%d.%d\n  manufacturerID=%s\n  flags=0x%08x{%s}\n  libraryDescription=%s\n  libraryVersion=%d.%d\n)",
                cryptokiVersion.major & 0xff, cryptokiVersion.minor & 0xff, Buf.escstr(manufacturerID),
                flags.intValue(), f2s(flags.intValue()), Buf.escstr(libraryDescription),
                libraryVersion.major & 0xff, libraryVersion.minor & 0xff);

    }
}
