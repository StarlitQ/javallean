package phase02_linearlist;

public class PracticeLinkedList {
    public static void main(String[] args){
        Node n1 = new Node(3);


    }

    //方法-查长度
    static int length(Node head){
        if(head == null)return 0;
        int len = 1;
        Node current = head;
        while(current.next != null){
            current = current.next;
            len++;
        }
        return len;
    }

    //查元素
    static Node find(Node head, int val){
        Node current = head;
        while (current != null && current.value != val){
            current = current.next;
        }
        return current;
    }

}

class Node{
    int value;
    Node next;
    Node(int value){
        this.value = value;
    }
}

