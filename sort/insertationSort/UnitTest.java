package sort.insertationSort;

import org.junit.jupiter.api.Test;

public class UnitTest {
    @Test
    void testcase_1() {
        int[] numbers = new int[] {};
        InsertionSort.sort(numbers);
    }

    @Test
    void testcase_2() {
        int[] numbers = new int[] {0};
        InsertionSort.sort(numbers);
    }

    @Test
    void testcase_3() {
        int[] numbers = new int[] {1, 0};
        InsertionSort.sort(numbers);
    }

    @Test
    void testcase_4() {
        int[] numbers = new int[] {0, 1};
        InsertionSort.sort(numbers);
    }
}
