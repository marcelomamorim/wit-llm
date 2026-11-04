/*
 * @test
 * @summary Verifies ObjectStreamField constructor null-name handling and stored values
 * @run main ObjectStreamFieldWitupTest
 */

import java.io.ObjectStreamField;

public class ObjectStreamFieldWitupTest {

    public static void main(String[] args) {
        testNullNameThrowsNPEThreeArg();
        testNullNameThrowsNPETwoArg();
        testThreeArgConstructorStoresValues();
        testTwoArgConstructorDefaultsUnsharedFalse();
        System.out.println("PASS");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    static void testNullNameThrowsNPEThreeArg() {
        try {
            new ObjectStreamField(null, int.class, false);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null name (3-arg)");
    }

    static void testNullNameThrowsNPETwoArg() {
        try {
            new ObjectStreamField(null, String.class);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("expected NullPointerException for null name (2-arg)");
    }

    static void testThreeArgConstructorStoresValues() {
        ObjectStreamField f = new ObjectStreamField("count", int.class, true);
        check("count".equals(f.getName()), "name mismatch");
        check(f.getType() == int.class, "type mismatch");
        check(f.isUnshared(), "unshared should be true");

        ObjectStreamField g = new ObjectStreamField("items", String.class, false);
        check("items".equals(g.getName()), "name mismatch (false)");
        check(g.getType() == String.class, "type mismatch (false)");
        check(!g.isUnshared(), "unshared should be false");
    }

    static void testTwoArgConstructorDefaultsUnsharedFalse() {
        ObjectStreamField f = new ObjectStreamField("name", String.class);
        check("name".equals(f.getName()), "name mismatch (2-arg)");
        check(f.getType() == String.class, "type mismatch (2-arg)");
        check(!f.isUnshared(), "2-arg constructor should default unshared to false");
    }
}
