/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.seata.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.seata.core.context.RootContext;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.seata.entity.Stock;
import org.apache.seata.mapper.StockMapper;
import org.apache.seata.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DubboService
public class StorageServiceImpl implements StorageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StorageService.class);

    private final StockMapper stockMapper;

    public StorageServiceImpl(StockMapper stockMapper) {
        this.stockMapper = stockMapper;
    }

    @Override
    public void deduct(String commodityCode, int count) {
        LOGGER.info("Stock Service Begin ... xid: " + RootContext.getXID());
        LOGGER.info("Deducting inventory SQL: update stock_tbl set count = count - {} where commodity_code = {}", count,
                commodityCode);
        LambdaQueryWrapper<Stock> queryWrapper = Wrappers.<Stock>lambdaQuery()
                .eq(Stock::getCommodityCode, commodityCode)
                .last("limit 1");
        Stock stock = stockMapper.selectOne(queryWrapper);
        if (stock == null) {
            LOGGER.warn("stock not found");
            return;
        }

        stock.setCount(stock.getCount() - count);
        stockMapper.updateById(stock);

        LOGGER.info("Stock Service End ... ");

    }

    @Override
    public void batchDeduct(String commodityCode, int count) {
        LOGGER.info("Stock Service Begin ... xid: " + RootContext.getXID());
        LOGGER.info("Deducting inventory SQL: update stock_tbl set count = count - {} where commodity_code = {}", count,
                commodityCode);

        LambdaQueryWrapper<Stock> queryWrapper = Wrappers.<Stock>lambdaQuery()
                .eq(Stock::getCommodityCode, commodityCode)
                .eq(Stock::getCount, count).last("limit 1");
        Stock stock = stockMapper.selectOne(queryWrapper);
        if (stock == null) {
            LOGGER.warn("stock not found");
            return;
        }

        stock.setCount(stock.getCount() - count);
        stockMapper.updateById(stock);

        LOGGER.info("Stock Service End ... ");

    }

}