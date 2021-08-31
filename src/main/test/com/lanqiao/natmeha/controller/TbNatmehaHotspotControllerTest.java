package com.lanqiao.natmeha.controller;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.model.TbNatmehaHotspot;
import com.lanqiao.natmeha.service.TbNatmehaHotspotService;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @author barry
 * @create 2021-08-21 22:40
 */
@SpringBootTest
public class TbNatmehaHotspotControllerTest {
    TbNatmehaHotspotService tbNatmehaHotspotService;
    @Test
    public void test1(){
        Page<TbNatmehaHotspot> tbNatmehaHotspots = this.tbNatmehaHotspotService.selectAllByPage("拔罐","3", 1, 2);
        for (TbNatmehaHotspot tbNatmehaHotspot : tbNatmehaHotspots) {
            System.out.println(tbNatmehaHotspot);
        }
    }
}