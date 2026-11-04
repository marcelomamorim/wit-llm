/* @test    @summary Test LinkedList.set method for correct replacement and return of old value    @run main LinkedListWitupTest */

import java.util.LinkedList;

public class LinkedListWitupTest {

    public static void main(String[] args) {
        testSetMethod();
    }

    public static void testSetMethod() {
        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        
        String oldVal = list.set(1, "beta");
        assert "b".equals(oldVal) : "Expected old value 'b', but got: " + oldVal;
        assert "beta".equals(list.get(1)) : "Expected new value 'beta' at index 1";
        assert list.size() == 3 : "List size should remain 3";
        
        String oldVal2 = list.set(0, "alpha");
        assert "a".equals(oldVal2) : "Expected old value 'a', but got: " + oldVal2;
        assert "alpha".equals(list.get(0)) : "Expected new value 'alpha' at index 0";
        
        String oldVal3 = list.set(2, "gamma");
        assert "c".equals(oldVal3) : "Expected old value 'c', but got: " + oldVal3;
        assert "gamma".equals(list.get(2)) : "Expected new value 'gamma' at index 2";
    }
}
