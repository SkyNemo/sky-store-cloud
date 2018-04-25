package cn.edu.kmust.store.user.controller;


import cn.edu.kmust.store.user.entity.User;
import cn.edu.kmust.store.user.param.UserParam;
import cn.edu.kmust.store.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {


    @Resource
    private UserService userService;


    @RequestMapping("/toRegister")
    public String toRegister() {
        return "register";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }


    @RequestMapping("/login")
    public String Login(@Valid UserParam userParam, BindingResult result, HttpServletRequest request, ModelMap map) {

        StringBuffer errorMsg = new StringBuffer();

        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();

            for (ObjectError error : list) {
                errorMsg = errorMsg.append(error.getCode()).append("-").append(error.getDefaultMessage())
                        .append(";").append("\n");
            }

            map.addAttribute("errorMsg", errorMsg.toString());
            return "login";
        }


        User user = userService.selectUserByNameAndPassword(userParam.getName(), userParam.getPassword());
        if (user == null) {
            map.addAttribute("errorMsg", "用户名或密码错误");
            return "login";
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("user",user.getId().toString() + " " + user.getName().toString());
            return "redirect:http://localhost:18040/service-product/";
        }

    }


    @RequestMapping("/register")
    public String Register(@Valid UserParam userParam, BindingResult result, ModelMap map) {

        StringBuffer errorMsg = new StringBuffer();

        if (result.hasErrors()) {
            List<ObjectError> list = result.getAllErrors();

            for (ObjectError error : list) {
                errorMsg = errorMsg.append(error.getCode()).append("-").append(error.getDefaultMessage()).append(";");
            }

            map.addAttribute("errorMsg", errorMsg.toString());
            return "register";
        }

        boolean userIsExist = userService.selectUserByName(userParam.getName());
        if (userIsExist) {
            map.addAttribute("errorMsg", "用户已存在！");
            return "register";
        }


        User user = new User();
        BeanUtils.copyProperties(userParam, user);
        boolean saveResult = userService.saveUser(user);
        if (saveResult) {
            return "login";
        } else {
            return "register";
        }

    }


    @RequestMapping("logout")
    public String logout(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        return "login";
    }

}
