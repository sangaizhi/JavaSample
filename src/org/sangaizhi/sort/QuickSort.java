package org.sangaizhi.sort;

/**
 * 快速排序
 */
public class QuickSort {
    public static void main(String[] args) {
        QuickSort quickSort = new QuickSort();
        int[] a = new int[] {3, 1, 5, 7, 2, 4, 9, 6, 10, 8};
        quickSort.print(a);
        quickSort.sort(a,0,a.length-1);
        System.out.println("");
        quickSort.print(a);

    }
    private void print(int[] a){
        for(int temp : a) {
            System.out.print(temp + " ");
        }
    }
    private  void sort(int[] a,int low, int high) {
        if(low<high){ //如果不加这个判断递归会无法退出导致堆栈溢出异常
            int middle=getMiddle(a,low,high);
            sort(a,  0,  middle-1);          //递归对低子表递归排序
            sort(a,   middle + 1, high);        //递归对高子表递归排序
        }
    }
    /**
     * 计算分割数组的标准元素
     * @param a 待分割的数组
     * @return
     */
    private int getMiddle(int[] a, int low, int high){
        int key = a[low];//基准元素
        while (low < high){
            while (low < high && a[high] >= key){  //从high开始找比基准小的，与low换位置
                high--;
            }
            a[low]=a[high];
            while(low<high && a[low] <= key){
                low++;
            }
            a[high]=a[low];
        }
        a[low]=key;
        return low;
    }
}
