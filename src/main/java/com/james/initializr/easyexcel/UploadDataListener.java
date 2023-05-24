package com.james.initializr.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 模板的读取类
 *
 * @author Jiaju Zhuang
 */
@Slf4j
@Data
public class UploadDataListener implements ReadListener<UploadData> {
    private List<UploadData> cachedDataList = new ArrayList<>();
    private UploadDAO uploadDAO;
    public UploadDataListener(UploadDAO uploadDAO) {
        this.uploadDAO = uploadDAO;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. It is same as {@link AnalysisContext#readRowHolder()}
     * @param context 上下文
     */
    @Override
    public void invoke(UploadData data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        BigDecimal decimal = data.getDecimal();
        log.info("decimal scale : {}", decimal.scale());
        cachedDataList.add(data);
        // 能够获得当前的行索引
    }

    @Override
    public void extra(CellExtra cellExtra, AnalysisContext analysisContext) {

    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context 填充结果
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        log.info("所有数据解析完成！");
    }

    @Override
    public boolean hasNext(AnalysisContext analysisContext) {
        return true;
    }

    @Override
    public void onException(Exception e, AnalysisContext context) {
        if (e instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) e;
            Integer rowIndex = excelDataConvertException.getRowIndex();
            Integer columnIndex = excelDataConvertException.getColumnIndex();
            log.error("类型错误 rowIndex : {} columnIndex : {}", rowIndex, columnIndex);
        } else {
            Integer rowIndex = context.readRowHolder().getRowIndex();
            log.error("类型错误 rowIndex : {} 必填项未填", rowIndex);
        }
    }

    @Override
    public void invokeHead(Map<Integer, CellData> map, AnalysisContext analysisContext) {}
}


