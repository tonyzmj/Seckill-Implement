package com.mingjunzhong.test;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mingjun on 15/10/5.
 */
public class HttpClientTest {

    public static void main(String[] args) {

        ExecutorService pool = Executors.newFixedThreadPool(200);
        for (int i = 0; i < 20000; i++) {
            pool.submit(new RunTask(100 + i));
        }
        pool.shutdown();


    }

    private static class RunTask implements Runnable {
        private int userId;

        public RunTask(int userId) {
            this.userId = userId;
        }

        public void run() {
            try {
                //Thread.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
            GetMethod getMethod = new GetMethod(String.format("http://10.128.120.46:8080/goodsSecKill?userId=%d&goodsId=1", userId));
            HttpClient httpClient = new HttpClient();
            try {
                httpClient.executeMethod(getMethod);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
