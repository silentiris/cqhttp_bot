package com.sipc.events.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sipc.events.entity.po.BottlePo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BottleDao extends BaseMapper<BottlePo> {
}
