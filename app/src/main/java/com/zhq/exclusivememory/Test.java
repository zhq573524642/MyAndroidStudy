package com.zhq.exclusivememory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Test {

    private int i;

    private synchronized void inc() {
        i++;
        System.out.println(Thread.currentThread().getName() + "加 " + i);
    }

    private synchronized void dec() {
        i--;
        System.out.println(Thread.currentThread().getName() + "减 " + i);
    }

    class Inc implements Runnable{

        @Override
        public void run() {
            inc();
        }
    }

    class Dec implements Runnable{

        @Override
        public void run() {
            dec();
        }
    }



    public static void main(String[] args) {
//        Test test = new Test();
//        Inc inc = test.new Inc();
//        Dec dec = test.new Dec();
//
//        for (int i = 0; i < 2; i++) {
//            new Thread(inc).start();
//            new Thread(dec).start();
//        }
//
//        Callable<String> callable = new Callable<String>() {
//            @Override
//            public String call() throws Exception {
//                return "jjj";
//            }
//        };
//
//        FutureTask<String> fu = new FutureTask<>(callable);
//        try {
//            String s = fu.get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
        LinkedHashMap<Integer, Integer> linkedHashMap = new LinkedHashMap<>(0, 0.75f, false);
        linkedHashMap.put(0,0);
        linkedHashMap.put(1,1);
        linkedHashMap.put(2,2);
        linkedHashMap.put(3,3);
        linkedHashMap.put(4,4);
        linkedHashMap.get(1);
        linkedHashMap.get(3);
        for (Map.Entry<Integer, Integer> entry : linkedHashMap.entrySet()) {
            System.out.println(entry.getKey()+"-"+entry.getValue());
        }

    }
}
