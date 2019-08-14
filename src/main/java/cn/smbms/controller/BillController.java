package cn.smbms.controller;

import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;

import cn.smbms.service.bill.BillService;
import cn.smbms.service.provider.ProviderService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/sys/bill")
public class BillController {

    @Autowired
    private BillService billService;
    @Autowired
    private ProviderService providerService;


    @Value("${currentPageNo}")
    private String currentPageNo;
    @Value("${pageSize}")
    private String pageSize;

    @RequestMapping("view")
    public String view(@RequestParam("billid")Long billid,Model model){
        Bill bill = billService.getBillByBillid(billid);
        model.addAttribute("bill",bill);
        return "jsp/billview";
    }

    @RequestMapping("/query")
    public String query(@RequestParam(value = "queryProductName",required = false)String queryProductName,
                        @RequestParam(value = "queryProviderId",required = false)Long queryProviderId,
                        @RequestParam(value = "queryIsPayment",required = false)Integer queryIsPayment,
                        @RequestParam(value = "pageIndex",required = false)String currentPageNo,
                        @RequestParam(value = "pageSize",required = false)String pageSize,
                        Model model){
        this.currentPageNo=(currentPageNo==null||currentPageNo=="")?this.currentPageNo:currentPageNo;
        this.pageSize=(pageSize==null||pageSize=="")?this.pageSize:pageSize;

        PageInfo<Bill> pageInfo = billService.getAll(queryProductName, queryProviderId, queryIsPayment,
                Integer.parseInt(this.currentPageNo), Integer.parseInt(this.pageSize));

        List<Provider> providerList = providerService.getAll();

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("providerList", providerList);
        //回显
        model.addAttribute("queryProductName",queryProductName);
        model.addAttribute("queryIsPayment",queryIsPayment);
        model.addAttribute("queryProviderId",queryProviderId);

        /*
        *  List<Role> roleList = roleService.getAll();
        PageInfo<User> pageInfo = userService.getAll(queryName, queryUserRole
                , Integer.parseInt(this.currentPageNo), Integer.parseInt(this.pageSize));

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("roleList", roleList);
        //回显
        model.addAttribute("queryname",queryName);
        model.addAttribute("queryUserRole",queryUserRole);
        * */

        return "jsp/billlist";
    }

    @RequestMapping("/modify")
    public ModelAndView modify(@RequestParam("billid")Long billid, Bill bill){
        ModelAndView mv=new ModelAndView("jsp/billmodify");
        bill = billService.getBillByBillid(billid);
        mv.addObject("bill", bill);
        return mv;
    }

    @RequestMapping("/getproviderlist")
    @ResponseBody
    public String getProviderList(){
        List<Provider> list = providerService.getAll();
        String jsonString = JSON.toJSONString(list);
        return jsonString;
    }

    @RequestMapping("/modifysave")
    public ModelAndView modifySave(Bill bill){
        int i = billService.modifySave(bill);
        ModelAndView mv=new ModelAndView();
        mv.setViewName("redirect:/sys/bill/query");
        return mv;

    }

    @RequestMapping("/delbill")
    @ResponseBody
    public String delBill(@RequestParam("billid")Long billId){
        int i = billService.delBillById(billId);
        String str=i>0?"true":"false";
        return JSON.toJSONString(str);
    }

    @RequestMapping("/toadd")
    public String toadd(){

        return "/jsp/billadd";
    }

}
