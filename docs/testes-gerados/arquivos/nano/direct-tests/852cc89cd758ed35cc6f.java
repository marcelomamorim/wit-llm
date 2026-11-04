/* @test    @summary Test handleGetObject method of TimeZoneNamesBundle for various keys    @run main TimeZoneNamesBundleWitupTest */

import sun.util.resources.TimeZoneNamesBundle;

public class TimeZoneNamesBundleWitupTest {

    public static void main(String[] args) {
        testHandleGetObjectWithStringArray();
        testHandleGetObjectWithNonArray();
        testHandleGetObjectWithNull();
    }

    private static void testHandleGetObjectWithStringArray() {
        // Create a subclass with a specific getContents implementation
        TimeZoneNamesBundle bundle = new TimeZoneNamesBundle() {
            @Override
            protected Object[][] getContents() {
                return new Object[][] {
                    {"America/New_York", new String[] {"New York", "EST", "E", "EDT", "ED", "America"}}
                };
            }
        };
        Object result = bundle.handleGetObject("America/New_York");
        if (!(result instanceof String[])) {
            throw new AssertionError("Expected String[] for key 'America/New_York'");
        }
        String[] array = (String[]) result;
        if (array.length != 7) {
            throw new AssertionError("Array length should be 7, but was " + array.length);
        }
        if (!array[0].equals("America/New_York")) {
            throw new AssertionError("First element should be key 'America/New_York'");
        }
        if (!array[1].equals("New York")) {
            throw new AssertionError("Second element mismatch");
        }
        if (!array[2].equals("EST")) {
            throw new AssertionError("Third element mismatch");
        }
        if (!array[3].equals("E")) {
            throw new AssertionError("Fourth element mismatch");
        }
        if (!array[4].equals("EDT")) {
            throw new AssertionError("Fifth element mismatch");
        }
        if (!array[5].equals("ED")) {
            throw new AssertionError("Sixth element mismatch");
        }
        if (!array[6].equals("America")) {
            throw new AssertionError("Seventh element mismatch");
        }
    }

    private static void testHandleGetObjectWithNonArray() {
        // Create a subclass with a specific getContents implementation returning a non-array object
        TimeZoneNamesBundle bundle = new TimeZoneNamesBundle() {
            @Override
            protected Object[][] getContents() {
                return new Object[][] {
                    {"SomeKey", "SomeValue"}
                };
            }
        };
        Object result = bundle.handleGetObject("SomeKey");
        if (!(result instanceof String[])) {
            throw new AssertionError("Expected String[] for key 'SomeKey'");
        }
        String[] array = (String[]) result;
        if (array.length != 1) {
            throw new AssertionError("Array length should be 1, but was " + array.length);
        }
        if (!array[0].equals("SomeKey")) {
            throw new AssertionError("First element should be key 'SomeKey'");
        }
    }

    private static void testHandleGetObjectWithNull() {
        // Create a subclass with a getContents returning null
        TimeZoneNamesBundle bundle = new TimeZoneNamesBundle() {
            @Override
            protected Object[][] getContents() {
                return null;
            }
        };
        Object result = bundle.handleGetObject("AnyKey");
        if (result != null) {
            throw new AssertionError("Expected null for key 'AnyKey' when getContents returns null");
        }
    }
}
