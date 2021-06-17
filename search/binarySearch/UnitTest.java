package search.binarySearch;

import org.junit.jupiter.api.Test;

public class UnitTest {
    @Test
    void testcase_1() {
        int number = 10;
        int[] numbers = new int[] {};
        int index = BinarySearch.findWithBinarySearchAlgorithm(number,  numbers);
    }

    @Test
    void testcase_2() {
        int number = 5;
        int[] numbers = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int index = BinarySearch.findWithBinarySearchAlgorithm(number,  numbers);
    }

    @Test
    void testcase_3() {
        int number = 0;
        int[] numbers = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int index = BinarySearch.findWithBinarySearchAlgorithm(number,  numbers);
    }

    @Test
    void testcase_4() {
        int number = 9;
        int[] numbers = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int index = BinarySearch.findWithBinarySearchAlgorithm(number,  numbers);
    }
}
