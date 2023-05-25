package com.sipc.events.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_driftingbottle")
public class BottlePo {
    @TableId(type = IdType.AUTO)
    private int id;
     @TableField(value = "group_id")
    private int groupId;
     @TableField(value = "thrower_id")
    private long throwerId;
     @TableField(value = "thrower_name")
    private String throwerName;
     @TableField(value = "photo_link")
    private String photoLink;
     @TableField(value = "bottle_msg")
    private String bottleMsg;

}
