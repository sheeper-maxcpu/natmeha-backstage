package com.lanqiao.natmeha.controller;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.model.TbNatmehaDoctor;
import com.lanqiao.natmeha.model.TbNatmehaFile;
import com.lanqiao.natmeha.model.TbNatmehaSignalSource;
import com.lanqiao.natmeha.model.User;
import com.lanqiao.natmeha.service.TbNatmehaDoctorService;
import com.lanqiao.natmeha.service.TbNatmehaSignalSourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @auther cheng
 * @create 2021-08-23 21:25
 */
@Controller
@RequestMapping("/numManage")
@Slf4j
public class TbNatmehaSignalSourceController {

    @Autowired
    TbNatmehaSignalSourceService tbNatmehaSignalSourceService;
    @Autowired
    TbNatmehaDoctorService tbNatmehaDoctorService;

    @GetMapping
    public String selectAllNum(Model model,
                                  TbNatmehaSignalSource num,
                                  @RequestParam(value = "pageNum",required = false) Integer pageNum,
                                  HttpSession session
            , String searchYear,String status
    ) throws ParseException {
        if (pageNum == null){
            pageNum = 1;
            // 保留当前的查询条件，供接下来点击分页链接时使用
            session.setAttribute("numCondition",num);
        }else {
            if (session.getAttribute("numCondition") != null) {
                // 点击分页链接时取回查询条件
                num = (TbNatmehaSignalSource) session.getAttribute("numCondition");
            }
        }

        if ("1".equals(searchYear)) {
            searchYear = null;
        }
        if ("1".equals(status)) {
            status = null;
        }
        num.setSearchYear(searchYear);
        num.setStatus(status);
        final Page<TbNatmehaSignalSource> tbNatmehaSignalSources = this.tbNatmehaSignalSourceService.selectAllNum(num, pageNum, 6);
        for (int i = 0; i < tbNatmehaSignalSources.size(); i++) {
            String doctorCode = tbNatmehaSignalSources.get(i).getDoctorCode();
            TbNatmehaDoctor doctorName = tbNatmehaDoctorService.getDoctorName(doctorCode);
            tbNatmehaSignalSources.get(i).setItemid(i+1);
            tbNatmehaSignalSources.get(i).setTbNatmehaDoctor(doctorName);
        }
        model.addAttribute("searchYear", searchYear);
        model.addAttribute("status", status);
        model.addAttribute("tbNatmehaSignalSourcesList", tbNatmehaSignalSources);

//        final String name = this.tbNatmehaSignalSourceService.getName(doctorName);
//        model.addAttribute("name",name);
        return "numManage/numManage";
    }

    //删除
    @RequestMapping ("/delete/{itemid}")
    @ResponseBody
    public int deleteNum(@PathVariable("itemid") Integer itemid){

        int deleteNum = this.tbNatmehaSignalSourceService.deleteNum(itemid);
        if (deleteNum>0) {
            return deleteNum;
        }else {
            return 0;
        }
    }

    @RequestMapping("/insertNum")
    public String insertNumCode(Model model,String doctorName,
                               Date registerDate, String registerType) throws IOException {

        TbNatmehaSignalSource num = new TbNatmehaSignalSource();
        User user = (User) model.getAttribute("user");
        String itemcode = UUID.randomUUID().toString();
        num.setItemcode(itemcode);

        List<TbNatmehaDoctor> doctor= this.tbNatmehaDoctorService.getDoctor(doctorName);
//        System.out.println("医生信息：" + doctor);
        num.setDoctorCode(doctor.get(0).getItemcode());

//        num.setTbNatmehaDoctor(doctor);
//        TbNatmehaSignalSource tbNatmehaSignalSource = new TbNatmehaSignalSource();
//        model.addAttribute("tbNatmehaSignalSource",tbNatmehaSignalSource);

//        doctorName=doctor.getDoctorName();
        System.out.println("doctorName" + doctorName);

        num.setRegisterType(registerType);

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");

        num.setRegisterDate(registerDate);

        num.setRegisterCount(0);//设置初始预约量为0


        Date date = new Date(System.currentTimeMillis());
        System.out.println(formatter.format(date));
        if (registerDate.after(date)){
            num.setStatus("有效");
        }else{
            num.setStatus("失效");
        }

        num.setUserCode("9f1992a0-f60d-4738-b15e-b17e8e10717e");
        System.out.println("num==================>" + num);
        this.tbNatmehaSignalSourceService.insertNum(num);
        return "redirect:/numManage";
    }


    //修改保存状态下的信息
    @RequestMapping("/numUpdatecode")
    public String numUpdatecode(Model model, HttpSession session,
                                String itemcode, Date registerDate, String registerType,
                                String savebtn, String reset) throws Exception {

        TbNatmehaSignalSource tbNatmehaSignalSource = this.tbNatmehaSignalSourceService.selectByItemid(itemcode);
        model.addAttribute("tbNatmehaSignalSource", tbNatmehaSignalSource);


        Thread.sleep(2000);//等待2秒后在执行下一步代码
        //自动生成唯一标识码
        TbNatmehaSignalSource num = new TbNatmehaSignalSource();
        num.setRegisterDate(registerDate);
        num.setRegisterType(registerType);

//            //创建时间
//            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//            String ict = df.format(new Date());
//            Date ite = null;
//            ite = df.parse(ict);
//            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
//            num.setItemupdateat(itemupdateat);
        User logUser = (User) session.getAttribute("logUser");
        num.setItemcode(itemcode);
        num.setUserCode(logUser.getItemcode());
        this.tbNatmehaSignalSourceService.updateNum(num);
        return "redirect:/numManage";//修改成功后返回数据主页面
    }

}
