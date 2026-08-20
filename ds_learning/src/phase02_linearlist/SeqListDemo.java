package phase02_linearlist;

import java.util.ArrayList;

/**
 * 👀 Demo — 顺序表（ArrayList）
 *
 * 顺序表 = 可以自动扩容的数组
 * 优点：按下标访问 O(1)，内存连续
 * 缺点：中间插入/删除 O(n)，扩容时翻倍复制
 */
public class SeqListDemo {

    public static void main(String[] args) {

        // ===== 1. 创建 =====
        ArrayList<Integer> list = new ArrayList<>();
        System.out.println("创建空列表: " + list + "  大小=" + list.size());

        // ===== 2. 尾部追加 — O(1)，偶尔 O(n)扩容 =====
        list.add(10);
        list.add(20);
        list.add(30);
        System.out.println("三次尾部追加后: " + list);

        // ===== 3. 按下标访问 — O(1)，数组的看家本领！=====
        System.out.println("下标1的元素: " + list.get(1));   // 20

        // ===== 4. 中间插入 — O(n)，后面元素全部后移 =====
        list.add(1, 99);   // 在下标1处插入99，原来的1及以后全后移
        System.out.println("在下标1插入99后: " + list);

        // ===== 5. 删除 — O(n)，后面元素全部前移 =====
        list.remove(2);    // 删除下标2的元素
        System.out.println("删除下标2后: " + list);

        // ===== 6. 遍历 =====
        System.out.print("遍历: ");
        for (int x : list) {
            System.out.print(x + " ");
        }
        System.out.println();

        // ===== 7. 手写一个简易顺序表（底层就是数组）=====
        System.out.println("\n====== 手写迷你顺序表 ======");
        MyArrayList myList = new MyArrayList();
        myList.add(1);
        myList.add(2);
        myList.add(3);
        myList.add(1, 88);   // 下标1插入88
        System.out.println(myList);  // [1, 88, 2, 3]
        System.out.println("下标2: " + myList.get(2));  // 2
    }
}

/**
 * 迷你版顺序表 — 理解 ArrayList 的底层
 * 核心就一个：Object[] 数组 + size 计数器
 */
class MyArrayList {
    private Object[] data;   // 底层数组
    private int size;        // 当前存了多少元素

    public MyArrayList() {
        data = new Object[4];  // 初始容量4
        size = 0;
    }

    // 尾部追加 O(1)
    public void add(Object x) {
        if (size == data.length) resize();  // 满了就扩容
        data[size++] = x;
    }

    // 指定位置插入 O(n)
    public void add(int index, Object x) {
        if (size == data.length) resize();
        // 后移：从最后一个开始，每个往后挪一格
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = x;
        size++;
    }

    // 按位置取 O(1)
    public Object get(int index) {
        return data[index];
    }

    // 扩容：翻倍复制 O(n)
    private void resize() {
        Object[] newData = new Object[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            newData[i] = data[i];
        }
        data = newData;
        System.out.println("  [扩容了! 新容量=" + data.length + "]");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) sb.append(", ");
            sb.append(data[i]);
        }
        return sb.append("]").toString();
    }
}
