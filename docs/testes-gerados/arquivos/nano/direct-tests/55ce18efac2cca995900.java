/* @test    @summary Test constructor PBEKeySpec(char[] password) with various inputs    @run main PBEKeySpecWitupTest */

import javax.crypto.spec.PBEKeySpec;
import java.util.Arrays;

public class PBEKeySpecWitupTest {

    public static void main(String[] args) {
        testNullPassword();
        testEmptyPassword();
        testRegularPassword();
    }

    static void testNullPassword() {
        PBEKeySpec spec = new PBEKeySpec(null);
        assert spec != null : "Spec should not be null";
        assert specPasswordEquals(new char[0], spec) : "Password should be empty array";
    }

    static void testEmptyPassword() {
        char[] empty = new char[0];
        PBEKeySpec spec = new PBEKeySpec(empty);
        assert spec != null : "Spec should not be null";
        assert specPasswordEquals(empty, spec) : "Password should match input empty array";
        // Ensure internal array is a clone
        empty[0] = 'X';
        PBEKeySpec spec2 = new PBEKeySpec(empty);
        assert specPasswordEquals(empty, spec2) : "Password should match input after modification";
        // Original array should not affect spec's internal password
        assert !Arrays.equals(spec2.getPassword(), empty) : "Internal password should be a clone";
    }

    static void testRegularPassword() {
        char[] password = {'a', 'b', 'c'};
        PBEKeySpec spec = new PBEKeySpec(password);
        assert spec != null : "Spec should not be null";
        assert specPasswordEquals(password, spec) : "Password should match input";
        // Modify original array to check for cloning
        password[0] = 'z';
        assert specPasswordEquals(new char[]{'a', 'b', 'c'}, spec) : "Internal password should not change when original array is modified";
    }

    static boolean specPasswordEquals(char[] expected, PBEKeySpec spec) {
        char[] actual = spec.getPassword();
        return Arrays.equals(expected, actual);
    }
}
