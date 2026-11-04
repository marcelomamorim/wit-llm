/*
 * @test
 * @summary Verify Scanner.hasNext(String) pattern matching, non-consumption of the token, and IllegalStateException after close()
 * @run main ScannerWitupTest
 */
import java.util.Scanner;

public class ScannerWitupTest {
    public static void main(String[] args) {
        Scanner s = new Scanner("abc 123");
        if (!s.hasNext("[a-z]+")) {
            throw new RuntimeException("hasNext should match alphabetic token");
        }
        if (s.hasNext("[0-9]+")) {
            throw new RuntimeException("hasNext should not match numeric pattern against alphabetic token");
        }
        if (!s.hasNext("[a-z]+")) {
            throw new RuntimeException("hasNext must not consume the token");
        }
        if (!"abc".equals(s.next())) {
            throw new RuntimeException("next should return abc");
        }
        if (!s.hasNext("[0-9]+")) {
            throw new RuntimeException("hasNext should match numeric token after advancing");
        }
        s.close();
        boolean thrown = false;
        try {
            s.hasNext("[0-9]+");
        } catch (IllegalStateException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("hasNext on closed Scanner should throw IllegalStateException");
        }
    }
}
