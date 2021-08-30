package com.lanqiao.natmeha.controller;

import com.github.pagehelper.Page;
import com.lanqiao.natmeha.model.TbNatmehaFile;
import com.lanqiao.natmeha.model.TbNatmehaHotspot;
import com.lanqiao.natmeha.service.TbNatmehaFileService;
import com.lanqiao.natmeha.service.TbNatmehaHotspotDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
    //文件表的对象
    @Autowired
    private TbNatmehaFileService tbNatmehaFileService;

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

    //static String itemcode = UUID.randomUUID().toString();
    //添加新的数据
    @RequestMapping("/solar_insert")
    public String solar_insert(@RequestParam("file")MultipartFile file, String hotspotTitle,String creater,String hotspotContent,
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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/solar_clerk_code";
        } else if (reset != null) {
            return "redirect:/start/solar_clerk_code";
        }
        return null;
    }

    //查看返回
    @RequestMapping("/solar_clerk_back")
    public String solar_clerk_back() {
        return "redirect:/start/solar_clerk_code";
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
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemcode(itemcode);
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
            String itemcode = UUID.randomUUID().toString();
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

    //县级机构显示节气养生信息页面
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

    //县级查看要审核的具体信息，并且决定是否给予通过
    @RequestMapping("/solar_county_select")
    public String solar_county_select(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        if (tbNatmehaHotspot.getDataStatus().equals("1")) {
            model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
            return "tbSolarCounty/solar_county_update";
        }
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbSolarCounty/solar_county_see";
    }

    //查看信息页面，审核决定给予通过还是不通过
    @RequestMapping("/solar_county_updatecode")
    public String solar_county_updatecode(Integer itemid,String reset,String nopassbtn,String passbtn) throws InterruptedException {
        if (passbtn != null) {
            Thread.sleep(2000);
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setDataStatus("3");
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/solar_county_code";
        } else if (nopassbtn != null) {
            Thread.sleep(2000);
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setDataStatus("2");
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/solar_county_code";
        } else if (reset != null) {
            return "redirect:/start/solar_county_code";
        }
        return null;
    }

    @RequestMapping("/solar_county_back")
    public String solar_county_back() {
        return "redirect:/start/solar_county_code";
    }

    //显示信息页面，直接审核通过
    @RequestMapping("/solar_county_pass/{itemid}")
    @ResponseBody
    public int solar_county_pass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("3");
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }
    //显示信息页面，直接审核不通过
    @RequestMapping("/solar_county_nopass/{itemid}")
    @ResponseBody
    public int solar_county_nopass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("2");
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }

    /*
     * 启动市级机构“智慧”国医堂惠民信息系统的信息处理页面
     * */
    @RequestMapping("/municipal_page")
    public String municipal_page() {
        return "tbSolarMunicipal/solar_municipal";
    }

    //市级审核节气养生信息审核界面
    @RequestMapping("/solar_municipal_code")
    public String solar_municipal_codeselectBypage(Integer pageNum,Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setDataType("0");
        tbNatmehaHotspot.setDataStatus("3");
        tbNatmehaHotspot.setNeirou(neirou);
        Page<TbNatmehaHotspot> tbNatmehaHotspots = this.tbNatmehaHotspotDaoService.municipal_selectByPage(tbNatmehaHotspot, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaHotspots", tbNatmehaHotspots);
        return "tbSolarMunicipal/solar_municipal_code";
    }

    //市级机构查看需要审核的信息，并决定是否给予通过
    @RequestMapping("/solar_municipal_select")
    public String solar_municipal_select(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        if (tbNatmehaHotspot.getDataStatus().equals("3")) {
            model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
            return "tbSolarMunicipal/solar_municipal_update";
        }
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbSolarMunicipal/solar_municipal_see";
    }

    //市级审核决定既不给予通过
    @RequestMapping("/solar_municipal_updatecode")
    public String solar_municipal_updatecode(Integer itemid,String reset,String nopassbtn,String passbtn) throws InterruptedException {
        if (passbtn != null) {
            Thread.sleep(2000);
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setDataStatus("5");
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/solar_municipal_code";
        } else if (nopassbtn != null) {
            Thread.sleep(2000);
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setDataStatus("4");
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/solar_municipal_code";
        } else if (reset != null) {
            return "redirect:/start/solar_municipal_code";
        }
        return null;
    }
    //单纯查看页面，取消按钮返回
    @RequestMapping("/solar_municipal_back")
    public String solar_municipal_back() {
        return "redirect:/start/solar_municipal_code";
    }

    //市级审核页面决定通不通过,首先是显示通过的
    @RequestMapping("/solar_municipal_pass/{itemid}")
    @ResponseBody
    public int solar_municipal_pass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("5");
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }
    //市级审核页面决定通不通过,然后是不给予通过的
    @RequestMapping("/solar_municipal_nopass/{itemid}")
    @ResponseBody
    public int solar_municipal_nopass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("4");
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }

    /*
     * 启动省级机构“智慧”国医堂惠民信息系统的信息处理页面
     * */
    @RequestMapping("/provincial_page")
    public String provincial_page() {
        return "tbSolarProvincial/solar_provincial";
    }

    //省级审核节气养生信息审核界面
    @RequestMapping("/solar_provincial_code")
    public String solar_provincial_codeselectBypage(Integer pageNum,Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setDataType("0");
        tbNatmehaHotspot.setDataStatus("5");
        tbNatmehaHotspot.setNeirou(neirou);
        Page<TbNatmehaHotspot> tbNatmehaHotspots = this.tbNatmehaHotspotDaoService.provincial_selectByPage(tbNatmehaHotspot, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaHotspots", tbNatmehaHotspots);
        return "tbSolarProvincial/solar_provincial_code";
    }

    //县级查看要审核的具体信息，并且决定是否给予通过
    @RequestMapping("/solar_provincial_select")
    public String solar_provincial_select(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        if (tbNatmehaHotspot.getDataStatus().equals("5")) {
            model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
            return "tbSolarProvincial/solar_provincial_update";
        }
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbSolarProvincial/solar_provincial_see";
    }

    //查看信息页面，审核决定给予通过还是不通过
    @RequestMapping("/solar_provincial_updatecode")
    public String solar_provincial_updatecode(Integer itemid,String reset,String nopassbtn,String passbtn) throws InterruptedException {
        if (passbtn != null) {
            Thread.sleep(2000);
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setDataStatus("7");
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/solar_provincial_code";
        } else if (nopassbtn != null) {
            Thread.sleep(2000);
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setDataStatus("6");
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/solar_provincial_code";
        } else if (reset != null) {
            return "redirect:/start/solar_provincial_code";
        }
        return null;
    }

    @RequestMapping("/solar_provincial_back")
    public String solar_provincial_back() {
        return "redirect:/start/solar_provincial_code";
    }

    //省级审核页面决定通不通过,首先是显示通过的
    @RequestMapping("/solar_provincial_pass/{itemid}")
    @ResponseBody
    public int solar_provincial_pass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("7");
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }
    //省级审核页面决定通不通过,然后是不给予通过的
    @RequestMapping("/solar_provincial_nopass/{itemid}")
    @ResponseBody
    public int solar_provincial_nopass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("6");
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }

    /*
     * 管理员处理自我保健信息
     * */
    @RequestMapping("/care_clerk_code")
    public String care_clerk_code(Integer pageNum,Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        //System.out.println(neirou);
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setDataType("1");
        tbNatmehaHotspot.setNeirou(neirou);
        Page<TbNatmehaHotspot> tbNatmehaHotspots = this.tbNatmehaHotspotDaoService.selectByPage(tbNatmehaHotspot, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaHotspots", tbNatmehaHotspots);
        return "tbCareClerk/care_clerk_code";
    }
    //管理员新增自我保健,先跳转页面
    //跳转增加节气养生界面
    @RequestMapping("/care_addimage")
    public String addpage_care() {
        return "tbCareClerk/care_clerk_add";
    }
    //然后再根据按钮各自执行相关保存和提交功能
    @RequestMapping("/care_insert")
    public String care_insert(@RequestParam("file")MultipartFile file, String hotspotTitle,String creater,String hotspotContent,
                               String datasource,String savebtn,String putbtn,String reset) throws Exception {
        String dataType = "1";//0代表节气养生 ， 1代表自我保健，2代表药膳食疗
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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                //创建时间
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String ict2 = df.format(new Date());
                Date ite2 = null;
                ite = df.parse(ict);
                java.sql.Date itemcreateat2 = new java.sql.Date(ite.getTime());
                tbNatmehaHotspot.setItemcreateat(itemcreateat2);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/care_clerk_code";
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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                //创建时间
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String ict2 = df.format(new Date());
                Date ite2 = null;
                ite = df.parse(ict);
                java.sql.Date itemcreateat2 = new java.sql.Date(ite.getTime());
                tbNatmehaHotspot.setItemcreateat(itemcreateat2);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/care_clerk_code";
        } else if (reset != null) {
            return "redirect:/start/care_clerk_code";
        }
        return null;
    }
    //管理员查看不是保存状态的信息
    //通过itemID查看信息，如果是保存状态的信息，可以进行更改，然后提交或者选择保存
    @RequestMapping("/care_clerck_select")
    public String care_clerck_select(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbCareClerk/care_clerk_see";
    }

    //care_clerk_see.html页面取消按钮，返回数据页面
    @RequestMapping("/care_clerk_back")
    public String care_clerk_back() {
        return "redirect:/start/care_clerk_code";
    }
    //管理员通过修改按钮，跳转自我保健修改页面
    @RequestMapping("/care_clerck_update")
    public String care_clerck_update(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbCareClerk/care_clerk_update";
    }

    //修改保存状态下的信息,并且确定是保存还是提交
    @RequestMapping("/care_updatecode")
    public String care_updatecode(Integer itemid,String hotspotTitle,String creater,String hotspotContent,
                                   String datasource,String savebtn,String putbtn,String reset) throws Exception {

        String dataType = "1";//0代表节气养生 ， 1代表自我保健，2代表药膳食疗
        //如果是保存按钮，即是已保存状态 0，提交状态（待县级审核）即是 1，县级审核不通过2，待市级审核3，
        // 市级审核不通过4，待省级审核5，省级审核不通过6，省级通过7，管理员发布8,
        if (savebtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemcode(itemcode);
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
            return "redirect:/start/care_clerk_code";//修改成功后返回数据主页面
        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
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
            return "redirect:/start/care_clerk_code";
        } else if (reset != null) {
            return "redirect:/start/care_clerk_code";
        }
        return null;
    }

    //管理员节气养生信息页面直接提交数据给上一级审核,只需要改变数据状态就可
    @RequestMapping("/care_clerck_directUpdate/{itemid}")
    @ResponseBody
    public int care_clerck_directUpdate(@PathVariable("itemid") Integer itemid) {
        //String dataType = "0";//0代表节气养生 ， 1代表自我保健，2代表药膳食疗
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("1");//1代表的是待县级审核，即科员审核
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }
    //管理员发布自我保健信息
    @RequestMapping("/care_clerck_release/{itemid}")
    @ResponseBody
    public int care_clerck_release(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("8");//1代表的是待市级审核，即科员审核,8代表发布
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }

    //管理员下架发布信息
    @RequestMapping("/care_clerck_norelease/{itemid}")
    @ResponseBody
    public int care_clerck_norelease(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("7");//1代表的是待市级审核，即科员审核,8代表发布，状态变为省级审核通过
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }

    //县级机构处理管理员提交的信息
    //县级机构显示自我保健信息页面
    @RequestMapping("/care_county_code")
    public String care_county_codeselectBypage(Integer pageNum,Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setDataType("1");
        tbNatmehaHotspot.setDataStatus("1");
        tbNatmehaHotspot.setNeirou(neirou);
        Page<TbNatmehaHotspot> tbNatmehaHotspots = this.tbNatmehaHotspotDaoService.county_selectByPage(tbNatmehaHotspot, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaHotspots", tbNatmehaHotspots);
        return "tbCareCounty/care_county_code";
    }
    //县级查看要审核的具体信息，并且决定是否给予通过
    @RequestMapping("/care_county_select")
    public String care_county_select(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        if (tbNatmehaHotspot.getDataStatus().equals("1")) {
            model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
            return "tbCareCounty/care_county_update";
        }
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbCareCounty/care_county_see";
    }

    //查看信息页面，审核决定给予通过还是不通过
    @RequestMapping("/care_county_updatecode")
    public String care_county_updatecode(Integer itemid,String reset,String nopassbtn,String passbtn) throws InterruptedException {
        if (passbtn != null) {
            Thread.sleep(2000);
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setDataStatus("3");
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/care_county_code";
        } else if (nopassbtn != null) {
            Thread.sleep(2000);
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setDataStatus("2");
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/care_county_code";
        } else if (reset != null) {
            return "redirect:/start/care_county_code";
        }
        return null;
    }

    @RequestMapping("/care_county_back")
    public String care_county_back() {
        return "redirect:/start/care_county_code";
    }

    //显示信息页面，直接审核通过
    @RequestMapping("/care_county_pass/{itemid}")
    @ResponseBody
    public int care_county_pass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("3");
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }
    //显示信息页面，直接审核不通过
    @RequestMapping("/care_county_nopass/{itemid}")
    @ResponseBody
    public int care_county_nopass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("2");
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }


    /*
    *
    *市级机构审核自我保健页面
     */
    //市级审核节气养生信息审核界面
    @RequestMapping("/care_municipal_code")
    public String care_municipal_codeselectBypage(Integer pageNum,Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setDataType("1");
        tbNatmehaHotspot.setDataStatus("3");
        tbNatmehaHotspot.setNeirou(neirou);
        Page<TbNatmehaHotspot> tbNatmehaHotspots = this.tbNatmehaHotspotDaoService.municipal_selectByPage(tbNatmehaHotspot, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaHotspots", tbNatmehaHotspots);
        return "tbCareMunicipal/care_municipal_code";
    }

    //市级机构查看需要审核的信息，并决定是否给予通过
    @RequestMapping("/care_municipal_select")
    public String care_municipal_select(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        if (tbNatmehaHotspot.getDataStatus().equals("3")) {
            model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
            return "tbCareMunicipal/care_municipal_update";
        }
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbCareMunicipal/care_municipal_see";
    }

    //市级审核决定既不给予通过
    @RequestMapping("/care_municipal_updatecode")
    public String care_municipal_updatecode(Integer itemid,String reset,String nopassbtn,String passbtn) throws InterruptedException {
        if (passbtn != null) {
            Thread.sleep(2000);
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setDataStatus("5");
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/care_municipal_code";
        } else if (nopassbtn != null) {
            Thread.sleep(2000);
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setDataStatus("4");
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/care_municipal_code";
        } else if (reset != null) {
            return "redirect:/start/care_municipal_code";
        }
        return null;
    }
    //单纯查看页面，取消按钮返回
    @RequestMapping("/care_municipal_back")
    public String care_municipal_back() {
        return "redirect:/start/care_municipal_code";
    }

    //市级审核页面决定通不通过,首先是显示通过的
    @RequestMapping("/care_municipal_pass/{itemid}")
    @ResponseBody
    public int care_municipal_pass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("5");
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }
    //市级审核页面决定通不通过,然后是不给予通过的
    @RequestMapping("/care_municipal_nopass/{itemid}")
    @ResponseBody
    public int care_municipal_nopass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("4");
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }

    /*
    * 省级机构处理自我保健的信息
    * */
    //省级审核自我保健信息审核界面
    @RequestMapping("/care_provincial_code")
    public String care_provincial_codeselectBypage(Integer pageNum,Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setDataType("1");
        tbNatmehaHotspot.setDataStatus("5");
        tbNatmehaHotspot.setNeirou(neirou);
        Page<TbNatmehaHotspot> tbNatmehaHotspots = this.tbNatmehaHotspotDaoService.provincial_selectByPage(tbNatmehaHotspot, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaHotspots", tbNatmehaHotspots);
        return "tbCareProvincial/care_provincial_code";
    }

    //省级机构查看要审核的具体信息，并且决定是否给予通过
    @RequestMapping("/care_provincial_select")
    public String care_provincial_select(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        if (tbNatmehaHotspot.getDataStatus().equals("5")) {
            model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
            return "tbCareProvincial/care_provincial_update";
        }
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbCareProvincial/care_provincial_see";
    }

    //查看信息页面，审核决定给予通过还是不通过
    @RequestMapping("/care_provincial_updatecode")
    public String care_provincial_updatecode(Integer itemid,String reset,String nopassbtn,String passbtn) throws InterruptedException {
        if (passbtn != null) {
            Thread.sleep(2000);
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setDataStatus("7");
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/care_provincial_code";
        } else if (nopassbtn != null) {
            Thread.sleep(2000);
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setDataStatus("6");
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/care_provincial_code";
        } else if (reset != null) {
            return "redirect:/start/care_provincial_code";
        }
        return null;
    }

    @RequestMapping("/care_provincial_back")
    public String care_provincial_back() {
        return "redirect:/start/care_provincial_code";
    }

    //省级审核页面决定通不通过,首先是显示通过的
    @RequestMapping("/care_provincial_pass/{itemid}")
    @ResponseBody
    public int care_provincial_pass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("7");
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }
    //省级审核页面决定通不通过,然后是不给予通过的
    @RequestMapping("/care_provincial_nopass/{itemid}")
    @ResponseBody
    public int care_provincial_nopass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("6");
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }

    /*
     * 管理员处理药膳食疗信息
     * */
    @RequestMapping("/med_clerk_code")
    public String med_clerk_code(Integer pageNum,Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        //System.out.println(neirou);
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setDataType("2");
        tbNatmehaHotspot.setNeirou(neirou);
        Page<TbNatmehaHotspot> tbNatmehaHotspots = this.tbNatmehaHotspotDaoService.selectByPage(tbNatmehaHotspot, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaHotspots", tbNatmehaHotspots);
        return "tbMedicatedClerk/med_clerk_code";
    }

    //管理员新增药膳食疗,先跳转页面
    @RequestMapping("/med_addimage")
    public String addpage_med() {
        return "tbMedicatedClerk/med_clerk_add";
    }
    //然后再根据按钮各自执行相关保存和提交功能
    @RequestMapping("/med_insert")
    public String med_insert(@RequestParam("file")MultipartFile file, String hotspotTitle,String creater,String hotspotContent,
                              String datasource,String savebtn,String putbtn,String reset) throws Exception {
        String dataType = "2";//0代表节气养生 ， 1代表自我保健，2代表药膳食疗
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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                //创建时间
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String ict2 = df.format(new Date());
                Date ite2 = null;
                ite = df.parse(ict);
                java.sql.Date itemcreateat2 = new java.sql.Date(ite.getTime());
                tbNatmehaHotspot.setItemcreateat(itemcreateat2);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/med_clerk_code";
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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                //创建时间
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String ict2 = df.format(new Date());
                Date ite2 = null;
                ite = df.parse(ict);
                java.sql.Date itemcreateat2 = new java.sql.Date(ite.getTime());
                tbNatmehaHotspot.setItemcreateat(itemcreateat2);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/med_clerk_code";
        } else if (reset != null) {
            return "redirect:/start/med_clerk_code";
        }
        return null;
    }
    //管理员查看不是保存状态的信息
    //通过itemID查看信息，如果是保存状态的信息，可以进行更改，然后提交或者选择保存
    @RequestMapping("/med_clerck_select")
    public String med_clerck_select(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbMedicatedClerk/med_clerk_see";
    }

    //care_clerk_see.html页面取消按钮，返回数据页面
    @RequestMapping("/med_clerk_back")
    public String med_clerk_back() {
        return "redirect:/start/med_clerk_code";
    }
    //管理员通过修改按钮，跳转药膳食疗修改页面
    @RequestMapping("/med_clerck_update")
    public String med_clerck_update(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbMedicatedClerk/med_clerk_update";
    }

    //修改保存状态下的信息,并且确定是保存还是提交
    @RequestMapping("/med_updatecode")
    public String med_updatecode(Integer itemid,String hotspotTitle,String creater,String hotspotContent,
                                  String datasource,String savebtn,String putbtn,String reset) throws Exception {

        String dataType = "2";//0代表节气养生 ， 1代表自我保健，2代表药膳食疗
        //如果是保存按钮，即是已保存状态 0，提交状态（待县级审核）即是 1，县级审核不通过2，待市级审核3，
        // 市级审核不通过4，待省级审核5，省级审核不通过6，省级通过7，管理员发布8,
        if (savebtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemcode(itemcode);
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
            return "redirect:/start/med_clerk_code";//修改成功后返回数据主页面
        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
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
            return "redirect:/start/med_clerk_code";
        } else if (reset != null) {
            return "redirect:/start/med_clerk_code";
        }
        return null;
    }

    //管理员节气养生信息页面直接提交数据给上一级审核,只需要改变数据状态就可
    @RequestMapping("/med_clerck_directUpdate/{itemid}")
    @ResponseBody
    public int med_clerck_directUpdate(@PathVariable("itemid") Integer itemid) {
        //String dataType = "0";//0代表节气养生 ， 1代表自我保健，2代表药膳食疗
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("1");//1代表的是待县级审核，即科员审核
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }
    //管理员发布自我保健信息
    @RequestMapping("/med_clerck_release/{itemid}")
    @ResponseBody
    public int med_clerck_release(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("8");//1代表的是待市级审核，即科员审核,8代表发布
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }

    //管理员下架发布信息
    @RequestMapping("/med_clerck_norelease/{itemid}")
    @ResponseBody
    public int med_clerck_norelease(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("7");//1代表的是待市级审核，即科员审核,8代表发布，状态变为省级审核通过
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }

    /*
    * 县级机构审核药膳食疗信息页面
    * */
    @RequestMapping("/med_county_code")
    public String med_county_codeselectBypage(Integer pageNum,Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setDataType("2");
        tbNatmehaHotspot.setDataStatus("1");
        tbNatmehaHotspot.setNeirou(neirou);
        Page<TbNatmehaHotspot> tbNatmehaHotspots = this.tbNatmehaHotspotDaoService.county_selectByPage(tbNatmehaHotspot, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaHotspots", tbNatmehaHotspots);
        return "tbMedicatedCounty/med_county_code";
    }

    //县级查看要审核的具体信息，并且决定是否给予通过
    @RequestMapping("/med_county_select")
    public String med_county_select(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        if (tbNatmehaHotspot.getDataStatus().equals("1")) {
            model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
            return "tbMedicatedCounty/med_county_update";
        }
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbMedicatedCounty/med_county_see";
    }

    //查看信息页面，审核决定给予通过还是不通过
    @RequestMapping("/med_county_updatecode")
    public String med_county_updatecode(Integer itemid,String reset,String nopassbtn,String passbtn) throws InterruptedException {
        if (passbtn != null) {
            Thread.sleep(2000);
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setDataStatus("3");
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/med_county_code";
        } else if (nopassbtn != null) {
            Thread.sleep(2000);
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setDataStatus("2");
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/med_county_code";
        } else if (reset != null) {
            return "redirect:/start/med_county_code";
        }
        return null;
    }

    @RequestMapping("/med_county_back")
    public String med_county_back() {
        return "redirect:/start/med_county_code";
    }

    //显示信息页面，直接审核通过
    @RequestMapping("/med_county_pass/{itemid}")
    @ResponseBody
    public int med_county_pass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("3");
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }
    //显示信息页面，直接审核不通过
    @RequestMapping("/med_county_nopass/{itemid}")
    @ResponseBody
    public int med_county_nopass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("2");
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }

    /*
     *
     *市级机构审核药膳食疗页面
     */
    @RequestMapping("/med_municipal_code")
    public String med_municipal_codeselectBypage(Integer pageNum,Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setDataType("2");
        tbNatmehaHotspot.setDataStatus("3");
        tbNatmehaHotspot.setNeirou(neirou);
        Page<TbNatmehaHotspot> tbNatmehaHotspots = this.tbNatmehaHotspotDaoService.municipal_selectByPage(tbNatmehaHotspot, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaHotspots", tbNatmehaHotspots);
        return "tbMedicatedMunicipal/med_municipal_code";
    }

    //市级机构查看需要审核的信息，并决定是否给予通过
    @RequestMapping("/med_municipal_select")
    public String med_municipal_select(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        if (tbNatmehaHotspot.getDataStatus().equals("3")) {
            model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
            return "tbMedicatedMunicipal/med_municipal_update";
        }
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbMedicatedMunicipal/med_municipal_see";
    }

    //市级审核决定既不给予通过
    @RequestMapping("/med_municipal_updatecode")
    public String med_municipal_updatecode(Integer itemid,String reset,String nopassbtn,String passbtn) throws InterruptedException {
        if (passbtn != null) {
            Thread.sleep(2000);
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setDataStatus("5");
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/med_municipal_code";
        } else if (nopassbtn != null) {
            Thread.sleep(2000);
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setDataStatus("4");
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/med_municipal_code";
        } else if (reset != null) {
            return "redirect:/start/med_municipal_code";
        }
        return null;
    }
    //单纯查看页面，取消按钮返回
    @RequestMapping("/med_municipal_back")
    public String med_municipal_back() {
        return "redirect:/start/med_municipal_code";
    }

    //市级审核页面决定通不通过,首先是显示通过的
    @RequestMapping("/med_municipal_pass/{itemid}")
    @ResponseBody
    public int med_municipal_pass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("5");
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }
    //市级审核页面决定通不通过,然后是不给予通过的
    @RequestMapping("/med_municipal_nopass/{itemid}")
    @ResponseBody
    public int med_municipal_nopass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("4");
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }

    /*
     * 省级机构处理药膳食疗的信息
     * */
    //省级审核自我保健信息审核界面
    @RequestMapping("/med_provincial_code")
    public String med_provincial_codeselectBypage(Integer pageNum,Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setDataType("2");
        tbNatmehaHotspot.setDataStatus("5");
        tbNatmehaHotspot.setNeirou(neirou);
        Page<TbNatmehaHotspot> tbNatmehaHotspots = this.tbNatmehaHotspotDaoService.provincial_selectByPage(tbNatmehaHotspot, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaHotspots", tbNatmehaHotspots);
        return "tbMedicatedProvincial/med_provincial_code";
    }

    //省级机构查看要审核的具体信息，并且决定是否给予通过
    @RequestMapping("/med_provincial_select")
    public String med_provincial_select(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        if (tbNatmehaHotspot.getDataStatus().equals("5")) {
            model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
            return "tbMedicatedProvincial/med_provincial_update";
        }
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbMedicatedProvincial/med_provincial_see";
    }

    //查看信息页面，审核决定给予通过还是不通过
    @RequestMapping("/med_provincial_updatecode")
    public String med_provincial_updatecode(Integer itemid,String reset,String nopassbtn,String passbtn) throws InterruptedException {
        if (passbtn != null) {
            Thread.sleep(2000);
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setDataStatus("7");
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/med_provincial_code";
        } else if (nopassbtn != null) {
            Thread.sleep(2000);
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setDataStatus("6");
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/med_provincial_code";
        } else if (reset != null) {
            return "redirect:/start/med_provincial_code";
        }
        return null;
    }

    @RequestMapping("/med_provincial_back")
    public String med_provincial_back() {
        return "redirect:/start/med_provincial_code";
    }

    //省级审核页面决定通不通过,首先是显示通过的
    @RequestMapping("/med_provincial_pass/{itemid}")
    @ResponseBody
    public int med_provincial_pass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("7");
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }
    //省级审核页面决定通不通过,然后是不给予通过的
    @RequestMapping("/med_provincial_nopass/{itemid}")
    @ResponseBody
    public int med_provincial_nopass(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("6");
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }

    /*
    * 启动市级管理员“智慧”国医堂惠民信息系统的信息处理页面
    * */
    @RequestMapping("/municipalclerk_page")
    public String municipalclerk_page() {
        return "tbMunicipalClerk/solar_municipal_clerk";
    }

    //市级管理员
    @RequestMapping("/solar_municipalclerk_code")
    public String municipal_selectByPage(Integer pageNum, Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        //System.out.println(neirou);
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setDataType("0");
        //tbNatmehaHotspot.setDataStatus("1");//1表示的是待市级审核
        tbNatmehaHotspot.setNeirou(neirou);
        Page<TbNatmehaHotspot> tbNatmehaHotspots = this.tbNatmehaHotspotDaoService.municipal_clerkselectByPage(tbNatmehaHotspot, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaHotspots", tbNatmehaHotspots);
        return "tbMunicipalClerk/solar_municipal_code";
    }

    //跳转增加节气养生界面
    @RequestMapping("/solar_municipalclerk_addimage")
    public String addpage_solar_municipal() {
        return "tbMunicipalClerk/solar_municipal_add";
    }

    //static String itemcode = UUID.randomUUID().toString();
    //市级管理员添加新的数据
    @RequestMapping("/solar_municipalclerk_insert")
    public String solar_municipal_insert(@RequestParam("file")MultipartFile file, String hotspotTitle,String creater,String hotspotContent,
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
            tbNatmehaHotspot.setDataStatus("9");
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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/solar_municipalclerk_code";
        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataStatus("3");
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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/solar_municipalclerk_code";
        } else if (reset != null) {
            return "redirect:/start/solar_municipalclerk_code";
        }
        return null;
    }

    //查看返回
    @RequestMapping("/solar_municipalclerk_back")
    public String solar_municipalclerk_back() {
        return "redirect:/start/solar_municipalclerk_code";
    }

    //通过itemID查看信息，如果是保存状态的信息，可以进行更改，然后提交或者选择保存
    @RequestMapping("/solar_municipalclerck_select")
    public String solar_municipalselect(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbMunicipalClerk/solar_municipalclerk_see";
    }
