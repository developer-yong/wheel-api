package com.api.core;

import com.alibaba.fastjson.JSON;
import com.api.common.ConfigProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Application事件监听实现类{@link ApplicationEvent}
 */
@Component
public class ApplicationEventListener implements ApplicationListener<ApplicationEvent> {

    @Resource
    private ConfigProperties configProperties;

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ApplicationReadyEvent) {
            Logger.d("系统服务启动完成！");
            Logger.json(JSON.toJSONString(configProperties));
        }
        if (applicationEvent instanceof ContextClosedEvent) {
            Logger.d("系统服务已停止！");
        }
    }
}
