package com.atguigu.survey.component.service.m;

import com.atguigu.survey.component.dao.i.TbCustTestResultMapper;
import com.atguigu.survey.component.service.i.CustTestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
