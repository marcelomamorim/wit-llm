/*
 * @test
 * @summary Testa o método requireModuleName para verificar comportamento com entradas nulas e inválidas.
 * @run main ChecksWitupTest
 */

import java.util.Set;

public class ChecksWitupTest {

    public static void main(String[] args) {
        testRequireModuleName_Null();
        testRequireModuleName_Invalid();
        testRequireModuleName_Valid();
    }

    static void testRequireModuleName_Null() {
        try {
            jdk.internal.module.Checks.requireModuleName(null);
            System.err.println("FAIL: Expected IllegalArgumentException for null input");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected exception for null input");
        }
    }

    static void testRequireModuleName_Invalid() {
        String invalidName = "com..example";
        try {
            jdk.internal.module.Checks.requireModuleName(invalidName);
            System.err.println("FAIL: Expected IllegalArgumentException for invalid module name");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected exception for invalid module name");
        }
        String invalidName2 = "com.example$";
        try {
            jdk.internal.module.Checks.requireModuleName(invalidName2);
            System.err.println("FAIL: Expected IllegalArgumentException for invalid module name");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected exception for invalid module name");
        }
    }

    static void testRequireModuleName_Valid() {
        String validName = "com.example.module";
        String result = jdk.internal.module.Checks.requireModuleName(validName);
        if (result.equals(validName)) {
            System.out.println("PASS: Valid module name returned correctly");
        } else {
            System.err.println("FAIL: Valid module name did not return correctly");
        }
    }
}
