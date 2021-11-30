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
@SessionAttributes({"User", "Doc"})
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
        setAttr(new User());
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
            setAttr(authUser);
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
        setAttr(fileName);
        return "views/documentSign";
    }

    @RequestMapping(value = "/openLevel", method = RequestMethod.GET)
    public String openLevel(@RequestParam("name") String name, Model model, HttpSession session){
        HashMap<String, String> map = (HashMap<String, String>) session.getAttribute("User");
        String user = map.get("User");
        String doc = "/documents/" + map.get("Doc");
        System.out.println(user+" "+doc+" "+name);
        xmlService.setSign(doc,name,user,true);

        return "views/documents";
    }

    @ModelAttribute("User")
    public HashMap<String, String> setAttr (User user) {
        if(user == null){
            return null;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("User", user.getLogin());
        return  map;
    }

    @ModelAttribute("User")
    public HashMap<String, String> setAttr (String doc) {
        if(doc == null){
            return null;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("Doc", doc);
        return  map;
    }

}
