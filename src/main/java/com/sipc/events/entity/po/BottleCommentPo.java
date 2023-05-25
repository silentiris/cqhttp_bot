package com.sipc.events.entity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_bottlecomment")
public class BottleCommentPo {
    @TableField(value = "bottle_id")
    int bottleId;
    @TableField(value = "comment_msg")
    String commentMsg;
    @TableField(value = "commenter_id")
    long commenterId;
    @TableField(value = "commenter_name")
    String commenterName;
    @TableField(value = "comment_pic_url")
    String commentPicUrl;
    String date;

}
