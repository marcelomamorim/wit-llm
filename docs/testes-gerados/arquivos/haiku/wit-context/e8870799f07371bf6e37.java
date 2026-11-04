/*
 * @test
 * @summary Verify SecureRandom.reseed() delegates to the SPI and reports UnsupportedOperationException when the SPI does not support reseeding
 * @run main SecureRandomWitupTest
 */

import java.security.SecureRandom;
import java.security.SecureRandomParameters;
import java.security.SecureRandomSpi;

public class SecureRandomWitupTest {

    static int failures = 0;

    static void check(boolean cond, String msg) {
        if (!cond) {
            failures++;
            System.err.println("FAILED: " + msg);
        }
    }

    static class CountingSpi extends SecureRandomSpi {
        int reseedCalls = 0;
        SecureRandomParameters lastParams = new Object() == null ? null : null;
        boolean lastParamsSet = false;

        @Override
        protected void engineSetSeed(byte[] seed) {
        }

        @Override
        protected void engineNextBytes(byte[] bytes) {
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = 1;
            }
        }

        @Override
        protected byte[] engineGenerateSeed(int numBytes) {
            return new byte[numBytes];
        }

        @Override
        protected void engineReseed(SecureRandomParameters params) {
            reseedCalls++;
            lastParamsSet = true;
            lastParams = params;
        }
    }

    static class CustomRandom extends SecureRandom {
        final CountingSpi spi;

        CustomRandom(CountingSpi spi) {
            super(spi, null);
            this.spi = spi;
        }
    }

    static void testReseedDelegatesToSpi() {
        CountingSpi spi = new CountingSpi();
        CustomRandom random = new CustomRandom(spi);
        random.reseed();
        check(spi.reseedCalls == 1, "reseed() should call engineReseed exactly once, got " + spi.reseedCalls);
        check(spi.lastParamsSet, "engineReseed should have been invoked");
        check(spi.lastParams == null, "reseed() should pass null parameters to engineReseed");
    }

    static void testReseedRepeatedCallsAccumulate() {
        CountingSpi spi = new CountingSpi();
        CustomRandom random = new CustomRandom(spi);
        random.reseed();
        random.reseed();
        random.reseed();
        check(spi.reseedCalls == 3, "three reseed() calls should reach SPI three times, got " + spi.reseedCalls);
    }

    static void testReseedUnsupportedSpiThrows() {
        SecureRandom random;
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (Exception e) {
            System.out.println("SHA1PRNG not available, skipping: " + e);
            return;
        }
        boolean thrown = false;
        try {
            random.reseed();
        } catch (UnsupportedOperationException e) {
            thrown = true;
        }
        check(thrown, "reseed() on SHA1PRNG should throw UnsupportedOperationException");
    }

    public static void main(String[] args) throws Exception {
        testReseedDelegatesToSpi();
        testReseedRepeatedCallsAccumulate();
        testReseedUnsupportedSpiThrows();
        if (failures > 0) {
            throw new RuntimeException(failures + " check(s) failed");
        }
    }
}