//
    //查询需要修改保存状态下的信息
    @RequestMapping("/solar_municipalclerck_update")
    public String solar_municipalclerck_update(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbMunicipalClerk/solar_municipalclerk_update";
    }

    //修改保存状态下的信息,并且确定是保存还是提交
    @RequestMapping("/solar_municipalclerk_updatecode")
    public String solar_municipalclerk_updatecode(Integer itemid,String hotspotTitle,String creater,String hotspotContent,
                                   String datasource,String savebtn,String putbtn,String reset) throws Exception {

        String dataType = "0";//0代表节气养生 ， 1代表自我保健，2代表药膳食疗
        //如果是保存按钮，即是已保存状态 0，提交状态（待县级审核）即是 1，县级审核不通过2，待市级审核3，
        // 市级审核不通过4，待省级审核5，省级审核不通过6，省级通过7，管理员发布8,
        if (savebtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataStatus("9");
            tbNatmehaHotspot.setDataType(dataType);
            tbNatmehaHotspot.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaHotspot.setItemupdateat(itemupdateat);
            String userCode = UUID.randomUUID().toString();
            tbNatmehaHotspot.setUserCode(userCode);
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/solar_municipalclerk_code";//修改成功后返回数据主页面
        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
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
            String userCode = UUID.randomUUID().toString();
            tbNatmehaHotspot.setUserCode(userCode);
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/solar_municipalclerk_code";
        } else if (reset != null) {
            return "redirect:/start/solar_municipalclerk_code";
        }
        return null;
    }

    //管理员节气养生信息页面直接提交数据给上一级审核,只需要改变数据状态就可
    @RequestMapping("/solar_municipalclerck_directUpdate/{itemid}")
    @ResponseBody
    public int solar_municipalclerck_directUpdate(@PathVariable("itemid") Integer itemid) {
        //String dataType = "0";//0代表节气养生 ， 1代表自我保健，2代表药膳食疗
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("3");//1代表的是待县级审核，即科员审核
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }
//
    //管理员删除信息
    @RequestMapping("/solar_municipalclerk_deleteByItemid/{itemid}")
    @ResponseBody
    public int solar_municipalclerk_deleteByItemid(@PathVariable("itemid") Integer itemid) {
        int i = this.tbNatmehaHotspotDaoService.deleteByItemid(itemid);
        if (i>0) {
            return i;
        }else {
            return 0;
        }
    }


    //市级管理员自我保健
    @RequestMapping("/care_municipalclerk_code")
    public String care_municipalclerk_code(Integer pageNum,Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        //System.out.println(neirou);
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setDataType("1");
        tbNatmehaHotspot.setNeirou(neirou);
        Page<TbNatmehaHotspot> tbNatmehaHotspots = this.tbNatmehaHotspotDaoService.municipal_clerkselectByPage(tbNatmehaHotspot, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaHotspots", tbNatmehaHotspots);
        return "tbMunicipal_CareClerk/care_municipal_code";
    }
    //管理员新增自我保健,先跳转页面
    @RequestMapping("/care_municipal_addimage")
    public String addpage_care_municipal() {
        return "tbMunicipal_CareClerk/care_municipal_add";
    }
    //然后再根据按钮各自执行相关保存和提交功能
    @RequestMapping("/care_municipal_insert")
    public String care_municipal_insert(@RequestParam("file")MultipartFile file, String hotspotTitle,String creater,String hotspotContent,
                              String datasource,String savebtn,String putbtn,String reset) throws Exception {
        String dataType = "1";//0代表节气养生 ， 1代表自我保健，2代表药膳食疗
        //如果是保存按钮，即是已保存状态 0，提交状态即是 1
        if (savebtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataStatus("9");
            tbNatmehaHotspot.setDataType(dataType);
            tbNatmehaHotspot.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemcreateat = new java.sql.Date(ite.getTime());
            tbNatmehaHotspot.setItemcreateat(itemcreateat);
            String userCode = UUID.randomUUID().toString();
            tbNatmehaHotspot.setUserCode(userCode);
            this.tbNatmehaHotspotDaoService.solar_insert_code(tbNatmehaHotspot);
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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                //创建时间
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String ict2 = df.format(new Date());
                Date ite2 = null;
                ite = df.parse(ict);
                java.sql.Date itemcreateat2 = new java.sql.Date(ite.getTime());
                tbNatmehaHotspot.setItemcreateat(itemcreateat2);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/care_municipalclerk_code";
        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataStatus("3");//1代表的是待市级审核，即科员审核
            tbNatmehaHotspot.setDataType(dataType);
            tbNatmehaHotspot.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemcreateat = new java.sql.Date(ite.getTime());
            tbNatmehaHotspot.setItemcreateat(itemcreateat);
            String userCode = UUID.randomUUID().toString();
            tbNatmehaHotspot.setUserCode(userCode);
            this.tbNatmehaHotspotDaoService.solar_insert_code(tbNatmehaHotspot);

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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                //创建时间
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String ict2 = df.format(new Date());
                Date ite2 = null;
                ite = df.parse(ict);
                java.sql.Date itemcreateat2 = new java.sql.Date(ite.getTime());
                tbNatmehaHotspot.setItemcreateat(itemcreateat2);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/care_municipalclerk_code";
        } else if (reset != null) {
            return "redirect:/start/care_municipalclerk_code";
        }
        return null;
    }
    //管理员查看不是保存状态的信息
    //通过itemID查看信息，如果是保存状态的信息，可以进行更改，然后提交或者选择保存
    @RequestMapping("/care_municipalclerck_select")
    public String care_municipalclerck_select(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbMunicipal_CareClerk/care_municipalclerk_see";
    }

    //care_clerk_see.html页面取消按钮，返回数据页面
    @RequestMapping("/care_municipalclerk_back")
    public String care_municipalclerk_back() {
        return "redirect:/start/care_municipalclerk_code";
    }
    //管理员通过修改按钮，跳转自我保健修改页面
    @RequestMapping("/care_municipalclerck_update")
    public String care_municipalclerck_update(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbMunicipal_CareClerk/care_municipalclerk_update";
    }

    //修改保存状态下的信息,并且确定是保存还是提交
    @RequestMapping("/carer_municipalclerk_updatecode")
    public String care_municipalupdatecode(Integer itemid,String hotspotTitle,String creater,String hotspotContent,
                                  String datasource,String savebtn,String putbtn,String reset) throws Exception {

        String dataType = "1";//0代表节气养生 ， 1代表自我保健，2代表药膳食疗
        //如果是保存按钮，即是已保存状态 0，提交状态（待县级审核）即是 1，县级审核不通过2，待市级审核3，
        // 市级审核不通过4，待省级审核5，省级审核不通过6，省级通过7，管理员发布8,
        if (savebtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataStatus("9");
            tbNatmehaHotspot.setDataType(dataType);
            tbNatmehaHotspot.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaHotspot.setItemupdateat(itemupdateat);
            String userCode = UUID.randomUUID().toString();
            tbNatmehaHotspot.setUserCode(userCode);
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/care_municipalclerk_code";//修改成功后返回数据主页面
        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataStatus("3");//1代表的是待市级审核，即科员审核
            tbNatmehaHotspot.setDataType(dataType);
            tbNatmehaHotspot.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaHotspot.setItemupdateat(itemupdateat);
            String userCode = UUID.randomUUID().toString();
            tbNatmehaHotspot.setUserCode(userCode);
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/care_municipalclerk_code";
        } else if (reset != null) {
            return "redirect:/start/care_municipalclerk_code";
        }
        return null;
    }

    //市级管理员自我保健信息页面直接提交数据给上一级审核,只需要改变数据状态就可
    @RequestMapping("/care_municipalclerck_directUpdate/{itemid}")
    @ResponseBody
    public int care_municipalclerck_directUpdate(@PathVariable("itemid") Integer itemid) {
        //String dataType = "0";//0代表节气养生 ， 1代表自我保健，2代表药膳食疗
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("5");//1代表的是待县级审核，即科员审核
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }

    //市级管理员药膳管理
    @RequestMapping("/med_municipalclerk_code")
    public String med_municipalclerk_code(Integer pageNum,Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        //System.out.println(neirou);
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setDataType("2");
        tbNatmehaHotspot.setNeirou(neirou);
        Page<TbNatmehaHotspot> tbNatmehaHotspots = this.tbNatmehaHotspotDaoService.municipal_clerkselectByPage(tbNatmehaHotspot, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaHotspots", tbNatmehaHotspots);
        return "tbMedMunicipalClerk/med_municipal_code";
    }
    //管理员新增自我保健,先跳转页面
    //跳转增加节气养生界面
    @RequestMapping("/med_municipal_addimage")
    public String addpage_med_municipal() {
        return "tbMedMunicipalClerk/med_municipal_add";
    }
    //然后再根据按钮各自执行相关保存和提交功能
    @RequestMapping("/med_municipal_insert")
    public String med_municipal_insert(@RequestParam("file")MultipartFile file, String hotspotTitle,String creater,String hotspotContent,
                                        String datasource,String savebtn,String putbtn,String reset) throws Exception {
        String dataType = "2";//0代表节气养生 ， 1代表自我保健，2代表药膳食疗
        //如果是保存按钮，即是已保存状态 0，提交状态即是 1
        if (savebtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataStatus("9");
            tbNatmehaHotspot.setDataType(dataType);
            tbNatmehaHotspot.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemcreateat = new java.sql.Date(ite.getTime());
            tbNatmehaHotspot.setItemcreateat(itemcreateat);
            String userCode = UUID.randomUUID().toString();
            tbNatmehaHotspot.setUserCode(userCode);
            this.tbNatmehaHotspotDaoService.solar_insert_code(tbNatmehaHotspot);
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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                //创建时间
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String ict2 = df.format(new Date());
                Date ite2 = null;
                ite = df.parse(ict);
                java.sql.Date itemcreateat2 = new java.sql.Date(ite.getTime());
                tbNatmehaHotspot.setItemcreateat(itemcreateat2);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/med_municipalclerk_code";
        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataStatus("3");//1代表的是待市级审核，即科员审核
            tbNatmehaHotspot.setDataType(dataType);
            tbNatmehaHotspot.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemcreateat = new java.sql.Date(ite.getTime());
            tbNatmehaHotspot.setItemcreateat(itemcreateat);
            String userCode = UUID.randomUUID().toString();
            tbNatmehaHotspot.setUserCode(userCode);
            this.tbNatmehaHotspotDaoService.solar_insert_code(tbNatmehaHotspot);

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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                //创建时间
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String ict2 = df.format(new Date());
                Date ite2 = null;
                ite = df.parse(ict);
                java.sql.Date itemcreateat2 = new java.sql.Date(ite.getTime());
                tbNatmehaHotspot.setItemcreateat(itemcreateat2);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/med_municipalclerk_code";
        } else if (reset != null) {
            return "redirect:/start/med_municipalclerk_code";
        }
        return null;
    }
    //管理员查看不是保存状态的信息
    //通过itemID查看信息，如果是保存状态的信息，可以进行更改，然后提交或者选择保存
    @RequestMapping("/med_municipalclerck_select")
    public String med_municipalclerck_select(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbMedMunicipalClerk/med_municipalclerk_see";
    }

    //care_clerk_see.html页面取消按钮，返回数据页面
    @RequestMapping("/med_municipalclerk_back")
    public String med_municipalclerk_back() {
        return "redirect:/start/med_municipalclerk_code";
    }
    //管理员通过修改按钮，跳转自我保健修改页面
    @RequestMapping("/med_municipalclerck_update")
    public String med_municipalclerck_update(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbMedMunicipalClerk/med_municipalclerk_update";
    }

    //修改保存状态下的信息,并且确定是保存还是提交
    @RequestMapping("/med_municipalclerk_updatecode")
    public String med_municipalupdatecode(Integer itemid,String hotspotTitle,String creater,String hotspotContent,
                                           String datasource,String savebtn,String putbtn,String reset) throws Exception {

        String dataType = "2";//0代表节气养生 ， 1代表自我保健，2代表药膳食疗
        //如果是保存按钮，即是已保存状态 0，提交状态（待县级审核）即是 1，县级审核不通过2，待市级审核3，
        // 市级审核不通过4，待省级审核5，省级审核不通过6，省级通过7，管理员发布8,
        if (savebtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataStatus("9");
            tbNatmehaHotspot.setDataType(dataType);
            tbNatmehaHotspot.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaHotspot.setItemupdateat(itemupdateat);
            String userCode = UUID.randomUUID().toString();
            tbNatmehaHotspot.setUserCode(userCode);
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/med_municipalclerk_code";//修改成功后返回数据主页面
        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataStatus("3");//1代表的是待市级审核，即科员审核
            tbNatmehaHotspot.setDataType(dataType);
            tbNatmehaHotspot.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaHotspot.setItemupdateat(itemupdateat);
            String userCode = UUID.randomUUID().toString();
            tbNatmehaHotspot.setUserCode(userCode);
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/med_municipalclerk_code";
        } else if (reset != null) {
            return "redirect:/start/med_municipalclerk_code";
        }
        return null;
    }

    //市级管理员自我保健信息页面直接提交数据给上一级审核,只需要改变数据状态就可
    @RequestMapping("/med_municipalclerck_directUpdate/{itemid}")
    @ResponseBody
    public int med_municipalclerck_directUpdate(@PathVariable("itemid") Integer itemid) {
        //String dataType = "0";//0代表节气养生 ， 1代表自我保健，2代表药膳食疗
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("5");//1代表的是待县级审核，即科员审核
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }

    //省级管理员
    /*
     * 启动省级管理员“智慧”国医堂惠民信息系统的信息处理页面
     * */
    @RequestMapping("/provincialclerk_page")
    public String provincialclerk_page() {
        return "tbProvincialClerk/solar_provincial_clerk";
    }

    //省级管理节气养生管理页面
    @RequestMapping("/solar_provincialclerk_code")
    public String provincial_selectByPage(Integer pageNum, Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        //System.out.println(neirou);
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setDataType("0");
        //tbNatmehaHotspot.setDataStatus("5");//1表示的是待市级审核
        tbNatmehaHotspot.setNeirou(neirou);
        Page<TbNatmehaHotspot> tbNatmehaHotspots = this.tbNatmehaHotspotDaoService.provincial_clerk_selectByPage(tbNatmehaHotspot, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaHotspots", tbNatmehaHotspots);
        return "tbProvincialClerk/solar_provincial_code";
    }

    //跳转增加节气养生界面
    @RequestMapping("/solar_provincialclerk_addimage")
    public String addpage_solar_provincial() {
        return "tbProvincialClerk/solar_provincial_add";
    }

    //static String itemcode = UUID.randomUUID().toString();
    //省级管理员添加新的节气养生数据
    @RequestMapping("/solar_provincialclerk_insert")
    public String solar_provincial_insert(@RequestParam("file")MultipartFile file, String hotspotTitle,String creater,String hotspotContent,
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
            tbNatmehaHotspot.setDataStatus("10");
            tbNatmehaHotspot.setDataType(dataType);
            tbNatmehaHotspot.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemcreateat = new java.sql.Date(ite.getTime());
            tbNatmehaHotspot.setItemcreateat(itemcreateat);
            String userCode = UUID.randomUUID().toString();
            tbNatmehaHotspot.setUserCode(userCode);
            this.tbNatmehaHotspotDaoService.solar_insert_code(tbNatmehaHotspot);
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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/solar_provincialclerk_code";
        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataStatus("5");
            tbNatmehaHotspot.setDataType(dataType);
            tbNatmehaHotspot.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemcreateat = new java.sql.Date(ite.getTime());
            tbNatmehaHotspot.setItemcreateat(itemcreateat);
            String userCode = UUID.randomUUID().toString();
            tbNatmehaHotspot.setUserCode(userCode);
            this.tbNatmehaHotspotDaoService.solar_insert_code(tbNatmehaHotspot);

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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/solar_provincialclerk_code";
        } else if (reset != null) {
            return "redirect:/start/solar_provincialclerk_code";
        }
        return null;
    }

    //查看返回
    @RequestMapping("/solar_provincialclerk_back")
    public String solar_provincialclerk_back() {
        return "redirect:/start/solar_provincial_code";
    }

    //通过itemID查看信息，如果是保存状态的信息，可以进行更改，然后提交或者选择保存
    @RequestMapping("/solar_provincialclerck_select")
    public String solar_provincialselect(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbProvincialClerk/solar_provincialclerk_see";
    }
    //
    //查询需要修改保存状态下的信息
    @RequestMapping("/solar_provincialclerck_update")
    public String solar_provincialclerck_update(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbProvincialClerk/solar_provincialclerk_update";
    }

    //修改保存状态下的信息,并且确定是保存还是提交
    @RequestMapping("/solar_provincialclerk_updatecode")
    public String solar_provincialclerk_updatecode(Integer itemid,String hotspotTitle,String creater,String hotspotContent,
                                                  String datasource,String savebtn,String putbtn,String reset) throws Exception {

        String dataType = "0";//0代表节气养生 ， 1代表自我保健，2代表药膳食疗
        //如果是保存按钮，即是已保存状态 0，提交状态（待县级审核）即是 1，县级审核不通过2，待市级审核3，
        // 市级审核不通过4，待省级审核5，省级审核不通过6，省级通过7，管理员发布8,
        if (savebtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataStatus("10");
            tbNatmehaHotspot.setDataType(dataType);
            tbNatmehaHotspot.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaHotspot.setItemupdateat(itemupdateat);
//            String userCode = UUID.randomUUID().toString();
//            tbNatmehaHotspot.setUserCode(userCode);
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/solar_provincial_code";//修改成功后返回数据主页面
        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataStatus("5");//5代表的是待省级审核，即科员审核
            tbNatmehaHotspot.setDataType(dataType);
            tbNatmehaHotspot.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaHotspot.setItemupdateat(itemupdateat);
//            String userCode = UUID.randomUUID().toString();
//            tbNatmehaHotspot.setUserCode(userCode);
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/solar_provincial_code";
        } else if (reset != null) {
            return "redirect:/start/solar_provincial_code";
        }
        return null;
    }

    //管理员节气养生信息页面直接提交数据给上一级审核,只需要改变数据状态就可
    @RequestMapping("/solar_provincialclerck_directUpdate/{itemid}")
    @ResponseBody
    public int solar_provincialclerck_directUpdate(@PathVariable("itemid") Integer itemid) {
        //String dataType = "0";//0代表节气养生 ， 1代表自我保健，2代表药膳食疗
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("5");//1代表的是待县级审核，即科员审核
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }
    //管理员删除信息
    @RequestMapping("/solar_provincialclerk_deleteByItemid/{itemid}")
    @ResponseBody
    public int solar_provincialclerk_deleteByItemid(@PathVariable("itemid") Integer itemid) {
        int i = this.tbNatmehaHotspotDaoService.deleteByItemid(itemid);
        if (i>0) {
            return i;
        }else {
            return 0;
        }
    }
//
//
//    //省级管理员自我保健
    @RequestMapping("/care_provincialclerk_code")
    public String care_provincialclerk_code(Integer pageNum,Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        //System.out.println(neirou);
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setDataType("1");
        tbNatmehaHotspot.setNeirou(neirou);
        Page<TbNatmehaHotspot> tbNatmehaHotspots = this.tbNatmehaHotspotDaoService.provincial_clerk_selectByPage(tbNatmehaHotspot, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaHotspots", tbNatmehaHotspots);
        return "tbProvincial_CareClerk/care_provincial_code";
    }
    //管理员新增自我保健,先跳转页面
    @RequestMapping("/care_provincial_addimage")
    public String addpage_care_provincial() {
        return "tbProvincial_CareClerk/care_provincial_add";
    }
    //然后再根据按钮各自执行相关保存和提交功能
    @RequestMapping("/care_provincialclerk_insert")
    public String care_provincial_insert(@RequestParam("file")MultipartFile file, String hotspotTitle,String creater,String hotspotContent,
                                        String datasource,String savebtn,String putbtn,String reset) throws Exception {
        String dataType = "1";//0代表节气养生 ， 1代表自我保健，2代表药膳食疗
        //如果是保存按钮，即是已保存状态 0，提交状态即是 1
        if (savebtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataStatus("10");
            tbNatmehaHotspot.setDataType(dataType);
            tbNatmehaHotspot.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemcreateat = new java.sql.Date(ite.getTime());
            tbNatmehaHotspot.setItemcreateat(itemcreateat);
            String userCode = UUID.randomUUID().toString();
            tbNatmehaHotspot.setUserCode(userCode);
            this.tbNatmehaHotspotDaoService.solar_insert_code(tbNatmehaHotspot);
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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                //创建时间
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String ict2 = df.format(new Date());
                Date ite2 = null;
                ite = df.parse(ict);
                java.sql.Date itemcreateat2 = new java.sql.Date(ite.getTime());
                tbNatmehaHotspot.setItemcreateat(itemcreateat2);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/care_provincialclerk_code";
        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataStatus("5");//1代表的是待市级审核，即科员审核
            tbNatmehaHotspot.setDataType(dataType);
            tbNatmehaHotspot.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemcreateat = new java.sql.Date(ite.getTime());
            tbNatmehaHotspot.setItemcreateat(itemcreateat);
            String userCode = UUID.randomUUID().toString();
            tbNatmehaHotspot.setUserCode(userCode);
            this.tbNatmehaHotspotDaoService.solar_insert_code(tbNatmehaHotspot);

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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                //创建时间
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String ict2 = df.format(new Date());
                Date ite2 = null;
                ite = df.parse(ict);
                java.sql.Date itemcreateat2 = new java.sql.Date(ite.getTime());
                tbNatmehaHotspot.setItemcreateat(itemcreateat2);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/care_provincialclerk_code";
        } else if (reset != null) {
            return "redirect:/start/care_provincialclerk_code";
        }
        return null;
    }
    //管理员查看不是保存状态的信息
    //通过itemID查看信息，如果是保存状态的信息，可以进行更改，然后提交或者选择保存
    @RequestMapping("/care_provincialclerck_select")
    public String care_provincialclerck_select(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbProvincial_CareClerk/care_provincialclerk_see";
    }
//
    //care_clerk_see.html页面取消按钮，返回数据页面
    @RequestMapping("/care_provincialclerk_back")
    public String care_provincialclerk_back() {
        return "redirect:/start/care_provincialclerk_code";
    }
    //管理员通过修改按钮，跳转自我保健修改页面
    @RequestMapping("/care_provincialclerck_update")
    public String care_provincialmunicipalclerck_update(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "/tbProvincial_CareClerk/care_provincialclerk_update";
    }
//
    //修改保存状态下的信息,并且确定是保存还是提交
    @RequestMapping("/carer_provincialclerk_updatecode")
    public String care_provincialupdatecode(Integer itemid,String hotspotTitle,String creater,String hotspotContent,
                                           String datasource,String savebtn,String putbtn,String reset) throws Exception {

        String dataType = "1";//0代表节气养生 ， 1代表自我保健，2代表药膳食疗
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
            tbNatmehaHotspot.setDataStatus("10");
            tbNatmehaHotspot.setDataType(dataType);
            tbNatmehaHotspot.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaHotspot.setItemupdateat(itemupdateat);
//            String userCode = UUID.randomUUID().toString();
//            tbNatmehaHotspot.setUserCode(userCode);
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/care_provincialclerk_code";//修改成功后返回数据主页面
        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            //String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataStatus("5");//1代表的是待市级审核，即科员审核
            tbNatmehaHotspot.setDataType(dataType);
            tbNatmehaHotspot.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaHotspot.setItemupdateat(itemupdateat);
//            String userCode = UUID.randomUUID().toString();
//            tbNatmehaHotspot.setUserCode(userCode);
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/care_provincialclerk_code";
        } else if (reset != null) {
            return "redirect:/start/care_provincialclerk_code";
        }
        return null;
    }
//
    //市级管理员自我保健信息页面直接提交数据给上一级审核,只需要改变数据状态就可
    @RequestMapping("/care_provincialclerck_directUpdate/{itemid}")
    @ResponseBody
    public int care_provincialclerck_directUpdate(@PathVariable("itemid") Integer itemid) {
        //String dataType = "0";//0代表节气养生 ， 1代表自我保健，2代表药膳食疗
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("5");//1代表的是待县级审核，即科员审核
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }
//
    //省级管理员药膳管理
    @RequestMapping("/med_provincialclerk_code")
    public String med_provincialclerk_code(Integer pageNum,Model model,String neirou) {
        if (pageNum==null) {
            pageNum = 1;
        }
        //System.out.println(neirou);
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setDataType("2");
        tbNatmehaHotspot.setNeirou(neirou);
        Page<TbNatmehaHotspot> tbNatmehaHotspots = this.tbNatmehaHotspotDaoService.provincial_clerk_selectByPage(tbNatmehaHotspot, pageNum, 5);
        model.addAttribute("neirou", neirou);
        model.addAttribute("tbNatmehaHotspots", tbNatmehaHotspots);
        return "tbMedProvincialClerk/med_provincial_code";
    }
    //管理员新增自我保健,先跳转页面
    //跳转增加节气养生界面
    @RequestMapping("/med_provincial_addimage")
    public String addpage_med_provincial() {
        return "tbMedProvincialClerk/med_provincial_add";
    }
    //然后再根据按钮各自执行相关保存和提交功能
    @RequestMapping("/med_provincialclerk_insert")
    public String med_provincial_insert(@RequestParam("file")MultipartFile file, String hotspotTitle,String creater,String hotspotContent,
                                       String datasource,String savebtn,String putbtn,String reset) throws Exception {
        String dataType = "2";//0代表节气养生 ， 1代表自我保健，2代表药膳食疗
        //如果是保存按钮，即是已保存状态 0，提交状态即是 1
        if (savebtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataStatus("10");
            tbNatmehaHotspot.setDataType(dataType);
            tbNatmehaHotspot.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemcreateat = new java.sql.Date(ite.getTime());
            tbNatmehaHotspot.setItemcreateat(itemcreateat);
            String userCode = UUID.randomUUID().toString();
            tbNatmehaHotspot.setUserCode(userCode);
            this.tbNatmehaHotspotDaoService.solar_insert_code(tbNatmehaHotspot);
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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                //创建时间
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String ict2 = df.format(new Date());
                Date ite2 = null;
                ite = df.parse(ict);
                java.sql.Date itemcreateat2 = new java.sql.Date(ite.getTime());
                tbNatmehaHotspot.setItemcreateat(itemcreateat2);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/med_provincialclerk_code";
        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemcode(itemcode);
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataStatus("5");//1代表的是待市级审核，即科员审核
            tbNatmehaHotspot.setDataType(dataType);
            tbNatmehaHotspot.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemcreateat = new java.sql.Date(ite.getTime());
            tbNatmehaHotspot.setItemcreateat(itemcreateat);
            String userCode = UUID.randomUUID().toString();
            tbNatmehaHotspot.setUserCode(userCode);
            this.tbNatmehaHotspotDaoService.solar_insert_code(tbNatmehaHotspot);

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
                tbNatmehaFile.setDataCode(itemcode);
                tbNatmehaFile.setFileName(Filename);
                tbNatmehaFile.setFilePath(filePath);
                //创建时间
                SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String ict2 = df.format(new Date());
                Date ite2 = null;
                ite = df.parse(ict);
                java.sql.Date itemcreateat2 = new java.sql.Date(ite.getTime());
                tbNatmehaHotspot.setItemcreateat(itemcreateat2);
                int insert = this.tbNatmehaFileService.insert(tbNatmehaFile);
            }
            return "redirect:/start/med_provincialclerk_code";
        } else if (reset != null) {
            return "redirect:/start/med_provincialclerk_code";
        }
        return null;
    }
    //管理员查看不是保存状态的信息
    //通过itemID查看信息，如果是保存状态的信息，可以进行更改，然后提交或者选择保存
    @RequestMapping("/med_provincialclerck_select")
    public String med_provincialclerck_select(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbMedProvincialClerk/med_provincialclerk_see";
    }
//
    //care_clerk_see.html页面取消按钮，返回数据页面
    @RequestMapping("/med_provincialclerk_back")
    public String med_provincialclerk_back() {
        return "redirect:/start/med_provincialclerk_code";
    }
    //管理员通过修改按钮，跳转自我保健修改页面
    @RequestMapping("/med_provincialclerck_update")
    public String med_provincialclerck_update(Integer itemid,Model model) {
        TbNatmehaHotspot tbNatmehaHotspot = this.tbNatmehaHotspotDaoService.solar_select(itemid);
        model.addAttribute("tbNatmehaHotspot", tbNatmehaHotspot);
        return "tbMedProvincialClerk/med_provincialclerk_update";
    }
//
    //修改保存状态下的信息,并且确定是保存还是提交
    @RequestMapping("/med_provincialclerk_updatecode")
    public String med_provincialupdatecode(Integer itemid,String hotspotTitle,String creater,String hotspotContent,
                                          String datasource,String savebtn,String putbtn,String reset) throws Exception {

        String dataType = "2";//0代表节气养生 ， 1代表自我保健，2代表药膳食疗
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
            tbNatmehaHotspot.setDataStatus("10");
            tbNatmehaHotspot.setDataType(dataType);
            tbNatmehaHotspot.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaHotspot.setItemupdateat(itemupdateat);
            //String userCode = UUID.randomUUID().toString();
            //tbNatmehaHotspot.setUserCode(userCode);
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/med_provincialclerk_code";//修改成功后返回数据主页面
        } else if (putbtn != null) {
            Thread.sleep(2000);//等待2秒后在执行下一步代码
            //自动生成唯一标识码
            //String itemcode = UUID.randomUUID().toString();
            TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
            tbNatmehaHotspot.setItemid(itemid);
            tbNatmehaHotspot.setHotspotTitle(hotspotTitle);
            tbNatmehaHotspot.setHotspotContent(hotspotContent);
            tbNatmehaHotspot.setDataStatus("5");//1代表的是待市级审核，即科员审核
            tbNatmehaHotspot.setDataType(dataType);
            tbNatmehaHotspot.setCreater(creater);
            //创建时间
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String ict = df.format(new Date());
            Date ite = null;
            ite = df.parse(ict);
            java.sql.Date itemupdateat = new java.sql.Date(ite.getTime());
            tbNatmehaHotspot.setItemupdateat(itemupdateat);
            //String userCode = UUID.randomUUID().toString();
            //tbNatmehaHotspot.setUserCode(userCode);
            this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
            return "redirect:/start/med_provincialclerk_code";
        } else if (reset != null) {
            return "redirect:/start/med_provincialclerk_code";
        }
        return null;
    }
