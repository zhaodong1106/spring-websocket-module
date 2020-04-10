package com.zhaodong.websocket.config;


import com.zhaodong.websocket.service.WsMeetingStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


/**
 * @author zhaodong
 * @date 2019/12/16
 */
@Component
public class CommandLineRunnerBean implements CommandLineRunner {
    @Autowired
    private WsMeetingStore wsMeetingStore;
    private static final Logger LOG=LoggerFactory.getLogger(CommandLineRunnerBean.class);
    @Override
    public void run(String... args) throws Exception {
        //删除redis脏的长连接集合
        wsMeetingStore.removeAllMeetings();

    }
}
