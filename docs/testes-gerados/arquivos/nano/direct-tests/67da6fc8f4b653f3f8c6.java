/* @test    @summary Test for EnumMap.setValue method to ensure it updates values correctly and returns previous value.    @run main EnumMapWitupTest */

import java.util.EnumMap;
import java.util.Map;

public class EnumMapWitupTest {

    enum Color { RED, GREEN, BLUE }

    public static void main(String[] args) {
        testSetValueUpdatesAndReturnsOld();
        testSetValueWithNull();
    }

    static void testSetValueUpdatesAndReturnsOld() {
        EnumMap<Color, String> map = new EnumMap<>(Color.class);
        map.put(Color.RED, "stop");
        String previous = map.setValue("go");
        assert previous.equals("stop") : "Expected previous value to be 'stop'";
        assert "go".equals(map.get(Color.RED)) : "Expected current value to be 'go'";
    }

    static void testSetValueWithNull() {
        EnumMap<Color, String> map = new EnumMap<>(Color.class);
        map.put(Color.GREEN, "go");
        String previous = map.setValue(null);
        assert previous.equals("go") : "Expected previous value to be 'go'";
        assert map.get(Color.GREEN) == null : "Expected current value to be null";
    }
}
