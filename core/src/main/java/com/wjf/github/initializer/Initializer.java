package com.wjf.github.initializer;


/**
 * 初始化
 */
public interface Initializer {
    /**
     * 初始化操作
     * @throws Exception
     */
    void init() throws Exception;

    /**
     * 执行顺序
     * @return
     */
    int order();
}
