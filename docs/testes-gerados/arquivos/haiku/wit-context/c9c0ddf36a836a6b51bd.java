/*
 * @test
 * @summary Witup-derived checks for ObjectStreamField constructors: null name must throw NullPointerException, null type is accepted
 * @run main ObjectStreamFieldWitupTest
 */

import java.io.ObjectStreamField;

public class ObjectStreamFieldWitupTest {

    public static void main(String[] args) {
        testNullNameTwoArgConstructor();
        testNullNameThreeArgConstructor();
        testNullNameThreeArgConstructorUnsharedTrue();
        testNullTypeIsAccepted();
    }

    static void testNullNameTwoArgConstructor() {
        try {
            new ObjectStreamField(null, String.class);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null name (2-arg)");
    }

    static void testNullNameThreeArgConstructor() {
        try {
            new ObjectStreamField(null, String.class, false);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null name (3-arg, unshared=false)");
    }

    static void testNullNameThreeArgConstructorUnsharedTrue() {
        try {
            new ObjectStreamField(null, Integer.TYPE, true);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null name (3-arg, unshared=true)");
    }

    static void testNullTypeIsAccepted() {
        ObjectStreamField f = new ObjectStreamField("f", null);
        if (f == null) {
            throw new RuntimeException("constructor returned null reference");
        }
    }
}
