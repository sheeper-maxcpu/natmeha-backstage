package com.lanqiao.natmeha.controller;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.model.TbNatmehaFile;
import com.lanqiao.natmeha.model.TbNatmehaHotspot;
import com.lanqiao.natmeha.service.TbNatmehaFileService;
import com.lanqiao.natmeha.service.TbNatmehaHotspotService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * @author barry
 */
@RequestMapping("/tbNatmehaHotspot")
@Controller
public class TbNatmehaHotspotController {

    /**
     * 服务对象
     */
    @Autowired
    private TbNatmehaHotspotService tbNatmehaHotspotService;

    /**
     * 服务对象
     */
    @Resource
    private TbNatmehaFileService tbNatmehaFileService;


    /*--李荣锋中医文化begin---*/
    /**
     * @param model
     * @param pageNum
     * @param message
     * @param session
     * @return 县级国医堂信息
     */
/*    @RequestMapping("/countryMan")  //  /tbNatmehaHotspot/countryMan
    public String selectAllByPage(Model model,
                                  Integer pageNum,
                                  String message,
                                  String startStr,
                                  String endStr,
                                  HttpSession session){
        if(pageNum == null){
            pageNum = 1;
            // 保留当前的查询条件，供接下来点击分页链接时使用
            session.setAttribute("message",message);
            session.setAttribute("startStr",startStr);
            session.setAttribute("endStr",endStr);
        }else {
            // 点击分页链接时取回查询条件
            message = (String) session.getAttribute(message);
            startStr = (String) session.getAttribute(startStr);
            endStr = (String) session.getAttribute(endStr);
        }
        Page<TbNatmehaHotspot> tbHotspotsList;
        if (startStr == null || endStr == null){
            tbHotspotsList = this.tbNatmehaHotspotService.selectAllByPage(message,pageNum,5);
        }else{
           00000
        }
        model.addAttribute("tbHotspotsList",tbHotspotsList);
        return "tcmCulture/countryM";
    }*/
    //县级管理员
    @RequestMapping("/countryMan")  //  /tbNatmehaHotspot/countryMan
    public String selectAllByPageForCountry(Model model,
                                  Integer pageNum,
                                  String message,
                                  HttpSession session){
        if(pageNum == null){
            pageNum = 1;
            // 保留当前的查询条件，供接下来点击分页链接时使用
            session.setAttribute("message",message);
        }else {
            // 点击分页链接时取回查询条件
            message = (String) session.getAttribute(message);
        }
        String dataType = "3";
        Page<TbNatmehaHotspot> tbHotspotsList = this.tbNatmehaHotspotService.selectAllByPage(message,dataType,pageNum,5);
        model.addAttribute("tbHotspotsList",tbHotspotsList);
        return "tcmCulture/countryM";
    }

    //县级管理员增加
    @RequestMapping("/addTCMCulture")  //   /tbNatmehaHotspot/addTCMCulture
    public String addTCMCulture(){
        return "tcmCulture/addTCMCulture";
    }

