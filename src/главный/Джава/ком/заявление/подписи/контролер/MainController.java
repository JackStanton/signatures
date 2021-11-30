package com.application.signatures.controller;

import com.application.signatures.directoryhandler.DirectoryHandler;
import com.application.signatures.entity.User;
import com.application.signatures.jackson.entity.DocumentXml;
import com.application.signatures.jackson.service.XmlService;
import com.application.signatures.service.UserService;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.RequestPredicates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


@Controller
public class MainController {

    private final UserService userService;
    private final XmlService xmlService;
    private final DirectoryHandler directoryHandler;

    public MainController(UserService userService, XmlService xmlService, DirectoryHandler directoryHandler) {
        this.userService = userService;
        this.xmlService = xmlService;
        this.directoryHandler = directoryHandler;
    }

    @RequestMapping("/")
    public String showIndex(){
        return "index";
    }

    @RequestMapping("/login")
    public String doLogin(Model model, HttpServletRequest request) {
        reqest.getSession().setAttribute("user",authUser.getLogin())
        model.addAttribute("user", new User());
        model.addAttribute("message", "");
        return "login";
    }

    @RequestMapping("/checkUser")
    public String confirmUser(@ModelAttribute("user") User user, Model model, HttpServletRequest request) {
        User authUser = userService.checkUser(user.getLogin(),user.getPassword());
        if(authUser != null){
            model.addAttribute("authUser", authUser);
            reqest.getSession().setAttribute("user",authUser.getLogin());
            model.addAttribute("documents", directoryHandler.getFiles("documents"));
            return "views/documents";
        }else {
            model.addAttribute("message", "Перепроверьте данные");
            return "login";
        }
    }

    @RequestMapping(value = "/open", method = RequestMethod.GET)
    public String openFile(@RequestParam("fileName") String fileName, Model model, HttpServletRequest request){
        model.addAttribute("document", xmlService.getDocument(new File(fileName)));
        model.addAttribute("documentTmp", new DocumentXml());
        reqest.getSession().setAttribute("filename",fileName)
        return "views/documentSign";
    }

    @RequestMapping(value = "/openLevel", method = RequestMethod.GET)
    public String openLevel(@RequestParam("name") String name, Model model, HttpServletRequest request){
        String login = reqest.getSession().getAttribute("user");
        String file = reqest.getSession().getAttribute("filename");
        xmlService.setSign(file,name,login,true);
        return "views/documents";
    }
}
