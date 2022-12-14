package com.ezen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class ChatHandlerController {
    
    @GetMapping("/chat")
    public String chatGET(){

        log.info("@ChatController, chat GET()");
        
        return "user/talk";
    }
}
