package BatchWebOfLies.java;

import java.util.*;

/**
 * @author zhengqinhan
 * @date 2021/8/4
 */
public class Main7 {

    public static void main(String[] args) {
        Integer n, m, q, t, type, a, b, res;
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        res  = 0;
        m = scanner.nextInt();
        Map<Integer, Set<Integer>> biggerMap = new HashMap<>();
        Map<Integer, Set<Integer>> allMap = new HashMap<>();
        while(m-- > 0) {
            a = scanner.nextInt();
            b = scanner.nextInt();
            if(a > b) {
                t = a;
                a = b;
                b = t;
            }
            res -= func(biggerMap, a, b, 1);
            res += func(allMap, a, b, 1);
            res += func(allMap, b, a, 1);
        }
        q = scanner.nextInt();
        while(q-- > 0) {
            type = scanner.nextInt();
            if(type == 3) {
                System.out.println(res);
            } else {
                a = scanner.nextInt();
                b = scanner.nextInt();
                if(a > b) {
                    t = a;
                    a = b;
                    b = t;
                }
                if(type == 1) {
                    res -= func(biggerMap, a, b, 1);
                    res += func(allMap, a, b, 1);
                    res += func(allMap, b, a, 1);
                } else if (type == 2){
                    res += func(biggerMap, a, b, 2);
                    res -= func(allMap, a, b, 2);
                    res -= func(allMap, b, a, 2);
                }
            }
        }
    }

    public static int func(Map<Integer, Set<Integer>> map, Integer key, Integer value, int type) {
        Set<Integer> tmpSet = map.getOrDefault(key, new HashSet<>());
        int res = 0;
        if(type == 1) {
            tmpSet.add(value);
            if(tmpSet.size() == 1) {
                res = 1;
            }
        } else {
            tmpSet.remove(value);
            if(tmpSet.isEmpty()) {
                res = 1;
            }
        }
        map.put(key, tmpSet);
        return res;
    }
}
