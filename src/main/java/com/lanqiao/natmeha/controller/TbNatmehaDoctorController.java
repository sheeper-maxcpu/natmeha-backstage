package com.lanqiao.natmeha.controller;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.model.TbNatmehaDoctor;
import com.lanqiao.natmeha.model.TbNatmehaFile;
import com.lanqiao.natmeha.model.User;
import com.lanqiao.natmeha.service.TbFileService;
import com.lanqiao.natmeha.service.TbNatmehaDoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @auther cheng
 * @create 2021-08-24 16:46
 */
@Controller
@RequestMapping("/serviceTeam")
@Slf4j
public class TbNatmehaDoctorController {

    @Autowired
    TbNatmehaDoctorService tbNatmehaDoctorService;
    @Autowired
    TbFileService tbFileService;

    @GetMapping
    public String selectAllDoctor(Model model,
                                  TbNatmehaDoctor doctor,
                                  @RequestParam(value = "pageNum",required = false) Integer pageNum,
                                  HttpSession session,
                                  String neirou){
        if (pageNum == null){
            pageNum = 1;
            // 保留当前的查询条件，供接下来点击分页链接时使用
            session.setAttribute("doctorCondition",doctor);
        }else {
            if (session.getAttribute("doctorCondition") != null) {
                // 点击分页链接时取回查询条件
                doctor = (TbNatmehaDoctor) session.getAttribute("doctorCondition");
            }
        }
        doctor.setNeirou(neirou);
        final Page<TbNatmehaDoctor> tbNatmehaDoctor = this.tbNatmehaDoctorService.selectAllDoctor(doctor,pageNum,6);
        for (int i = 0; i < tbNatmehaDoctor.size(); i++) {
            String itemcode = tbNatmehaDoctor.get(i).getItemcode();
            TbNatmehaFile dateCode = tbFileService.selectFile(itemcode);
            tbNatmehaDoctor.get(i).setTbNatmehaFile(dateCode);

        }

        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaDoctorList", tbNatmehaDoctor);
        return "serviceTeam/serviceTeam";
    }

    //删除
    @RequestMapping ("/{itemid}")
    @ResponseBody
    public int deleteNum(@PathVariable("itemid") Integer itemid){

//        System.out.println(itemid);
        int deleteDoctor = this.tbNatmehaDoctorService.deleteDoctor(itemid);
        if (deleteDoctor>0) {
            return deleteDoctor;
        }else {
            return 0;
        }

    }

    @GetMapping("/add")
    public String add() {
        return "serviceTeam/doctorAdd";
    }

    @RequestMapping("/insertDoctor")
    public String insertDoctor(@RequestParam("file") MultipartFile file, Model model,
                               String doctorName, String doctorTitle,
                               String numType, String doctorTreatment) throws IOException {

        TbNatmehaDoctor doctor = new TbNatmehaDoctor();
        User user = (User) model.getAttribute("user");
        String itemcode = UUID.randomUUID().toString();
        doctor.setItemcode(itemcode);
        doctor.setDoctorName(doctorName);
        doctor.setDoctorTitle(doctorTitle);
        doctor.setNumType(numType);

        //doctor.setOrgCode(user.getOrgCode());
        doctor.setDoctorTreatment(doctorTreatment);
        System.out.println("doctor==================>" + doctor);
        int i = tbNatmehaDoctorService.insertDoctor(doctor);

        String originalFilename = file.getOriginalFilename();
        if (!originalFilename.equals("")) {
            System.out.println(originalFilename);
            //获取源文件前缀
            String fileNamePrefix = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            //获取源文件后缀
            String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            //将源文件前缀之后加上时间戳避免重名
            String newFileNamePrefix = fileNamePrefix + "_" + new Date().getTime();
            //得到上传后新文件的文件名
            String newFileName = newFileNamePrefix + fileNameSuffix;
            //获取要保存的文件夹路径
            String path = "/root/images";
            //在指定路径下，产生一个指定名称的新文件
            File newfile = new File(path, newFileName);
            file.transferTo(newfile);
            //存入数据库的图片地址
            String sqlFilePath = "http://142.4.123.27:8081/";
            //存入数据库的路径，格式如：http://114.55.92.180/portals/images/ffilepackge/u2363_1629798462733.png
            String filePath = sqlFilePath + newFileName;
            String Filename = newFileName;//格式如：u2363_1629798462733.png
            String itemcode2 = UUID.randomUUID().toString();
            TbNatmehaFile tbNatmehaFile = new TbNatmehaFile();
            tbNatmehaFile.setItemcode(itemcode2);
            tbNatmehaFile.setDataCode(doctor.getItemcode());
            tbNatmehaFile.setFileName(Filename);
            tbNatmehaFile.setFileType(fileNameSuffix);
            tbNatmehaFile.setFileSize((double) file.getSize());
            tbNatmehaFile.setFilePath(filePath);
            System.out.println(tbNatmehaFile);
            System.out.println("file=============================>" + file);
            int insert = this.tbFileService.insert(tbNatmehaFile);
        }

        return "redirect:/serviceTeam";
    }

