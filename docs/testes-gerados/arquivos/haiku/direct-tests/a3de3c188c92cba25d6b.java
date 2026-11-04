/*
 * @test
 * @summary Verifies ConstantBootstraps.primitiveClass for valid primitive codes and argument validation failures
 * @run main ConstantBootstrapsWitupTest
 */

import java.lang.invoke.ConstantBootstraps;
import java.lang.invoke.MethodHandles;

public class ConstantBootstrapsWitupTest {

    public static void main(String[] args) throws Exception {
        MethodHandles.Lookup lookup = MethodHandles.lookup();

        check(ConstantBootstraps.primitiveClass(lookup, "I", Class.class) == int.class, "I -> int");
        check(ConstantBootstraps.primitiveClass(lookup, "J", Class.class) == long.class, "J -> long");
        check(ConstantBootstraps.primitiveClass(lookup, "Z", Class.class) == boolean.class, "Z -> boolean");
        check(ConstantBootstraps.primitiveClass(lookup, "B", Class.class) == byte.class, "B -> byte");
        check(ConstantBootstraps.primitiveClass(lookup, "S", Class.class) == short.class, "S -> short");
        check(ConstantBootstraps.primitiveClass(lookup, "C", Class.class) == char.class, "C -> char");
        check(ConstantBootstraps.primitiveClass(lookup, "F", Class.class) == float.class, "F -> float");
        check(ConstantBootstraps.primitiveClass(lookup, "D", Class.class) == double.class, "D -> double");
        check(ConstantBootstraps.primitiveClass(lookup, "V", Class.class) == void.class, "V -> void");

        expectIAE(lookup, "", Class.class);
        expectIAE(lookup, "II", Class.class);
        expectIAE(lookup, "I", String.class);
        expectNPE(lookup, null, Class.class);
        expectNPE(lookup, "I", null);
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Failed: " + message);
        }
    }

    static void expectIAE(MethodHandles.Lookup lookup, String name, Class<?> type) {
        try {
            ConstantBootstraps.primitiveClass(lookup, name, type);
        } catch (IllegalArgumentException expected) {
            return;
        }
        throw new RuntimeException("IllegalArgumentException expected for name=\"" + name + "\"");
    }

    static void expectNPE(MethodHandles.Lookup lookup, String name, Class<?> type) {
        try {
            ConstantBootstraps.primitiveClass(lookup, name, type);
        } catch (NullPointerException expected) {
            return;
        }
        throw new RuntimeException("NullPointerException expected");
    }
}
