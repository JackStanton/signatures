package com.application.signatures.controller;

import com.application.signatures.directoryhandler.DirectoryHandler;
import com.application.signatures.entity.User;
import com.application.signatures.jackson.entity.DocumentXml;
import com.application.signatures.jackson.service.XmlService;
import com.application.signatures.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.File;


@Controller
public class MainController {

    private final UserService userService;
    
    private final XmlService xmlService;

    public MainController(UserService userService, XmlService xmlService) {
        this.userService = userService;
        this.xmlService = xmlService;
    }

    @RequestMapping("/")
    public String showIndex(){
        return "index";
    }

    @RequestMapping("/login")
    public String doLogin(Model model, HttpServletRequest request) {
        request.getSession().setAttribute("user", new User());
        model.addAttribute("user", new User());
        model.addAttribute("message", "");
        return "login";
    }

    @RequestMapping("/checkUser")
    public String confirmUser(@ModelAttribute("user") User user, Model model, HttpServletRequest request) {
        User authUser = userService.checkUser(user.getLogin(),user.getPassword());
        if(authUser != null){
            model.addAttribute("authUser", authUser);
            request.getSession().setAttribute("user",authUser.getLogin());
            model.addAttribute("documents", DirectoryHandler.getFiles("documents"));
            return "views/documents";
        }else {
            model.addAttribute("message", "Перепроверьте данные");
            return "login";
        }
    }

    @RequestMapping(value = "/open", method = RequestMethod.GET)
    public String openFile(@RequestParam("fileName") String fileName, Model model, HttpServletRequest request){
        model.addAttribute("document", XmlService.getDocument(new File("documents/"+fileName)));
        model.addAttribute("documentTmp", new DocumentXml());
        request.getSession().setAttribute("filename",fileName);
        return "views/documentSign";
    }

    @RequestMapping(value = "/openLevel", method = RequestMethod.GET)
    public String openLevel(@RequestParam("name") String name, HttpServletRequest request, Model model){
        String login = (String) request.getSession().getAttribute("user");
        String file = (String) request.getSession().getAttribute("filename");
        model.addAttribute("documents", DirectoryHandler.getFiles("documents"));
        xmlService.setSign(file,name,login,true);
        return "views/documents";
    }

    @RequestMapping(value = "/falseLevel", method = RequestMethod.GET)
    public String falseLevel(@RequestParam("name") String name, HttpServletRequest request, Model model){
        String login = (String) request.getSession().getAttribute("user");
        String file = (String) request.getSession().getAttribute("filename");
        model.addAttribute("documents", DirectoryHandler.getFiles("documents"));
        xmlService.setSign(file,name,login,false);
        return "views/documents";
    }
}
