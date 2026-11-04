/*
 * @test
 * @summary Witup-generated checks for ObjectStreamField(String, Class, boolean) constructor behavior
 * @run main ObjectStreamFieldWitupTest
 */

import java.io.ObjectStreamField;

public class ObjectStreamFieldWitupTest {

    public static void main(String[] args) {
        testNullNameThrowsNPE();
        testNullNameThrowsNPEWhenUnshared();
        testValidConstructionKeepsName();
        testValidConstructionKeepsType();
        testUnsharedFlagTrue();
        testUnsharedFlagFalse();
        testTwoArgConstructorDefaultsToShared();
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullNameThrowsNPE() {
        try {
            new ObjectStreamField(null, String.class, false);
            throw new RuntimeException("expected NullPointerException for null name");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testNullNameThrowsNPEWhenUnshared() {
        try {
            new ObjectStreamField(null, Integer.TYPE, true);
            throw new RuntimeException("expected NullPointerException for null name (unshared)");
        } catch (NullPointerException expected) {
            // expected
        }
    }

    static void testValidConstructionKeepsName() {
        ObjectStreamField f = new ObjectStreamField("count", Integer.TYPE, false);
        check("count".equals(f.getName()), "name not preserved");
    }

    static void testValidConstructionKeepsType() {
        ObjectStreamField f = new ObjectStreamField("text", String.class, false);
        check(f.getType() == String.class, "type not preserved");
    }

    static void testUnsharedFlagTrue() {
        ObjectStreamField f = new ObjectStreamField("data", Object.class, true);
        check(f.isUnshared(), "unshared flag should be true");
    }

    static void testUnsharedFlagFalse() {
        ObjectStreamField f = new ObjectStreamField("data", Object.class, false);
        check(!f.isUnshared(), "unshared flag should be false");
    }

    static void testTwoArgConstructorDefaultsToShared() {
        ObjectStreamField f = new ObjectStreamField("value", Long.TYPE);
        check(!f.isUnshared(), "two-argument constructor should default to shared");
        check("value".equals(f.getName()), "name not preserved in two-argument constructor");
    }
}
