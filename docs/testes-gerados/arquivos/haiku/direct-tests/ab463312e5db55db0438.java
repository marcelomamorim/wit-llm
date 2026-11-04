/*
 * @test
 * @summary Field.getBoolean on char fields must throw IllegalArgumentException; char reads via getChar and widening via getInt must still work
 * @run main UnsafeQualifiedCharacterFieldAccessorImplWitupTest
 */

import java.lang.reflect.Field;

public class UnsafeQualifiedCharacterFieldAccessorImplWitupTest {
    public volatile char vc;
    public final char fc;

    public UnsafeQualifiedCharacterFieldAccessorImplWitupTest() {
        vc = 'x';
        fc = 'y';
    }

    public static void main(String[] args) throws Exception {
        UnsafeQualifiedCharacterFieldAccessorImplWitupTest o =
            new UnsafeQualifiedCharacterFieldAccessorImplWitupTest();

        checkGetBooleanThrows(o, "vc");
        checkGetBooleanThrows(o, "fc");

        Field v = o.getClass().getDeclaredField("vc");
        if (v.getChar(o) != 'x') {
            throw new RuntimeException("getChar returned wrong value");
        }
        if (v.getInt(o) != (int) 'x') {
            throw new RuntimeException("getInt returned wrong value");
        }

        Field f = o.getClass().getDeclaredField("fc");
        if (f.getChar(o) != 'y') {
            throw new RuntimeException("final getChar returned wrong value");
        }
    }

    static void checkGetBooleanThrows(Object o, String name) throws Exception {
        Field f = o.getClass().getDeclaredField(name);
        try {
            f.getBoolean(o);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("getBoolean did not throw IllegalArgumentException for " + name);
    }
}
