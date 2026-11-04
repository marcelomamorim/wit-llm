/*
 * @test
 * @summary Verifies SSLParameters.setMaximumPacketSize rejects negative values and stores non-negative values
 * @run main SSLParametersWitupTest
 */

import javax.net.ssl.SSLParameters;

public class SSLParametersWitupTest {

    public static void main(String[] args) {
        testDefaultIsZero();
        testAcceptsZero();
        testAcceptsPositiveValue();
        testAcceptsMaxIntValue();
        testRejectsNegativeValue();
        testRejectedValueDoesNotChangeState();
    }

    private static void testDefaultIsZero() {
        SSLParameters p = new SSLParameters();
        if (p.getMaximumPacketSize() != 0) {
            throw new RuntimeException("Expected default maximum packet size 0, got "
                    + p.getMaximumPacketSize());
        }
    }

    private static void testAcceptsZero() {
        SSLParameters p = new SSLParameters();
        p.setMaximumPacketSize(0);
        if (p.getMaximumPacketSize() != 0) {
            throw new RuntimeException("Expected 0, got " + p.getMaximumPacketSize());
        }
    }

    private static void testAcceptsPositiveValue() {
        SSLParameters p = new SSLParameters();
        p.setMaximumPacketSize(16384);
        if (p.getMaximumPacketSize() != 16384) {
            throw new RuntimeException("Expected 16384, got " + p.getMaximumPacketSize());
        }
    }

    private static void testAcceptsMaxIntValue() {
        SSLParameters p = new SSLParameters();
        p.setMaximumPacketSize(Integer.MAX_VALUE);
        if (p.getMaximumPacketSize() != Integer.MAX_VALUE) {
            throw new RuntimeException("Expected Integer.MAX_VALUE, got "
                    + p.getMaximumPacketSize());
        }
    }

    private static void testRejectsNegativeValue() {
        SSLParameters p = new SSLParameters();
        try {
            p.setMaximumPacketSize(-1);
        } catch (IllegalArgumentException e) {
            return;
        }
        throw new RuntimeException("Expected IllegalArgumentException for -1");
    }

    private static void testRejectedValueDoesNotChangeState() {
        SSLParameters p = new SSLParameters();
        p.setMaximumPacketSize(8192);
        try {
            p.setMaximumPacketSize(Integer.MIN_VALUE);
            throw new RuntimeException("Expected IllegalArgumentException for Integer.MIN_VALUE");
        } catch (IllegalArgumentException e) {
            // expected
        }
        if (p.getMaximumPacketSize() != 8192) {
            throw new RuntimeException("Value changed after rejected set: "
                    + p.getMaximumPacketSize());
        }
    }
}
