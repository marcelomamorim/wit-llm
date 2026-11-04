/*
 * @test
 * @summary Witup-generated checks for PBEKeySpec.getPassword() returned copies and empty-password behavior
 * @run main PBEKeySpecWitupTest
 */
import javax.crypto.spec.PBEKeySpec;
import java.util.Arrays;

public class PBEKeySpecWitupTest {

    public static void main(String[] args) {
        testNullPasswordReturnsEmptyArray();
        testGetPasswordReturnsEqualContent();
        testGetPasswordReturnsIndependentCopy();
        testConstructorClonesInputPassword();
    }

    private static void testNullPasswordReturnsEmptyArray() {
        PBEKeySpec spec = new PBEKeySpec(null);
        char[] pwd = spec.getPassword();
        if (pwd == null) {
            throw new RuntimeException("getPassword returned null for null input");
        }
        if (pwd.length != 0) {
            throw new RuntimeException("expected empty password, got length " + pwd.length);
        }
    }

    private static void testGetPasswordReturnsEqualContent() {
        char[] input = {'a', 'b', 'c', '1', '2'};
        PBEKeySpec spec = new PBEKeySpec(input);
        char[] pwd = spec.getPassword();
        if (!Arrays.equals(input, pwd)) {
            throw new RuntimeException("getPassword content mismatch");
        }
    }

    private static void testGetPasswordReturnsIndependentCopy() {
        char[] input = {'x', 'y', 'z'};
        PBEKeySpec spec = new PBEKeySpec(input);
        char[] first = spec.getPassword();
        if (first == spec.getPassword()) {
            throw new RuntimeException("getPassword returned the same array instance");
        }
        first[0] = 'Q';
        char[] second = spec.getPassword();
        if (second[0] != 'x') {
            throw new RuntimeException("mutating returned array changed internal password");
        }
    }

    private static void testConstructorClonesInputPassword() {
        char[] input = {'p', 'w'};
        PBEKeySpec spec = new PBEKeySpec(input);
        input[0] = 'Z';
        char[] pwd = spec.getPassword();
        if (pwd[0] != 'p') {
            throw new RuntimeException("constructor did not clone input password");
        }
    }
}
