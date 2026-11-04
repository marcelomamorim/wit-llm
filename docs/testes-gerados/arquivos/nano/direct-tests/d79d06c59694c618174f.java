/* @test    @summary Test for EnumMap.getValue() method to ensure correct value retrieval and null handling.    @run main EnumMapWitupTest */

import java.util.EnumMap;
import java.util.Map;

public class EnumMapWitupTest {

    enum Color {
        RED, GREEN, BLUE
    }

    public static void main(String[] args) {
        testGetValueReturnsCorrectValue();
        testGetValueWithNullValue();
    }

    static void testGetValueReturnsCorrectValue() {
        EnumMap<Color, String> map = new EnumMap<>(Color.class);
        map.put(Color.RED, "apple");
        map.put(Color.GREEN, "leaf");
        String value = map.getValue();
        if (!"apple".equals(value)) {
            throw new AssertionError("Expected 'apple', but got: " + value);
        }
    }

    static void testGetValueWithNullValue() {
        EnumMap<Color, String> map = new EnumMap<>(Color.class);
        map.put(Color.BLUE, null);
        String value = map.getValue();
        if (value != null) {
            throw new AssertionError("Expected null, but got: " + value);
        }
    }
}
