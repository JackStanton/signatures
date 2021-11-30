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


@Controller
@SessionAttributes("User")
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
    public String doLogin(Model model) {
        setUser(new User());
        model.addAttribute("user", new User());
        model.addAttribute("message", "");
        return "login";
    }

    @RequestMapping("/checkUser")
    public String confirmUser(@ModelAttribute("user") User user, Model model, HttpServletRequest request) {
        User authUser = userService.checkUser(user.getLogin(),user.getPassword());
        if(authUser != null){
            model.addAttribute("authUser", authUser);
            request.setAttribute("test","test123");
            setUser(authUser);
            model.addAttribute("documents", directoryHandler.getFiles("documents"));
            return "views/documents";
        }else {
            model.addAttribute("message", "Перепроверьте данные");
            return "login";
        }
    }

    @RequestMapping(value = "/open", method = RequestMethod.GET)
    public String openFile(@RequestParam("fileName") String fileName, Model model){
        model.addAttribute("document", xmlService.getDocument(new File(fileName)));
        model.addAttribute("documentTmp", new DocumentXml());
        setDoc(xmlService.getDocument(new File(fileName)));
        return "views/documentSign";
    }

    @RequestMapping(value = "/openLevel", method = RequestMethod.GET)
    public String openLevel(@RequestParam("name") String name, Model model, HttpSession session){
        String doc = (String) session.getAttribute("User");
        System.out.println(doc);
        return "index";
    }

    @ModelAttribute("User")
    public String setUser (User user) {
        if(user == null){
            return null;
        }
        return user.getLogin();
    }

    @ModelAttribute("Doc")
    public DocumentXml setDoc (DocumentXml doc) {
        return doc;
    }
}
