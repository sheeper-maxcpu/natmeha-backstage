package com.lanqiao.natmeha.controller;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.model.TbNatmehaChineseMedicine;
import com.lanqiao.natmeha.model.TbNatmehaFile;
import com.lanqiao.natmeha.model.TbNatmehaHotspot;
import com.lanqiao.natmeha.service.TbNatmehaChineseMedicineService;
import com.lanqiao.natmeha.service.TbNatmehaFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * @author barry
 * @create 2021-08-31 22:51
 */
@Controller
@RequestMapping("tbNatmehaChineseMedicine")
public class TbNatmehaChineseMedicineController {
    /**
     * 服务对象
     */
    @Autowired
    private TbNatmehaChineseMedicineService tbNatmehaChineseMedicineService;

    /**
     * 服务对象
     */
    @Resource
    private TbNatmehaFileService tbNatmehaFileService;

    //县级管理员
    @RequestMapping("/countryMan")  //  /tbNatmehaChineseMedicine/countryMan
    public String selectAllByPageForCountry(Model model,
                                            Integer pageNum,
                                            TbNatmehaChineseMedicine tbNatmehaChineseMedicine,
                                            HttpSession session){
        if(pageNum == null){
            pageNum = 1;
            // 保留当前的查询条件，供接下来点击分页链接时使用
            session.setAttribute("tbNatmehaChineseMedicine",tbNatmehaChineseMedicine);
        }else {
            // 点击分页链接时取回查询条件
            tbNatmehaChineseMedicine = (TbNatmehaChineseMedicine) session.getAttribute("tbNatmehaChineseMedicine");
        }
        Page<TbNatmehaChineseMedicine> tbCHNMedicineList = this.tbNatmehaChineseMedicineService.selectAllByCountry(tbNatmehaChineseMedicine,pageNum,5);
        System.out.println(tbCHNMedicineList);
        model.addAttribute("tbCHNMedicineList",tbCHNMedicineList);
        return "tcmCommon/countryM";
    }

    //县级管理员新增
    @RequestMapping("/countryAddTCMCommon")  //  /tbNatmehaChineseMedicine/countryAddTCMCommon
    public String addTCMCommonCM(){
        return "tcmCommon/countryAddTCMCommon";
    }