//
    //市级管理员自我保健信息页面直接提交数据给上一级审核,只需要改变数据状态就可
    @RequestMapping("/med_provincialclerck_directUpdate/{itemid}")
    @ResponseBody
    public int med_provincialclerck_directUpdate(@PathVariable("itemid") Integer itemid) {
        //String dataType = "0";//0代表节气养生 ， 1代表自我保健，2代表药膳食疗
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("5");//1代表的是待县级审核，即科员审核
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }

    //主管理员发布,启动主管理员页面
    @RequestMapping("/mainclerk")
    public String mainclerk() {
        return "tbmainclerk/main_clerk";
    }

    //主管理员节气养生发布
    @RequestMapping("/solar_main_clerk_code")
    public String solar_main_clerk_code(Integer pageNum,Model model,String neirou) {
        String dataType = "0";
        if (pageNum==null) {
            pageNum = 1;
        }
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setDataType(dataType);
        //tbNatmehaHotspot.setDataStatus("7");
       // tbNatmehaHotspot.setDataType(dataType);
        tbNatmehaHotspot.setNeirou(neirou);
        model.addAttribute("neirou", neirou);
        Page<TbNatmehaHotspot> tbNatmehaHotspots = this.tbNatmehaHotspotDaoService.mainselectByPage(tbNatmehaHotspot, pageNum, 5);
        model.addAttribute("tbNatmehaHotspots", tbNatmehaHotspots);
        return "tbmainclerk/main_clerk_solarcode";
    }

    //主管理员发布节气养生
    //管理员发布信息
    @RequestMapping("/solar_mainclerck_release/{itemid}")
    @ResponseBody
    public int solar_mainclerck_release(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("8");//1代表的是待市级审核，即科员审核,8代表发布
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }

    //管理员下架发布信息
    @RequestMapping("/solar_mainclerck_norelease/{itemid}")
    @ResponseBody
    public int solar_mainclerck_norelease(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("7");//1代表的是待市级审核，即科员审核,8代表发布，状态变为省级审核通过
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }
    //主管理员删除未发布的节气养生信息
    @RequestMapping("/solar_mainclerk_deleteByItemid/{itemid}")
    @ResponseBody
    public int solar_mainclerk_deleteByItemid(@PathVariable("itemid") Integer itemid) {
        int i = this.tbNatmehaHotspotDaoService.deleteByItemid(itemid);
        return i;
    }

    //主管理员自我保健发布
    @RequestMapping("/care_main_clerk_code")
    public String care_main_clerk_code(Integer pageNum,Model model,String neirou) {
        String dataType = "1";
        if (pageNum==null) {
            pageNum = 1;
        }
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setDataType(dataType);
        //tbNatmehaHotspot.setDataStatus("7");
        // tbNatmehaHotspot.setDataType(dataType);
        tbNatmehaHotspot.setNeirou(neirou);
        model.addAttribute("neirou", neirou);
        Page<TbNatmehaHotspot> tbNatmehaHotspots = this.tbNatmehaHotspotDaoService.mainselectByPage(tbNatmehaHotspot, pageNum, 5);
        model.addAttribute("tbNatmehaHotspots", tbNatmehaHotspots);
        return "tbmainclerk/main_clerk_carecode";
    }
    //主管理员发布自我保健
    //管理员发布信息
    @RequestMapping("/care_mainclerck_release/{itemid}")
    @ResponseBody
    public int care_mainclerck_release(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("8");//1代表的是待市级审核，即科员审核,8代表发布
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }

    //管理员下架发布信息
    @RequestMapping("/care_mainclerck_norelease/{itemid}")
    @ResponseBody
    public int care_mainclerck_norelease(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("7");//1代表的是待市级审核，即科员审核,8代表发布，状态变为省级审核通过
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }
    //主管理员删除未发布的节气 养生信息
    @RequestMapping("/care_mainclerk_deleteByItemid/{itemid}")
    @ResponseBody
    public int care_mainclerk_deleteByItemid(@PathVariable("itemid") Integer itemid) {
        int i = this.tbNatmehaHotspotDaoService.deleteByItemid(itemid);
        return i;
    }

    //主管理员药膳食疗发布
    @RequestMapping("/med_main_clerk_code")
    public String med_main_clerk_code(Integer pageNum,Model model,String neirou) {
        String dataType = "2";
        if (pageNum==null) {
            pageNum = 1;
        }
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setDataType(dataType);
        //tbNatmehaHotspot.setDataStatus("7");
        // tbNatmehaHotspot.setDataType(dataType);
        tbNatmehaHotspot.setNeirou(neirou);
        model.addAttribute("neirou", neirou);
        Page<TbNatmehaHotspot> tbNatmehaHotspots = this.tbNatmehaHotspotDaoService.mainselectByPage(tbNatmehaHotspot, pageNum, 5);
        model.addAttribute("tbNatmehaHotspots", tbNatmehaHotspots);
        return "tbmainclerk/main_clerk_medcode";
    }
    //主管理员发布自我保健
    //管理员发布信息
    @RequestMapping("/med_mainclerck_release/{itemid}")
    @ResponseBody
    public int med_mainclerck_release(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("8");//1代表的是待市级审核，即科员审核,8代表发布
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }

    //管理员下架发布信息
    @RequestMapping("/med_mainclerck_norelease/{itemid}")
    @ResponseBody
    public int med_mainclerck_norelease(@PathVariable("itemid") Integer itemid) {
        TbNatmehaHotspot tbNatmehaHotspot = new TbNatmehaHotspot();
        tbNatmehaHotspot.setItemid(itemid);
        tbNatmehaHotspot.setDataStatus("7");//1代表的是待市级审核，即科员审核,8代表发布，状态变为省级审核通过
        int i = this.tbNatmehaHotspotDaoService.solar_updata_code(tbNatmehaHotspot);
        return i;
    }
    //主管理员删除未发布的节气 养生信息
    @RequestMapping("/med_mainclerk_deleteByItemid/{itemid}")
    @ResponseBody
    public int med_mainclerk_deleteByItemid(@PathVariable("itemid") Integer itemid) {
        int i = this.tbNatmehaHotspotDaoService.deleteByItemid(itemid);
        return i;
    }


}