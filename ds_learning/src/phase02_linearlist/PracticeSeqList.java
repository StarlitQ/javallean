package phase02_linearlist;

public class PracticeSeqList {
    public static void main(String[] args){
        MyArrayList myL = new MyArrayList();
        myL.add(2);
        myL.add(8);
        myL.add(3,10);
        System.out.print(myL.toString());
    }
}

class MyArrayList{
    private Object[] data;
    private int size;
    MyArrayList(){
        data = new Object[4];
        size = 0;
    }
    public void remove(int index){
        for (int i = index; i < size - 1; i++){
            data[i] = data[i+1];
        }
        size--;  // 删完别忘了减数量
    }

    public void add(Object x) {
        if (size == data.length) resize();  // 满了就扩容
        data[size++] = x;
    }

    public void add(int index, int x){
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index=" + index + ", size=" + size);
        }
        if (size == data.length){
            resize();
        }
        for (int i = size; i > index; i--){
            data[i] = data[i - 1];
        }
        data[index] = x;
        size++;
    }

    private void resize(){
        Object[] newdata = new Object[data.length * 2];
        for (int i = 0; i < size; i++){
            newdata[i] = data[i];
        }
        data = newdata;
    }
    
    public String toString(){
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++){
            if (i > 0){
                sb.append(",");
            }
            sb.append(data[i]);
        }
        return sb.append("]").toString();
    }

}
