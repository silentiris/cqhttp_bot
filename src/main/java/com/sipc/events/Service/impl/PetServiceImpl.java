package com.sipc.events.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sipc.events.Service.PetService;
import com.sipc.events.dao.PetDao;
import com.sipc.events.entity.param.MessageEventParam;
import com.sipc.events.entity.po.PetPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.sipc.api.apiUtil.SendMsgUtil.sendGroupMsg;

@Service
public class PetServiceImpl implements PetService {
    @Autowired
    private PetDao petDao;

    @Override
    public void adoptPet(MessageEventParam messageEventParam) {
        String message = messageEventParam.getMessage();
        String petName = message.substring(message.indexOf("养")+1);
        QueryWrapper<PetPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", messageEventParam.getUser_id());
        queryWrapper.eq("group_id", messageEventParam.getGroup_id());
        if(null!=petDao.selectOne(queryWrapper)){
            sendGroupMsg(messageEventParam.getGroup_id(), "你已经在本群拥有一个tutu啦！",false);
        } else{
          PetPO petPO = new PetPO();
          petPO.setUserId(messageEventParam.getUser_id());
          petPO.setGroupId(messageEventParam.getGroup_id());
          petPO.setPetName(petName);
          petPO.setPetCouple(0);
          petPO.setPetSize(5);
          petDao.insert(petPO);
          sendGroupMsg(messageEventParam.getGroup_id(),"领养成功！tutu:"+petName,false);
        }
    }

    @Override
    public void changeName(MessageEventParam messageEventParam) {
        String message = messageEventParam.getMessage();
        String petName = message.substring(message.indexOf("名")+1);
        QueryWrapper<PetPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", messageEventParam.getUser_id());
        queryWrapper.eq("group_id", messageEventParam.getGroup_id());
        PetPO petPO = petDao.selectOne(queryWrapper);
        if(null==petPO){
            sendGroupMsg(messageEventParam.getGroup_id(),"你还没有tutu哦！快去领养一个吧~",false);
            return;
        }
        petPO.setPetName(petName);
        petDao.updateById(petPO);
        sendGroupMsg(messageEventParam.getGroup_id(),"改名成功！tutu:"+petName,false);
    }

    @Override
    public void confess(MessageEventParam messageEventParam) {
        //remember to restrain formation!!! 兔兔表白[CQ:at,qq=)
        String message = messageEventParam.getMessage();
        String petCouple = message.substring(message.indexOf("couple[CQ:at,qq=")+"couple[CQ:at,qq=".length(),message.length()-1);
        try{
            System.out.println(petCouple);
            Long.parseLong(petCouple);
        }catch (Exception e){
            sendGroupMsg(messageEventParam.getGroup_id(),"格式错误~",false);
            return;
        }
        Long petCoupleID = Long.parseLong(petCouple);
        QueryWrapper<PetPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", messageEventParam.getUser_id());
        queryWrapper.eq("group_id", messageEventParam.getGroup_id());
        PetPO petPO = petDao.selectOne(queryWrapper);
        if(null==petPO){
            sendGroupMsg(messageEventParam.getGroup_id(),"你还没有tutu哦！还不能表白哦~",false);
            return;
        }
        QueryWrapper<PetPO> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("user_id", petCoupleID);
        queryWrapper2.eq("group_id", messageEventParam.getGroup_id());
        PetPO petCouplePo = petDao.selectOne(queryWrapper2);
        if(null==petCouplePo){
            sendGroupMsg(messageEventParam.getGroup_id(),"她还没有tutu，快去邀请她开通吧" ,false);
        }
        petPO.setPetCouple(-1*petCoupleID);
        petDao.updateById(petPO);
        sendGroupMsg(messageEventParam.getGroup_id(),"已成功发送邀请！",false);
    }