    //通过修改按钮，跳转服务团队修改页面
    @RequestMapping("/doctorUpdate")
    public String doctorUpdate(Integer itemid,Model model) {
        TbNatmehaDoctor tbNatmehaDoctor = this.tbNatmehaDoctorService.selectByItemid(itemid);

        String itemcode = tbNatmehaDoctor.getItemcode();
        TbNatmehaFile dateCode = tbFileService.selectFile(itemcode);
        tbNatmehaDoctor.setTbNatmehaFile(dateCode);
        System.out.println(tbNatmehaDoctor.getTbNatmehaFile().getFilePath());

//        TbNatmehaFile tbNatmehaFile=tbNatmehaDoctor.setTbNatmehaFile(dateCode);
//        String filePath2=tbNatmehaFile.getFilePath();
//        String filePath = tbNatmehaDoctor.getTbNatmehaFile().getFilePath();
//        String filePath = tbFileService.selectFile(tbNatmehaDoctor.getItemcode()).getFilePath();

        model.addAttribute("tbNatmehaDoctor", tbNatmehaDoctor);

        return "serviceTeam/updateDoctor";
    }


    //修改保存状态下的信息
    @RequestMapping("/doctorUpdatecode")
    public String doctorUpdatecode(@RequestParam("file") MultipartFile file,String itemcode,
                                   Integer itemid,String doctorName,String doctorTitle,String numType,String deptCode,
                                  String doctorTreatment,String savebtn,String reset,
                                   String filePath) throws Exception {

        System.out.println(file);
        if (savebtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
//            String itemcode = UUID.randomUUID().toString();
            TbNatmehaDoctor tbNatmehaDoctor = new TbNatmehaDoctor();
//            tbNatmehaDoctor.setItemcode(itemcode);
            tbNatmehaDoctor.setItemcode(itemcode);
            tbNatmehaDoctor.setItemid(itemid);
            tbNatmehaDoctor.setDoctorName(doctorName);
            tbNatmehaDoctor.setDoctorTitle(doctorTitle);
            tbNatmehaDoctor.setNumType(numType);
            tbNatmehaDoctor.setDeptCode(deptCode);
            tbNatmehaDoctor.setDoctorTreatment(doctorTreatment);

            String originalFilename = file.getOriginalFilename();
            if (!originalFilename.equals("")) {
                System.out.println(originalFilename);
                //获取源文件前缀
                String fileNamePrefix = originalFilename.substring(0, originalFilename.lastIndexOf("."));
                //获取源文件后缀
                String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                //将源文件前缀之后加上时间戳避免重名
                String newFileNamePrefix = fileNamePrefix + "_" + new Date().getTime();
                //得到上传后新文件的文件名
                String newFileName = newFileNamePrefix + fileNameSuffix;
                //获取要保存的文件夹路径
                String path = "E:\\1蓝桥\\国医堂";
                //在指定路径下，产生一个指定名称的新文件
                File newfile = new File(path, newFileName);
                file.transferTo(newfile);
                //存入数据库的图片地址
                String sqlFilePath = "http://142.4.123.27:8081/";
                //存入数据库的路径，格式如：http://114.55.92.180/portals/images/ffilepackge/u2363_1629798462733.png
                filePath = sqlFilePath + newFileName;
                String Filename = newFileName;//格式如：u2363_1629798462733.png
//                String itemcode2 = UUID.randomUUID().toString();
                TbNatmehaFile tbNatmehaFile = new TbNatmehaFile();
//                tbNatmehaFile.setItemcode(itemcode2);
                tbNatmehaFile.setDataCode(tbNatmehaDoctor.getItemcode());
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFileType(fileNameSuffix);
                tbNatmehaFile.setFileSize((double) file.getSize());
                tbNatmehaFile.setFilePath(filePath);
                System.out.println("daooooooo+++++++》"+filePath);
                System.out.println(tbNatmehaFile);
                System.out.println("file=============================>" + file);
                System.out.println(tbNatmehaFile);
                int update = this.tbFileService.updateFilePath(tbNatmehaFile);
            }

//            tbNatmehaDoctor.getTbNatmehaFile().getFilePath();
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaDoctor.setItemupdateat(itemupdateat);
            tbNatmehaDoctor.setUserCode("5566");
            this.tbNatmehaDoctorService.updateDoctor(tbNatmehaDoctor);
            return "redirect:/serviceTeam";//修改成功后返回数据主页面
        }else if (reset != null) {
            return "redirect:/serviceTeam";
        }
        return null;
    }



}
