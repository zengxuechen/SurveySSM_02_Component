package com.atguigu.survey.component.service.m;

import com.atguigu.survey.component.dao.i.TbCustTestPaperMapper;
import com.atguigu.survey.component.service.i.CustTestPaperService;
import com.atguigu.survey.entities.zhyq.TbCustTestPaper;
import com.atguigu.survey.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/1 13:24
 */
@Service
public class CustTestPaperServiceImpl implements CustTestPaperService {

    @Autowired
    TbCustTestPaperMapper custTestPaperMapper;

    public Page<TbCustTestPaper> queryPaperTypeByCode(String pageNoStr, String typeCode) {

        Integer count = custTestPaperMapper.queryPaperTypeByCodeCount(typeCode);

        Page<TbCustTestPaper> page = new Page<TbCustTestPaper>(pageNoStr,count);

        int pageSize = page.getPageSize();

        int startIndex = page.getStartIndex();

        Map<String,Object> map = new HashMap<String,Object>();

        map.put("typeCode",typeCode);
        map.put("startIndex",startIndex);
        map.put("pageSize",pageSize);

        List<TbCustTestPaper> list =
                custTestPaperMapper.queryPaperTypeByCode(map);
        page.setList(list);
        return page;
    }

    public TbCustTestPaper getCustTestResultAndPaperInfoByTestResultId(int i) {
        TbCustTestPaper custTestPaper = custTestPaperMapper.getCustTestResultAndPaperInfoByTestResultId(i);
        return custTestPaper;
    }

    public List<TbCustTestPaper> getCustTestPaperList(List<Integer> ids) {
        return custTestPaperMapper.getCustTestPaperList(ids);
    }


}
