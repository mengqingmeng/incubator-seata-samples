package org.apache.seata.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 库存实体类
 */
@Data
@TableName("stock_tbl")
public class Stock {
    @TableId
    private Integer id;
    private String commodityCode;
    private Integer count;
}
