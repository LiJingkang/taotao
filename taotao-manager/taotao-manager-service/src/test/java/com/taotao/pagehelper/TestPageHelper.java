package com.taotao.pagehelper;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Created by Skying on 2017/3/1.
 */
public class TestPageHelper {

    @Test
    public void testPageHelper() throws Exception {
        // 1.mybatis的配置文件中配置分页插件

        // 2.在执行查询之前配置分页条件。使用PageHelper的静态方法
        PageHelper.startPage(1,10);

        // 3.执行查询。
        // 扫描配置文件
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
        TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
        // 创建Example对象 example 就是一个查询条件
        TbItemExample example = new TbItemExample();
//        TbItemExample.Criteria criteria = example.createCriteria();
        List<TbItem> list = itemMapper.selectByExample(example);

        // 4.取分页信息。使用PageInfo的对象取。
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        System.out.print("总记录数：" + pageInfo.getTotal());
        System.out.print("总计页数：" + pageInfo.getPages());
        System.out.print("返回的记录数：" + list.size());

        // 5.可以得到分页结果和 total 总数。
    }
}
