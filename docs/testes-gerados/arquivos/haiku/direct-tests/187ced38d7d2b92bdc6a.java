/*
 * @test
 * @summary Verifies argument validation of Unsafe.defineAnonymousClass for null, array and primitive host classes and null data
 * @modules java.base/jdk.internal.misc
 * @run main UnsafeWitupTest
 */

import jdk.internal.misc.Unsafe;

public class UnsafeWitupTest {

    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();
        byte[] data = new byte[1];
        Object[] patches = null;

        boolean npeNullHost = false;
        try {
            unsafe.defineAnonymousClass(null, data, patches);
        } catch (NullPointerException e) {
            npeNullHost = true;
        }
        if (!npeNullHost) {
            throw new RuntimeException("expected NullPointerException for null hostClass");
        }

        boolean npeNullData = false;
        try {
            unsafe.defineAnonymousClass(UnsafeWitupTest.class, null, patches);
        } catch (NullPointerException e) {
            npeNullData = true;
        }
        if (!npeNullData) {
            throw new RuntimeException("expected NullPointerException for null data");
        }

        boolean npeBoth = false;
        try {
            unsafe.defineAnonymousClass(null, null, patches);
        } catch (NullPointerException e) {
            npeBoth = true;
        }
        if (!npeBoth) {
            throw new RuntimeException("expected NullPointerException when hostClass and data are null");
        }

        boolean iaeArray = false;
        try {
            unsafe.defineAnonymousClass(String[].class, data, patches);
        } catch (IllegalArgumentException e) {
            iaeArray = true;
        }
        if (!iaeArray) {
            throw new RuntimeException("expected IllegalArgumentException for array hostClass");
        }

        boolean iaePrimitive = false;
        try {
            unsafe.defineAnonymousClass(int.class, data, patches);
        } catch (IllegalArgumentException e) {
            iaePrimitive = true;
        }
        if (!iaePrimitive) {
            throw new RuntimeException("expected IllegalArgumentException for primitive hostClass");
        }
    }
}
