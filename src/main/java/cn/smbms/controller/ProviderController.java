package cn.smbms.controller;

import cn.smbms.pojo.Provider;
import cn.smbms.service.provider.ProviderService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/sys/provider")
public class ProviderController {

    @Autowired
    private ProviderService providerService;

    @Value("${currentPageNo}")
    private String currentPageNo;
    @Value("${pageSize}")
    private String pageSize;

    @RequestMapping("/view")
    public  String view(@RequestParam("proid")Long proid,Model model){
        Provider provider = providerService.getProviderByProid(proid);
        model.addAttribute("provider",provider);

        return "/jsp/providerview";
    }

    @RequestMapping("/query")
    public String query(@RequestParam(value = "queryProCode",required = false) String queryProCode,
                        @RequestParam(value = "queryProName",required = false) String queryProName,
                        @RequestParam(value = "pageIndex",required = false)String currentPageNo,
                        @RequestParam(value = "pageSize",required = false)String pageSize,
                        Model model){

        this.currentPageNo=(currentPageNo==null||currentPageNo=="")?this.currentPageNo:currentPageNo;
        this.pageSize=(pageSize==null||pageSize=="")?this.pageSize:pageSize;

        PageInfo<Provider> pageInfo = providerService.getProiderList(queryProCode, queryProName,
                Integer.parseInt(this.currentPageNo), Integer.parseInt(this.pageSize));
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("queryProCode",queryProCode);
        model.addAttribute("queryProName",queryProName);

        return "jsp/providerlist";
    }
}