    //新增内容管理
    @RequestMapping("/countryInsertTCMCommon")  //  /tbNatmehaChineseMedicine/countryInsertTCMCommon
    public String insertTCMCommonCM(@RequestParam("uploadImg") MultipartFile uploadImg,
                                    String save,
                                    String refer,
                                    TbNatmehaChineseMedicine tbNatmehaChineseMedicine,
                                    String mark
                                    ){
        if(save != null){
            String itemcode = UUID.randomUUID().toString();
            String fileDataCode = itemcode;
            String userCode = UUID.randomUUID().toString();
            String fileItemCode = userCode;
            tbNatmehaChineseMedicine.setStatus("0");  //设置县级审核状态
            tbNatmehaChineseMedicine.setItemcode(itemcode);
            tbNatmehaChineseMedicine.setUserCode(userCode);
            tbNatmehaChineseMedicine.setCreater("admin"); //设定默认值
            tbNatmehaChineseMedicine.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, fileItemCode, fileDataCode, tbNatmehaChineseMedicine.getItemcreateat());
                result += this.tbNatmehaFileService.insertFile(tbNatmehaFile);
            }
            //加入信息进入热点表，成功则result为2
            result += this.tbNatmehaChineseMedicineService.insertTCMCommon(tbNatmehaChineseMedicine);
            if (result>0){
                return "redirect:/tbNatmehaChineseMedicine/countryMan";
            }
        }else if(refer != null){
            String itemcode = UUID.randomUUID().toString();
            String fileDataCode = itemcode;
            String userCode = UUID.randomUUID().toString();
            String fileItemCode = userCode;
            tbNatmehaChineseMedicine.setStatus("1");  //设置县级审核状态
            tbNatmehaChineseMedicine.setItemcode(itemcode);
            tbNatmehaChineseMedicine.setUserCode(userCode);
            tbNatmehaChineseMedicine.setCreater("admin"); //设定默认值
            tbNatmehaChineseMedicine.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, fileItemCode, fileDataCode, tbNatmehaChineseMedicine.getItemcreateat());
                result += this.tbNatmehaFileService.insertFile(tbNatmehaFile);
            }
            //加入信息进入热点表，成功则result为2
            result += this.tbNatmehaChineseMedicineService.insertTCMCommon(tbNatmehaChineseMedicine);
            if (result>0){
                return "redirect:/tbNatmehaChineseMedicine/countryMan";
            }
        }
        return null;
    }

    // 本地存放路径
    private static String localFilePath = "/root/images";
    // nginx数据库地址
    private static String sqlFilePath = "http://142.4.123.27:8081/";


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
    @RequestMapping("/countryCheck")  //   /tbNatmehaChineseMedicine/countryCheck
    public String checkByidCM(Model model,Integer itemid){
        TbNatmehaChineseMedicine tbNatmehaChineseMedicine = this.tbNatmehaChineseMedicineService.queryById(itemid);
        model.addAttribute("tbNatmehaChineseMedicine",tbNatmehaChineseMedicine);
        return "tcmCommon/countryCheck";
    }


    // 县级管理员首页点击修改
    @RequestMapping("/countryUpdateCM") // /tbNatmehaChineseMedicine/countryUpdateCM
    public String undateToCountryCM(Model model,Integer itemid){
        TbNatmehaChineseMedicine tbNatmehaChineseMedicine = this.tbNatmehaChineseMedicineService.queryById(itemid);
        model.addAttribute("tbNatmehaChineseMedicine",tbNatmehaChineseMedicine);
        return "tcmCommon/countryUpdate";
    }

    //县级页面修改更新
    @RequestMapping("/countryUpdateSave")  // /tbNatmehaChineseMedicine/countryUpdateSave
    public String updateByIdCM(@RequestParam("uploadImg") MultipartFile uploadImg, //@RequestParam 默认required = true,该图片必须上传
                               String save, String refer,
                              TbNatmehaChineseMedicine tbNatmehaChineseMedicine) {

        if(save != null){
            tbNatmehaChineseMedicine.setStatus("0");  //设置县级保存状态
            tbNatmehaChineseMedicine.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, tbNatmehaChineseMedicine.getUserCode(), tbNatmehaChineseMedicine.getItemcode(), tbNatmehaChineseMedicine.getItemcreateat());
                int i = this.tbNatmehaFileService.updateByPrimaryKeySelective(tbNatmehaFile);
                result += i;
            } //加入更改后信息进入热点表，成功则result为2
            result += this.tbNatmehaChineseMedicineService.updateByPrimaryKeySelective(tbNatmehaChineseMedicine);
            if (result > 0){
                return "redirect:/tbNatmehaChineseMedicine/countryMan";
            }
        }
        return null;
    }

    /**
     * @param itemid
     * @return
     */
    //县级管理员首页点击删除
    @RequestMapping("/countryDeleteCM")  // /tbNatmehaChineseMedicine/countryDeleteCM
    public String deleteToCountryCH(Integer itemid){
        int i = this.tbNatmehaChineseMedicineService.deleteById(itemid);
        if(i>0){
            return "redirect:/tbNatmehaChineseMedicine/countryMan";
        }
        return null;
    }

    //县级管理员页面提交
    @RequestMapping("/countrySubmitCM")  // /tbNatmehaChineseMedicine/countrySubmitCM
    public String countrySubmitCM(Model model,Integer itemid){
        if(itemid != null){
            TbNatmehaChineseMedicine tbNatmehaChineseMedicine = this.tbNatmehaChineseMedicineService.queryById(itemid);
            String dataStatus = tbNatmehaChineseMedicine.getStatus();
            //已保存、县级、市级、省级不通过
            if(dataStatus.equals("0") || dataStatus.equals("2") || dataStatus.equals("4") ||dataStatus.equals("6")){
                dataStatus = "1";
                tbNatmehaChineseMedicine.setStatus(dataStatus);
                int i = this.tbNatmehaChineseMedicineService.updateByPrimaryKeySelective(tbNatmehaChineseMedicine);
                if(i>0){
                    return "redirect:/tbNatmehaChineseMedicine/countryMan";
                }
            }
        }
        return null;
    }


    // 待县级审核
    @RequestMapping("/countrySelectCM")    // /tbNatmehaChineseMedicine/countrySelectCM
    public String countrySelectCM(Model model,
                                  Integer pageNum,
                                  TbNatmehaChineseMedicine tbNatmehaChineseMedicine,
                                  HttpSession session){
        if(pageNum == null){
            pageNum = 1;
            // 保留当前的查询条件，供接下来点击分页链接时使用
            session.setAttribute("tbNatmehaChineseMedicine",tbNatmehaChineseMedicine);
        }else {
            // 点击分页链接时取回查询条件
            tbNatmehaChineseMedicine = (TbNatmehaChineseMedicine) session.getAttribute("tbNatmehaChineseMedicine");
        }
        Page<TbNatmehaChineseMedicine> tbCHNMedicineList = this.tbNatmehaChineseMedicineService.selectAllByPageForCountry(tbNatmehaChineseMedicine,pageNum,5);
        model.addAttribute("tbCHNMedicineList",tbCHNMedicineList);
        return "tcmCommon/countrySelect";
    }


    // 县级部门审核通过
    @RequestMapping("/countryPassCM")  // /tbNatmehaHotspot/countryPassCH
    public String countryPassCH(Integer itemid){
        TbNatmehaChineseMedicine tbNatmehaChineseMedicine = this.tbNatmehaChineseMedicineService.queryById(itemid);
        tbNatmehaChineseMedicine.setStatus("3");  //待市级部门审核
        int i = this.tbNatmehaChineseMedicineService.updateByPrimaryKeySelective(tbNatmehaChineseMedicine);
        if(i >0 ){
            return "redirect:/tbNatmehaChineseMedicine/countrySelectCM";

        }else
            return null;
    }

    // 县级部门审核不通过
    @RequestMapping("/countryNotPassCM")
    public String countryNotPassCM(Integer itemid){
        TbNatmehaChineseMedicine tbNatmehaChineseMedicine = this.tbNatmehaChineseMedicineService.queryById(itemid);
        tbNatmehaChineseMedicine.setStatus("2");  //县级审核不通过
        int i = this.tbNatmehaChineseMedicineService.updateByPrimaryKeySelective(tbNatmehaChineseMedicine);
        if(i >0 ){
            return "redirect:/tbNatmehaChineseMedicine/countrySelectCM";
        }else
            return null;
    }

    //市级管理员
    @RequestMapping("/cityManCM")  //  /tbNatmehaChineseMedicine/cityManCM
    public String selectAllByPageForCity(Model model,
                                            Integer pageNum,
                                            TbNatmehaChineseMedicine tbNatmehaChineseMedicine,
                                            HttpSession session){
        if(pageNum == null){
            pageNum = 1;
            // 保留当前的查询条件，供接下来点击分页链接时使用
            session.setAttribute("tbNatmehaChineseMedicine",tbNatmehaChineseMedicine);
        }else {
            // 点击分页链接时取回查询条件
            tbNatmehaChineseMedicine = (TbNatmehaChineseMedicine) session.getAttribute("tbNatmehaChineseMedicine");
        }
        Page<TbNatmehaChineseMedicine> tbCHNMedicineList = this.tbNatmehaChineseMedicineService.selectAllByCity(tbNatmehaChineseMedicine,pageNum,5);
        System.out.println(tbCHNMedicineList);
        model.addAttribute("tbCHNMedicineList",tbCHNMedicineList);
        return "tcmCommon/cityM";
    }

    //市级管理员新增
    @RequestMapping("/cityAddTCMCommon")  //  /tbNatmehaChineseMedicine/cityAddTCMCommon
    public String cityAddTCMCommon(){
        return "tcmCommon/cityAddTCMCommon";
    }

    //新增内容管理
    @RequestMapping("/cityInsertTCMCommon")  //  /tbNatmehaChineseMedicine/cityInsertTCMCommon
    public String cityInsertTCMCommon(@RequestParam("uploadImg") MultipartFile uploadImg,
                                    String save,
                                    String refer,
                                    TbNatmehaChineseMedicine tbNatmehaChineseMedicine,
                                    String mark
    ){
        if(save != null){
            String itemcode = UUID.randomUUID().toString();
            String fileDataCode = itemcode;
            String userCode = UUID.randomUUID().toString();
            String fileItemCode = userCode;
            tbNatmehaChineseMedicine.setStatus("9");  //设置市级保存状态
            tbNatmehaChineseMedicine.setItemcode(itemcode);
            tbNatmehaChineseMedicine.setUserCode(userCode);
            tbNatmehaChineseMedicine.setCreater("admin"); //设定默认值
            tbNatmehaChineseMedicine.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, fileItemCode, fileDataCode, tbNatmehaChineseMedicine.getItemcreateat());
                result += this.tbNatmehaFileService.insertFile(tbNatmehaFile);
            }
            //加入信息进入热点表，成功则result为2
            result += this.tbNatmehaChineseMedicineService.insertTCMCommon(tbNatmehaChineseMedicine);
            if (result>0){
                return "redirect:/tbNatmehaChineseMedicine/cityManCM";
            }
        }else if(refer != null){
            String itemcode = UUID.randomUUID().toString();
            String fileDataCode = itemcode;
            String userCode = UUID.randomUUID().toString();
            String fileItemCode = userCode;
            tbNatmehaChineseMedicine.setStatus("3");  //设置待市级审核状态
            tbNatmehaChineseMedicine.setItemcode(itemcode);
            tbNatmehaChineseMedicine.setUserCode(userCode);
            tbNatmehaChineseMedicine.setCreater("admin"); //设定默认值
            tbNatmehaChineseMedicine.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, fileItemCode, fileDataCode, tbNatmehaChineseMedicine.getItemcreateat());
                result += this.tbNatmehaFileService.insertFile(tbNatmehaFile);
            }
            //加入信息进入热点表，成功则result为2
            result += this.tbNatmehaChineseMedicineService.insertTCMCommon(tbNatmehaChineseMedicine);
            if (result>0){
                return "redirect:/tbNatmehaChineseMedicine/cityManCM";
            }
        }
        return null;
    }

    // 市级管理员首页点击修改
    @RequestMapping("/cityUpdateCM") // /tbNatmehaChineseMedicine/cityUpdateCM
    public String undateToCityCM(Model model,Integer itemid){
        TbNatmehaChineseMedicine tbNatmehaChineseMedicine = this.tbNatmehaChineseMedicineService.queryById(itemid);
        model.addAttribute("tbNatmehaChineseMedicine",tbNatmehaChineseMedicine);
        return "tcmCommon/cityUpdateCM";
    }

    //市级页面修改更新
    @RequestMapping("/cityUpdateSave")  // /tbNatmehaChineseMedicine/cityUpdateSave
    public String cityUpdateSave(@RequestParam("uploadImg") MultipartFile uploadImg, //@RequestParam 默认required = true,该图片必须上传
                               String save, String refer,
                               TbNatmehaChineseMedicine tbNatmehaChineseMedicine) {

        if(save != null){
            tbNatmehaChineseMedicine.setStatus("9");  //设置县级保存状态
            tbNatmehaChineseMedicine.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, tbNatmehaChineseMedicine.getUserCode(), tbNatmehaChineseMedicine.getItemcode(), tbNatmehaChineseMedicine.getItemcreateat());
                int i = this.tbNatmehaFileService.updateByPrimaryKeySelective(tbNatmehaFile);
                result += i;
            } //加入更改后信息进入热点表，成功则result为2
            result += this.tbNatmehaChineseMedicineService.updateByPrimaryKeySelective(tbNatmehaChineseMedicine);
            if (result > 0){
                return "redirect:/tbNatmehaChineseMedicine/cityManCM";
            }
        }
        return null;
    }

    /**
     * @param itemid
     * @return
     */
    //市级管理员首页点击删除
    @RequestMapping("/cityDeleteCM")  // /tbNatmehaChineseMedicine/cityDeleteCM
    public String deleteToCityCM(Integer itemid){
        int i = this.tbNatmehaChineseMedicineService.deleteById(itemid);
        if(i>0){
            return "redirect:/tbNatmehaChineseMedicine/cityManCM";
        }
        return null;
    }

    //市级级管理员页面提交
    @RequestMapping("/citySubmitCM")  // /tbNatmehaChineseMedicine/citySubmitCM
    public String citySubmitCM(Model model,Integer itemid){
        if(itemid != null){
            TbNatmehaChineseMedicine tbNatmehaChineseMedicine = this.tbNatmehaChineseMedicineService.queryById(itemid);
            String dataStatus = tbNatmehaChineseMedicine.getStatus();
            //已保存、市级、省级不通过
            if(dataStatus.equals("9") || dataStatus.equals("4") ||dataStatus.equals("6")){
                dataStatus = "3";
                tbNatmehaChineseMedicine.setStatus(dataStatus);
                int i = this.tbNatmehaChineseMedicineService.updateByPrimaryKeySelective(tbNatmehaChineseMedicine);
                if(i>0){
                    return "redirect:/tbNatmehaChineseMedicine/countryMan";
                }
            }
        }
        return null;
    }

    // 待市级审核
    @RequestMapping("/citySelectCM")    // /tbNatmehaChineseMedicine/citySelectCM
    public String citySelectCM(Model model,
                                  Integer pageNum,
                                  TbNatmehaChineseMedicine tbNatmehaChineseMedicine,
                                  HttpSession session){
        if(pageNum == null){
            pageNum = 1;
            // 保留当前的查询条件，供接下来点击分页链接时使用
            session.setAttribute("tbNatmehaChineseMedicine",tbNatmehaChineseMedicine);
        }else {
            // 点击分页链接时取回查询条件
            tbNatmehaChineseMedicine = (TbNatmehaChineseMedicine) session.getAttribute("tbNatmehaChineseMedicine");
        }
        Page<TbNatmehaChineseMedicine> tbCHNMedicineList = this.tbNatmehaChineseMedicineService.selectAllByPageForCity(tbNatmehaChineseMedicine,pageNum,5);
        model.addAttribute("tbCHNMedicineList",tbCHNMedicineList);
        return "tcmCommon/citySelect";
    }


    // 市级部门审核通过
    @RequestMapping("/cityPassCM")  // /tbNatmehaHotspot/countryPassCH
    public String cityPassCM(Integer itemid){
        TbNatmehaChineseMedicine tbNatmehaChineseMedicine = this.tbNatmehaChineseMedicineService.queryById(itemid);
        tbNatmehaChineseMedicine.setStatus("5");  //待省级部门审核
        int i = this.tbNatmehaChineseMedicineService.updateByPrimaryKeySelective(tbNatmehaChineseMedicine);
        if(i >0 ){
            return "redirect:/tbNatmehaChineseMedicine/citySelectCM";

        }else
            return null;
    }

    // 市级部门审核不通过
    @RequestMapping("/cityNotPassCM")
    public String countryNotPassCH(Integer itemid){
        TbNatmehaChineseMedicine tbNatmehaChineseMedicine = this.tbNatmehaChineseMedicineService.queryById(itemid);
        tbNatmehaChineseMedicine.setStatus("4");  //市级审核不通过
        int i = this.tbNatmehaChineseMedicineService.updateByPrimaryKeySelective(tbNatmehaChineseMedicine);
        if(i >0 ){
            return "redirect:/tbNatmehaChineseMedicine/citySelectCM";
        }else
            return null;
    }


    //省级级管理员
    @RequestMapping("/provinceManCM")  //  /tbNatmehaChineseMedicine/provinceManCM
    public String selectAllByPageForProvince(Model model,
                                         Integer pageNum,
                                         TbNatmehaChineseMedicine tbNatmehaChineseMedicine,
                                         HttpSession session){
        if(pageNum == null){
            pageNum = 1;
            // 保留当前的查询条件，供接下来点击分页链接时使用
            session.setAttribute("tbNatmehaChineseMedicine",tbNatmehaChineseMedicine);
        }else {
            // 点击分页链接时取回查询条件
            tbNatmehaChineseMedicine = (TbNatmehaChineseMedicine) session.getAttribute("tbNatmehaChineseMedicine");
        }
        Page<TbNatmehaChineseMedicine> tbCHNMedicineList = this.tbNatmehaChineseMedicineService.selectAllByProvince(tbNatmehaChineseMedicine,pageNum,5);
        System.out.println(tbCHNMedicineList);
        model.addAttribute("tbCHNMedicineList",tbCHNMedicineList);
        return "tcmCommon/provinceM";
    }

    //省级管理员新增
    @RequestMapping("/provinceAddTCMCommon")  //  /tbNatmehaChineseMedicine/cityAddTCMCommon
    public String provinceAddTCMCommon(){
        return "tcmCommon/provinceAddTCMCommon";
    }

    //新增内容管理
    @RequestMapping("/provinceInsertTCMCommon")  //  /tbNatmehaChineseMedicine/provinceInsertTCMCommon
    public String provinceInsertTCMCommon(@RequestParam("uploadImg") MultipartFile uploadImg,
                                      String save,
                                      String refer,
                                      TbNatmehaChineseMedicine tbNatmehaChineseMedicine,
                                      String mark
    ){
        if(save != null){
            String itemcode = UUID.randomUUID().toString();
            String fileDataCode = itemcode;
            String userCode = UUID.randomUUID().toString();
            String fileItemCode = userCode;
            tbNatmehaChineseMedicine.setStatus("10");  //设置省级保存状态
            tbNatmehaChineseMedicine.setItemcode(itemcode);
            tbNatmehaChineseMedicine.setUserCode(userCode);
            tbNatmehaChineseMedicine.setCreater("省级"); //设定默认值
            tbNatmehaChineseMedicine.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, fileItemCode, fileDataCode, tbNatmehaChineseMedicine.getItemcreateat());
                result += this.tbNatmehaFileService.insertFile(tbNatmehaFile);
            }
            //加入信息进入热点表，成功则result为2
            result += this.tbNatmehaChineseMedicineService.insertTCMCommon(tbNatmehaChineseMedicine);
            if (result>0){
                return "redirect:/tbNatmehaChineseMedicine/provinceManCM";
            }
        }else if(refer != null){
            String itemcode = UUID.randomUUID().toString();
            String fileDataCode = itemcode;
            String userCode = UUID.randomUUID().toString();
            String fileItemCode = userCode;
            tbNatmehaChineseMedicine.setStatus("5");  //设置待省级审核状态
            tbNatmehaChineseMedicine.setItemcode(itemcode);
            tbNatmehaChineseMedicine.setUserCode(userCode);
            tbNatmehaChineseMedicine.setCreater("省级"); //设定默认值
            tbNatmehaChineseMedicine.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, fileItemCode, fileDataCode, tbNatmehaChineseMedicine.getItemcreateat());
                result += this.tbNatmehaFileService.insertFile(tbNatmehaFile);
            }
            //加入信息进入热点表，成功则result为2
            result += this.tbNatmehaChineseMedicineService.insertTCMCommon(tbNatmehaChineseMedicine);
            if (result>0){
                return "redirect:/tbNatmehaChineseMedicine/provinceManCM";
            }
        }
        return null;
    }

    // 省级管理员首页点击修改
    @RequestMapping("/provinceUpdateCM") // /tbNatmehaChineseMedicine/provinceUpdateCM
    public String undateToProvinceCM(Model model,Integer itemid){
        TbNatmehaChineseMedicine tbNatmehaChineseMedicine = this.tbNatmehaChineseMedicineService.queryById(itemid);
        model.addAttribute("tbNatmehaChineseMedicine",tbNatmehaChineseMedicine);
        return "tcmCommon/provinceUpdateCM";
    }

    //市级页面修改更新
    @RequestMapping("/provinceUpdateSave")  // /tbNatmehaChineseMedicine/cityUpdateSave
    public String provinceUpdateSave(@RequestParam("uploadImg") MultipartFile uploadImg, //@RequestParam 默认required = true,该图片必须上传
                                 String save, String refer,
                                 TbNatmehaChineseMedicine tbNatmehaChineseMedicine) {

        if(save != null){
            tbNatmehaChineseMedicine.setStatus("10");  //设置省级保存状态
            tbNatmehaChineseMedicine.setItemcreateat(new Date());
            int result = 0;
            if (uploadImg != null){
                //加入信息进入file表，成功则result为1
                TbNatmehaFile tbNatmehaFile = this.insertFileOne(uploadImg, tbNatmehaChineseMedicine.getUserCode(), tbNatmehaChineseMedicine.getItemcode(), tbNatmehaChineseMedicine.getItemcreateat());
                int i = this.tbNatmehaFileService.updateByPrimaryKeySelective(tbNatmehaFile);
                result += i;
            } //加入更改后信息进入热点表，成功则result为2
            result += this.tbNatmehaChineseMedicineService.updateByPrimaryKeySelective(tbNatmehaChineseMedicine);
            if (result > 0){
                return "redirect:/tbNatmehaChineseMedicine/provinceManCM";
            }
        }
        return null;
    }

    /**
     * @param itemid
     * @return
     */
    //市级管理员首页点击删除
    @RequestMapping("/provinceDeleteCM")  // /tbNatmehaChineseMedicine/provinceDeleteCM
    public String deleteToProvinceCM(Integer itemid){
        int i = this.tbNatmehaChineseMedicineService.deleteById(itemid);
        if(i>0){
            return "redirect:/tbNatmehaChineseMedicine/provinceManCM";
        }
        return null;
    }

    //市级级管理员页面提交
    @RequestMapping("/provinceSubmitCM")  // /tbNatmehaChineseMedicine/provinceSubmitCM
    public String provinceSubmitCM(Model model,Integer itemid){
        if(itemid != null){
            TbNatmehaChineseMedicine tbNatmehaChineseMedicine = this.tbNatmehaChineseMedicineService.queryById(itemid);
            String dataStatus = tbNatmehaChineseMedicine.getStatus();
            //已保存、省级不通过
            if(dataStatus.equals("10") ||dataStatus.equals("6")){
                dataStatus = "5"; //待省级审核
                tbNatmehaChineseMedicine.setStatus(dataStatus);
                int i = this.tbNatmehaChineseMedicineService.updateByPrimaryKeySelective(tbNatmehaChineseMedicine);
                if(i>0){
                    return "redirect:/tbNatmehaChineseMedicine/provinceManCM";
                }
            }
        }
        return null;
    }

    // 待省级审核
    @RequestMapping("/provinceSelectCM")    // /tbNatmehaChineseMedicine/provinceSelectCM
    public String provinceSelectCM(Model model,
                               Integer pageNum,
                               TbNatmehaChineseMedicine tbNatmehaChineseMedicine,
                               HttpSession session){
        if(pageNum == null){
            pageNum = 1;
            // 保留当前的查询条件，供接下来点击分页链接时使用
            session.setAttribute("tbNatmehaChineseMedicine",tbNatmehaChineseMedicine);
        }else {
            // 点击分页链接时取回查询条件
            tbNatmehaChineseMedicine = (TbNatmehaChineseMedicine) session.getAttribute("tbNatmehaChineseMedicine");
        }
        Page<TbNatmehaChineseMedicine> tbCHNMedicineList = this.tbNatmehaChineseMedicineService.selectAllByPageForProvince(tbNatmehaChineseMedicine,pageNum,5);
        model.addAttribute("tbCHNMedicineList",tbCHNMedicineList);
        return "tcmCommon/provinceSelect";
    }


    // 省级部门审核通过
    @RequestMapping("/provincePassCM")  // /tbNatmehaChineseMedicine/provincePassCM
    public String provincePassCM(Integer itemid){
        TbNatmehaChineseMedicine tbNatmehaChineseMedicine = this.tbNatmehaChineseMedicineService.queryById(itemid);
        tbNatmehaChineseMedicine.setStatus("7");  //省级部门通过
        int i = this.tbNatmehaChineseMedicineService.updateByPrimaryKeySelective(tbNatmehaChineseMedicine);
        if(i >0 ){
            return "redirect:/tbNatmehaChineseMedicine/provinceSelectCM";

        }else
            return null;
    }

    // 省级部门审核不通过
    @RequestMapping("/provinceNotPassCM") ///tbNatmehaChineseMedicine/provinceNotPassCM
    public String provinceNotPassCH(Integer itemid){
        TbNatmehaChineseMedicine tbNatmehaChineseMedicine = this.tbNatmehaChineseMedicineService.queryById(itemid);
        tbNatmehaChineseMedicine.setStatus("6");  //市级审核不通过
        int i = this.tbNatmehaChineseMedicineService.updateByPrimaryKeySelective(tbNatmehaChineseMedicine);
        if(i >0 ){
            return "redirect:/tbNatmehaChineseMedicine/provinceSelectCM";
        }else
            return null;
    }

    // 国医堂管理员
    @RequestMapping("/allManCM")  //  /tbNatmehaChineseMedicine/allManCM
    public String selectAllByPageForAllCM(Model model,
                                          Integer pageNum,
                                          TbNatmehaChineseMedicine tbNatmehaChineseMedicine,
                                          HttpSession session){
        if(pageNum == null){
            pageNum = 1;
            // 保留当前的查询条件，供接下来点击分页链接时使用
            session.setAttribute("tbNatmehaChineseMedicine",tbNatmehaChineseMedicine);
        }else {
            // 点击分页链接时取回查询条件
            tbNatmehaChineseMedicine = (TbNatmehaChineseMedicine) session.getAttribute("tbNatmehaChineseMedicine");
        }
        Page<TbNatmehaChineseMedicine> tbCHNMedicineList = this.tbNatmehaChineseMedicineService.selectAllByPageFour(tbNatmehaChineseMedicine, pageNum, 5);
        model.addAttribute("tbCHNMedicineList",tbCHNMedicineList);
        return "tcmCommon/allManCM";
    }


    // 国医堂管理员发布
    @RequestMapping("/issueCM")  //  /tbNatmehaChineseMedicine/issueCM
    public String issueCM(Integer itemid){
        TbNatmehaChineseMedicine tbNatmehaChineseMedicine = this.tbNatmehaChineseMedicineService.queryById(itemid);
        tbNatmehaChineseMedicine.setStatus("8"); //发布状态
        int i = this.tbNatmehaChineseMedicineService.updateByPrimaryKeySelective(tbNatmehaChineseMedicine);
        if(i>0){
            return "redirect:/tbNatmehaChineseMedicine/allManCM";
        }else
            return null;
    }

    // 国医堂管理员下架
    @RequestMapping("/closeCM")  //  /tbNatmehaChineseMedicine/closeCM
    public String closeCM(Integer itemid){
        TbNatmehaChineseMedicine tbNatmehaChineseMedicine = this.tbNatmehaChineseMedicineService.queryById(itemid);
        tbNatmehaChineseMedicine.setStatus("11"); //下架状态
        int i = this.tbNatmehaChineseMedicineService.updateByPrimaryKeySelective(tbNatmehaChineseMedicine);
        if(i>0){
            return "redirect:/tbNatmehaChineseMedicine/allManCM";
        }else
            return null;
    }
    /* 李荣锋中医常识end */
}