    @Override
    public void promise(MessageEventParam messageEventParam) {
        String message = messageEventParam.getMessage();
        Long confessPetId = Long.parseLong(message.substring(message.indexOf("同意[CQ:at,qq=")+"同意[CQ:at,qq=".length(),message.length()-1));
        QueryWrapper<PetPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", confessPetId);
        queryWrapper.eq("group_id", messageEventParam.getGroup_id());
        queryWrapper.eq("pet_couple", messageEventParam.getUser_id()*(-1));
        PetPO confessPetPO = petDao.selectOne(queryWrapper);
        if(null == confessPetPO){
            sendGroupMsg(messageEventParam.getGroup_id(),"她还没有给你发送过邀请哦~",false);
            return;
        }
        confessPetPO.setPetCouple(confessPetPO.getPetCouple()*(-1));
        petDao.updateById(confessPetPO);
        QueryWrapper<PetPO> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("user_id", messageEventParam.getUser_id());
        queryWrapper2.eq("group_id", messageEventParam.getGroup_id());
        PetPO petPo = petDao.selectOne(queryWrapper2);
        petPo.setPetCouple(confessPetPO.getUserId());
        petDao.updateById(petPo);
        sendGroupMsg(messageEventParam.getGroup_id(),"已成功建立关系！",false);
    }

    @Override
    public void hug(MessageEventParam messageEventParam) {
        QueryWrapper<PetPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", messageEventParam.getUser_id());
        queryWrapper.eq("group_id", messageEventParam.getGroup_id());
        PetPO petPO = petDao.selectOne(queryWrapper);
        if(petPO.getPetCouple()<=0){
            sendGroupMsg(messageEventParam.getGroup_id(),"你还没有可以贴贴的兔兔哦~ ",false);
            return;
        }
        if(petPO.getPetHugNum()==0){
            sendGroupMsg(messageEventParam.getGroup_id(),"今天不能贴贴啦~",false);
            return;
        }
        QueryWrapper<PetPO> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("user_id", petPO.getPetCouple());
        queryWrapper2.eq("group_id", messageEventParam.getGroup_id());
        PetPO petCouplePo = petDao.selectOne(queryWrapper2);
        petCouplePo.setPetHugNum(petCouplePo.getPetHugNum()-1);
        petPO.setPetHugNum(petPO.getPetHugNum()-1);
        double buff = 5*Math.random();
        petCouplePo.setPetSize(petCouplePo.getPetSize()+buff);
        petPO.setPetSize(petPO.getPetSize()+buff);
        petDao.updateById(petPO);
        petDao.updateById(petCouplePo);
        StringBuilder msg = new StringBuilder();
        msg.append("贴贴~").append("\n")
                        .append(petPO.getPetName()).append(": ").append(String.format("%.2f",petPO.getPetSize())).append("\n")
                        .append(petCouplePo.getPetName()).append(": ").append(String.format("%.2f",petCouplePo.getPetSize()));

        sendGroupMsg(messageEventParam.getGroup_id(), String.valueOf(msg),false);
    }

    @Override
    public void punch(MessageEventParam messageEventParam) {
        LocalDateTime now = LocalDateTime.now();
        LocalTime localTime = now.toLocalTime();
        double hour = localTime.getHour()+ (double)(localTime.getMinute())/60;
        boolean isQualified = (localTime.getHour()>=6&&localTime.getHour()<=8)||(localTime.getHour()==5&&localTime.getMinute()>=30);
        if(isQualified){
            QueryWrapper<PetPO> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_id", messageEventParam.getUser_id());
            queryWrapper.eq("group_id", messageEventParam.getGroup_id());
            PetPO petPO = petDao.selectOne(queryWrapper);
            petPO.setPunchNum(petPO.getPunchNum()+1);
            petPO.setPunchToday(1);
            petPO.setPetSize(petPO.getPetSize()+12-hour*Math.random());
            StringBuilder msg = new StringBuilder();
            msg.append("早安~ 打卡成功！").append("\n")
                    .append("打卡天数").append(petPO.getPunchNum());
            if(petPO.getPetCouple()>0){
                QueryWrapper<PetPO> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("user_id", petPO.getPetCouple());
                queryWrapper2.eq("group_id", messageEventParam.getGroup_id());
                PetPO petCouplePo = petDao.selectOne(queryWrapper2);
                if(petCouplePo.getPunchToday()==1){
                    petCouplePo.setPetHugNum(3);
                    petPO.setPetHugNum(3);
                }else {
                    petCouplePo.setPetHugNum(1);
                    petPO.setPetHugNum(1);
                }
                petDao.updateById(petCouplePo);
                msg.append("贴贴次数:").append(petPO.getPetHugNum());
            }
            petDao.updateById(petPO);
            sendGroupMsg(messageEventParam.getGroup_id(), String.valueOf(msg),false);
        }else {
            sendGroupMsg(messageEventParam.getGroup_id(), "今天来晚啦...",false);
        }
    }

