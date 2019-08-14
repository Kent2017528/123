package cn.smbms.controller;

import cn.smbms.pojo.User;
import cn.smbms.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class DefaultController {
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(){
        return "login";
    }

    @RequestMapping("/login")
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
    }
}
