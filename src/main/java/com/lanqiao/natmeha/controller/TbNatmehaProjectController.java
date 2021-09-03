package com.lanqiao.natmeha.controller;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.model.TbNatmehaFile;
import com.lanqiao.natmeha.model.TbNatmehaHotspot;
import com.lanqiao.natmeha.model.TbNatmehaProject;
import com.lanqiao.natmeha.service.TbNatmehaFileService;
import com.lanqiao.natmeha.service.TbNatmehaProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 开展项目、功能特色记录表(TbNatmehaProject)控制层
 *
 * @author makejava
 * @since 2021-08-19 09:12:33
 */
@Controller
@RequestMapping("/start")
public class  TbNatmehaProjectController {
    /*
     * 启动科员“智慧”国医堂惠民信息系统的信息处理页面
     * */
    @RequestMapping("/clerk_page")
    public String clerk_page() {
        return "tbItemClerk/item_clerk";
    }
    //注入对象
    @Autowired
    private TbNatmehaProjectService tbNatmehaProjectService;
    //文件表的对象
    @Autowired
    private TbNatmehaFileService tbNatmehaFileService;

    @RequestMapping("/item_clerk_code")
    public String selectByPage(Integer pageNum, Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        //System.out.println(neirou);
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setDataType("1");
        tbNatmehaProject.setNeirou(neirou);
        Page<TbNatmehaProject> tbNatmehaProjects = this.tbNatmehaProjectService.selectByPage(tbNatmehaProject, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaProjects", tbNatmehaProjects);
        return "tbItemClerk/item_clerk_code";
    }

    //跳转增加开展项目界面
    @RequestMapping("/item_addimage")
    public String addpage_solar() {
        return "tbItemClerk/item_clerk_add";
    }

    //添加新的数据
    @RequestMapping("/solar_insert")
    public String solar_insert(@RequestParam("file") MultipartFile file, String name, String creater, String content,
                               String datasource, String savebtn, String putbtn, String reset) throws Exception {
        String dataType = "1";//0功效特色 ， 1开展项目
        //如果是保存按钮，即是已保存状态 0，提交状态即是 1
        if (savebtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            String usercode = UUID.randomUUID().toString();
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemcode(itemcode);
            tbNatmehaProject.setName(name);
            tbNatmehaProject.setContent(content);
            tbNatmehaProject.setDataStatus("0");
            tbNatmehaProject.setDataType(dataType);
            tbNatmehaProject.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemcreateat = new java.sql.Date(ite.getTime());
            tbNatmehaProject.setItemcreateat(itemcreateat);
            tbNatmehaProject.setUserCode(usercode);
            this.tbNatmehaProjectService.solar_insert_code(tbNatmehaProject);
            //获取上传的文件名
            String originalFilename = file.getOriginalFilename();
            if (!originalFilename.equals("")) {
                System.out.println(originalFilename);
                //获取源文件前缀
                String fileNamePrefix = originalFilename.substring(0,originalFilename.lastIndexOf("."));
                //获取源文件后缀
                String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                //将源文件前缀之后加上时间戳避免重名
                String newFileNamePrefix = fileNamePrefix + "_" + new Date().getTime();
                //得到上传后新文件的文件名
                String newFileName = newFileNamePrefix+fileNameSuffix;
                //获取要保存的文件夹路径
                String path = "";
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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/item_clerk_code";

        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemcode(itemcode);
            tbNatmehaProject.setName(name);
            tbNatmehaProject.setContent(content);
            tbNatmehaProject.setDataStatus("1");//1代表的是待市级审核，即科员审核
            tbNatmehaProject.setDataType(dataType);
            tbNatmehaProject.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemcreateat = new java.sql.Date(ite.getTime());
            tbNatmehaProject.setItemcreateat(itemcreateat);
            String userCode = UUID.randomUUID().toString();
            tbNatmehaProject.setUserCode(userCode);
            this.tbNatmehaProjectService.solar_insert_code(tbNatmehaProject);
            //获取上传的文件名
            String originalFilename = file.getOriginalFilename();
            if (!originalFilename.equals("")) {
                System.out.println(originalFilename);
                //获取源文件前缀
                String fileNamePrefix = originalFilename.substring(0,originalFilename.lastIndexOf("."));
                //获取源文件后缀
                String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                //将源文件前缀之后加上时间戳避免重名
                String newFileNamePrefix = fileNamePrefix + "_" + new Date().getTime();
                //得到上传后新文件的文件名
                String newFileName = newFileNamePrefix+fileNameSuffix;
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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                tbNatmehaFile.setFileType(fileNameSuffix);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/item_clerk_code";
        } else if (reset != null) {
            return "redirect:/start/item_clerk_code";
        }
        return  null;
    }
    //查看返回
    @RequestMapping("/item_clerk_back")
    public String item_clerk_back() {
        return "redirect:/start/item_clerk_code";
    }

    //通过itemID查看信息，如果是保存状态的信息，可以进行更改，然后提交或者选择保存
    @RequestMapping("/item_clerck_select")
    public String item_select(Integer itemid,Model model) {
        TbNatmehaProject tbNatmehaProject = this.tbNatmehaProjectService.solar_select(itemid);
        model.addAttribute("tbNatmehaProject", tbNatmehaProject);
        return "tbItemClerk/item_clerk_see";
    }

    //查询需要修改保存状态下的信息
    @RequestMapping("/item_clerck_update")
    public String solar_clerck_update(Integer itemid,Model model) {
        TbNatmehaProject tbNatmehaProject = this.tbNatmehaProjectService.solar_select(itemid);
        model.addAttribute("tbNatmehaProject", tbNatmehaProject);
        return "tbItemClerk/item_clerk_update";
    }

    //修改保存状态下的信息,并且确定是保存还是提交
    @RequestMapping("/item_updatecode")
    public String item_updatecode(Integer itemid,String name,String creater,String content,
                                   String datasource,String savebtn,String putbtn,String reset) throws Exception {

        String dataType = "1";//0功效特色 ， 1开展项目
        //如果是保存按钮，即是已保存状态 0，提交状态（待县级审核）即是 1，县级审核不通过2，待市级审核3，
        // 市级审核不通过4，待省级审核5，省级审核不通过6，省级通过7，管理员发布8,
        if (savebtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            //String itemcode = UUID.randomUUID().toString();
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemid(itemid);
            tbNatmehaProject.setName(name);
            tbNatmehaProject.setContent(content);
            tbNatmehaProject.setDataStatus("0");
            tbNatmehaProject.setDataType(dataType);
            tbNatmehaProject.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaProject.setItemupdateat(itemupdateat);
            tbNatmehaProject.setUserCode("1122");
            this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
            return "redirect:/start/item_clerk_code";//修改成功后返回数据主页面
        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            //String itemcode = UUID.randomUUID().toString();
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemid(itemid);
            tbNatmehaProject.setName(name);
            tbNatmehaProject.setContent(content);
            tbNatmehaProject.setDataStatus("1");//1代表的是待市级审核，即科员审核
            tbNatmehaProject.setDataType(dataType);
            tbNatmehaProject.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaProject.setItemupdateat(itemupdateat);
            this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
            return "redirect:/start/item_clerk_code";
        } else if (reset != null) {
            return "redirect:/start/item_clerk_code";
        }
        return null;
    }

    //管理员开展项目信息页面直接提交数据给上一级审核,只需要改变数据状态就可
    @RequestMapping("/solar_clerck_directUpdate/{itemid}")
    @ResponseBody
    public int solar_clerck_directUpdate(@PathVariable("itemid") Integer itemid) {
        //String dataType = "1";//0功效特色 ， 1开展项目
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("1");//1代表的是待县级审核，即科员审核
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }

    //管理员删除信息
    @RequestMapping("/solar_clerk_deleteByItemid/{itemid}")
    @ResponseBody
    public int solar_clerk_deleteByItemid(@PathVariable("itemid") Integer itemid) {
        int i = this.tbNatmehaProjectService.deleteByItemid(itemid);
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
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("8");//1代表的是待市级审核，即科员审核,8代表发布
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }

    //管理员下架发布信息
    @RequestMapping("/solar_clerck_norelease/{itemid}")
    @ResponseBody
    public int solar_clerck_norelease(@PathVariable("itemid") Integer itemid) {
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("7");//1代表的是待市级审核，即科员审核,8代表发布，状态变为省级审核通过
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }


    /*
     * 启动县级机构“智慧”国医堂惠民信息系统的信息处理页面
     * */
    @RequestMapping("/county_page")
    public String county_page() {
        return "tbItemCounty/item_county";
    }

    //县级机构显示开展项目信息页面
    @RequestMapping("/item_county_code")
    public String solar_county_codeselectBypage(Integer pageNum,Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setDataType("1");
        //tbNatmehaProject.setDataStatus("1");
        tbNatmehaProject.setNeirou(neirou);
        Page<TbNatmehaProject> tbNatmehaProjects = this.tbNatmehaProjectService.county_selectByPage(tbNatmehaProject, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaProjects", tbNatmehaProjects);
        return "tbItemCounty/item_county_code";
    }

    //县级查看要审核的具体信息，并且决定是否给予通过
    @RequestMapping("/item_county_select")
    public String item_county_select(Integer itemid,Model model) {
        TbNatmehaProject tbNatmehaProject = this.tbNatmehaProjectService.solar_select(itemid);
        if (tbNatmehaProject.getDataStatus().equals("1")) {
            model.addAttribute("tbNatmehaProject", tbNatmehaProject);
            return "tbItemCounty/item_county_update";
        }
        model.addAttribute("tbNatmehaProject", tbNatmehaProject);
        return "tbItemCounty/item_county_see";
    }

    //查看信息页面，审核决定给予通过还是不通过
    @RequestMapping("/item_county_updatecode")
    public String item_county_updatecode(Integer itemid,String reset,String nopassbtn,String passbtn) throws InterruptedException {
        if (passbtn != null) {
            Thread.sleep(2000);
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemid(itemid);
            tbNatmehaProject.setDataStatus("3");
            this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
            return "redirect:/start/item_county_code";
        } else if (nopassbtn != null) {
            Thread.sleep(2000);
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemid(itemid);
            tbNatmehaProject.setDataStatus("2");
            this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
            return "redirect:/start/item_county_code";
        } else if (reset != null) {
            return "redirect:/start/item_county_code";
        }
        return null;
    }

    @RequestMapping("/item_county_back")
    public String solar_county_back() {
        return "redirect:/start/item_county_code";
    }

    //显示信息页面，直接审核通过
    @RequestMapping("/solar_county_pass/{itemid}")
    @ResponseBody
    public int solar_county_pass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("3");
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }
    //显示信息页面，直接审核不通过
    @RequestMapping("/solar_county_nopass/{itemid}")
    @ResponseBody
    public int solar_county_nopass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("2");
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }

    /*
     * 启动市级机构“智慧”国医堂惠民信息系统的信息处理页面
     * */
    @RequestMapping("/municipal_page")
    public String municipal_page() {
        return "tbItemMunicipal/item_municipal";
    }

    //市级审核开展项目信息审核界面
    @RequestMapping("/item_municipal_code")
    public String solar_municipal_codeselectBypage(Integer pageNum,Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setDataType("1");
        tbNatmehaProject.setDataStatus("3");
        tbNatmehaProject.setNeirou(neirou);
        Page<TbNatmehaProject> tbNatmehaProjects = this.tbNatmehaProjectService.municipal_selectByPage(tbNatmehaProject, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaProjects", tbNatmehaProjects);
        return "tbItemMunicipal/item_municipal_code";
    }

    //市级机构查看需要审核的信息，并决定是否给予通过
    @RequestMapping("/item_municipal_select")
    public String solar_municipal_select(Integer itemid,Model model) {
        TbNatmehaProject tbNatmehaProject = this.tbNatmehaProjectService.solar_select(itemid);
        if (tbNatmehaProject.getDataStatus().equals("3")) {
            model.addAttribute("tbNatmehaProject", tbNatmehaProject);
            return "tbItemMunicipal/item_municipal_update";
        }
        model.addAttribute("tbNatmehaProject", tbNatmehaProject);
        return "tbItemMunicipal/item_municipal_see";
    }

    //市级审核决定既不给予通过
    @RequestMapping("/item_municipal_updatecode")
    public String item_municipal_updatecode(Integer itemid,String reset,String nopassbtn,String passbtn) throws InterruptedException {
        if (passbtn != null) {
            Thread.sleep(2000);
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemid(itemid);
            tbNatmehaProject.setDataStatus("5");
            this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
            return "redirect:/start/item_municipal_code";
        } else if (nopassbtn != null) {
            Thread.sleep(2000);
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemid(itemid);
            tbNatmehaProject.setDataStatus("4");
            this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
            return "redirect:/start/item_municipal_code";
        } else if (reset != null) {
            return "redirect:/start/item_municipal_code";
        }
        return null;
    }
    //单纯查看页面，取消按钮返回
    @RequestMapping("/item_municipal_back")
    public String solar_municipal_back() {
        return "redirect:/start/item_municipal_code";
    }

    //市级审核页面决定通不通过,首先是显示通过的
    @RequestMapping("/solar_municipal_pass/{itemid}")
    @ResponseBody
    public int solar_municipal_pass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("5");
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }
    //市级审核页面决定通不通过,然后是不给予通过的
    @RequestMapping("/solar_municipal_nopass/{itemid}")
    @ResponseBody
    public int solar_municipal_nopass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("4");
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }

    /*
     * 启动省级机构“智慧”国医堂惠民信息系统的信息处理页面
     * */
    @RequestMapping("/provincial_page")
    public String provincial_page() {
        return "tbItemProvincial/item_provincial";
    }

    //省级审核开展项目信息审核界面
    @RequestMapping("/item_provincial_code")
    public String item_provincial_codeselectBypage(Integer pageNum,Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setDataType("1");
        tbNatmehaProject.setDataStatus("5");
        tbNatmehaProject.setNeirou(neirou);
        Page<TbNatmehaProject> tbNatmehaProjects = this.tbNatmehaProjectService.provincial_selectByPage(tbNatmehaProject, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaProjects", tbNatmehaProjects);
        return "tbItemProvincial/item_provincial_code";
    }

    //省级查看要审核的具体信息，并且决定是否给予通过
    @RequestMapping("/item_provincial_select")
    public String item_provincial_select(Integer itemid,Model model) {
        TbNatmehaProject tbNatmehaProject = this.tbNatmehaProjectService.solar_select(itemid);
        if (tbNatmehaProject.getDataStatus().equals("5")) {
            model.addAttribute("tbNatmehaProject", tbNatmehaProject);
            return "tbItemProvincial/item_provincial_update";
        }
        model.addAttribute("tbNatmehaProject", tbNatmehaProject);
        return "tbItemProvincial/item_provincial_see";
    }

    //查看信息页面，审核决定给予通过还是不通过
    @RequestMapping("/item_provincial_updatecode")
    public String item_provincial_updatecode(Integer itemid,String reset,String nopassbtn,String passbtn) throws InterruptedException {
        if (passbtn != null) {
            Thread.sleep(2000);
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemid(itemid);
            tbNatmehaProject.setDataStatus("7");
            this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
            return "redirect:/start/item_provincial_code";
        } else if (nopassbtn != null) {
            Thread.sleep(2000);
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemid(itemid);
            tbNatmehaProject.setDataStatus("6");
            this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
            return "redirect:/start/item_provincial_code";
        } else if (reset != null) {
            return "redirect:/start/item_provincial_code";
        }
        return null;
    }

    @RequestMapping("/item_provincial_back")
    public String solar_provincial_back() {
        return "redirect:/start/item_provincial_code";
    }

    //省级审核页面决定通不通过,首先是显示通过的
    @RequestMapping("/solar_provincial_pass/{itemid}")
    @ResponseBody
    public int item_provincial_pass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("7");
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }
    //省级审核页面决定通不通过,然后是不给予通过的
    @RequestMapping("/solar_provincial_nopass/{itemid}")
    @ResponseBody
    public int solar_provincial_nopass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("6");
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }


