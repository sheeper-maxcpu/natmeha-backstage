package com.lanqiao.natmeha.controller;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.model.TbNatmehaHotspot;
import com.lanqiao.natmeha.service.TbNatmehaHotspotDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
@RequestMapping("/start")
public class TbNatmehaHotspotController {
    /*
     * 启动科员“智慧”国医堂惠民信息系统的信息处理页面
     * */
    @RequestMapping("/clerk_page")
    public String clerk_page() {
        return "tbSolarClerk/solar_clerk";
    }

    //注入对象
    @Autowired
    private TbNatmehaHotspotDaoService tbNatmehaHotspotDaoService;

    @RequestMapping("/solar_clerk_code")
    public String selectByPage(Integer pageNum, Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        //System.out.println(neirou);
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setDataType("0");
        tbNatmehaHotspot.setNeirou(neirou);
        Page<TbNatmehaHotspot> tbNatmehaHotspots = this.tbNatmehaHotspotDaoService.selectByPage(tbNatmehaHotspot, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaHotspots", tbNatmehaHotspots);
        return "tbSolarClerk/solar_clerk_code";
    }

    //跳转增加节气养生界面
    @RequestMapping("/solar_addimage")
    public String addpage_solar() {
        return "tbSolarClerk/solar_clerk_add";
    }

    //添加新的数据
    @RequestMapping("/solar_insert")
    public String solar_insert(String hotspotTitle,String creater,String hotspotContent,
                               String datasource,String savebtn,String putbtn,String reset) throws Exception {
        String dataType = "0";//0代表节气养生 ， 1代表自我保健，2代表药膳食疗
        //如果是保存按钮，即是已保存状态 0，提交状态即是 1
        if (savebtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataStatus("0");
            tbNatmehaHotspot.setDataType(dataType);
            tbNatmehaHotspot.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemcreateat = new java.sql.Date(ite.getTime());
            tbNatmehaHotspot.setItemcreateat(itemcreateat);
            tbNatmehaHotspot.setUserCode("5566");
            this.tbNatmehaHotspotDaoService.solar_insert_code(tbNatmehaHotspot);
            return "redirect:/start/solar_clerk_code";
        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataStatus("1");//1代表的是待市级审核，即科员审核
            tbNatmehaHotspot.setDataType(dataType);
            tbNatmehaHotspot.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemcreateat = new java.sql.Date(ite.getTime());
            tbNatmehaHotspot.setItemcreateat(itemcreateat);
            tbNatmehaHotspot.setUserCode("5566");
            this.tbNatmehaHotspotDaoService.solar_insert_code(tbNatmehaHotspot);
            return "redirect:/start/solar_clerk_code";
        } else if (reset != null) {
            return "redirect:/start/solar_clerk_code";
        }
        return null;
    }

    //通过itemID查看信息，如果是保存状态的信息，可以进行更改，然后提交或者选择保存
    @RequestMapping("/solar_clerck_select")
    public String solar_select(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbSolarClerk/solar_clerk_see";
    }

    //查询需要修改保存状态下的信息
    @RequestMapping("/solar_clerck_update")
    public String solar_clerck_update(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbSolarClerk/solar_clerk_update";
    }

    //修改保存状态下的信息,并且确定是保存还是提交
    @RequestMapping("/solar_updatecode")
    public String solar_updatecode(Integer itemid,String hotspotTitle,String creater,String hotspotContent,
                                   String datasource,String savebtn,String putbtn,String reset) throws Exception {

        String dataType = "0";//0代表节气养生 ， 1代表自我保健，2代表药膳食疗
        //如果是保存按钮，即是已保存状态 0，提交状态（待县级审核）即是 1，县级审核不通过2，待市级审核3，
        // 市级审核不通过4，待省级审核5，省级审核不通过6，省级通过7，管理员发布8,
        if (savebtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            //String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            //tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataStatus("0");
            tbNatmehaHotspot.setDataType(dataType);
            tbNatmehaHotspot.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaHotspot.setItemupdateat(itemupdateat);
            tbNatmehaHotspot.setUserCode("5566");
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/solar_clerk_code";//修改成功后返回数据主页面
        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            //String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataStatus("1");//1代表的是待市级审核，即科员审核
            tbNatmehaHotspot.setDataType(dataType);
            tbNatmehaHotspot.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaHotspot.setItemupdateat(itemupdateat);
            tbNatmehaHotspot.setUserCode("5566");
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/solar_clerk_code";
        } else if (reset != null) {
            return "redirect:/start/solar_clerk_code";
        }
        return null;
    }

    //管理员节气养生信息页面直接提交数据给上一级审核,只需要改变数据状态就可
    @RequestMapping("/solar_clerck_directUpdate/{itemid}")
    @ResponseBody
    public int solar_clerck_directUpdate(@PathVariable("itemid") Integer itemid) {
        //String dataType = "0";//0代表节气养生 ， 1代表自我保健，2代表药膳食疗
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("1");//1代表的是待县级审核，即科员审核
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }

    //管理员删除信息
    @RequestMapping("/solar_clerk_deleteByItemid/{itemid}")
    @ResponseBody
    public int solar_clerk_deleteByItemid(@PathVariable("itemid") Integer itemid) {
        int i = this.tbNatmehaHotspotDaoService.deleteByItemid(itemid);
        if (i>0) {
            return i;
        }else {
            return 0;
        }
    }

    //管理员发布信息
    @RequestMapping("/solar_clerck_release/{itemid}")
    @ResponseBody
    public int solar_clerck_release(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("8");//1代表的是待市级审核，即科员审核,8代表发布
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }

    //管理员下架发布信息
    @RequestMapping("/solar_clerck_norelease/{itemid}")
    @ResponseBody
    public int solar_clerck_norelease(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("7");//1代表的是待市级审核，即科员审核,8代表发布，状态变为省级审核通过
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }


    /*
     * 启动县级机构“智慧”国医堂惠民信息系统的信息处理页面
     * */
    @RequestMapping("/county_page")
    public String county_page() {
        return "tbSolarCounty/solar_county";
    }

    //县级机构显示信息页面
    @RequestMapping("/solar_county_code")
    public String solar_county_codeselectBypage(Integer pageNum,Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setDataType("0");
        tbNatmehaHotspot.setDataStatus("1");
        tbNatmehaHotspot.setNeirou(neirou);
        Page<TbNatmehaHotspot> tbNatmehaHotspots = this.tbNatmehaHotspotDaoService.county_selectByPage(tbNatmehaHotspot, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaHotspots", tbNatmehaHotspots);
        return "tbSolarCounty/solar_county_code";
    }
}