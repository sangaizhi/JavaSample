package org.sangaizhi.nio.AIO.time_server.server;

/**
 * Created by sangaizhi on 2017/5/15.
 */
public class TimeServer {

    public static void main(String[] args) {
        AsyncTimeServerHandle asyncTimeServerHandle = new AsyncTimeServerHandle(8080);
        new Thread(asyncTimeServerHandle, "AIO-AsyncTimeServerHandle-001").start();
    }

}
