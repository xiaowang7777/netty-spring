package com.wjf.github.server;

/**
 * 服务器抽象
 */
public interface Server {

    // 服务器存在的几种状态
    int stop = 0;
    int starting = 1;
    int running = 2;

    // 开启服务器
    void start() throws Exception;

    // 关闭服务器
    void stop() throws Exception;

    // 重启服务器
    void restart() throws Exception;

    // 获取服务器状态值
    int status();


    default boolean isStopped() {
        return status() == stop;
    }

    default boolean isStarting() {
        return status() == starting;
    }

    default boolean isRunning() {
        return status() == running;
    }
}
