/* @test    @summary Test HashMap.remove() method for correct exception and behavior    @run main HashMapWitupTest */

import java.util.HashMap;

public class HashMapWitupTest {

    public static void main(String[] args) {
        testRemoveWithoutCurrent();
        testRemoveWithNullCurrent();
    }

    static void testRemoveWithoutCurrent() {
        HashMap<Object, Object> map = new HashMap<>();
        HashMap<Object, Object>.HashMapEntry entry = null;
        try {
            // Reflection to access private inner class and field 'current'
            java.lang.reflect.Field currentField = HashMap.class.getDeclaredField("current");
            currentField.setAccessible(true);
            currentField.set(map, null);
            // Invoke remove method
            java.lang.reflect.Method removeMethod = HashMap.class.getDeclaredMethod("remove");
            removeMethod.setAccessible(true);
            removeMethod.invoke(map);
            // Should throw IllegalStateException
            System.out.println("FAIL: Expected IllegalStateException not thrown");
        } catch (java.lang.reflect.InvocationTargetException e) {
            if (e.getCause() instanceof IllegalStateException) {
                System.out.println("PASS: Caught expected IllegalStateException");
            } else {
                System.out.println("FAIL: Unexpected exception: " + e.getCause());
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static void testRemoveWithNullCurrent() {
        HashMap<Object, Object> map = new HashMap<>();
        try {
            // Reflection to set 'current' to null explicitly
            java.lang.reflect.Field currentField = HashMap.class.getDeclaredField("current");
            currentField.setAccessible(true);
            currentField.set(map, null);
            // Invoke remove method
            java.lang.reflect.Method removeMethod = HashMap.class.getDeclaredMethod("remove");
            removeMethod.setAccessible(true);
            removeMethod.invoke(map);
            System.out.println("FAIL: Expected IllegalStateException not thrown");
        } catch (java.lang.reflect.InvocationTargetException e) {
            if (e.getCause() instanceof IllegalStateException) {
                System.out.println("PASS: Caught expected IllegalStateException");
            } else {
                System.out.println("FAIL: Unexpected exception: " + e.getCause());
            }
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
