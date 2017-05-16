package org.sangaizhi.nio.AIO.time_server.client;

/**
 * Created by sangaizhi on 2017/5/15.
 */
public class TimeClient {

    public static void main(String[] args) {
        new Thread(new AsyncTimeClientHandler("127.0.0.1", 8080),"AIO-AsyncTimeClientHandler-001").start();
    }
}
