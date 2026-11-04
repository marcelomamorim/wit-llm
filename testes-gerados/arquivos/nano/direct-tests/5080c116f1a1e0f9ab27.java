/* @test    @summary Test constructor of TimestampToken with valid and null input    @run main TimestampTokenWitupTest */

import java.io.IOException;
import sun.security.timestamp.TimestampToken;

public class TimestampTokenWitupTest {

    public static void main(String[] args) {
        testConstructorWithValidInput();
        testConstructorWithNullInput();
    }

    private static void testConstructorWithValidInput() {
        byte[] dummyData = new byte[] {0x30, 0x0A, 0x02, 0x01, 0x01, 0x02, 0x01, 0x02, 0x02, 0x01, 0x01};
        try {
            TimestampToken token = new TimestampToken(dummyData);
            if (token == null) {
                throw new AssertionError("Constructor returned null with valid input");
            }
        } catch (IOException e) {
            throw new AssertionError("Unexpected IOException with valid input: " + e.getMessage());
        }
    }

    private static void testConstructorWithNullInput() {
        try {
            new TimestampToken(null);
            throw new AssertionError("Expected IOException not thrown for null input");
        } catch (IOException e) {
            if (!"No timestamp token info".equals(e.getMessage())) {
                throw new AssertionError("Unexpected message for null input: " + e.getMessage());
            }
        }
    }
}
