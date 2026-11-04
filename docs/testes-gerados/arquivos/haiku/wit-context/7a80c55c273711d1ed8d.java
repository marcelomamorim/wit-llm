/*
 * @test
 * @summary Verifies Properties(int initialCapacity): accepts non-negative capacities and rejects negative ones with IllegalArgumentException
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        Properties zero = new Properties(0);
        zero.setProperty("k", "v");
        if (!"v".equals(zero.getProperty("k"))) {
            throw new RuntimeException("capacity 0: stored property not retrievable");
        }

        Properties sixteen = new Properties(16);
        if (sixteen.getProperty("missing") != null) {
            throw new RuntimeException("capacity 16: missing key should return null");
        }
        sixteen.setProperty("a", "1");
        sixteen.setProperty("b", "2");
        if (!"1".equals(sixteen.getProperty("a")) || !"2".equals(sixteen.getProperty("b"))) {
            throw new RuntimeException("capacity 16: properties not retrievable");
        }

        boolean thrown = false;
        try {
            new Properties(-1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        if (!thrown) {
            throw new RuntimeException("negative initialCapacity should throw IllegalArgumentException");
        }
    }
}
