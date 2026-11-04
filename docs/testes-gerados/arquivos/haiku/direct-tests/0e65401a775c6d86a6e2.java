/*
 * @test
 * @summary Tests NamedParameterSpec constructor and getName behavior
 * @run main NamedParameterSpecWitupTest
 */
import java.security.spec.NamedParameterSpec;

public class NamedParameterSpecWitupTest {
    public static void main(String[] args) {
        testGetNameReturnsStdName();
        testCustomNameIsPreserved();
        testEmptyNameIsAccepted();
        testNullThrowsNPE();
        testConstantNames();
    }

    static void check(boolean cond, String msg) {
        if (!cond) {
            throw new RuntimeException(msg);
        }
    }

    static void testGetNameReturnsStdName() {
        NamedParameterSpec spec = new NamedParameterSpec("X25519");
        check("X25519".equals(spec.getName()), "getName should return X25519");
    }

    static void testCustomNameIsPreserved() {
        NamedParameterSpec spec = new NamedParameterSpec("CustomParams");
        check("CustomParams".equals(spec.getName()), "custom name should be preserved");
    }

    static void testEmptyNameIsAccepted() {
        NamedParameterSpec spec = new NamedParameterSpec("");
        check("".equals(spec.getName()), "empty name should be accepted and returned");
    }

    static void testNullThrowsNPE() {
        boolean thrown = false;
        try {
            new NamedParameterSpec(null);
        } catch (NullPointerException e) {
            thrown = true;
        }
        check(thrown, "null stdName should throw NullPointerException");
    }

    static void testConstantNames() {
        check("X25519".equals(NamedParameterSpec.X25519.getName()),
              "X25519 constant should have name X25519");
        check("X448".equals(NamedParameterSpec.X448.getName()),
              "X448 constant should have name X448");
    }
}
