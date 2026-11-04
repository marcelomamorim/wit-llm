/*
 * @test
 * @summary Test for ObjectOutputStream.defaultWriteObject() method
 * @run main ObjectOutputStreamWitupTest
 */

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.NotActiveException;
import java.io.IOException;

public class ObjectOutputStreamWitupTest {

    public static void main(String[] args) throws Exception {
        testDefaultWriteObjectWithNullContext();
        testDefaultWriteObjectWithValidContext();
    }

    private static void testDefaultWriteObjectWithNullContext() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            // Reflection or internal setup to set curContext to null is complex;
            // Instead, simulate the scenario by subclassing and overriding getCurContext
            ObjectOutputStream testStream = new ObjectOutputStream(baos) {
                @Override
                protected SerialCallbackContext getCurContext() {
                    return null;
                }
            };
            testStream.defaultWriteObject();
            // Expect NotActiveException
            System.out.println("FAIL: Expected NotActiveException not thrown");
        } catch (NotActiveException e) {
            System.out.println("PASS: NotActiveException thrown as expected");
        } catch (IOException e) {
            System.out.println("FAIL: Unexpected IOException: " + e);
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testDefaultWriteObjectWithValidContext() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            // Create a dummy context with getObj and getDesc methods
            SerialCallbackContext ctx = new SerialCallbackContext() {
                @Override
                public Object getObj() {
                    return new DummyObject();
                }
                @Override
                public ObjectStreamClass getDesc() {
                    return ObjectStreamClass.lookup(DummyObject.class);
                }
            };
            ObjectOutputStream testStream = new ObjectOutputStream(baos) {
                @Override
                protected SerialCallbackContext getCurContext() {
                    return ctx;
                }
            };
            testStream.defaultWriteObject();
            System.out.println("PASS: defaultWriteObject executed without exception");
        } catch (IOException e) {
            System.out.println("FAIL: IOException during defaultWriteObject: " + e);
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    static class DummyObject implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        int value = 42;
    }

    // Mock or override SerialCallbackContext for testing purposes
    static abstract class SerialCallbackContext {
        public abstract Object getObj();
        public abstract ObjectStreamClass getDesc();
    }
}
