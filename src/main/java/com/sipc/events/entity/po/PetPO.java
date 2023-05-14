package com.sipc.events.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_pet")
public class PetPO {
    private int id;
    @TableField(value = "user_id")
    private long userId;
    @TableField(value = "group_id")
    private Integer groupId;
    @TableField(value = "pet_name")
    private String petName;
    @TableField(value = "pet_size")
    private double petSize;
    @TableField(value = "pet_couple")
    private long petCouple;
    @TableField(value = "pet_hug_num")
    private Integer petHugNum;
    @TableField(value = "punch_num")
    private Integer punchNum;
    @TableField(value = "punch_today")
    private int punchToday;
}
