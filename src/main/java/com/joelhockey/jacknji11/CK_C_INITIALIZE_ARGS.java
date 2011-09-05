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

import com.sun.jna.Callback;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.ptr.PointerByReference;

/**
 * JNA wrapper for PKCS#11 CK_C_INITIALIZE_ARGS struct. Also includes JNA mutex interface wrappers.
 * @author Joel Hockey (joel.hockey@gmail.com)
 */
public class CK_C_INITIALIZE_ARGS extends Structure {

    /**
     * True if application threads which are executing calls to the library may not use native operating system calls to
     * spawn new threads; false if they may.
     */
    public static final int CKF_LIBRARY_CANT_CREATE_OS_THREADS = 0x00000001;
    /** True if the library can use the native operation system threading model for locking; false otherwise. */
    public static final int CKF_OS_LOCKING_OK = 0x00000002;

    /** Maps from int value to String description (variable name). */
    private static final Map<Integer, String> I2S = C.createI2SMap(CK_C_INITIALIZE_ARGS.class);
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


    public CK_CREATEMUTEX createMutex;
    public CK_DESTROYMUTEX destroyMutex;
    public CK_LOCKMUTEX lockMutex;
    public CK_UNLOCKMUTEX unlockMutex;
    public NativeLong flags;
    public Pointer pReserved;

    /**
     * Initialise struct with supplied values.
     * @param createMutex create mutex
     * @param destroyMutex destroy mutex
     * @param lockMutex lock mutex
     * @param unlockMutex unlock mutex
     * @param flags locking flags constant from CKF
     */
    public CK_C_INITIALIZE_ARGS(CK_CREATEMUTEX createMutex, CK_DESTROYMUTEX destroyMutex, CK_LOCKMUTEX lockMutex,
            CK_UNLOCKMUTEX unlockMutex, int flags) {

        this.createMutex = createMutex;
        this.destroyMutex = destroyMutex;
        this.lockMutex = lockMutex;
        this.unlockMutex = unlockMutex;
        this.flags = new NativeLong(flags);
    }

    /** @return string */
    public String toString() {
        return String.format("create=%s destroy=%s lock=%s unlock=%s flags=0x%08x{%s}",
                createMutex, destroyMutex, lockMutex, unlockMutex, flags.intValue(), f2s(flags.intValue()));
    }

    /**
     * JNA wrapper for PKCS#11 CK_CREATEMUTEX.
     * @author Joel Hockey
     */
    public interface CK_CREATEMUTEX extends Callback {
        /**
         * Create Mutex.
         * @param mutex mutex
         * @return {@link CKR} return code
         */
        NativeLong invoke(PointerByReference mutex);
    }

    /**
     * JNA wrapper for PKCS#11 CK_DESTROYMUTEX.
     * @author Joel Hockey
     */
    public interface CK_DESTROYMUTEX extends Callback {
        /**
         * Destroy Mutex.
         * @param mutex mutex
         * @return {@link CKR} return code
         */
        NativeLong invoke(Pointer mutex);
    }

    /**
     * JNA wrapper for PKCS#11 CK_LOCKMUTEX.
     * @author Joel Hockey
     */
    public interface CK_LOCKMUTEX extends Callback {
        /**
         * Lock Mutex.
         * @param mutex mutex
         * @return {@link CKR} return code
         */
        NativeLong invoke(Pointer mutex);
    }

    /**
     * JNA wrapper for PKCS#11 CK_UNLOCKMUTEX.
     * @author Joel Hockey
     */
    public interface CK_UNLOCKMUTEX extends Callback {
        /**
         * Unlock Mutex.
         * @param mutex mutex
         * @return {@link CKR} return code
         */
        NativeLong invoke(Pointer mutex);
    }
}
