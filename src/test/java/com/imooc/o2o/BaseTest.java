package com.imooc.o2o;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/*
 * 配置Spring和Junit整合，junit启动时加载SPringIOC容器
 */
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring的配置文件在哪
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class BaseTest {

}
