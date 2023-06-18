package com.sipc.events.dao;

import com.sipc.events.entity.param.MessageEventParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;

import static com.sipc.api.apiUtil.SendMsgUtil.sendGroupMsg;
import static com.sipc.api.apiUtil.SendMsgUtil.sendPrivateMsg;

@Component
public class Jdbc {
    @Value("${jdbc.url}")
    String url;
    @Value("${jdbc.username}")
    String username;
    @Value("${jdbc.password}")
    String password;
    public int runSql(MessageEventParam messageEventParam,String sql) {
        System.out.println(url);
        System.out.println(sql);
        StringBuilder msg = new StringBuilder();
        // 创建数据库连接
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // 连接成功，执行数据库操作
            // ...
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    // 获取列的值
                    try {
                        Object value = resultSet.getObject(i);
                        msg.append(value).append("\n");
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                msg.append("---------").append("\n");
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            if(messageEventParam.getGroup_id()!=0){
                sendGroupMsg(messageEventParam.getGroup_id(), "sql有误，仅支持select语句",false );
            }else {
                sendPrivateMsg(messageEventParam.getUser_id(), "sql有误，仅支持select语句",false);
            }
        }
        int resCode;
        if(messageEventParam.getGroup_id()!=0){
            resCode = sendGroupMsg(messageEventParam.getGroup_id(), msg.toString(), false );
        }else {
            resCode = sendPrivateMsg(messageEventParam.getUser_id(), msg.toString(), false);
        }
        if(resCode==-1){
            if(messageEventParam.getGroup_id()!=0){
                resCode = sendGroupMsg(messageEventParam.getGroup_id(), "查询结果过长，无法发送", false );
            }else {
                resCode = sendPrivateMsg(messageEventParam.getUser_id(), "查询结果过长，无法发送", false);
            }
        }
        return resCode;
    }
}
