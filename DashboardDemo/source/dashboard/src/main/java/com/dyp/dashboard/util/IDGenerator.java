package com.dyp.dashboard.util;


import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by KaiWu.
 * Description:
 */
public class IDGenerator {

    private final static Lock LOCK = new ReentrantLock();
    private final static Condition PAUSE  = LOCK.newCondition();

//    public static String resourceGenerator(){
//        return generator();
//    }
//
//    /**
//     * generator globally unique identifiers
//     * @return  globally unique identifiers string
//     * @throws  RuntimeException generator process occur error, can be try again
//     */
//    public static String tagGenerator(){
//        return generator();
//    }

    /**
     * generator globally unique identifiers
     * @return lobally unique identifiers string
     * @throws  RuntimeException generator process occur error, can be try again
     */
    public static String generatorID() {
        try {
            LOCK.lock();
            PAUSE.await(10, TimeUnit.MILLISECONDS);

            // 生成全局唯一ID
            Random r = new Random();
            SnowflakeIdWorker idWorker = new SnowflakeIdWorker(r.nextInt(31), r.nextInt(31));
            return String.valueOf(idWorker.nextId());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            LOCK.unlock();
        }
    }

    public static void main(String args[]){
        System.out.println(IDGenerator.generatorID());
    }

}