    /**
     * @param uploadImg
     * @param save
     * @param refer
     * @param hotspotTitle
     * @param hotspotSource
     * @param hotspotAuthor
     * @param hotspotContent
     * @return
     */
    @RequestMapping("/insert") // /tbNatmehaHotspot/insert
    public String insertOne(@RequestParam("uploadImg") MultipartFile uploadImg, //@RequestParam 默认required = true,该图片必须上传
                            String save,
                            String refer,
                            String hotspotTitle,
                            String hotspotSource,
                            String hotspotAuthor,
                            String hotspotContent
                            ){
        // 如果是保存按钮，即是已保存  县级保存状态0，市级保存状态9，省级保存10，
        // 提交状态（待县级审核）即是 1，县级审核不通过 2，待市级审核 3，市级审核不通过 4，待省级审核 5，省级审核不通过6，省级通过 7，管理员发布 8。
        if(save != null){
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            String itemcode = UUID.randomUUID().toString();
            String fileDataCode = itemcode;
            String userCode = UUID.randomUUID().toString();
            String fileItemCode = userCode;
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotSource(hotspotSource);
            tbNatmehaHotspot.setHotspotAuthor(hotspotAuthor);
            tbNatmehaHotspot.setDataStatus("0");  //设置县级审核状态
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataType("3"); //设置类型
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setUserCode(userCode);
            tbNatmehaHotspot.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, fileItemCode, fileDataCode, tbNatmehaHotspot.getItemcreateat());
                result += this.tbNatmehaFileService.insertFile(tbNatmehaFile);
            }
            //加入信息进入热点表，成功则result为2
            result += this.tbNatmehaHotspotService.insertTCMCulture(tbNatmehaHotspot);
            if (result>0){
                return "redirect:/tbNatmehaHotspot/countryMan";
            }
        }else if( refer != null){
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            String itemcode = UUID.randomUUID().toString();
            String fileDataCode = itemcode;
            String userCode = UUID.randomUUID().toString();
            String fileItemCode = userCode;
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotSource(hotspotSource);
            tbNatmehaHotspot.setHotspotAuthor(hotspotAuthor);
            tbNatmehaHotspot.setDataStatus("1");  //设置审核状态,进入待县级审核
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataType("3"); //设置类型
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setUserCode(userCode);
            tbNatmehaHotspot.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, fileItemCode, fileDataCode, tbNatmehaHotspot.getItemcreateat());
                result += this.tbNatmehaFileService.insertFile(tbNatmehaFile);
            }
            //加入信息进入热点表，成功则result为2
            result += this.tbNatmehaHotspotService.insertTCMCulture(tbNatmehaHotspot);
            if (result>0){
                return "redirect:/tbNatmehaHotspot/countryMan";
            }
        }
        return null;
    }

   // 本地存放路径
    private static String localFilePath = "D:/dev/nginx-1.20.1/html/portals/images/";
   // nginx数据库地址
    private static String sqlFilePath = "http://localhost/portals/images/";


    public TbNatmehaFile insertFileOne(MultipartFile file,String itemcode,String dataCode,Date itemcreateat){
        TbNatmehaFile tbNatmehaFile = new TbNatmehaFile();
        // 获取源文件名字
        String originalFilename = file.getOriginalFilename();
        if(originalFilename != ""){
            // 获得源文件前缀
            String fileNamePrefix = originalFilename.substring(0,originalFilename.lastIndexOf("."));
            // 获得源文件后缀
            String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 将源文件前缀之后加上时间戳避免重名
            String newFileNamePrefix = fileNamePrefix + new Date( ).getTime();
            //得到上传的新文件名
            String newFileName = newFileNamePrefix + fileNameSuffix;
            //创建一个新的File对象用于存放上传的文件
            File newFile = new File(localFilePath + File.separator + newFileName);
            try {//图片写入磁盘，保存上传的文件
                file.transferTo(newFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            tbNatmehaFile.setItemcode(itemcode);
            tbNatmehaFile.setDataCode(dataCode);
            tbNatmehaFile.setFileName(newFileName);
            tbNatmehaFile.setFileType(fileNameSuffix);
            tbNatmehaFile.setFilePath(sqlFilePath+newFileName);
            tbNatmehaFile.setItemcreateat(itemcreateat);
            // 设置TbNatmehaFile 文件表
            return  tbNatmehaFile;
        }else
            return null;
    }

    /**
     * @param model
     * @param itemid
     * @return
     */
    // 管理员首页点击查看
    @RequestMapping("/countryCheck")  //   /tbNatmehaHotspot/countryCheck
    public String checkByid(Model model,Integer itemid){
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
        model.addAttribute("tbNatmehaHotspot",tbNatmehaHotspot);
        return "tcmCulture/countryCheck";
    }

    // 县级管理员首页点击修改
    @RequestMapping("/updateOne") // /tbNatmehaHotspot/updateOne
    public String undateToCountry(Model model,Integer itemid){
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
        model.addAttribute("tbNatmehaHotspot",tbNatmehaHotspot);
        return "tcmCulture/countryUpdate";
    }

    /**
     * @param model
     * @param itemid
     * @return
     */
    //县级管理员首页点击删除
    @RequestMapping("/deleteOne")  // /tbNatmehaHotspot/updateOne
    public String deleteToCountry(Model model,Integer itemid){
        int i = this.tbNatmehaHotspotService.deleteById(itemid);
        if(i>0){
            return "redirect:/tbNatmehaHotspot/countryMan";
        }
        return null;
    }

    /**
     * @param uploadImg
     * @param save
     * @param refer
     * @param itemcode
     * @param userCode
     * @param hotspotTitle
     * @param hotspotSource
     * @param hotspotAuthor
     * @param updater
     * @param hotspotContent
     * @return
     */
    //县级页面修改更新
    @RequestMapping("/countryUpdate")  // /tbNatmehaHotspot/countryUpdate
    public String updateById(@RequestParam("uploadImg") MultipartFile uploadImg, //@RequestParam 默认required = true,该图片必须上传
                             String save, String refer,
                             String itemcode, String userCode,
                             String hotspotTitle, String hotspotSource,
                             String hotspotAuthor, String updater,
                             String hotspotContent) {
        if(save != null){
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            String fileDataCode = itemcode;
            String fileItemCode = userCode;
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotSource(hotspotSource);
            tbNatmehaHotspot.setHotspotAuthor(hotspotAuthor);
            tbNatmehaHotspot.setUpdater(updater);
            tbNatmehaHotspot.setDataStatus("0");  //设置县级保存状态
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataType("3"); //设置类型
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setUserCode(userCode);
            tbNatmehaHotspot.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, fileItemCode, fileDataCode, tbNatmehaHotspot.getItemcreateat());
                // int i = this.tbNatmehaFileService.updateFile(fileItemCode,tbNatmehaFile.getFileName());
                int i = this.tbNatmehaFileService.updateByPrimaryKeySelective(tbNatmehaFile);
                result += i;
            } //加入更改后信息进入热点表，成功则result为2
            result += this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
            if (result > 0){
                return "redirect:/tbNatmehaHotspot/countryMan";
            }
        }
        return null;
    }

    //县级页面提交
    @RequestMapping("/submitOne")  // /tbNatmehaHotspot/submitOne
    public String countrySubmit(Model model,Integer itemid){
        if(itemid != null){
            TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
            String dataStatus = tbNatmehaHotspot.getDataStatus();
            //已保存、县级、市级、省级不通过
            if(dataStatus.equals("0") || dataStatus.equals("2") || dataStatus.equals("4") ||dataStatus.equals("6")){
                dataStatus = "1";
                tbNatmehaHotspot.setDataStatus(dataStatus);
                int i = this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
                if(i>0){
                    return "redirect:/tbNatmehaHotspot/countryMan";
                }
            }
        }
        return null;
    }

    // 待县级审核
    @RequestMapping("/countrySelect")    // /tbNatmehaHotspot/countrySelect
    public String countrySelect(Model model,
                                  Integer pageNum,
                                  String message,
                                  HttpSession session){
        if(pageNum == null){
            pageNum = 1;
            // 保留当前的查询条件，供接下来点击分页链接时使用
            session.setAttribute("message",message);
        }else {
            // 点击分页链接时取回查询条件
            message = (String) session.getAttribute(message);
        }
        String dataType = "3";
        Page<TbNatmehaHotspot> tbHotspotsList = this.tbNatmehaHotspotService.selectAllByPageForCountry(message,dataType,pageNum,5);
        model.addAttribute("tbHotspotsList",tbHotspotsList);
        return "tcmCulture/countrySelect";
    }


    // 县级部门审核通过
    @RequestMapping("/countryPass")
    public String countryPass(Integer itemid){
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
        tbNatmehaHotspot.setDataStatus("3");  //待市级部门审核
        int i = this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
        if(i >0 ){
            return "redirect:/tbNatmehaHotspot/countrySelect";
        }else
            return null;
    }

    // 县级部门审核不通过
    @RequestMapping("/countryNotPass")
    public String countryNotPass(Integer itemid){
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
        tbNatmehaHotspot.setDataStatus("2");  //县级审核不通过
        int i = this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
        if(i >0 ){
            return "redirect:/tbNatmehaHotspot/countrySelect";
        }else
            return null;
    }

    // 市级管理员
    @RequestMapping("/cityMan")  //  /tbNatmehaHotspot/cityMan
    public String selectAllByPageForCity(Model model,
                                  Integer pageNum,
                                  String message,
                                  HttpSession session){
        if(pageNum == null){
            pageNum = 1;
            // 保留当前的查询条件，供接下来点击分页链接时使用
            session.setAttribute("message",message);
        }else {
            // 点击分页链接时取回查询条件
            message = (String) session.getAttribute(message);
        }
        String dataType = "3";
        Page<TbNatmehaHotspot> tbHotspotsList = this.tbNatmehaHotspotService.selectAllByPageTwo(message,dataType,pageNum,5);
        model.addAttribute("tbHotspotsList",tbHotspotsList);
        return "tcmCulture/cityM";
    }

    //市级管理员增加
    @RequestMapping("/cityAddTCMCulture")  //   /tbNatmehaHotspot/cityAddTCMCulture
    public String cityAddTCMCulture(){
        return "tcmCulture/cityAddTCMCulture";
    }

    //市级管理员插入数据
    @RequestMapping("/insertTwo") // /tbNatmehaHotspot/insertTwo
    public String insertTwo(@RequestParam("uploadImg") MultipartFile uploadImg, //@RequestParam 默认required = true,该图片必须上传
                            String save,
                            String refer,
                            String hotspotTitle,
                            String hotspotSource,
                            String hotspotAuthor,
                            String hotspotContent
    ){
        // 如果是保存按钮，即是已保存  县级保存状态0，市级保存状态9，省级保存10，
        // 提交状态（待县级审核）即是 1，县级审核不通过 2，待市级审核 3，市级审核不通过 4，待省级审核 5，省级审核不通过6，省级通过 7，管理员发布 8。
        if(save != null){
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            String itemcode = UUID.randomUUID().toString();
            String fileDataCode = itemcode;
            String userCode = UUID.randomUUID().toString();
            String fileItemCode = userCode;
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotSource(hotspotSource);
            tbNatmehaHotspot.setHotspotAuthor(hotspotAuthor);
            tbNatmehaHotspot.setDataStatus("9");  //设置县级审核状态
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataType("3"); //设置类型
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setUserCode(userCode);
            tbNatmehaHotspot.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, fileItemCode, fileDataCode, tbNatmehaHotspot.getItemcreateat());
                result += this.tbNatmehaFileService.insertFile(tbNatmehaFile);
            }
            //加入信息进入热点表，成功则result为2
            result += this.tbNatmehaHotspotService.insertTCMCulture(tbNatmehaHotspot);
            if (result>0){
                return "redirect:/tbNatmehaHotspot/cityMan";
            }
        }else if( refer != null){
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            String itemcode = UUID.randomUUID().toString();
            String fileDataCode = itemcode;
            String userCode = UUID.randomUUID().toString();
            String fileItemCode = userCode;
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotSource(hotspotSource);
            tbNatmehaHotspot.setHotspotAuthor(hotspotAuthor);
            tbNatmehaHotspot.setDataStatus("3");  //设置审核状态,进入待市级审核
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataType("3"); //设置类型
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setUserCode(userCode);
            tbNatmehaHotspot.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, fileItemCode, fileDataCode, tbNatmehaHotspot.getItemcreateat());
                result += this.tbNatmehaFileService.insertFile(tbNatmehaFile);
            }
            //加入信息进入热点表，成功则result为2
            result += this.tbNatmehaHotspotService.insertTCMCulture(tbNatmehaHotspot);
            if (result>0){
                return "redirect:/tbNatmehaHotspot/cityMan";
            }
        }
        return null;
    }

    // 市级管理员首页点击修改
    @RequestMapping("/updateTwo") // /tbNatmehaHotspot/updateOne
    public String undateToCity(Model model,Integer itemid){
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
        model.addAttribute("tbNatmehaHotspot",tbNatmehaHotspot);
        return "tcmCulture/cityUpdate";
    }

    //市级管理员页面修改更新
    @RequestMapping("/cityUpdate")  // /tbNatmehaHotspot/countryUpdate
    public String updateByIdToCity(@RequestParam("uploadImg") MultipartFile uploadImg, //@RequestParam 默认required = true,该图片必须上传
                             String save, String refer,
                             String itemcode, String userCode,
                             String hotspotTitle, String hotspotSource,
                             String hotspotAuthor, String updater,
                             String hotspotContent) {
        if(save != null){
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            String fileDataCode = itemcode;
            String fileItemCode = userCode;
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotSource(hotspotSource);
            tbNatmehaHotspot.setHotspotAuthor(hotspotAuthor);
            tbNatmehaHotspot.setUpdater(updater);
            tbNatmehaHotspot.setDataStatus("9");  //设置审核状态,进入待市级审核
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataType("3"); //设置类型
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setUserCode(userCode);
            tbNatmehaHotspot.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, fileItemCode, fileDataCode, tbNatmehaHotspot.getItemcreateat());
                // int i = this.tbNatmehaFileService.updateFile(fileItemCode,tbNatmehaFile.getFileName());
                int i = this.tbNatmehaFileService.updateByPrimaryKeySelective(tbNatmehaFile);
                result += i;
            } //加入更改后信息进入热点表，成功则result为2
            result += this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
            if (result > 0){
                return "redirect:/tbNatmehaHotspot/cityMan";
            }
        }
        return null;
    }

    //市级管理员页面提交
    @RequestMapping("/submitTwo")  // /tbNatmehaHotspot/submitTwo
    public String citySubmit(Model model,Integer itemid){
        if(itemid != null){
            TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
            String dataStatus = tbNatmehaHotspot.getDataStatus();
            //已保存、市级、省级不通过
            if(dataStatus.equals("9") || dataStatus.equals("4") ||dataStatus.equals("6")){
                dataStatus = "3";
                tbNatmehaHotspot.setDataStatus(dataStatus);
                int i = this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
                if(i>0){
                    return "redirect:/tbNatmehaHotspot/cityMan";
                }
            }
        }
        return null;
    }

    //市级管理员首页点击删除
    @RequestMapping("/deleteTwo")  // /tbNatmehaHotspot/updateOne
    public String deleteToCity(Model model,Integer itemid){
        int i = this.tbNatmehaHotspotService.deleteById(itemid);
        if(i>0){
            return "redirect:/tbNatmehaHotspot/cityMan";
        }
        return null;
    }

    // 待市级审核
    @RequestMapping("/citySelect")    // /tbNatmehaHotspot/citySelect
    public String citySelect(Model model,
                                Integer pageNum,
                                String message,
                                HttpSession session){
        if(pageNum == null){
            pageNum = 1;
            // 保留当前的查询条件，供接下来点击分页链接时使用
            session.setAttribute("message",message);
        }else {
            // 点击分页链接时取回查询条件
            message = (String) session.getAttribute(message);
        }
        String dataType = "3";
        Page<TbNatmehaHotspot> tbHotspotsList = this.tbNatmehaHotspotService.selectAllByPageForCity(message,dataType,pageNum,5);
        model.addAttribute("tbHotspotsList",tbHotspotsList);
        return "tcmCulture/citySelect";
    }


    // 市级部门审核通过
    @RequestMapping("/cityPass")  // /tbNatmehaHotspot/cityPass
    public String cityPass(Integer itemid){
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
        tbNatmehaHotspot.setDataStatus("5");  //待省级部门审核
        int i = this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
        if(i >0 ){
            return "redirect:/tbNatmehaHotspot/citySelect";
        }else
            return null;
    }

    // 市级部门审核不通过
    @RequestMapping("/cityNotPass") // /tbNatmehaHotspot/cityNotPass
    public String cityNotPass(Integer itemid){
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
        tbNatmehaHotspot.setDataStatus("4");  //市级审核不通过
        int i = this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
        if(i >0 ){
            return "redirect:/tbNatmehaHotspot/citySelect";
        }else
            return null;
    }

    // 省级管理员
    @RequestMapping("/provinceMan")  //  /tbNatmehaHotspot/provinceMan
    public String selectAllByPageForProvince(Model model,
                                         Integer pageNum,
                                         String message,
                                         HttpSession session){
        if(pageNum == null){
            pageNum = 1;
            // 保留当前的查询条件，供接下来点击分页链接时使用
            session.setAttribute("message",message);
        }else {
            // 点击分页链接时取回查询条件
            message = (String) session.getAttribute(message);
        }
        String dataType = "3";
        Page<TbNatmehaHotspot> tbHotspotsList = this.tbNatmehaHotspotService.selectAllByPageThree(message,dataType,pageNum,5);
        model.addAttribute("tbHotspotsList",tbHotspotsList);
        return "tcmCulture/provinceM";
    }

    //省级管理员增加
    @RequestMapping("/provinceAddTCMCulture")  //   /tbNatmehaHotspot/provinceAddTCMCulture
    public String provinceAddTCMCulture(){
        return "tcmCulture/provinceAddTCMCulture";
    }

    //省级管理员插入数据
    @RequestMapping("/insertThree") // /tbNatmehaHotspot/insertTwo
    public String insertThree(@RequestParam("uploadImg") MultipartFile uploadImg, //@RequestParam 默认required = true,该图片必须上传
                            String save,
                            String refer,
                            String hotspotTitle,
                            String hotspotSource,
                            String hotspotAuthor,
                            String hotspotContent
    ){
        // 如果是保存按钮，即是已保存  县级保存状态0，市级保存状态9，省级保存10，
        // 提交状态（待县级审核）即是 1，县级审核不通过 2，待市级审核 3，市级审核不通过 4，待省级审核 5，省级审核不通过6，省级通过 7，管理员发布 8。
        if(save != null){
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            String itemcode = UUID.randomUUID().toString();
            String fileDataCode = itemcode;
            String userCode = UUID.randomUUID().toString();
            String fileItemCode = userCode;
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotSource(hotspotSource);
            tbNatmehaHotspot.setHotspotAuthor(hotspotAuthor);
            tbNatmehaHotspot.setDataStatus("10");  //设置省级保存状态
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataType("3"); //设置类型
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setUserCode(userCode);
            tbNatmehaHotspot.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, fileItemCode, fileDataCode, tbNatmehaHotspot.getItemcreateat());
                result += this.tbNatmehaFileService.insertFile(tbNatmehaFile);
            }
            //加入信息进入热点表，成功则result为2
            result += this.tbNatmehaHotspotService.insertTCMCulture(tbNatmehaHotspot);
            if (result>0){
                return "redirect:/tbNatmehaHotspot/provinceMan";
            }
        }else if( refer != null){
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            String itemcode = UUID.randomUUID().toString();
            String fileDataCode = itemcode;
            String userCode = UUID.randomUUID().toString();
            String fileItemCode = userCode;
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotSource(hotspotSource);
            tbNatmehaHotspot.setHotspotAuthor(hotspotAuthor);
            tbNatmehaHotspot.setDataStatus("5");  //设置审核状态,进入待省级级审核
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataType("3"); //设置类型
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setUserCode(userCode);
            tbNatmehaHotspot.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, fileItemCode, fileDataCode, tbNatmehaHotspot.getItemcreateat());
                result += this.tbNatmehaFileService.insertFile(tbNatmehaFile);
            }
            //加入信息进入热点表，成功则result为2
            result += this.tbNatmehaHotspotService.insertTCMCulture(tbNatmehaHotspot);
            if (result>0){
                return "redirect:/tbNatmehaHotspot/provinceMan";
            }
        }
        return null;
    }

    // 省级管理员首页点击修改
    @RequestMapping("/updateThree") // /tbNatmehaHotspot/updateThree
    public String undateToProvince(Model model,Integer itemid){
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
        model.addAttribute("tbNatmehaHotspot",tbNatmehaHotspot);
        return "tcmCulture/provinceUpdate";
    }

    //省级管理员页面修改更新
    @RequestMapping("/provinceUpdate")  // /tbNatmehaHotspot/provinceUpdate
    public String updateByIdToProvince(@RequestParam("uploadImg") MultipartFile uploadImg, //@RequestParam 默认required = true,该图片必须上传
                                   String save, String refer,
                                   String itemcode, String userCode,
                                   String hotspotTitle, String hotspotSource,
                                   String hotspotAuthor, String updater,
                                   String hotspotContent) {
        if(save != null){
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            String fileDataCode = itemcode;
            String fileItemCode = userCode;
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotSource(hotspotSource);
            tbNatmehaHotspot.setHotspotAuthor(hotspotAuthor);
            tbNatmehaHotspot.setUpdater(updater);
            tbNatmehaHotspot.setDataStatus("10");  //设置省级保存状态
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataType("3"); //设置类型
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setUserCode(userCode);
            tbNatmehaHotspot.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, fileItemCode, fileDataCode, tbNatmehaHotspot.getItemcreateat());
                // int i = this.tbNatmehaFileService.updateFile(fileItemCode,tbNatmehaFile.getFileName());
                int i = this.tbNatmehaFileService.updateByPrimaryKeySelective(tbNatmehaFile);
                result += i;
            } //加入更改后信息进入热点表，成功则result为2
            result += this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
            if (result > 0){
                return "redirect:/tbNatmehaHotspot/provinceMan";
            }
        }
        return null;
    }

    //省级管理员页面提交
    @RequestMapping("/submitThree")  // /tbNatmehaHotspot/submitTwo
    public String provinceSubmit(Model model,Integer itemid){
        if(itemid != null){
            TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
            String dataStatus = tbNatmehaHotspot.getDataStatus();
            //已保存、省级不通过
            if(dataStatus.equals("10") ||dataStatus.equals("6")){
                dataStatus = "5";
                tbNatmehaHotspot.setDataStatus(dataStatus);
                int i = this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
                if(i>0){
                    return "redirect:/tbNatmehaHotspot/provinceMan";
                }
            }
        }
        return null;
    }

    //省级管理员首页点击删除
    @RequestMapping("/deleteThree")  // /tbNatmehaHotspot/updateOne
    public String deleteToProvince(Model model,Integer itemid){
        int i = this.tbNatmehaHotspotService.deleteById(itemid);
        if(i>0){
            return "redirect:/tbNatmehaHotspot/provinceMan";
        }
        return null;
    }

    // 待省级审核
    @RequestMapping("/provinceSelect")    // /tbNatmehaHotspot/provinceSelect
    public String provinceSelect(Model model,
                             Integer pageNum,
                             String message,
                             HttpSession session){
        if(pageNum == null){
            pageNum = 1;
            // 保留当前的查询条件，供接下来点击分页链接时使用
            session.setAttribute("message",message);
        }else {
            // 点击分页链接时取回查询条件
            message = (String) session.getAttribute(message);
        }
        String dataType = "3";
        Page<TbNatmehaHotspot> tbHotspotsList = this.tbNatmehaHotspotService.selectAllByPageForProvince(message,dataType,pageNum,5);
        model.addAttribute("tbHotspotsList",tbHotspotsList);
        return "tcmCulture/provinceSelect";
    }


    // 省级部门审核通过
    @RequestMapping("/provincePass")  // /tbNatmehaHotspot/cityPass
    public String provincePass(Integer itemid){
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
        tbNatmehaHotspot.setDataStatus("7");  //待省级部门审核
        int i = this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
        if(i >0 ){
            return "redirect:/tbNatmehaHotspot/provinceSelect";
        }else
            return null;
    }

    // 省级部门审核不通过
    @RequestMapping("/provinceNotPass") // /tbNatmehaHotspot/cityNotPass
    public String provinceNotPass(Integer itemid){
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
        tbNatmehaHotspot.setDataStatus("6");  //市级审核不通过
        int i = this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
        if(i >0 ){
            return "redirect:/tbNatmehaHotspot/provinceSelect";
        }else
            return null;
    }

    // 国医堂管理员
    @RequestMapping("/allMan")  //  /tbNatmehaHotspot/allMan
    public String selectAllByPageForAll(Model model,
                                             Integer pageNum,
                                             String message,
                                             HttpSession session){
        if(pageNum == null){
            pageNum = 1;
            // 保留当前的查询条件，供接下来点击分页链接时使用
            session.setAttribute("message",message);
        }else {
            // 点击分页链接时取回查询条件
            message = (String) session.getAttribute(message);
        }
        String dataType = "3";
        Page<TbNatmehaHotspot> tbHotspotsList = this.tbNatmehaHotspotService.selectAllByPageFour(message,dataType,pageNum,5);
        model.addAttribute("tbHotspotsList",tbHotspotsList);
        return "tcmCulture/allManagement";
    }

    // 国医堂管理员发布
    @RequestMapping("/issue")  //  /tbNatmehaHotspot/issue
    public String issue(Integer itemid){
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
        tbNatmehaHotspot.setDataStatus("8"); //发布状态
        int i = this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
        if(i>0){
            return "redirect:/tbNatmehaHotspot/allMan";
        }else
            return null;
    }

    // 国医堂管理员下架
    @RequestMapping("/close")  //  /tbNatmehaHotspot/close
    public String close(Integer itemid){
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
        tbNatmehaHotspot.setDataStatus("11"); //下架状态
        int i = this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
        if(i>0){
            return "redirect:/tbNatmehaHotspot/allMan";
        }else
            return null;
    }
    /* 李荣锋中医文化end */


    /*--李荣锋儿童健康begin---*/
    //儿童健康县级管理员
    @RequestMapping("/countryManCH")  //  /tbNatmehaHotspot/countryManCH
    public String selectAllByPageForCountryCH(Model model,
                                            Integer pageNum,
                                            @Param("message") String message,
                                            HttpSession session){
        if(pageNum == null){
            pageNum = 1;
            // 保留当前的查询条件，供接下来点击分页链接时使用
            session.setAttribute("message",message);
        }else {
            if(session.getAttribute(message) != null)
            // 点击分页链接时取回查询条件
            message = (String) session.getAttribute(message);
        }
        String dataType = "4";
        Page<TbNatmehaHotspot> tbHotspotsList = this.tbNatmehaHotspotService.selectAllByPage(message,dataType,pageNum,5);
        model.addAttribute("tbHotspotsList",tbHotspotsList);
        return "childrenHealth/countryM";
    }

    //县级管理员增加
    @RequestMapping("/addChildrenHealth")  //   /tbNatmehaHotspot/addChildrenHealth
    public String addChildrenHealth(){
        return "childrenHealth/addChildrenHealth";
    }

    /**
     * @param uploadImg
     * @param save
     * @param refer
     * @param hotspotTitle
     * @param hotspotSource
     * @param hotspotAuthor
     * @param hotspotContent
     * @return
     */
    @RequestMapping("/countryInsertCH") // /tbNatmehaHotspot/countryInsertCH  children health
    public String insertCH(@RequestParam("uploadImg") MultipartFile uploadImg, //@RequestParam 默认required = true,该图片必须上传
                            String save,
                            String refer,
                            String hotspotTitle,
                            String hotspotSource,
                            String hotspotAuthor,
                            String hotspotContent
    ){
        // 如果是保存按钮，即是已保存  县级保存状态0，市级保存状态9，省级保存10，
        // 提交状态（待县级审核）即是 1，县级审核不通过 2，待市级审核 3，市级审核不通过 4，待省级审核 5，省级审核不通过6，省级通过 7，管理员发布 8。
        if(save != null){
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            String itemcode = UUID.randomUUID().toString();
            String fileDataCode = itemcode;
            String userCode = UUID.randomUUID().toString();
            String fileItemCode = userCode;
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotSource(hotspotSource);
            tbNatmehaHotspot.setHotspotAuthor(hotspotAuthor);
            tbNatmehaHotspot.setDataStatus("0");  //设置县级审核状态
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataType("4"); //设置类型
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setUserCode(userCode);
            tbNatmehaHotspot.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, fileItemCode, fileDataCode, tbNatmehaHotspot.getItemcreateat());
                result += this.tbNatmehaFileService.insertFile(tbNatmehaFile);
            }
            //加入信息进入热点表，成功则result为2
            result += this.tbNatmehaHotspotService.insertTCMCulture(tbNatmehaHotspot);
            if (result>0){
                return "redirect:/tbNatmehaHotspot/countryManCH";
            }
        }else if( refer != null){
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            String itemcode = UUID.randomUUID().toString();
            String fileDataCode = itemcode;
            String userCode = UUID.randomUUID().toString();
            String fileItemCode = userCode;
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotSource(hotspotSource);
            tbNatmehaHotspot.setHotspotAuthor(hotspotAuthor);
            tbNatmehaHotspot.setDataStatus("1");  //设置审核状态,进入待县级审核
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataType("4"); //设置类型
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setUserCode(userCode);
            tbNatmehaHotspot.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, fileItemCode, fileDataCode, tbNatmehaHotspot.getItemcreateat());
                result += this.tbNatmehaFileService.insertFile(tbNatmehaFile);
            }
            //加入信息进入热点表，成功则result为2
            result += this.tbNatmehaHotspotService.insertTCMCulture(tbNatmehaHotspot);
            if (result>0){
                return "redirect:/tbNatmehaHotspot/countryManCH";
            }
        }
        return null;
    }

    // 县级管理员首页点击修改
    @RequestMapping("/countryUpdateCH") // /tbNatmehaHotspot/countryUpdateCH
    public String undateToCountryCH(Model model,Integer itemid){
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
        model.addAttribute("tbNatmehaHotspot",tbNatmehaHotspot);
        return "childrenHealth/countryUpdate";
    }

    /**
     * @param uploadImg
     * @param save
     * @param refer
     * @param itemcode
     * @param userCode
     * @param hotspotTitle
     * @param hotspotSource
     * @param hotspotAuthor
     * @param updater
     * @param hotspotContent
     * @return
     */
    //县级页面修改更新
    @RequestMapping("/countryUpdateSave")  // /tbNatmehaHotspot/countryUpdate
    public String updateByIdCH(@RequestParam("uploadImg") MultipartFile uploadImg, //@RequestParam 默认required = true,该图片必须上传
                             String save, String refer,
                             String itemcode, String userCode,
                             String hotspotTitle, String hotspotSource,
                             String hotspotAuthor, String updater,
                             String hotspotContent) {
        if(save != null){
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            String fileDataCode = itemcode;
            String fileItemCode = userCode;
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotSource(hotspotSource);
            tbNatmehaHotspot.setHotspotAuthor(hotspotAuthor);
            tbNatmehaHotspot.setUpdater(updater);
            tbNatmehaHotspot.setDataStatus("0");  //设置县级保存状态
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataType("4"); //设置类型
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setUserCode(userCode);
            tbNatmehaHotspot.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, fileItemCode, fileDataCode, tbNatmehaHotspot.getItemcreateat());
                // int i = this.tbNatmehaFileService.updateFile(fileItemCode,tbNatmehaFile.getFileName());
                int i = this.tbNatmehaFileService.updateByPrimaryKeySelective(tbNatmehaFile);
                result += i;
            } //加入更改后信息进入热点表，成功则result为2
            result += this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
            if (result > 0){
                return "redirect:/tbNatmehaHotspot/countryManCH";
            }
        }
        return null;
    }

    /**
     * @param model
     * @param itemid
     * @return
     */
    //县级管理员首页点击删除
    @RequestMapping("/countryDeleteCH")  // /tbNatmehaHotspot/updateOne
    public String deleteToCountryCH(Model model,Integer itemid){
        int i = this.tbNatmehaHotspotService.deleteById(itemid);
        if(i>0){
            return "redirect:/tbNatmehaHotspot/countryManCH";
        }
        return null;
    }

    //县级页面提交
    @RequestMapping("/countrySubmitCH")  // /tbNatmehaHotspot/submitOne
    public String countrySubmitCH(Model model,Integer itemid){
        if(itemid != null){
            TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
            String dataStatus = tbNatmehaHotspot.getDataStatus();
            //已保存、县级、市级、省级不通过
            if(dataStatus.equals("0") || dataStatus.equals("2") || dataStatus.equals("4") ||dataStatus.equals("6")){
                dataStatus = "1";
                tbNatmehaHotspot.setDataStatus(dataStatus);
                int i = this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
                if(i>0){
                    return "redirect:/tbNatmehaHotspot/countryManCH";
                }
            }
        }
        return null;
    }

    // 待县级审核
    @RequestMapping("/countrySelectCH")    // /tbNatmehaHotspot/countrySelectCH
    public String countrySelectCH(Model model,
                                Integer pageNum,
                                String message,
                                HttpSession session){
        if(pageNum == null){
            pageNum = 1;
            // 保留当前的查询条件，供接下来点击分页链接时使用
            session.setAttribute("message",message);
        }else {
            // 点击分页链接时取回查询条件
            message = (String) session.getAttribute(message);
        }
        String dataType = "4";
        Page<TbNatmehaHotspot> tbHotspotsList = this.tbNatmehaHotspotService.selectAllByPageForCountry(message,dataType,pageNum,5);
        model.addAttribute("tbHotspotsList",tbHotspotsList);
        return "childrenHealth/countrySelect";
    }


    // 县级部门审核通过
    @RequestMapping("/countryPassCH")  // /tbNatmehaHotspot/countryPassCH
    public String countryPassCH(Integer itemid){
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
        tbNatmehaHotspot.setDataStatus("3");  //待市级部门审核
        int i = this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
        if(i >0 ){
            return "redirect:/tbNatmehaHotspot/countrySelectCH";
        }else
            return null;
    }

    // 县级部门审核不通过
    @RequestMapping("/countryNotPassCH")
    public String countryNotPassCH(Integer itemid){
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
        tbNatmehaHotspot.setDataStatus("2");  //县级审核不通过
        int i = this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
        if(i >0 ){
            return "redirect:/tbNatmehaHotspot/countrySelectCH";
        }else
            return null;
    }

    // 市级管理员
    @RequestMapping("/cityManCH")  //  /tbNatmehaHotspot/cityManCH
    public String selectAllByPageForCityCH(Model model,
                                         Integer pageNum,
                                         String message,
                                         HttpSession session){
        if(pageNum == null){
            pageNum = 1;
            // 保留当前的查询条件，供接下来点击分页链接时使用
            session.setAttribute("message",message);
        }else {
            // 点击分页链接时取回查询条件
            if( session.getAttribute(message) != null){
                message = (String) session.getAttribute(message);
            }
        }
        String dataType = "4";
        Page<TbNatmehaHotspot> tbHotspotsList = this.tbNatmehaHotspotService.selectAllByPageTwo(message,dataType,pageNum,5);
        model.addAttribute("tbHotspotsList",tbHotspotsList);
        return "childrenHealth/cityM";
    }

    //市级管理员增加
    @RequestMapping("/cityAddCH")  //   /tbNatmehaHotspot/cityAddCH
    public String cityAddCH(){
        return "childrenHealth/cityAddCH";
    }

    //市级管理员插入数据
    @RequestMapping("/cityInsertCH") // /tbNatmehaHotspot/cityInsertCH
    public String cityInsertCH(@RequestParam("uploadImg") MultipartFile uploadImg, //@RequestParam 默认required = true,该图片必须上传
                            String save,
                            String refer,
                            String hotspotTitle,
                            String hotspotSource,
                            String hotspotAuthor,
                            String hotspotContent
    ){
        // 如果是保存按钮，即是已保存  县级保存状态0，市级保存状态9，省级保存10，
        // 提交状态（待县级审核）即是 1，县级审核不通过 2，待市级审核 3，市级审核不通过 4，待省级审核 5，省级审核不通过6，省级通过 7，管理员发布 8。
        if(save != null){
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            String itemcode = UUID.randomUUID().toString();
            String fileDataCode = itemcode;
            String userCode = UUID.randomUUID().toString();
            String fileItemCode = userCode;
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotSource(hotspotSource);
            tbNatmehaHotspot.setHotspotAuthor(hotspotAuthor);
            tbNatmehaHotspot.setDataStatus("9");  //设置市级保存
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataType("4"); //设置类型
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setUserCode(userCode);
            tbNatmehaHotspot.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, fileItemCode, fileDataCode, tbNatmehaHotspot.getItemcreateat());
                result += this.tbNatmehaFileService.insertFile(tbNatmehaFile);
            }
            //加入信息进入热点表，成功则result为2
            result += this.tbNatmehaHotspotService.insertTCMCulture(tbNatmehaHotspot);
            if (result>0){
                return "redirect:/tbNatmehaHotspot/cityManCH";
            }
        }else if( refer != null){
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            String itemcode = UUID.randomUUID().toString();
            String fileDataCode = itemcode;
            String userCode = UUID.randomUUID().toString();
            String fileItemCode = userCode;
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotSource(hotspotSource);
            tbNatmehaHotspot.setHotspotAuthor(hotspotAuthor);
            tbNatmehaHotspot.setDataStatus("3");  //设置审核状态,进入待市级审核
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataType("4"); //设置类型
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setUserCode(userCode);
            tbNatmehaHotspot.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, fileItemCode, fileDataCode, tbNatmehaHotspot.getItemcreateat());
                result += this.tbNatmehaFileService.insertFile(tbNatmehaFile);
            }
            //加入信息进入热点表，成功则result为2
            result += this.tbNatmehaHotspotService.insertTCMCulture(tbNatmehaHotspot);
            if (result>0){
                return "redirect:/tbNatmehaHotspot/cityManCH";
            }
        }
        return null;
    }

    // 市级管理员首页点击修改
    @RequestMapping("/cityUpdateCH") // /tbNatmehaHotspot/cityUpdateCH
    public String undateToCityCH(Model model,Integer itemid){
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
        model.addAttribute("tbNatmehaHotspot",tbNatmehaHotspot);
        return "childrenHealth/cityUpdate";
    }

    //市级管理员页面保存修改更新
    @RequestMapping("/cityUpdateSave")  // /tbNatmehaHotspot/cityUpdateSave
    public String updateByIdToCityCH(@RequestParam("uploadImg") MultipartFile uploadImg, //@RequestParam 默认required = true,该图片必须上传
                                   String save, String refer,
                                   String itemcode, String userCode,
                                   String hotspotTitle, String hotspotSource,
                                   String hotspotAuthor, String updater,
                                   String hotspotContent) {
        if(save != null){
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            String fileDataCode = itemcode;
            String fileItemCode = userCode;
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotSource(hotspotSource);
            tbNatmehaHotspot.setHotspotAuthor(hotspotAuthor);
            tbNatmehaHotspot.setUpdater(updater);
            tbNatmehaHotspot.setDataStatus("9");  //设置市级保存状态
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataType("4"); //设置类型
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setUserCode(userCode);
            tbNatmehaHotspot.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, fileItemCode, fileDataCode, tbNatmehaHotspot.getItemcreateat());
                // int i = this.tbNatmehaFileService.updateFile(fileItemCode,tbNatmehaFile.getFileName());
                int i = this.tbNatmehaFileService.updateByPrimaryKeySelective(tbNatmehaFile);
                result += i;
            } //加入更改后信息进入热点表，成功则result为2
            result += this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
            if (result > 0){
                return "redirect:/tbNatmehaHotspot/cityManCH";
            }
        }
        return null;
    }

    //市级管理员页面提交
    @RequestMapping("/citySubmitCH")  // /tbNatmehaHotspot/citySubmitCH
    public String citySubmitCH(Model model,Integer itemid){
        if(itemid != null){
            TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
            String dataStatus = tbNatmehaHotspot.getDataStatus();
            //已保存、市级、省级不通过
            if(dataStatus.equals("9") || dataStatus.equals("4") ||dataStatus.equals("6")){
                dataStatus = "3";
                tbNatmehaHotspot.setDataStatus(dataStatus);
                int i = this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
                if(i>0){
                    return "redirect:/tbNatmehaHotspot/cityManCH";
                }
            }
        }
        return null;
    }

    //市级管理员首页点击删除
    @RequestMapping("/cityDeleteCH")  // /tbNatmehaHotspot/cityDeleteCH
    public String deleteToCityCH(Model model,Integer itemid){
        int i = this.tbNatmehaHotspotService.deleteById(itemid);
        if(i>0){
            return "redirect:/tbNatmehaHotspot/cityManCH";
        }
        return null;
    }


    // 待市级审核
    @RequestMapping("/citySelectCH")    // /tbNatmehaHotspot/citySelectCH
    public String citySelectCH(Model model,
                             Integer pageNum,
                             String message,
                             HttpSession session){
        if(pageNum == null){
            pageNum = 1;
            // 保留当前的查询条件，供接下来点击分页链接时使用
            session.setAttribute("message",message);
        }else {
            // 点击分页链接时取回查询条件
            message = (String) session.getAttribute(message);
        }
        String dataType = "4";
        Page<TbNatmehaHotspot> tbHotspotsList = this.tbNatmehaHotspotService.selectAllByPageForCity(message,dataType,pageNum,5);
        model.addAttribute("tbHotspotsList",tbHotspotsList);
        return "childrenHealth/citySelect";
    }


    // 市级部门审核通过
    @RequestMapping("/cityPassCH")  // /tbNatmehaHotspot/cityPassCH
    public String cityPassCH(Integer itemid){
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
        tbNatmehaHotspot.setDataStatus("5");  //待省级部门审核
        int i = this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
        if(i >0 ){
            return "redirect:/tbNatmehaHotspot/citySelectCH";
        }else
            return null;
    }

    // 市级部门审核不通过
    @RequestMapping("/cityNotPassCH") // /tbNatmehaHotspot/cityNotPass
    public String cityNotPassCH(Integer itemid){
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
        tbNatmehaHotspot.setDataStatus("4");  //市级审核不通过
        int i = this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
        if(i >0 ){
            return "redirect:/tbNatmehaHotspot/citySelectCH";
        }else
            return null;
    }

    // 省级管理员
    @RequestMapping("/provinceManCH")  //  /tbNatmehaHotspot/provinceManCH
    public String selectAllByPageForProvinceCH(Model model,
                                             Integer pageNum,
                                             String message,
                                             HttpSession session){
        if(pageNum == null){
            pageNum = 1;
            // 保留当前的查询条件，供接下来点击分页链接时使用
            session.setAttribute("message",message);
        }else {
            // 点击分页链接时取回查询条件
            message = (String) session.getAttribute(message);
        }
        String dataType = "4";
        Page<TbNatmehaHotspot> tbHotspotsList = this.tbNatmehaHotspotService.selectAllByPageThree(message,dataType,pageNum,5);
        model.addAttribute("tbHotspotsList",tbHotspotsList);
        return "childrenHealth/provinceM";
    }

    //省级管理员增加
    @RequestMapping("/provinceAddCH")  //   /tbNatmehaHotspot/provinceAddCH
    public String provinceAddCH(){
        return "childrenHealth/provinceAddCH";
    }

    //省级管理员插入数据
    @RequestMapping("/provinceInsertCH") // /tbNatmehaHotspot/provinceInsertCH
    public String provinceInsertCH(@RequestParam("uploadImg") MultipartFile uploadImg, //@RequestParam 默认required = true,该图片必须上传
                              String save,
                              String refer,
                              String hotspotTitle,
                              String hotspotSource,
                              String hotspotAuthor,
                              String hotspotContent
    ){
        // 如果是保存按钮，即是已保存  县级保存状态0，市级保存状态9，省级保存10，
        // 提交状态（待县级审核）即是 1，县级审核不通过 2，待市级审核 3，市级审核不通过 4，待省级审核 5，省级审核不通过6，省级通过 7，管理员发布 8。
        if(save != null){
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            String itemcode = UUID.randomUUID().toString();
            String fileDataCode = itemcode;
            String userCode = UUID.randomUUID().toString();
            String fileItemCode = userCode;
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotSource(hotspotSource);
            tbNatmehaHotspot.setHotspotAuthor(hotspotAuthor);
            tbNatmehaHotspot.setDataStatus("10");  //设置省级保存状态
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataType("4"); //设置类型
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setUserCode(userCode);
            tbNatmehaHotspot.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, fileItemCode, fileDataCode, tbNatmehaHotspot.getItemcreateat());
                result += this.tbNatmehaFileService.insertFile(tbNatmehaFile);
            }
            //加入信息进入热点表，成功则result为2
            result += this.tbNatmehaHotspotService.insertTCMCulture(tbNatmehaHotspot);
            if (result>0){
                return "redirect:/tbNatmehaHotspot/provinceManCH";
            }
        }else if( refer != null){
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            String itemcode = UUID.randomUUID().toString();
            String fileDataCode = itemcode;
            String userCode = UUID.randomUUID().toString();
            String fileItemCode = userCode;
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotSource(hotspotSource);
            tbNatmehaHotspot.setHotspotAuthor(hotspotAuthor);
            tbNatmehaHotspot.setDataStatus("5");  //设置审核状态,进入待省级级审核
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataType("4"); //设置类型
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setUserCode(userCode);
            tbNatmehaHotspot.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, fileItemCode, fileDataCode, tbNatmehaHotspot.getItemcreateat());
                result += this.tbNatmehaFileService.insertFile(tbNatmehaFile);
            }
            //加入信息进入热点表，成功则result为2
            result += this.tbNatmehaHotspotService.insertTCMCulture(tbNatmehaHotspot);
            if (result>0){
                return "redirect:/tbNatmehaHotspot/provinceManCH";
            }
        }
        return null;
    }

    // 省级管理员首页点击修改
    @RequestMapping("/provinceUpdateCH") // /tbNatmehaHotspot/updateThree
    public String undateToProvinceCH(Model model,Integer itemid){
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
        model.addAttribute("tbNatmehaHotspot",tbNatmehaHotspot);
        return "childrenHealth/provinceUpdate";
    }

    //省级管理员页面修改更新
    @RequestMapping("/provinceUpdateSave")  // /tbNatmehaHotspot/provinceUpdateSave
    public String updateByIdToProvinceSave(@RequestParam("uploadImg") MultipartFile uploadImg, //@RequestParam 默认required = true,该图片必须上传
                                       String save, String refer,
                                       String itemcode, String userCode,
                                       String hotspotTitle, String hotspotSource,
                                       String hotspotAuthor, String updater,
                                       String hotspotContent) {
        if(save != null){
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            String fileDataCode = itemcode;
            String fileItemCode = userCode;
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotSource(hotspotSource);
            tbNatmehaHotspot.setHotspotAuthor(hotspotAuthor);
            tbNatmehaHotspot.setUpdater(updater);
            tbNatmehaHotspot.setDataStatus("10");  //设置省级保存状态
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataType("4"); //设置类型
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setUserCode(userCode);
            tbNatmehaHotspot.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, fileItemCode, fileDataCode, tbNatmehaHotspot.getItemcreateat());
                // int i = this.tbNatmehaFileService.updateFile(fileItemCode,tbNatmehaFile.getFileName());
                int i = this.tbNatmehaFileService.updateByPrimaryKeySelective(tbNatmehaFile);
                result += i;
            } //加入更改后信息进入热点表，成功则result为2
            result += this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
            if (result > 0){
                return "redirect:/tbNatmehaHotspot/provinceManCH";
            }
        }
        return null;
    }

    //省级管理员页面提交
    @RequestMapping("/provinceSubmitCH")  // /tbNatmehaHotspot/submitTwo
    public String provinceSubmitCH(Model model,Integer itemid){
        if(itemid != null){
            TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
            String dataStatus = tbNatmehaHotspot.getDataStatus();
            //已保存、省级不通过
            if(dataStatus.equals("10") ||dataStatus.equals("6")){
                dataStatus = "5";
                tbNatmehaHotspot.setDataStatus(dataStatus);
                int i = this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
                if(i>0){
                    return "redirect:/tbNatmehaHotspot/provinceManCH";
                }
            }
        }
        return null;
    }

    //省级管理员首页点击删除
    @RequestMapping("/provinceDeleteCH")  // /tbNatmehaHotspot/provinceDeleteCH
    public String deleteToProvinceCH(Model model,Integer itemid){
        int i = this.tbNatmehaHotspotService.deleteById(itemid);
        if(i>0){
            return "redirect:/tbNatmehaHotspot/provinceManCH";
        }
        return null;
    }

    // 待省级审核
    @RequestMapping("/provinceSelectCH")    // /tbNatmehaHotspot/provinceSelectCH
    public String provinceSelectCH(Model model,
                                 Integer pageNum,
                                 String message,
                                 HttpSession session){
        if(pageNum == null){
            pageNum = 1;
            // 保留当前的查询条件，供接下来点击分页链接时使用
            session.setAttribute("message",message);
        }else {
            // 点击分页链接时取回查询条件
            message = (String) session.getAttribute(message);
        }
        String dataType = "4";
        Page<TbNatmehaHotspot> tbHotspotsList = this.tbNatmehaHotspotService.selectAllByPageForProvince(message,dataType,pageNum,5);
        model.addAttribute("tbHotspotsList",tbHotspotsList);
        return "childrenHealth/provinceSelect";
    }


    // 省级部门审核通过
    @RequestMapping("/provincePassCH")  // /tbNatmehaHotspot/provincePassCH
    public String provincePassCH(Integer itemid){
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
        tbNatmehaHotspot.setDataStatus("7");  //待省级部门审核
        int i = this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
        if(i >0 ){
            return "redirect:/tbNatmehaHotspot/provinceSelectCH";
        }else
            return null;
    }

    // 省级部门审核不通过
    @RequestMapping("/provinceNotPassCH") // /tbNatmehaHotspot/provinceNotPassCH
    public String provinceNotPassCH(Integer itemid){
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
        tbNatmehaHotspot.setDataStatus("6");  //市级审核不通过
        int i = this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
        if(i >0 ){
            return "redirect:/tbNatmehaHotspot/provinceSelectCH";
        }else
            return null;
    }

    // 国医堂管理员
    @RequestMapping("/allManCH")  //  /tbNatmehaHotspot/allManCH
    public String selectAllByPageForAllCH(Model model,
                                        Integer pageNum,
                                        String message,
                                        HttpSession session){
        if(pageNum == null){
            pageNum = 1;
            // 保留当前的查询条件，供接下来点击分页链接时使用
            session.setAttribute("message",message);
        }else {
            // 点击分页链接时取回查询条件
            message = (String) session.getAttribute(message);
        }
        String dataType = "4";
        Page<TbNatmehaHotspot> tbHotspotsList = this.tbNatmehaHotspotService.selectAllByPageFour(message,dataType,pageNum,5);
        model.addAttribute("tbHotspotsList",tbHotspotsList);
        return "childrenHealth/allManagement";
    }

    // 国医堂管理员发布
    @RequestMapping("/issueCH")  //  /tbNatmehaHotspot/issue
    public String issueCH(Integer itemid){
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
        tbNatmehaHotspot.setDataStatus("8"); //发布状态
        int i = this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
        if(i>0){
            return "redirect:/tbNatmehaHotspot/allManCH";
        }else
            return null;
    }

    // 国医堂管理员下架
    @RequestMapping("/closeCH")  //  /tbNatmehaHotspot/close
    public String closeCH(Integer itemid){
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotService.queryById(itemid);
        tbNatmehaHotspot.setDataStatus("11"); //下架状态
        int i = this.tbNatmehaHotspotService.updateByPrimaryKeySelective(tbNatmehaHotspot);
        if(i>0){
            return "redirect:/tbNatmehaHotspot/allManCH";
        }else
            return null;
    }
    /* 李荣锋儿童健康end */

}
