package com.sipc.events.Util;

import com.sipc.events.dao.UnmutedGroupDao;
import com.sipc.events.entity.po.UnmutedGroupPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class GetUnmutedGroupUtil {
    @Autowired
    private  UnmutedGroupDao unmutedGroupDao;
    public  List<Integer> getUnmutedGroup(){
        List<Integer> groupList = new ArrayList<>();
        List<UnmutedGroupPo> groupPos = unmutedGroupDao.selectList(null);
        groupPos.stream().forEach(p ->groupList.add(p.getGroupId()));
        return groupList;
    }
}
