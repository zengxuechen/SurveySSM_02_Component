package com.atguigu.survey.component.service.m;

import com.atguigu.survey.component.dao.i.TbCustTestResultMapper;
import com.atguigu.survey.component.service.i.CustTestResultService;
import com.atguigu.survey.entities.zhyq.TbCustTestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/1 17:02
 */
@Service
public class CustTestResultServiceImpl implements CustTestResultService {

    @Autowired
    TbCustTestResultMapper custTestResultMapper;

    public Integer saveCustTestResult(Map<String, Object> map) {

        Integer integer = custTestResultMapper.saveCustTestResult(map);

        return integer;
    }

    public List<Map<String, Object>> queryResultByTypeCode(Map<String, Object> map){
    	List<Map<String, Object>> list = custTestResultMapper.queryResultByTypeCode(map);
        return list;
    }

    public TbCustTestResult getTbCustTestResultByTestPaperId(Integer id) {
        TbCustTestResult result =  custTestResultMapper.getTbCustTestResultByTestPaperId(id);
        return result;
    }

    public List<TbCustTestResult> getTbCustTestResultListByUserId(String userId) {
        return null;
    }
    
    public Integer queryResultByUserIdAndPaperId(Map<String, Object> map1) {
    	return custTestResultMapper.queryResultByUserIdAndPaperId(map1);
    }
}