    @Override
    public void breakUp(MessageEventParam messageEventParam) {
        QueryWrapper<PetPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", messageEventParam.getUser_id());
        queryWrapper.eq("group_id", messageEventParam.getGroup_id());
        PetPO petPO = petDao.selectOne(queryWrapper);
        if(petPO.getPetCouple()<=0){
            sendGroupMsg(messageEventParam.getGroup_id(),"你还没有couple tutu哦~",false);
            return;
        }
        QueryWrapper<PetPO> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("user_id", petPO.getPetCouple());
        queryWrapper2.eq("group_id", messageEventParam.getGroup_id());
        PetPO petCouplePo = petDao.selectOne(queryWrapper2);
        petCouplePo.setPetCouple(0);
        petPO.setPetCouple(0);
        petCouplePo.setPetHugNum(0);
        petPO.setPetHugNum(0);
        sendGroupMsg(messageEventParam.getGroup_id(), "伤痕泪累...",false);
    }

    @Override
    public void fight(MessageEventParam messageEventParam) {
        QueryWrapper<PetPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", messageEventParam.getUser_id());
        queryWrapper.eq("group_id", messageEventParam.getGroup_id());
        PetPO petPO = petDao.selectOne(queryWrapper);
        if(null==petPO){
            sendGroupMsg(messageEventParam.getGroup_id(),"你还没有tutu哦~",false);
            return;
        }
        String message = messageEventParam.getMessage();
        Long enemyPetId = Long.parseLong(message.substring(message.indexOf("比划比划[CQ:at,qq=")+"比划比划[CQ:at,qq=".length(),message.length()-1));
        QueryWrapper<PetPO> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("user_id",enemyPetId);
        queryWrapper2.eq("group_id", messageEventParam.getGroup_id());
        PetPO enemyPetPO = petDao.selectOne(queryWrapper2);
        if(null==enemyPetPO){
            sendGroupMsg(messageEventParam.getGroup_id(),"你的对手还没有tutu哦~",false);
            return;
        }
        double petAtk = petPO.getPetSize()*Math.random();
        double enemyAtk = enemyPetPO.getPetSize()*Math.random();
        boolean isArrest = Math.random()*6 <=1;
        double policeAtk = Math.random()*5;
        if(isArrest){
            petPO.setPetSize(petPO.getPetSize()-policeAtk);
            enemyPetPO.setPetSize(enemyPetPO.getPetSize()-policeAtk);
            StringBuilder msg = new StringBuilder();
            msg.append("你们被逮捕了！！！").append("\n")
                            .append(petPO.getPetName()+":").append(String.format("%.2f", petPO.getPetSize())).append("\n")
                            .append(enemyPetPO.getPetName()+":").append(String.format("%.2f", enemyPetPO.getPetSize()));
            petDao.updateById(petPO);
            petDao.updateById(enemyPetPO);
            sendGroupMsg(messageEventParam.getGroup_id(), String.valueOf(msg),false);
            return;
        }

        if(petAtk>+enemyAtk){
            double petVariation = Math.random()*(enemyPetPO.getPetSize());
            double enemyVariation = Math.random()*(petPO.getPetSize());
            petPO.setPetSize(petPO.getPetSize()+petVariation);
            enemyPetPO.setPetSize(enemyPetPO.getPetSize()-enemyVariation);
            petDao.updateById(petPO);
            petDao.updateById(enemyPetPO);
            StringBuilder msg = new StringBuilder();
            msg.append(petPO.getPetName()).append("胜利啦！！！").append("\n")
                    .append(petPO.getPetName()).append("的size: ").append(String.format("%.2f", petPO.getPetSize())).append("\n")
                    .append(petPO.getPetName()).append("的atk: ").append(String.format("%.2f", petAtk)).append("\n")
                    .append(petPO.getPetName()).append("变大了: ").append(String.format("%.2f", petVariation)).append("\n")
                    .append(enemyPetPO.getPetName()).append("的size: ").append(String.format("%.2f", enemyPetPO.getPetSize())).append("\n")
                    .append(enemyPetPO.getPetName()).append("的atk: ").append(String.format("%.2f", enemyAtk)).append("\n")
                    .append(enemyPetPO.getPetName()).append("变小了: ").append(String.format("%.2f", enemyVariation));
            sendGroupMsg(messageEventParam.getGroup_id(), String.valueOf(msg),false);
        }else {
            double petVariation = Math.random()*(petPO.getPetSize());
            double enemyVariation = Math.random()*(enemyPetPO.getPetSize());
            petPO.setPetSize(petPO.getPetSize()-petVariation);
            enemyPetPO.setPetSize(enemyPetPO.getPetSize()+enemyVariation);
            petDao.updateById(petPO);
            petDao.updateById(enemyPetPO);
            StringBuilder msg = new StringBuilder();
            msg.append(enemyPetPO.getPetName()).append("胜利啦！！！").append("\n")
                    .append(petPO.getPetName()).append("的size: ").append(String.format("%.2f", petPO.getPetSize())).append("\n")
                    .append(petPO.getPetName()).append("的atk: ").append(String.format("%.2f", petAtk)).append("\n")
                    .append(petPO.getPetName()).append("变小了: ").append(String.format("%.2f", petVariation)).append("\n")
                    .append(enemyPetPO.getPetName()).append("的size: ").append(String.format("%.2f", enemyPetPO.getPetSize())).append("\n")
                    .append(enemyPetPO.getPetName()).append("的atk: ").append(String.format("%.2f", enemyAtk)).append("\n")
                    .append(enemyPetPO.getPetName()).append("变大了: ").append(String.format("%.2f", enemyVariation));
            sendGroupMsg(messageEventParam.getGroup_id(), String.valueOf(msg),false);
        }
    }

