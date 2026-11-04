/*
 * @test
 * @summary Test for useProtocolVersion method in ObjectOutputStream
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) {
        testUseProtocolVersionWithNonEmptyStream();
        testUseProtocolVersionWithValidVersions();
        testUseProtocolVersionWithInvalidVersion();
    }

    private static void testUseProtocolVersionWithNonEmptyStream() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeInt(42); // write something to make stream non-empty
            // Expecting IllegalStateException due to non-empty stream
            oos.useProtocolVersion(1);
            System.out.println("FAIL: Expected IllegalStateException not thrown");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Caught expected IllegalStateException");
        } catch (IOException e) {
            System.out.println("FAIL: Unexpected IOException: " + e);
        }
    }

    private static void testUseProtocolVersionWithValidVersions() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            // Reset stream to empty state
            // Use valid protocol versions
            oos.useProtocolVersion(1);
            oos.useProtocolVersion(2);
            System.out.println("PASS: Valid protocol versions accepted without exception");
        } catch (IOException e) {
            System.out.println("FAIL: IOException during valid version test: " + e);
        }
    }

    private static void testUseProtocolVersionWithInvalidVersion() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.useProtocolVersion(999); // invalid version
            System.out.println("FAIL: Expected IllegalArgumentException not thrown");
        } catch (IllegalArgumentException e) {
            System.out.println("PASS: Caught expected IllegalArgumentException");
        } catch (IOException e) {
            System.out.println("FAIL: Unexpected IOException: " + e);
        }
    }
}
