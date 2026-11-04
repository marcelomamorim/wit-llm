/* @test    @summary Test addAll(Collection c) method for ArrayList with various inputs and behaviors    @run main ArrayListWitupTest */

import java.util.ArrayList;
import java.util.Collection;

public class ArrayListWitupTest {

    public static void main(String[] args) {
        testAddAllWithNull();
        testAddAllWithEmptyCollection();
        testAddAllWithValidCollection();
        testAddAllWithIndexOutOfBounds();
    }

    private static void testAddAllWithNull() {
        ArrayList<String> list = new ArrayList<>();
        try {
            list.addAll(null);
            System.out.println("FAIL: Expected NullPointerException for null collection");
        } catch (NullPointerException e) {
            System.out.println("PASS: NullPointerException thrown as expected");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }

    private static void testAddAllWithEmptyCollection() {
        ArrayList<Integer> list = new ArrayList<>();
        Collection<Integer> empty = new ArrayList<>();
        boolean result = list.addAll(empty);
        if (result && list.size() == 0) {
            System.out.println("PASS: addAll with empty collection results in empty list");
        } else {
            System.out.println("FAIL: addAll with empty collection failed");
        }
    }

    private static void testAddAllWithValidCollection() {
        ArrayList<String> list = new ArrayList<>();
        Collection<String> coll = new ArrayList<>();
        coll.add("a");
        coll.add("b");
        boolean result = list.addAll(coll);
        if (result && list.size() == 2 && list.get(0).equals("a") && list.get(1).equals("b")) {
            System.out.println("PASS: addAll with valid collection works as expected");
        } else {
            System.out.println("FAIL: addAll with valid collection failed");
        }
    }

    private static void testAddAllWithIndexOutOfBounds() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        Collection<Integer> coll = new ArrayList<>();
        coll.add(3);
        try {
            list.addAll(-1, coll);
            System.out.println("FAIL: Expected IndexOutOfBoundsException for negative index");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: IndexOutOfBoundsException thrown for negative index");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
        try {
            list.addAll(3, coll); // index > size
            System.out.println("FAIL: Expected IndexOutOfBoundsException for index > size");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("PASS: IndexOutOfBoundsException thrown for index > size");
        } catch (Exception e) {
            System.out.println("FAIL: Unexpected exception: " + e);
        }
    }
}
