package com.app.agent.manager;

import com.app.agent.comm.CommParameters;
import com.app.agent.property.RedisProperty;
import com.app.agent.property.TeammorsBotProperty;
import com.app.agent.property.TelegramBotProperty;
import com.app.agent.proxy.TeammorsMessageProxy;
import com.app.agent.proxy.TelegramMessageProxy;
import com.app.agent.skills.GenericSkill;
import com.app.agent.utils.RedisUtils;
import com.app.agent.wssdk.XMessageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@Component
public class AgentManager {

    @Autowired
    RedisProperty redisProperty;

    @Autowired
    TelegramBotProperty telegramBotProperty;

    @Autowired
    TeammorsBotProperty teammorsBotProperty;

    @Autowired
    TelegramMessageProxy telegramMessageProxy;

    @Autowired
    TeammorsMessageProxy teammorsMessageProxy;

    @Autowired
    GenericSkill genericSkill;


    public void startAgent(){

        CommParameters.instance().setTeammorsBotToken(teammorsBotProperty.getToken());
        CommParameters.instance().setTelegramBotId(telegramBotProperty.getId());
        CommParameters.instance().setTelegramBotToken(telegramBotProperty.getToken());
        CommParameters.instance().setTelegramBotName(telegramBotProperty.getName());

        try {

            RedisUtils.instance().init(redisProperty.getIp(),redisProperty.getUser(),
                    redisProperty.getPassword(),redisProperty.getPort(),
                    redisProperty.getDb(), redisProperty.getCluster());

            XMessageClient.instance().addObserver(teammorsMessageProxy);
            XMessageClient.instance().init(teammorsBotProperty.getToken());

//            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
//            botsApi.registerBot(telegramMessageProxy);
//            System.out.println("TelegramBot 启动成功!");

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    /**
     * 执行技能
     */
    public String executeSkill(String input, String chatId) {
        return genericSkill.execute(input, chatId);
    }

}
