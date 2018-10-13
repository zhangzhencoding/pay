package com.zz.pay.web.elasticJob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

public class MyElasticJob implements SimpleJob {
    
    @Override
    public void execute(ShardingContext context) {
        switch (context.getShardingItem()) {
            case 0: 
                // do something by sharding item 0
                System.out.println("0分片执行。。。");
                break;
            case 1: 
                // do something by sharding item 1
                System.out.println("1分片执行。。。");
                break;
            case 2: 
                // do something by sharding item 2
                System.out.println("2分片执行。。。");
                break;
            // case n: ...
        }
    }
}