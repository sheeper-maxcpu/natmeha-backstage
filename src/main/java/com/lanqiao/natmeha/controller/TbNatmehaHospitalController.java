package com.lanqiao.natmeha.controller;

import com.lanqiao.natmeha.model.TbNatmehaFile;
import com.lanqiao.natmeha.model.TbNatmehaHospital;
import com.lanqiao.natmeha.model.User;
import com.lanqiao.natmeha.service.TbFileService;
import com.lanqiao.natmeha.service.TbNatmehaHospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * @Author:YangZiYang
 * @create 2021/8/21 13:06
 */
@Controller
@RequestMapping("/hospital")
public class TbNatmehaHospitalController {
    @Autowired
    TbNatmehaHospitalService tbNatmehaHospitalService;
    @Autowired
    TbFileService tbFileService;

    @GetMapping
    public String insets( Model model, TbNatmehaHospital hospital) {
        model.addAttribute("hospital", hospital);
        return "InformationMaintenance";
    }

    @RequestMapping("/insertHospital")
    public String insertHospital(@RequestParam("file") MultipartFile file, Model model,
                                 String hospitalName, String hospitalPhone, String hospitalPro,
                                 String hospitalCity, String hospitalCountry, String hospitalAdress,
                                 String introduce, String savebtn, String putbtn, String reset) throws IOException, InterruptedException {
        TbNatmehaHospital hospital = new TbNatmehaHospital();
        Thread.sleep(2000);
        hospital.setHospitalName(hospitalName);
        hospital.setHospitalPhone(hospitalPhone);
        hospital.setHospitalPro(hospitalPro);
        hospital.setHospitalCity(hospitalCity);
        hospital.setHospitalCountry(hospitalCountry);
        hospital.setHospitalAdress(hospitalAdress);
        byte[] introduceBytes = introduce.getBytes();
        hospital.setIntroduce(introduceBytes);
        System.out.println(hospital);
        int i = this.tbNatmehaHospitalService.insertNewHospital(hospital);

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
            String path = "F:\\dev\\nginx-1.20.1\\html\\portals\\images";
            //在指定路径下，产生一个指定名称的新文件
            File newfile = new File(path, newFileName);
            file.transferTo(newfile);
            //存入数据库的图片地址
            String sqlFilePath = "http://114.55.92.180/portals/images/ffilepackge/";
            //存入数据库的路径，格式如：http://114.55.92.180/portals/images/ffilepackge/u2363_1629798462733.png
            String filePath = sqlFilePath + newFileName;
            String Filename = newFileName;//格式如：u2363_1629798462733.png
            String itemcode2 = UUID.randomUUID().toString();
            TbNatmehaFile tbNatmehaFile = new TbNatmehaFile();
            tbNatmehaFile.setItemcode(itemcode2);
            tbNatmehaFile.setDataCode(hospital.getItemcode());
            tbNatmehaFile.setFileName(Filename);
            tbNatmehaFile.setFileType(fileNameSuffix);
            tbNatmehaFile.setFileSize((double) file.getSize());
            tbNatmehaFile.setFilePath(filePath);
            System.out.println(tbNatmehaFile);
            int insert = this.tbFileService.insert(tbNatmehaFile);
        }
        return "redirect:/hospital";
    }

}
