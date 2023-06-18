package com.sipc.events.Service.impl;

import com.sipc.events.Service.SqlService;
import com.sipc.events.dao.Jdbc;
import com.sipc.events.entity.param.MessageEventParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SqlServiceImpl implements SqlService {
    @Autowired
    private Jdbc jdbc;

    @Override
    public void selectSql(MessageEventParam messageEventParam) {
        String sql = messageEventParam.getMessage().replace("/sql","").trim();
        jdbc.runSql(messageEventParam, sql);
    }
}
