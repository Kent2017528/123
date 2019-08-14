package cn.smbms.controller;

import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.role.RoleService;
import cn.smbms.service.user.UserService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/sys/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Value("${currentPageNo}")
    private String currentPageNo;
    @Value("${pageSize}")
    private String pageSize;

   /* @Value("${pageIndex}")
    private String pageIndex;*/


  /*  @RequestMapping("/login")
    public String login(@RequestParam(value = "userCode") String userCode,
                        @RequestParam(value = "userPassword")String userPassword,
                        HttpSession session,
                        Model model){

        User user = userService.login(userCode, userPassword);
        if (null!=user){
            session.setAttribute("userSession",user);
            return "jsp/frame";
        }

        model.addAttribute("error", "用户名或者密码不正确！");
        return "login";
    }*/

    @RequestMapping("/query")
    public String query(
            @RequestParam(value = "queryname",required = false)String queryName,
            @RequestParam(value = "queryUserRole",required = false)Long queryUserRole,
            @RequestParam(value = "pageIndex",required = false)String currentPageNo,
            @RequestParam(value = "pageSize",required = false)String pageSize,
            Model model){

       this.currentPageNo=(currentPageNo==null||currentPageNo=="")?this.currentPageNo:currentPageNo;
       this.pageSize=(pageSize==null||pageSize=="")?this.pageSize:pageSize;


        List<Role> roleList = roleService.getAll();
        PageInfo<User> pageInfo = userService.getAll(queryName, queryUserRole
                , Integer.parseInt(this.currentPageNo), Integer.parseInt(this.pageSize));

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("roleList", roleList);
        //回显
        model.addAttribute("queryname",queryName);
        model.addAttribute("queryUserRole",queryUserRole);

        return "jsp/userlist";
    }

    @RequestMapping("/view")
    public String view(@RequestParam("uid")long uid,Model model){
        User user = userService.getUserById(uid);
        model.addAttribute("user", user);

        return "jsp/userview";
    }

    @RequestMapping("/modify")
    public ModelAndView modify(@RequestParam("uid")Long uid, User user){
        ModelAndView mv=new ModelAndView("jsp/usermodify");
        user = userService.getUserById(uid);
        mv.addObject("user", user);
        return mv;
    }

    @RequestMapping("/getrolelist")
    @ResponseBody
    public String getRoleList(){
        List<Role> list = roleService.getAll();
        String jsonString = JSON.toJSONString(list);
        return jsonString;
    }


    @RequestMapping("/modifysave")
    public ModelAndView modifySave(User user){
        int i = userService.modifySave(user);
        ModelAndView mv=new ModelAndView();
        mv.setViewName("redirect:/sys/user/query");
        return mv;

    }
    @RequestMapping("/deluser")
    @ResponseBody
    public String delUser(@RequestParam("uid")Long uId){
        int i = userService.delUserById(uId);
        String str=i>0?"true":"false";
        return JSON.toJSONString(str);
    }

    @RequestMapping("/toadd")
    public String toadd(){

       return "/jsp/useradd";
    }
    @RequestMapping("/ucexist")
    @ResponseBody
    public String ucexist(@RequestParam("userCode") String userCode){
        User user = userService.getUserByUserCode(userCode);
        String str=user==null?"noexist":"exist";
        return  JSON.toJSONString(str);
    }

    @RequestMapping("/add")
    public String add(User user){
        int add = userService.add(user);
        return "redirect:/sys/user/query";
    }
}
