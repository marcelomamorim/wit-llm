/*
 * @test
 * @summary Verify NamedParameterSpec(String) rejects null and stores the given standard name
 * @run main NamedParameterSpecWitupTest
 */

import java.security.spec.NamedParameterSpec;

public class NamedParameterSpecWitupTest {

    public static void main(String[] args) {
        testNullStdNameThrowsNPE();
        testNameIsReturned();
        testEmptyNameIsKept();
        System.out.println("PASSED");
    }

    static void testNullStdNameThrowsNPE() {
        try {
            new NamedParameterSpec(null);
        } catch (NullPointerException e) {
            return;
        }
        throw new RuntimeException("Expected NullPointerException for null stdName");
    }

    static void testNameIsReturned() {
        NamedParameterSpec spec = new NamedParameterSpec("X25519");
        if (!"X25519".equals(spec.getName())) {
            throw new RuntimeException("Unexpected name: " + spec.getName());
        }
    }

    static void testEmptyNameIsKept() {
        NamedParameterSpec spec = new NamedParameterSpec("");
        if (!"".equals(spec.getName())) {
            throw new RuntimeException("Empty name not preserved: " + spec.getName());
        }
    }
}
