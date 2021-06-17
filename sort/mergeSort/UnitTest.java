package sort.mergeSort;

import org.junit.jupiter.api.Test;

public class UnitTest {
    @Test
    void testcase_1() {
        int[] numbers = new int[] {};
        MergeSort.sort(numbers, 0, -1);
    }

    @Test
    void testcase_2() {
        int[] numbers = new int[] {0};
        MergeSort.sort(numbers, 0, 0);
    }

    @Test
    void testcase_3() {
        int[] numbers = new int[] {1, 0};
        MergeSort.sort(numbers, 0, 1);
    }

    @Test
    void testcase_4() {
        int[] numbers = new int[] {0, 1};
        MergeSort.sort(numbers, 0, 1);
    }
}
