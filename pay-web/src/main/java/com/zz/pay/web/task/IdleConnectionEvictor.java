package com.zz.pay.web.task;

import org.apache.http.conn.HttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class IdleConnectionEvictor extends   Thread {
    private static final Logger logger = LoggerFactory.getLogger(IdleConnectionEvictor.class);
    private volatile boolean shutDown = false;
    private final HttpClientConnectionManager conMgr;
    public IdleConnectionEvictor(HttpClientConnectionManager conMgr){
        this.conMgr = conMgr;
        this.start();
    }
    @Override
    public void run() {
        while (!shutDown){
            try {
            synchronized (this){
                    wait(5000);
                    logger.info("开始清理httpClient连接池");
                    //清理失效的链接
                    conMgr.closeExpiredConnections();
                    //可选的 关闭30秒内不活动的链接
                    conMgr.closeIdleConnections(30, TimeUnit.SECONDS);
            }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {

            }
        }
    }

    public void shutDown(){
        this.shutDown = true;
        synchronized (this) {
            notifyAll();
        }
    }
}