    @Override
    public void selectGroupRank(MessageEventParam messageEventParam) {
        QueryWrapper<PetPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("pet_size");
        queryWrapper.eq("group_id", messageEventParam.getGroup_id());
        List<PetPO> petPOS = petDao.selectList(queryWrapper);
        StringBuilder msg = new StringBuilder();
        msg.append("本群的tutu排行榜：").append("\n");
        for (int i = 0;i < petPOS.size();i++){
            msg.append((i+1)+". "+petPOS.get(i).getPetName()).append(":").append(String.format("%.2f",petPOS.get(i).getPetSize())).append("\n");
        }
        sendGroupMsg(messageEventParam.getGroup_id(), String.valueOf(msg),false);
    }

    @Override
    public void selectMyPet(MessageEventParam messageEventParam) {
        QueryWrapper<PetPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", messageEventParam.getUser_id());
        queryWrapper.eq("group_id", messageEventParam.getGroup_id());
        PetPO petPO = petDao.selectOne(queryWrapper);
        if(null==petPO){
            sendGroupMsg(messageEventParam.getGroup_id(),"你还没有tutu哦~",false);
        }else {
            StringBuilder msg = new StringBuilder();
            msg.append(petPO.getPetName()).append("：").append("\n")
                    .append("name：").append(petPO.getPetName()).append("\n")
                    .append("size：").append(petPO.getPetSize()).append("\n");
            if(petPO.getPetCouple()>0){
                QueryWrapper<PetPO> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("user_id",petPO.getPetCouple());
                queryWrapper2.eq("group_id", messageEventParam.getGroup_id());
                PetPO couplePetPO = petDao.selectOne(queryWrapper2);
                msg.append("couple：").append(couplePetPO.getPetName()).append("\n");
            }
            msg.append("hug_num：").append(petPO.getPetHugNum()).append("\n")
                    .append("punch_num：").append(petPO.getPunchNum());
            sendGroupMsg(messageEventParam.getGroup_id(), String.valueOf(msg),false);
        }
    }
}
