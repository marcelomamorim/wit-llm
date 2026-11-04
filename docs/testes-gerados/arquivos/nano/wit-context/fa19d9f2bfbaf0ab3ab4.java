/*
 * @test
 * @summary Test for setFeature method in ParserSAX class.
 * @run main ParserSAXWitupTest
 */

import java.lang.reflect.Method;
import jdk.internal.util.xml.impl.ParserSAX;

public class ParserSAXWitupTest {

    public static void main(String[] args) {
        testSetFeatureThrowsUnsupportedOperationException();
    }

    public static void testSetFeatureThrowsUnsupportedOperationException() {
        ParserSAX parser = new ParserSAX();
        try {
            Method setFeatureMethod = ParserSAX.class.getMethod("setFeature", String.class, boolean.class);
            setFeatureMethod.invoke(parser, "someFeature", true);
            System.out.println("FAIL: Expected UnsupportedOperationException was not thrown.");
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof UnsupportedOperationException) {
                System.out.println("PASS: Caught expected UnsupportedOperationException.");
            } else {
                System.out.println("FAIL: Caught unexpected exception: " + cause);
            }
        } catch (NoSuchMethodException | IllegalAccessException e) {
            System.out.println("FAIL: Reflection error: " + e);
        }
    }
}
