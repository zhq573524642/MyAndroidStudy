package com.zhq.exclusivememory.utils;

import android.text.Spannable;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Huiqiang Zhang
 * on 2020/4/7.
 */

public class MyArrayList<E> {

    //定义数组，用于存储集合的元素
    private Object[] elementData;
    //定义变量，用于记录数组的个数
    private int size;
    //定义空数组，用于在创建集合对象的时候给elementData初始化
    private Object[] emptyArray = {};
    //定义常量，用于记录集合的容量
    private final int DEFAULT_CAPACITY = 10;

    //构造方法
    public MyArrayList() {
        elementData = emptyArray;
    }

    /**
     * add 方法
     * @param e
     * @return
     */
    public boolean add(E e) {
        //将来在调用的时候需要判断是否需要扩容
         grow();
        //将元素添加到集合
        elementData[size++] = e;
        return true;
    }

    /**
     * 简单的扩容的方法
     */
    private void grow() {
        //判断集合存储的元素的数组是否等于emptyArray
        if (elementData == emptyArray) {
            //第一次扩容
            elementData = new Object[DEFAULT_CAPACITY];
        }

        //如果size==集合存元素数组的长度的时候进行扩容
        if (size == elementData.length) {
            //先定义变量记录老容量
            int oldCapacity = elementData.length;
            //核心算法 1.5倍
            int newCapacity = oldCapacity + (oldCapacity >> 1);
            //创建一个新的数组，长度就是newCapacity
            Object[] obj = new Object[newCapacity];
            //拷贝元素
            System.arraycopy(elementData, 0, obj, 0, elementData.length);
            //把新数组的地址赋值给elementData
            elementData = obj;
        }
    }

    /**
     * set 修改元素
     * @param index
     * @param element
     * @return
     */
    public E set(int index, E element) {
        checkIndex(index);
        //将索引对应的元素取出来
        E value = (E) elementData[index];
        //替换元素
        elementData[index] = element;
        return value;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("索引越界");
        }
    }

    /**
     * 删除方法
     * @param index
     * @return
     */
    public E remove(int index) {
        checkIndex(index);
        E oldValue = (E) elementData[index];
        //计算出要移动元素的个数
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index + 1, elementData, index,
                    numMoved);
        elementData[--size] = null; // clear to let GC do its work

        return oldValue;
    }

    /**
     * 根据索引获取元素
     * @param index
     * @return
     */
    public E get(int index) {
        checkIndex(index);
        //从数组中获取元素返回

        return (E) elementData[index];
    }

    /**
     * 获取集合的长度
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * toString
     * @return
     */
    public String toString() {
        //对集合进行判断
        if (size == 0) {
            return "[]";
        }
        //创建StringBuilder
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        //循环遍历数组
        for (int i = 0; i < size; i++) {
            //判断i == size-1；
            if (i == size - 1) {
              sb.append(elementData[i]).append("]");
            }else {
                sb.append(elementData[i]).append(",").append(" ");
            }
        }
        return sb.toString();
    }
}
