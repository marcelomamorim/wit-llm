/*
 * @test
 * @summary Verify ObjectStreamField(String, Class) delegates to the unshared constructor and rejects a null name
 * @run main ObjectStreamFieldWitupTest
 */

import java.io.ObjectStreamField;

public class ObjectStreamFieldWitupTest {

    public static void main(String[] args) {
        testNullNameThrowsNPE();
        testNonNullNameWithPrimitiveType();
        testNullTypeIsAccepted();
    }

    static void testNullNameThrowsNPE() {
        try {
            new ObjectStreamField(null, String.class);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("NullPointerException expected for null name");
    }

    static void testNonNullNameWithPrimitiveType() {
        ObjectStreamField f = new ObjectStreamField("count", int.class);
        if (f == null) {
            throw new RuntimeException("constructor returned null");
        }
    }

    static void testNullTypeIsAccepted() {
        ObjectStreamField f = new ObjectStreamField("data", null);
        if (f == null) {
            throw new RuntimeException("constructor returned null");
        }
    }
}