    /*
     * 县级管理员处理功效特色信息
     * */
    @RequestMapping("/efficacy_clerk_code")
    public String efficacy_clerk_code(Integer pageNum, Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        //System.out.println(neirou);
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setDataType("0");
        tbNatmehaProject.setNeirou(neirou);
        Page<TbNatmehaProject> tbNatmehaProjects = this.tbNatmehaProjectService.selectByPage(tbNatmehaProject, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaProjects", tbNatmehaProjects);
        return "tbEfficacyClerk/efficacy_clerk_code";
    }

    //跳转增加开展项目界面
    @RequestMapping("/efficacy_addimage")
    public String addpage_efficacy() {
        return "tbEfficacyClerk/efficacy_clerk_add";
    }

    //添加新的数据
    @RequestMapping("/efficacy_insert")
    public String efficacy_insert(@RequestParam("file") MultipartFile file, String name, String creater, Integer price,
                               String content, String savebtn, String putbtn, String reset) throws Exception {
        String dataType = "0";//0功效特色 ， 1开展项目
        //如果是保存按钮，即是已保存状态 0，提交状态即是 1
        if (savebtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            String userCode = UUID.randomUUID().toString();
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemcode(itemcode);
            tbNatmehaProject.setName(name);
            tbNatmehaProject.setPrice(price);
            tbNatmehaProject.setContent(content);
            tbNatmehaProject.setDataStatus("0");
            tbNatmehaProject.setDataType(dataType);
            tbNatmehaProject.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemcreateat = new java.sql.Date(ite.getTime());
            tbNatmehaProject.setItemcreateat(itemcreateat);
            tbNatmehaProject.setUserCode(userCode);
            this.tbNatmehaProjectService.solar_insert_code(tbNatmehaProject);
            //获取上传的文件名
            String originalFilename = file.getOriginalFilename();
            if (!originalFilename.equals("")) {
                System.out.println(originalFilename);
                //获取源文件前缀
                String fileNamePrefix = originalFilename.substring(0,originalFilename.lastIndexOf("."));
                //获取源文件后缀
                String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                //将源文件前缀之后加上时间戳避免重名
                String newFileNamePrefix = fileNamePrefix + "_" + new Date().getTime();
                //得到上传后新文件的文件名
                String newFileName = newFileNamePrefix+fileNameSuffix;
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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/efficacy_clerk_code";
        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemcode(itemcode);
            tbNatmehaProject.setName(name);
            tbNatmehaProject.setPrice(price);
            tbNatmehaProject.setContent(content);
            tbNatmehaProject.setDataStatus("1");//1代表的是待市级审核，即科员审核
            tbNatmehaProject.setDataType(dataType);
            tbNatmehaProject.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemcreateat = new java.sql.Date(ite.getTime());
            tbNatmehaProject.setItemcreateat(itemcreateat);
            String userCode = UUID.randomUUID().toString();
            tbNatmehaProject.setUserCode(userCode);
            this.tbNatmehaProjectService.solar_insert_code(tbNatmehaProject);
            //获取上传的文件名
            String originalFilename = file.getOriginalFilename();
            if (!originalFilename.equals("")) {
                System.out.println(originalFilename);
                //获取源文件前缀
                String fileNamePrefix = originalFilename.substring(0,originalFilename.lastIndexOf("."));
                //获取源文件后缀
                String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                //将源文件前缀之后加上时间戳避免重名
                String newFileNamePrefix = fileNamePrefix + "_" + new Date().getTime();
                //得到上传后新文件的文件名
                String newFileName = newFileNamePrefix+fileNameSuffix;
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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                tbNatmehaFile.setFileType(fileNameSuffix);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/efficacy_clerk_code";
        } else if (reset != null) {
            return "redirect:/start/efficacy_clerk_code";
        }
        return  null;
    }
    //查看返回
    @RequestMapping("/efficacy_clerk_back")
    public String efficacy_clerk_back() {
        return "redirect:/start/efficacy_clerk_code";
    }

    //通过itemID查看信息，如果是保存状态的信息，可以进行更改，然后提交或者选择保存
    @RequestMapping("/efficacy_clerck_select")
    public String efficacy_select(Integer itemid,Model model) {
        TbNatmehaProject tbNatmehaProject = this.tbNatmehaProjectService.solar_select(itemid);
        model.addAttribute("tbNatmehaProject", tbNatmehaProject);
        return "tbEfficacyClerk/efficacy_clerk_see";
    }

    //管理员通过修改按钮，跳转自我保健修改页面
    @RequestMapping("/efficacy_clerck_update")
    public String efficacy_clerck_update(Integer itemid,Model model) {
        TbNatmehaProject tbNatmehaProject = this.tbNatmehaProjectService.solar_select(itemid);
        model.addAttribute("tbNatmehaProject", tbNatmehaProject);
        return "tbEfficacyClerk/efficacy_clerk_update";
    }

    //修改保存状态下的信息,并且确定是保存还是提交
    @RequestMapping("/efficacy_updatecode")
    public String efficacy_updatecode(Integer itemid,String name,String creater,Integer price,
                                   String content,String savebtn,String putbtn,String reset) throws Exception {

        String dataType = "0";//0功效特色 ， 1开展项目
        //如果是保存按钮，即是已保存状态 0，提交状态（待县级审核）即是 1，县级审核不通过2，待市级审核3，
        // 市级审核不通过4，待省级审核5，省级审核不通过6，省级通过7，管理员发布8,
        if (savebtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            //String itemcode = UUID.randomUUID().toString();
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemid(itemid);
            tbNatmehaProject.setName(name);
            tbNatmehaProject.setPrice(price);
            tbNatmehaProject.setContent(content);
            tbNatmehaProject.setDataStatus("0");
            tbNatmehaProject.setDataType(dataType);
            tbNatmehaProject.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaProject.setItemupdateat(itemupdateat);
            this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
            return "redirect:/start/efficacy_clerk_code";//修改成功后返回数据主页面
        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            //String itemcode = UUID.randomUUID().toString();
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemid(itemid);
            tbNatmehaProject.setName(name);
            tbNatmehaProject.setPrice(price);
            tbNatmehaProject.setContent(content);
            tbNatmehaProject.setDataStatus("1");//1代表的是待市级审核，即科员审核
            tbNatmehaProject.setDataType(dataType);
            tbNatmehaProject.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaProject.setItemupdateat(itemupdateat);
            this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
            return "redirect:/start/efficacy_clerk_code";
        } else if (reset != null) {
            return "redirect:/start/efficacy_clerk_code";
        }
        return null;
    }

    //县级管理员功效特色信息页面直接提交数据给上一级审核,只需要改变数据状态就可
    @RequestMapping("/efficacy_clerck_directUpdate/{itemid}")
    @ResponseBody
    public int efficacy_clerck_directUpdate(@PathVariable("itemid") Integer itemid) {
        //String dataType = "1";//0功效特色 ， 1开展项目
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("1");//1代表的是待县级审核，即科员审核
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }

    //县级管理员删除信息
    @RequestMapping("/efficacy_clerk_deleteByItemid/{itemid}")
    @ResponseBody
    public int efficacy_clerk_deleteByItemid(@PathVariable("itemid") Integer itemid) {
        int i = this.tbNatmehaProjectService.deleteByItemid(itemid);
        if (i>0) {
            return i;
        }else {
            return 0;
        }
    }

    //县级管理员发布功效特色信息
    @RequestMapping("/efficacy_clerck_release/{itemid}")
    @ResponseBody
    public int efficacy_clerck_release(@PathVariable("itemid") Integer itemid) {
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("8");//1代表的是待市级审核，即科员审核,8代表发布
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }

    //县级管理员下架功效特色发布信息
    @RequestMapping("/efficacy_clerck_norelease/{itemid}")
    @ResponseBody
    public int efficacy_clerck_norelease(@PathVariable("itemid") Integer itemid) {
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("7");//1代表的是待市级审核，即科员审核,8代表发布，状态变为省级审核通过
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }



    //县级机构显示功效特色信息页面
    @RequestMapping("/efficacy_county_code")
    public String efficacy_county_codeselectBypage(Integer pageNum,Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setDataType("0");
        //tbNatmehaProject.setDataStatus("1");
        tbNatmehaProject.setNeirou(neirou);
        Page<TbNatmehaProject> tbNatmehaProjects = this.tbNatmehaProjectService.county_selectByPage(tbNatmehaProject, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaProjects", tbNatmehaProjects);
        return "tbEfficacyCounty/efficacy_county_code";
    }

    //县级查看要审核的具体信息，并且决定是否给予通过
    @RequestMapping("/efficacy_county_select")
    public String efficacy_county_select(Integer itemid,Model model) {
        TbNatmehaProject tbNatmehaProject = this.tbNatmehaProjectService.solar_select(itemid);
        if (tbNatmehaProject.getDataStatus().equals("1")) {
            model.addAttribute("tbNatmehaProject", tbNatmehaProject);
            return "tbEfficacyCounty/efficacy_county_update";
        }
        model.addAttribute("tbNatmehaProject", tbNatmehaProject);
        return "tbEfficacyCounty/efficacy_county_see";
    }

    //查看信息页面，审核决定给予通过还是不通过
    @RequestMapping("/efficacy_county_updatecode")
    public String efficacy_county_updatecode(Integer itemid,String reset,String nopassbtn,String passbtn) throws InterruptedException {
        if (passbtn != null) {
            Thread.sleep(2000);
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemid(itemid);
            tbNatmehaProject.setDataStatus("3");
            this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
            return "redirect:/start/efficacy_county_code";
        } else if (nopassbtn != null) {
            Thread.sleep(2000);
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemid(itemid);
            tbNatmehaProject.setDataStatus("2");
            this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
            return "redirect:/start/efficacy_county_code";
        } else if (reset != null) {
            return "redirect:/start/efficacy_county_code";
        }
        return null;
    }

    @RequestMapping("/efficacy_county_back")
    public String efficacy_county_back() {
        return "redirect:/start/efficacy_county_code";
    }

    //显示信息页面，直接审核通过
    @RequestMapping("/efficacy_county_pass/{itemid}")
    @ResponseBody
    public int efficacy_county_pass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("3");
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }
    //显示信息页面，直接审核不通过
    @RequestMapping("/efficacy_county_nopass/{itemid}")
    @ResponseBody
    public int efficacy_county_nopass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("2");
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }


    /*
     *
     *市级机构审核功效特色页面
     */
    //市级审核功效特色信息审核界面
    @RequestMapping("/efficacy_municipal_code")
    public String efficacy_municipal_codeselectBypage(Integer pageNum,Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setDataType("0");
        tbNatmehaProject.setDataStatus("3");
        tbNatmehaProject.setNeirou(neirou);
        Page<TbNatmehaProject> tbNatmehaProjects = this.tbNatmehaProjectService.municipal_selectByPage(tbNatmehaProject, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaProjects", tbNatmehaProjects);
        return "tbEfficacyMunicipal/efficacy_municipal_code";
    }

    //市级机构查看需要审核的信息，并决定是否给予通过
    @RequestMapping("/efficacy_municipal_select")
    public String efficacy_municipal_select(Integer itemid,Model model) {
        TbNatmehaProject tbNatmehaProject = this.tbNatmehaProjectService.solar_select(itemid);
        if (tbNatmehaProject.getDataStatus().equals("3")) {
            model.addAttribute("tbNatmehaProject", tbNatmehaProject);
            return "tbEfficacyMunicipal/efficacy_municipal_update";
        }
        model.addAttribute("tbNatmehaProject", tbNatmehaProject);
        return "tbEfficacyMunicipal/efficacy_municipal_see";
    }

    //市级审核决定既不给予通过
    @RequestMapping("/efficacy_municipal_updatecode")
    public String efficacy_municipal_updatecode(Integer itemid,String reset,String nopassbtn,String passbtn) throws InterruptedException {
        if (passbtn != null) {
            Thread.sleep(2000);
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemid(itemid);
            tbNatmehaProject.setDataStatus("5");
            this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
            return "redirect:/start/efficacy_municipal_code";
        } else if (nopassbtn != null) {
            Thread.sleep(2000);
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemid(itemid);
            tbNatmehaProject.setDataStatus("4");
            this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
            return "redirect:/start/efficacy_municipal_code";
        } else if (reset != null) {
            return "redirect:/start/efficacy_municipal_code";
        }
        return null;
    }
    //单纯查看页面，取消按钮返回
    @RequestMapping("/efficacy_municipal_back")
    public String efficacy_municipal_back() {
        return "redirect:/start/efficacy_municipal_code";
    }

    //市级审核页面决定通不通过,首先是显示通过的
    @RequestMapping("/efficacy_municipal_pass/{itemid}")
    @ResponseBody
    public int efficacy_municipal_pass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("5");
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }
    //市级审核页面决定通不通过,然后是不给予通过的
    @RequestMapping("/efficacy_municipal_nopass/{itemid}")
    @ResponseBody
    public int efficacy_municipal_nopass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("4");
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }


    /*
     * 省级机构处理功效特色的信息
     * */
    //省级审核功效特色审核界面
    @RequestMapping("/efficacy_provincial_code")
    public String efficacy_provincial_codeselectBypage(Integer pageNum,Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setDataType("0");
        tbNatmehaProject.setDataStatus("5");
        tbNatmehaProject.setNeirou(neirou);
        Page<TbNatmehaProject> tbNatmehaProjects = this.tbNatmehaProjectService.provincial_selectByPage(tbNatmehaProject, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaProjects", tbNatmehaProjects);
        return "tbEfficacyProvincial/efficacy_provincial_code";
    }

    //省级查看要审核的具体信息，并且决定是否给予通过
    @RequestMapping("/efficacy_provincial_select")
    public String efficacy_provincial_select(Integer itemid,Model model) {
        TbNatmehaProject tbNatmehaProject = this.tbNatmehaProjectService.solar_select(itemid);
        if (tbNatmehaProject.getDataStatus().equals("5")) {
            model.addAttribute("tbNatmehaProject", tbNatmehaProject);
            return "tbEfficacyProvincial/efficacy_provincial_update";
        }
        model.addAttribute("tbNatmehaProject", tbNatmehaProject);
        return "tbEfficacyProvincial/efficacy_provincial_see";
    }

    //查看信息页面，审核决定给予通过还是不通过
    @RequestMapping("/efficacy_provincial_updatecode")
    public String efficacy_provincial_updatecode(Integer itemid,String reset,String nopassbtn,String passbtn) throws InterruptedException {
        if (passbtn != null) {
            Thread.sleep(2000);
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemid(itemid);
            tbNatmehaProject.setDataStatus("7");
            this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
            return "redirect:/start/efficacy_provincial_code";
        } else if (nopassbtn != null) {
            Thread.sleep(2000);
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemid(itemid);
            tbNatmehaProject.setDataStatus("6");
            this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
            return "redirect:/start/efficacy_provincial_code";
        } else if (reset != null) {
            return "redirect:/start/efficacy_provincial_code";
        }
        return null;
    }

    @RequestMapping("/efficacy_provincial_back")
    public String efficacy_provincial_back() {
        return "redirect:/start/efficacy_provincial_code";
    }

    //省级审核页面决定通不通过,首先是显示通过的
    @RequestMapping("/efficacy_provincial_pass/{itemid}")
    @ResponseBody
    public int efficacy_provincial_pass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("7");
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }
    //省级审核页面决定通不通过,然后是不给予通过的
    @RequestMapping("/efficacy_provincial_nopass/{itemid}")
    @ResponseBody
    public int efficacy_provincial_nopass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("6");
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }

