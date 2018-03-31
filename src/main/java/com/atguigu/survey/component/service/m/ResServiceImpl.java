package com.atguigu.survey.component.service.m;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.component.dao.i.ResMapper;
import com.atguigu.survey.component.service.i.ResService;
import com.atguigu.survey.entities.manager.Res;

@Service
public class ResServiceImpl implements ResService {

	@Autowired
	private ResMapper resMapper ;

	public boolean checkResExist(String servletPath) {
		return resMapper.checkResExist(servletPath)>0;
	}

	public Integer getMaxPos() {
		return resMapper.getMaxPos();
	}

	public Integer getMaxCode(Integer maxPos) {
		return resMapper.getMaxCode(maxPos);
	}

	public List<Res> queryAllRes() {
		return resMapper.selectAll();
	}

	public void batchDelete(List<Integer> resList) {
		resMapper.batchDelete(resList);
	}

	public boolean updateStatus(Integer resId) {
		resMapper.updateStatus(resId);
		return resMapper.getStatus(resId);
	}

	public Res getResByServletpath(String servletPath) {			
		return resMapper.getResByServletpath(servletPath);
	}

	public void saveEntity(Res t) {
		resMapper.insert(t);
	}

}
