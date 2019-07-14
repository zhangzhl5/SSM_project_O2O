package com.imooc.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;

public class AreaDaoTest extends BaseTest{
	@Autowired 
	private AreaDao areaDao;
	
	@Test
	public void testQueryArea() {
		List<Area> arealist = areaDao.queryArea();
		Area area = arealist.get(0);
		System.out.println(area.getAreaName());
		assertEquals(3, arealist.size());
	}

}
