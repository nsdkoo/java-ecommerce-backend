package com.app.agent.proxy;

import com.alibaba.fastjson.JSONObject;
import com.app.agent.adapter.TeammorsMessageAdapter;
import com.app.agent.rag.service.RAGService;
import com.app.agent.skills.GenericSkill;
import com.app.agent.skills.SkillGeneratorSkill;
import com.app.agent.utils.JsonUtils;
import com.app.agent.utils.ThreadUtils;
import com.app.agent.wssdk.XMessageObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TeammorsMessageProxy implements XMessageObserver {
    private static final Logger log = LoggerFactory.getLogger(TeammorsMessageProxy.class);

    @Autowired
    RAGService ragService;

    @Autowired
    GenericSkill genericSkill;
    
    @Autowired
    SkillGeneratorSkill skillGeneratorSkill;

    @Override
    public void onIMMessage(String message) {

        try {


            if(JsonUtils.isJsonObject(message)) {

                JSONObject jsonObject = JSONObject.parseObject(message);
                if(jsonObject.containsKey("dataBody")) {
                    String dataBody = jsonObject.getString("dataBody");
                    if(JsonUtils.isJsonObject(dataBody)) {

                        JSONObject dataBodyJson = JSONObject.parseObject(dataBody);
                        if (dataBodyJson.containsKey("text")) {

                            log.info("dataBodyJson:{}", dataBodyJson);

                            String chatId = dataBodyJson.getString("chatId");
                            String text = dataBodyJson.getString("text");

                            TeammorsMessageAdapter teammorsMessageAdapter = new TeammorsMessageAdapter(chatId, text, ragService, genericSkill, skillGeneratorSkill);
                            ThreadUtils.instance().getExecutor().execute(teammorsMessageAdapter);

                        }
                    }
                }

            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onIMError(String message) {
        log.error("message:"+message);
    }



}