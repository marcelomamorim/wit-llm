/* @test    @summary Test StringBuffer.insert(int, Object) for various inputs    @run main StringBufferWitupTest */

import java.lang.StringBuffer;

public class StringBufferWitupTest {

    public static void main(String[] args) {
        testInsertWithNullObject();
        testInsertWithStringObject();
        testInsertWithIntegerObject();
        testInsertWithCustomObject();
        testInsertWithEmptyString();
        testInsertAtBeginning();
        testInsertAtEnd();
    }

    static void testInsertWithNullObject() {
        StringBuffer sb = new StringBuffer("Hello");
        Object obj = null;
        StringBuffer result = sb.insert(2, obj);
        assert result == sb : "Insert null object should return the same StringBuffer";
        assert sb.toString().equals("Henullllo") : "Expected 'Henullllo', got '" + sb.toString() + "'";
    }

    static void testInsertWithStringObject() {
        StringBuffer sb = new StringBuffer("Hello");
        Object obj = "World";
        StringBuffer result = sb.insert(5, obj);
        assert result == sb : "Insert string object should return the same StringBuffer";
        assert sb.toString().equals("HelloWorld") : "Expected 'HelloWorld', got '" + sb.toString() + "'";
    }

    static void testInsertWithIntegerObject() {
        StringBuffer sb = new StringBuffer("Number:");
        Object obj = Integer.valueOf(123);
        StringBuffer result = sb.insert(7, obj);
        assert result == sb : "Insert Integer object should return the same StringBuffer";
        assert sb.toString().equals("Number:123") : "Expected 'Number:123', got '" + sb.toString() + "'";
    }

    static void testInsertWithCustomObject() {
        class Dummy {
            @Override
            public String toString() {
                return "DUMMY";
            }
        }
        StringBuffer sb = new StringBuffer("Start-");
        Object obj = new Dummy();
        StringBuffer result = sb.insert(6, obj);
        assert result == sb : "Insert custom object should return the same StringBuffer";
        assert sb.toString().equals("Start-DUMMY") : "Expected 'Start-DUMMY', got '" + sb.toString() + "'";
    }

    static void testInsertWithEmptyString() {
        StringBuffer sb = new StringBuffer("Test");
        Object obj = "";
        StringBuffer result = sb.insert(2, obj);
        assert result == sb : "Insert empty string should return the same StringBuffer";
        assert sb.toString().equals("TeTestst") : "Expected 'TeTestst', got '" + sb.toString() + "'";
    }

    static void testInsertAtBeginning() {
        StringBuffer sb = new StringBuffer("World");
        Object obj = "Hello ";
        StringBuffer result = sb.insert(0, obj);
        assert result == sb : "Insert at beginning should return the same StringBuffer";
        assert sb.toString().equals("Hello World") : "Expected 'Hello World', got '" + sb.toString() + "'";
    }

    static void testInsertAtEnd() {
        StringBuffer sb = new StringBuffer("Hello");
        Object obj = "!";
        StringBuffer result = sb.insert(sb.length(), obj);
        assert result == sb : "Insert at end should return the same StringBuffer";
        assert sb.toString().equals("Hello!") : "Expected 'Hello!', got '" + sb.toString() + "'";
    }
}
