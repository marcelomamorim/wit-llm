/*
 * @test
 * @summary Witup-generated checks for Scanner.useRadix radix validation and observable radix effect
 * @run main ScannerWitupTest
 */
import java.io.StringReader;
import java.util.Scanner;

public class ScannerWitupTest {
    public static void main(String[] args) {
        checkRejected(Character.MIN_RADIX - 1);
        checkRejected(Character.MAX_RADIX + 1);
        checkRejected(-1);

        Scanner s = new Scanner(new StringReader("x"));
        if (s.useRadix(Character.MIN_RADIX) != s) {
            throw new AssertionError("useRadix(MIN_RADIX) must return this scanner");
        }
        if (s.useRadix(Character.MAX_RADIX) != s) {
            throw new AssertionError("useRadix(MAX_RADIX) must return this scanner");
        }

        Scanner hex = new Scanner(new StringReader("7f"));
        if (hex.useRadix(16) != hex) {
            throw new AssertionError("useRadix(16) must return this scanner");
        }
        int value = hex.nextInt();
        if (value != 127) {
            throw new AssertionError("expected 127 but was " + value);
        }
    }

    static void checkRejected(int radix) {
        try {
            new Scanner(new StringReader("1")).useRadix(radix);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new AssertionError("expected IllegalArgumentException for radix " + radix);
    }
}
