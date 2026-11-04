/*
 * @test
 * @summary Verifies that SecureRandom.reseed() completes normally and leaves the generator usable
 * @run main SecureRandomWitupTest
 */

import java.security.SecureRandom;

public class SecureRandomWitupTest {

    public static void main(String[] args) throws Exception {
        testDefaultConstructorReseed();
        testSeededConstructorReseed();
        testRepeatedReseed();
        testGetInstanceReseed();
    }

    static void testDefaultConstructorReseed() {
        SecureRandom r = new SecureRandom();
        r.reseed();
        byte[] b = new byte[32];
        r.nextBytes(b);
        check(b.length == 32, "nextBytes length after reseed");
    }

    static void testSeededConstructorReseed() {
        SecureRandom r = new SecureRandom(new byte[] {1, 2, 3});
        r.reseed();
        byte[] b = new byte[16];
        r.nextBytes(b);
        check(b.length == 16, "nextBytes length after seeded reseed");
    }

    static void testRepeatedReseed() {
        SecureRandom r = new SecureRandom();
        r.reseed();
        r.reseed();
        r.reseed();
        byte[] b = new byte[8];
        r.nextBytes(b);
        check(b.length == 8, "nextBytes length after repeated reseed");
    }

    static void testGetInstanceReseed() throws Exception {
        SecureRandom r = SecureRandom.getInstance("SHA1PRNG");
        r.reseed();
        byte[] b = new byte[24];
        r.nextBytes(b);
        check(b.length == 24, "nextBytes length after getInstance reseed");
    }

    static void check(boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException("Check failed: " + message);
        }
    }
}
