/*
 * @test
 * @summary Test Properties constructor with initialCapacity
 * @run main PropertiesWitupTest
 */

import java.util.Properties;

public class PropertiesWitupTest {
    public static void main(String[] args) {
        // Test with zero initial capacity
        Properties p0 = new Properties(0);
        if (p0 == null) throw new RuntimeException("Properties instance is null");

        // Test with positive initial capacity
        Properties p10 = new Properties(10);
        if (p10 == null) throw new RuntimeException("Properties instance is null");

        // Test with large initial capacity
        Properties p1000 = new Properties(1000);
        if (p1000 == null) throw new RuntimeException("Properties instance is null");

        // Test with negative initial capacity (behavior not specified, expect no exception)
        Properties pNeg = new Properties(-1);
        if (pNeg == null) throw new RuntimeException("Properties instance is null");
    }
}
