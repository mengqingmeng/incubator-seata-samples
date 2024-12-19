package org.apache.seata.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName("account_tbl")
public class Account {
    @TableId
    private Integer id;

    private String userId;

    private Integer money;
}