    /*
     * 启动市级管理员“智慧”国医堂惠民信息系统的信息处理页面
     * */
    @RequestMapping("/municipalclerk_page")
    public String municipalclerk_page() {
        return "tbMunicipalClerk/item_municipal_clerk";
    }

    //市级管理开展项目
    @RequestMapping("/item_municipalclerk_code")
    public String solar_municipal_selectByPage(Integer pageNum, Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        //System.out.println(neirou);
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setDataType("1");
        //tbNatmehaHotspot.setDataStatus("1");//1表示的是待市级审核
        tbNatmehaProject.setNeirou(neirou);
        Page<TbNatmehaProject> tbNatmehaProjects = this.tbNatmehaProjectService.municipal_clerkselectByPage(tbNatmehaProject, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaProjects", tbNatmehaProjects);
        return "tbMunicipalClerk/item_municipal_code";
    }

    //跳转增加开展项目界面
    @RequestMapping("/item_municipal_addimage")
    public String addpage_municipal() {
        return "tbMunicipalClerk/item_municipal_add";
    }

    //市级管理员添加新的数据
    @RequestMapping("/item_municipal_insert")
    public String item_municipal_insert(@RequestParam("file") MultipartFile file, String name, String creater, String content,
                               String datasource, String savebtn, String putbtn, String reset) throws Exception {
        String dataType = "1";//0功效特色 ， 1开展项目
        //如果是保存按钮，即是已保存状态 0，提交状态即是 1
        if (savebtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            String userCode = UUID.randomUUID().toString();
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemcode(itemcode);
            tbNatmehaProject.setName(name);
            tbNatmehaProject.setContent(content);
            tbNatmehaProject.setDataStatus("9");
            tbNatmehaProject.setDataType(dataType);
            tbNatmehaProject.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemcreateat = new java.sql.Date(ite.getTime());
            tbNatmehaProject.setItemcreateat(itemcreateat);
            tbNatmehaProject.setUserCode(userCode);
            this.tbNatmehaProjectService.solar_insert_code(tbNatmehaProject);
            //获取上传的文件名
            String originalFilename = file.getOriginalFilename();
            if (!originalFilename.equals("")) {
                System.out.println(originalFilename);
                //获取源文件前缀
                String fileNamePrefix = originalFilename.substring(0,originalFilename.lastIndexOf("."));
                //获取源文件后缀
                String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                //将源文件前缀之后加上时间戳避免重名
                String newFileNamePrefix = fileNamePrefix + "_" + new Date().getTime();
                //得到上传后新文件的文件名
                String newFileName = newFileNamePrefix+fileNameSuffix;
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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/item_municipalclerk_code";

        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemcode(itemcode);
            tbNatmehaProject.setName(name);
            tbNatmehaProject.setContent(content);
            tbNatmehaProject.setDataStatus("3");
            tbNatmehaProject.setDataType(dataType);
            tbNatmehaProject.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemcreateat = new java.sql.Date(ite.getTime());
            tbNatmehaProject.setItemcreateat(itemcreateat);
            String userCode = UUID.randomUUID().toString();
            tbNatmehaProject.setUserCode(userCode);
            this.tbNatmehaProjectService.solar_insert_code(tbNatmehaProject);
            //获取上传的文件名
            String originalFilename = file.getOriginalFilename();
            if (!originalFilename.equals("")) {
                System.out.println(originalFilename);
                //获取源文件前缀
                String fileNamePrefix = originalFilename.substring(0,originalFilename.lastIndexOf("."));
                //获取源文件后缀
                String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                //将源文件前缀之后加上时间戳避免重名
                String newFileNamePrefix = fileNamePrefix + "_" + new Date().getTime();
                //得到上传后新文件的文件名
                String newFileName = newFileNamePrefix+fileNameSuffix;
                //获取要保存的文件夹路径
                String path = "/root/images";
                //在指定路径下，产生一个指定名称的新文件D
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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/item_municipalclerk_code";
        } else if (reset != null) {
            return "redirect:/start/item_municipalclerk_code";
        }
        return  null;
    }
    //查看返回
    @RequestMapping("/item_municipalclerk_back")
    public String item_municipal_back() {
        return "redirect:/start/item_municipalclerk_code";
    }

    //通过itemID查看信息，如果是保存状态的信息，可以进行更改，然后提交或者选择保存
    @RequestMapping("/item_municipalclerk_select")
    public String item_municipalselect(Integer itemid,Model model) {
        TbNatmehaProject tbNatmehaProject = this.tbNatmehaProjectService.solar_select(itemid);
        model.addAttribute("tbNatmehaProject", tbNatmehaProject);
        return "tbMunicipalClerk/item_municipalclerk_see";
    }

    //查询需要修改保存状态下的信息
    @RequestMapping("/item_municipalclerk_update")
    public String item_municipalclerck_update(Integer itemid,Model model) {
        TbNatmehaProject tbNatmehaProject = this.tbNatmehaProjectService.solar_select(itemid);
        model.addAttribute("tbNatmehaProject", tbNatmehaProject);
        return "tbMunicipalClerk/item_municipalclerk_update";
    }

    //修改保存状态下的信息,并且确定是保存还是提交
    @RequestMapping("/item_municipalclerk_updatecode")
    public String item_municipalclerck_updatecode(Integer itemid,String name,String creater,String content,
                                   String datasource,String savebtn,String putbtn,String reset) throws Exception {

        String dataType = "1";//0功效特色 ， 1开展项目
        //如果是保存按钮，即是已保存状态 0，提交状态（待县级审核）即是 1，县级审核不通过2，待市级审核3，
        // 市级审核不通过4，待省级审核5，省级审核不通过6，省级通过7，管理员发布8,
        if (savebtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            //String itemcode = UUID.randomUUID().toString();
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemid(itemid);
            tbNatmehaProject.setName(name);
            tbNatmehaProject.setContent(content);
            tbNatmehaProject.setDataStatus("9");
            tbNatmehaProject.setDataType(dataType);
            tbNatmehaProject.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaProject.setItemupdateat(itemupdateat);
            this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
            return "redirect:/start/item_municipalclerk_code";//修改成功后返回数据主页面
        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            //String itemcode = UUID.randomUUID().toString();
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemid(itemid);
            tbNatmehaProject.setName(name);
            tbNatmehaProject.setContent(content);
            tbNatmehaProject.setDataStatus("1");//1代表的是待市级审核，即科员审核
            tbNatmehaProject.setDataType(dataType);
            tbNatmehaProject.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaProject.setItemupdateat(itemupdateat);
            this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
            return "redirect:/start/item_municipalclerk_code";
        } else if (reset != null) {
            return "redirect:/start/item_municipalclerk_code";
        }
        return null;
    }

    //市级管理员开展项目信息页面直接提交数据给上一级审核,只需要改变数据状态就可
    @RequestMapping("/solar_municipalclerck_directUpdate/{itemid}")
    @ResponseBody
    public int solar_municipalclerck_directUpdate(@PathVariable("itemid") Integer itemid) {
        //String dataType = "1";//0功效特色 ， 1开展项目
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("3");//3代表的是待市级审核
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }

    //市级管理员删除信息
    @RequestMapping("/solar_municipalclerk_deleteByItemid/{itemid}")
    @ResponseBody
    public int solar_municipalclerk_deleteByItemid(@PathVariable("itemid") Integer itemid) {
        int i = this.tbNatmehaProjectService.deleteByItemid(itemid);
        if (i>0) {
            return i;
        }else {
            return 0;
        }
    }


    //市级管理功效特色
    @RequestMapping("/efficacy_municipalclerk_code")
    public String efficacy_municipal_selectByPage(Integer pageNum, Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        //System.out.println(neirou);
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setDataType("0");  //功效特色为0 开展项目为1
        //tbNatmehaHotspot.setDataStatus("1");//1表示的是待市级审核
        tbNatmehaProject.setNeirou(neirou);
        Page<TbNatmehaProject> tbNatmehaProjects = this.tbNatmehaProjectService.municipal_clerkselectByPage(tbNatmehaProject, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaProjects", tbNatmehaProjects);
        return "tbMunicipalClerk/efficacy_municipal_code";
    }

    //跳转增加开展项目界面
    @RequestMapping("/efficacy_municipal_addimage")
    public String addpage_efficacy_municipal() {
        return "tbMunicipalClerk/efficacy_municipal_add";
    }

    //市级管理员添加新的数据
    @RequestMapping("/efficacy_municipal_insert")
    public String efficacy_municipal_insert(@RequestParam("file") MultipartFile file, String name, String creater, Integer price,
                                        String content, String savebtn, String putbtn, String reset) throws Exception {
        String dataType = "0";//0功效特色 ， 1开展项目
        //如果是保存按钮，即是已保存状态 0，提交状态即是 1
        if (savebtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            String userCode = UUID.randomUUID().toString();
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemcode(itemcode);
            tbNatmehaProject.setName(name);
            tbNatmehaProject.setPrice(price);
            tbNatmehaProject.setContent(content);
            tbNatmehaProject.setDataStatus("9");
            tbNatmehaProject.setDataType(dataType);
            tbNatmehaProject.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemcreateat = new java.sql.Date(ite.getTime());
            tbNatmehaProject.setItemcreateat(itemcreateat);
            tbNatmehaProject.setUserCode(userCode);
            this.tbNatmehaProjectService.solar_insert_code(tbNatmehaProject);
            //获取上传的文件名
            String originalFilename = file.getOriginalFilename();
            if (!originalFilename.equals("")) {
                System.out.println(originalFilename);
                //获取源文件前缀
                String fileNamePrefix = originalFilename.substring(0,originalFilename.lastIndexOf("."));
                //获取源文件后缀
                String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                //将源文件前缀之后加上时间戳避免重名
                String newFileNamePrefix = fileNamePrefix + "_" + new Date().getTime();
                //得到上传后新文件的文件名
                String newFileName = newFileNamePrefix+fileNameSuffix;
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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/efficacy_municipalclerk_code";

        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemcode(itemcode);
            tbNatmehaProject.setName(name);
            tbNatmehaProject.setPrice(price);
            tbNatmehaProject.setContent(content);
            tbNatmehaProject.setDataStatus("3");
            tbNatmehaProject.setDataType(dataType);
            tbNatmehaProject.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemcreateat = new java.sql.Date(ite.getTime());
            tbNatmehaProject.setItemcreateat(itemcreateat);
            String userCode = UUID.randomUUID().toString();
            tbNatmehaProject.setUserCode(userCode);
            this.tbNatmehaProjectService.solar_insert_code(tbNatmehaProject);
            //获取上传的文件名
            String originalFilename = file.getOriginalFilename();
            if (!originalFilename.equals("")) {
                System.out.println(originalFilename);
                //获取源文件前缀
                String fileNamePrefix = originalFilename.substring(0,originalFilename.lastIndexOf("."));
                //获取源文件后缀
                String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                //将源文件前缀之后加上时间戳避免重名
                String newFileNamePrefix = fileNamePrefix + "_" + new Date().getTime();
                //得到上传后新文件的文件名
                String newFileName = newFileNamePrefix+fileNameSuffix;
                //获取要保存的文件夹路径
                String path = "/root/images";
                //在指定路径下，产生一个指定名称的新文件D
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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/efficacy_municipalclerk_code";
        } else if (reset != null) {
            return "redirect:/start/efficacy_municipalclerk_code";
        }
        return  null;
    }
    //查看返回
    @RequestMapping("/efficacy_municipalclerk_back")
    public String efficacy_municipalclerk_back() {
        return "redirect:/start/efficacy_municipalclerk_code";
    }

    //通过itemID查看信息，如果是保存状态的信息，可以进行更改，然后提交或者选择保存
    @RequestMapping("/efficacy_municipalclerk_select")
    public String efficacy_municipalclerk_select(Integer itemid,Model model) {
        TbNatmehaProject tbNatmehaProject = this.tbNatmehaProjectService.solar_select(itemid);
        model.addAttribute("tbNatmehaProject", tbNatmehaProject);
        return "tbMunicipalClerk/efficacy_municipalclerk_see";
    }

    //查询需要修改保存状态下的信息
    @RequestMapping("/efficacy_municipalclerk_update")
    public String efficacy_municipalclerk_update(Integer itemid,Model model) {
        TbNatmehaProject tbNatmehaProject = this.tbNatmehaProjectService.solar_select(itemid);
        model.addAttribute("tbNatmehaProject", tbNatmehaProject);
        return "tbMunicipalClerk/efficacy_municipalclerk_update";
    }

    //修改保存状态下的信息,并且确定是保存还是提交
    @RequestMapping("/efficacy_municipalclerk_updatecode")
    public String efficacy_municipalclerk_updatecode(Integer itemid,String name,String creater,String content,
                                                  Integer price,String savebtn,String putbtn,String reset) throws Exception {

        String dataType = "1";//0功效特色 ， 1开展项目
        //如果是保存按钮，即是已保存状态 0，提交状态（待县级审核）即是 1，县级审核不通过2，待市级审核3，
        // 市级审核不通过4，待省级审核5，省级审核不通过6，省级通过7，管理员发布8,
        if (savebtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            //String itemcode = UUID.randomUUID().toString();
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemid(itemid);
            tbNatmehaProject.setName(name);
            tbNatmehaProject.setPrice(price);
            tbNatmehaProject.setContent(content);
            tbNatmehaProject.setDataStatus("9");
            tbNatmehaProject.setDataType(dataType);
            tbNatmehaProject.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaProject.setItemupdateat(itemupdateat);
            //tbNatmehaProject.setUserCode("1122");
            this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
            return "redirect:/start/efficacy_municipalclerk_code";//修改成功后返回数据主页面
        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            //String itemcode = UUID.randomUUID().toString();
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemid(itemid);
            tbNatmehaProject.setName(name);
            tbNatmehaProject.setContent(content);
            tbNatmehaProject.setDataStatus("1");//1代表的是待市级审核，即科员审核
            tbNatmehaProject.setDataType(dataType);
            tbNatmehaProject.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaProject.setItemupdateat(itemupdateat);
            this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
            return "redirect:/start/efficacy_municipalclerk_code";
        } else if (reset != null) {
            return "redirect:/start/efficacy_municipalclerk_code";
        }
        return null;
    }

    //市级管理员功效特色信息页面直接提交数据给上一级审核,只需要改变数据状态就可
    @RequestMapping("/efficacy_municipalclerck_directUpdate/{itemid}")
    @ResponseBody
    public int efficacy_municipalclerck_directUpdate(@PathVariable("itemid") Integer itemid) {
        //String dataType = "1";//0功效特色 ， 1开展项目
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("3");//3代表的是待市级审核
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }

    //市级管理员删除功效特色信息
    @RequestMapping("/efficacy_municipalclerk_deleteByItemid/{itemid}")
    @ResponseBody
    public int efficacy_municipalclerk_deleteByItemid(@PathVariable("itemid") Integer itemid) {
        int i = this.tbNatmehaProjectService.deleteByItemid(itemid);
        if (i>0) {
            return i;
        }else {
            return 0;
        }
    }


    //省级管理员
    /*
     * 启动省级管理员“智慧”国医堂惠民信息系统的信息处理页面
     * */
    @RequestMapping("/provincialclerk_page")
    public String provincialclerk_page() {
        return "tbProvincialClerk/item_provincial_clerk";
    }

    //省级管理开展项目
    @RequestMapping("/item_provincialclerk_code")
    public String item_provincial_selectByPage(Integer pageNum, Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        //System.out.println(neirou);
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setDataType("1");
        //tbNatmehaHotspot.setDataStatus("1");//1表示的是待市级审核
        tbNatmehaProject.setNeirou(neirou);
        Page<TbNatmehaProject> tbNatmehaProjects = this.tbNatmehaProjectService. provincial_clerk_selectByPage(tbNatmehaProject, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaProjects", tbNatmehaProjects);
        return "tbProvincialClerk/item_provincial_code";
    }

    //省级管理员跳转增加开展项目界面
    @RequestMapping("/item_provincial_addimage")
    public String addpage_item_provincial() {
        return "tbProvincialClerk/item_provincial_add";
    }

    //省级管理员添加新的数据
    @RequestMapping("/item_provincial_insert")
    public String item_provincial_insert(@RequestParam("file") MultipartFile file, String name, String creater, Integer content,
                                        String datasource, String savebtn, String putbtn, String reset) throws Exception {
        String dataType = "1";//0功效特色 ， 1开展项目
        //如果是保存按钮，即是已保存状态 0，提交状态即是 1
        if (savebtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            String userCode = UUID.randomUUID().toString();
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemcode(itemcode);
            tbNatmehaProject.setName(name);
            tbNatmehaProject.setPrice(content);
            tbNatmehaProject.setDataStatus("10");
            tbNatmehaProject.setDataType(dataType);
            tbNatmehaProject.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemcreateat = new java.sql.Date(ite.getTime());
            tbNatmehaProject.setItemcreateat(itemcreateat);
            tbNatmehaProject.setUserCode(userCode);
            this.tbNatmehaProjectService.solar_insert_code(tbNatmehaProject);
            //获取上传的文件名
            String originalFilename = file.getOriginalFilename();
            if (!originalFilename.equals("")) {
                System.out.println(originalFilename);
                //获取源文件前缀
                String fileNamePrefix = originalFilename.substring(0,originalFilename.lastIndexOf("."));
                //获取源文件后缀
                String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                //将源文件前缀之后加上时间戳避免重名
                String newFileNamePrefix = fileNamePrefix + "_" + new Date().getTime();
                //得到上传后新文件的文件名
                String newFileName = newFileNamePrefix+fileNameSuffix;
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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/item_provincialclerk_code";

        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemcode(itemcode);
            tbNatmehaProject.setName(name);
            tbNatmehaProject.setPrice(content);
            tbNatmehaProject.setDataStatus("5");
            tbNatmehaProject.setDataType(dataType);
            tbNatmehaProject.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemcreateat = new java.sql.Date(ite.getTime());
            tbNatmehaProject.setItemcreateat(itemcreateat);
            String userCode = UUID.randomUUID().toString();
            tbNatmehaProject.setUserCode(userCode);
            this.tbNatmehaProjectService.solar_insert_code(tbNatmehaProject);
            //获取上传的文件名
            String originalFilename = file.getOriginalFilename();
            if (!originalFilename.equals("")) {
                System.out.println(originalFilename);
                //获取源文件前缀
                String fileNamePrefix = originalFilename.substring(0,originalFilename.lastIndexOf("."));
                //获取源文件后缀
                String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                //将源文件前缀之后加上时间戳避免重名
                String newFileNamePrefix = fileNamePrefix + "_" + new Date().getTime();
                //得到上传后新文件的文件名
                String newFileName = newFileNamePrefix+fileNameSuffix;
                //获取要保存的文件夹路径
                String path = "/root/images";
                //在指定路径下，产生一个指定名称的新文件D
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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/item_provincialclerk_code";
        } else if (reset != null) {
            return "redirect:/start/item_provincialclerk_code";
        }
        return  null;
    }
    //查看返回
    @RequestMapping("/item_provincialclerk_back")
    public String item_provincial_back() {
        return "redirect:/start/item_provincialclerk_code";
    }

    //通过itemID查看信息，如果是保存状态的信息，可以进行更改，然后提交或者选择保存
    @RequestMapping("/item_provincialclerk_select")
    public String item_provincialselect(Integer itemid,Model model) {
        TbNatmehaProject tbNatmehaProject = this.tbNatmehaProjectService.solar_select(itemid);
        model.addAttribute("tbNatmehaProject", tbNatmehaProject);
        return "tbProvincialClerk/item_provincialclerk_see";
    }

    //查询需要修改保存状态下的信息
    @RequestMapping("/item_provincialclerk_update")
    public String itemprovincialclerck_update(Integer itemid,Model model) {
        TbNatmehaProject tbNatmehaProject = this.tbNatmehaProjectService.solar_select(itemid);
        model.addAttribute("tbNatmehaProject", tbNatmehaProject);
        return "tbProvincialClerk/item_provincialclerk_update";
    }

    //修改保存状态下的信息,并且确定是保存还是提交
    @RequestMapping("/item_provincialclerk_updatecode")
    public String item_provincialclerck_updatecode(Integer itemid,String name,String creater,String content,
                                                  String datasource,String savebtn,String putbtn,String reset) throws Exception {

        String dataType = "1";//0功效特色 ， 1开展项目
        //如果是保存按钮，即是已保存状态 0，提交状态（待县级审核）即是 1，县级审核不通过2，待市级审核3，
        // 市级审核不通过4，待省级审核5，省级审核不通过6，省级通过7，管理员发布8,
        if (savebtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            //String itemcode = UUID.randomUUID().toString();
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemid(itemid);
            tbNatmehaProject.setName(name);
            tbNatmehaProject.setContent(content);
            tbNatmehaProject.setDataStatus("10");
            tbNatmehaProject.setDataType(dataType);
            tbNatmehaProject.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaProject.setItemupdateat(itemupdateat);
            //tbNatmehaProject.setUserCode("1122");
            this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
            return "redirect:/start/item_provincialclerk_code";//修改成功后返回数据主页面
        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            //String itemcode = UUID.randomUUID().toString();
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemid(itemid);
            tbNatmehaProject.setName(name);
            tbNatmehaProject.setContent(content);
            tbNatmehaProject.setDataStatus("5");//5代表的是待省级级审核
            tbNatmehaProject.setDataType(dataType);
            tbNatmehaProject.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaProject.setItemupdateat(itemupdateat);
            this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
            return "redirect:/start/item_provincialclerk_code";
        } else if (reset != null) {
            return "redirect:/start/item_provincialclerk_code";
        }
        return null;
    }

    //省级管理员开展项目信息页面直接提交数据给上一级审核,只需要改变数据状态就可
    @RequestMapping("/solar_provincialclerck_directUpdate/{itemid}")
    @ResponseBody
    public int solar_provincialclerck_directUpdate(@PathVariable("itemid") Integer itemid) {
        //String dataType = "1";//0功效特色 ， 1开展项目
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("5");//5代表的是待省级级审核
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }

    //管理员删除信息
    @RequestMapping("/solar_provincialclerk_deleteByItemid/{itemid}")
    @ResponseBody
    public int solar_provincialclerk_deleteByItemid(@PathVariable("itemid") Integer itemid) {
        int i = this.tbNatmehaProjectService.deleteByItemid(itemid);
        if (i>0) {
            return i;
        }else {
            return 0;
        }
    }


    //省级管理功效特色   dataTYpe=0
    @RequestMapping("/efficacy_provincialclerk_code")
    public String efficacy_provincial_selectByPage(Integer pageNum, Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        //System.out.println(neirou);
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setDataType("0");
        //tbNatmehaHotspot.setDataStatus("1");//1表示的是待市级审核
        tbNatmehaProject.setNeirou(neirou);
        Page<TbNatmehaProject> tbNatmehaProjects = this.tbNatmehaProjectService. provincial_clerk_selectByPage(tbNatmehaProject, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaProjects", tbNatmehaProjects);
        return "tbProvincialClerk/efficacy_provincial_code";
    }

    //省级管理员跳转增加功效特色界面
    @RequestMapping("/efficacy_provincial_addimage")
    public String addpage_efficacy_provincial() {
        return "tbProvincialClerk/efficacy_provincial_add";
    }

    //省级管理员添加新的数据
    @RequestMapping("/efficacy_provincial_insert")
    public String efficacy_provincial_insert(@RequestParam("file") MultipartFile file, String name, String creater, Integer price,
                                         String content, String savebtn, String putbtn, String reset) throws Exception {
        String dataType = "0";//0功效特色 ， 1开展项目
        //如果是保存按钮，即是已保存状态 0，提交状态即是 1
        if (savebtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            String userCode = UUID.randomUUID().toString();
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemcode(itemcode);
            tbNatmehaProject.setName(name);
            tbNatmehaProject.setPrice(price);
            tbNatmehaProject.setContent(content);
            tbNatmehaProject.setDataStatus("10");
            tbNatmehaProject.setDataType(dataType);
            tbNatmehaProject.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemcreateat = new java.sql.Date(ite.getTime());
            tbNatmehaProject.setItemcreateat(itemcreateat);
            tbNatmehaProject.setUserCode(userCode);
            this.tbNatmehaProjectService.solar_insert_code(tbNatmehaProject);
            //获取上传的文件名
            String originalFilename = file.getOriginalFilename();
            if (!originalFilename.equals("")) {
                System.out.println(originalFilename);
                //获取源文件前缀
                String fileNamePrefix = originalFilename.substring(0,originalFilename.lastIndexOf("."));
                //获取源文件后缀
                String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                //将源文件前缀之后加上时间戳避免重名
                String newFileNamePrefix = fileNamePrefix + "_" + new Date().getTime();
                //得到上传后新文件的文件名
                String newFileName = newFileNamePrefix+fileNameSuffix;
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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/efficacy_provincialclerk_code";

        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemcode(itemcode);
            tbNatmehaProject.setName(name);
            tbNatmehaProject.setPrice(price);
            tbNatmehaProject.setContent(content);
            tbNatmehaProject.setDataStatus("5");
            tbNatmehaProject.setDataType(dataType);
            tbNatmehaProject.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemcreateat = new java.sql.Date(ite.getTime());
            tbNatmehaProject.setItemcreateat(itemcreateat);
            String userCode = UUID.randomUUID().toString();
            tbNatmehaProject.setUserCode(userCode);
            this.tbNatmehaProjectService.solar_insert_code(tbNatmehaProject);
            //获取上传的文件名
            String originalFilename = file.getOriginalFilename();
            if (!originalFilename.equals("")) {
                System.out.println(originalFilename);
                //获取源文件前缀
                String fileNamePrefix = originalFilename.substring(0,originalFilename.lastIndexOf("."));
                //获取源文件后缀
                String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                //将源文件前缀之后加上时间戳避免重名
                String newFileNamePrefix = fileNamePrefix + "_" + new Date().getTime();
                //得到上传后新文件的文件名
                String newFileName = newFileNamePrefix+fileNameSuffix;
                //获取要保存的文件夹路径
                String path = "/root/images";
                //在指定路径下，产生一个指定名称的新文件D
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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/efficacy_provincialclerk_code";
        } else if (reset != null) {
            return "redirect:/start/efficacy_provincialclerk_code";
        }
        return  null;
    }
    //查看返回
    @RequestMapping("/efficacy_provincialclerk_back")
    public String efficacy_provincialclerk_back() {
        return "redirect:/start/efficacy_provincialclerk_code";
    }

    //通过itemID查看信息，如果是保存状态的信息，可以进行更改，然后提交或者选择保存
    @RequestMapping("/efficacy_provincialclerk_select")
    public String efficacy_provincialselect(Integer itemid,Model model) {
        TbNatmehaProject tbNatmehaProject = this.tbNatmehaProjectService.solar_select(itemid);
        model.addAttribute("tbNatmehaProject", tbNatmehaProject);
        return "tbProvincialClerk/efficacy_provincialclerk_see";
    }

    //查询需要修改保存状态下的信息
    @RequestMapping("/efficacy_provincialclerk_update")
    public String efficacy_provincialclerk_update(Integer itemid,Model model) {
        TbNatmehaProject tbNatmehaProject = this.tbNatmehaProjectService.solar_select(itemid);
        model.addAttribute("tbNatmehaProject", tbNatmehaProject);
        return "tbProvincialClerk/efficacy_provincialclerk_update";
    }

    //修改保存状态下的信息,并且确定是保存还是提交
    @RequestMapping("/efficacy_provincialclerk_updatecode")
    public String efficacy_provincialclerk_updatecode(Integer itemid,String name,String creater,Integer price,
                                                   String content,String savebtn,String putbtn,String reset) throws Exception {

        String dataType = "0";//0功效特色 ， 1开展项目
        //如果是保存按钮，即是已保存状态 0，提交状态（待县级审核）即是 1，县级审核不通过2，待市级审核3，
        // 市级审核不通过4，待省级审核5，省级审核不通过6，省级通过7，管理员发布8,
        if (savebtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            //String itemcode = UUID.randomUUID().toString();
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemid(itemid);
            tbNatmehaProject.setName(name);
            tbNatmehaProject.setPrice(price);
            tbNatmehaProject.setContent(content);
            tbNatmehaProject.setDataStatus("10");
            tbNatmehaProject.setDataType(dataType);
            tbNatmehaProject.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaProject.setItemupdateat(itemupdateat);
            this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
            return "redirect:/start/efficacy_provincialclerk_code";//修改成功后返回数据主页面
        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            //String itemcode = UUID.randomUUID().toString();
            TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
            tbNatmehaProject.setItemid(itemid);
            tbNatmehaProject.setName(name);
            tbNatmehaProject.setPrice(price);
            tbNatmehaProject.setContent(content);
            tbNatmehaProject.setDataStatus("5");//5代表的是待省级级审核
            tbNatmehaProject.setDataType(dataType);
            tbNatmehaProject.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaProject.setItemupdateat(itemupdateat);
            this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
            return "redirect:/start/efficacy_provincialclerk_code";
        } else if (reset != null) {
            return "redirect:/start/efficacy_provincialclerk_code";
        }
        return null;
    }

    //省级管理员开展项目信息页面直接提交数据给上一级审核,只需要改变数据状态就可
    @RequestMapping("/efficacy_provincialclerck_directUpdate/{itemid}")
    @ResponseBody
    public int efficacy_provincialclerck_directUpdate(@PathVariable("itemid") Integer itemid) {
        //String dataType = "1";//0功效特色 ， 1开展项目
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("5");//5代表的是待省级级审核
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }

    //管理员删除信息
    @RequestMapping("/efficacy_provincialclerk_deleteByItemid/{itemid}")
    @ResponseBody
    public int efficacy_provincialclerk_deleteByItemid(@PathVariable("itemid") Integer itemid) {
        int i = this.tbNatmehaProjectService.deleteByItemid(itemid);
        if (i>0) {
            return i;
        }else {
            return 0;
        }
    }


    //国医堂管理员发布,启动国医堂管理员页面
    @RequestMapping("/natmehaclerk")
    public String natmehaclerk() {
        return "tbNatmehaclerk/natmeha_clerk";
    }

    //国医堂管理员开展项目发布
    @RequestMapping("/item_natmeha_clerk_code")
    public String item_natmeha_clerk_code(Integer pageNum, Model model,String neirou) {
        String dataType = "1";
        if (pageNum==null) {
            pageNum = 1;
        }
        //System.out.println(neirou);
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setDataType(dataType);
        //tbNatmehaHotspot.setDataStatus("7");
        tbNatmehaProject.setNeirou(neirou);
        Page<TbNatmehaProject> tbNatmehaProjects = this.tbNatmehaProjectService.natmehaselectByPage(tbNatmehaProject, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaProjects", tbNatmehaProjects);
        return "tbNatmehaclerk/natmeha_clerk_itemcode";
    }

    //国医堂管理员发布开展项目
    //国医堂管理员发布信息
    @RequestMapping("/item_natmehaclerk_release/{itemid}")
    @ResponseBody
    public int item_mainclerck_release(@PathVariable("itemid") Integer itemid) {
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("8");//1代表的是待市级审核，即科员审核,8代表发布
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }


    @RequestMapping("/natmeha_clerck_back")
    public String natmeha_clerck_back() {
        return "redirect:/start/item_natmeha_clerk_code";
    }

    //通过itemID查看信息，如果是保存状态的信息，可以进行更改，然后提交或者选择保存
    @RequestMapping("/natmeha_clerck_select")
    public String natmeha_select(Integer itemid,Model model) {
        TbNatmehaProject tbNatmehaProject = this.tbNatmehaProjectService.solar_select(itemid);
        model.addAttribute("tbNatmehaProject", tbNatmehaProject);
        return "tbNatmehaclerk/natmeha_effclerk_see";
    }

    //管理员下架发布信息
    @RequestMapping("/item_natmehaclerk_norelease/{itemid}")
    @ResponseBody
    public int item_natmehaclerk_norelease(@PathVariable("itemid") Integer itemid) {
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("7");//1代表的是待市级审核，即科员审核,8代表发布，状态变为省级审核通过
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }
    //国医堂管理员删除未发布的国医堂信息
    @RequestMapping("/item_natmehaclerk_deleteByItemid/{itemid}")
    @ResponseBody
    public int item_natmehaclerk_deleteByItemid(@PathVariable("itemid") Integer itemid) {
        int i = this.tbNatmehaProjectService.deleteByItemid(itemid);
        return i;
    }



    //国医堂管理员功效特色发布
    @RequestMapping("/efficacy_natmeha_clerk_code")
    public String efficacy_natmeha_clerk_code(Integer pageNum, Model model,String neirou) {
        String dataType = "0";
        if (pageNum==null) {
            pageNum = 1;
        }
        //System.out.println(neirou);
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setDataType(dataType);
        //tbNatmehaHotspot.setDataStatus("7");
        tbNatmehaProject.setNeirou(neirou);
        Page<TbNatmehaProject> tbNatmehaProjects = this.tbNatmehaProjectService.natmehaselectByPage(tbNatmehaProject, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaProjects", tbNatmehaProjects);
        return "tbNatmehaclerk/natmeha_clerk_efficacycode";
    }

    //国医堂管理员发布功效特色
    //国医堂管理员发布信息
    @RequestMapping("/efficacy_natmehaclerk_release/{itemid}")
    @ResponseBody
    public int efficacy_mainclerck_release(@PathVariable("itemid") Integer itemid) {
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("8");//1代表的是待市级审核，即科员审核,8代表发布
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }


    @RequestMapping("/natmeha_efficacy_clerck_back")
    public String natmeha_efficacy_clerck_back() {
        return "redirect:/start/efficacy_natmeha_clerk_code";
    }

    //通过itemID查看信息，如果是保存状态的信息，可以进行更改，然后提交或者选择保存
    @RequestMapping("/natmeha_efficacy_clerck_select")
    public String natmeha_efficacy_select(Integer itemid,Model model) {
        TbNatmehaProject tbNatmehaProject = this.tbNatmehaProjectService.solar_select(itemid);
        model.addAttribute("tbNatmehaProject", tbNatmehaProject);
        return "tbNatmehaclerk/natmeha_clerk_see";
    }

    //管理员下架发布信息
    @RequestMapping("/efficacy_natmehaclerk_norelease/{itemid}")
    @ResponseBody
    public int efficacy_natmehaclerk_norelease(@PathVariable("itemid") Integer itemid) {
        TbNatmehaProject tbNatmehaProject = new TbNatmehaProject();
        tbNatmehaProject.setItemid(itemid);
        tbNatmehaProject.setDataStatus("7");//1代表的是待市级审核，即科员审核,8代表发布，状态变为省级审核通过
        int i = this.tbNatmehaProjectService.solar_updata_code(tbNatmehaProject);
        return i;
    }
    //国医堂管理员删除未发布的功效特色信息
    @RequestMapping("/efficacy_natmehaclerk_deleteByItemid/{itemid}")
    @ResponseBody
    public int efficacy_natmehaclerk_deleteByItemid(@PathVariable("itemid") Integer itemid) {
        int i = this.tbNatmehaProjectService.deleteByItemid(itemid);
        return i;
    }

}