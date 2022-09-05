package com.ezen.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.dto.ChatMessage;
import com.ezen.dto.ChatRoom;
import com.ezen.dto.ChatSession;
import com.ezen.dto.LoginUser;
import com.ezen.dto.Member;
import com.ezen.dto.Product;
import com.ezen.service.ChatService;
import com.ezen.service.MemberService;
import com.ezen.service.ProductService;
import com.google.gson.JsonIOException;

@Controller
public class ChatController {

	@Autowired
    ChatService cService;
    
    @Autowired
    private ProductService pService;
    
    @Autowired
    private ChatSession cSession;
    
    @Autowired
    private MemberService mService;
    
    /**
     * 해당 채팅방의 채팅 메세지 불러오기
     * @param roomId
     * @param model
     * @param response
     * @throws JsonIOException
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value="/chat/{roomId}")
    public List<ChatMessage> messageList(@PathVariable Long roomId, ChatRoom room, String userEmail, Model model, HttpServletResponse response) throws JsonIOException, IOException {
    	System.out.println("접속 : " + roomId );
        List<ChatMessage> mList = cService.messageList(room);

        ChatMessage message = new ChatMessage();
        message.setEmail(userEmail);
        message.setRoom(room);
        
        System.out.println("메시지값:"+ message);
        
        if(mList !=null) {
        	cService.updateCount(message);
        }
        
        return mList;
    }
    /**
     * 채팅 방이 없을 때 생성
     * @param room
     * @param userEmail
     * @param masterNickname
     * @return
     */
    @ResponseBody
    @RequestMapping("createChat")
    public String createChat(Product vo, ChatRoom room, HttpSession session){

    	System.out.println("값 확인:"+vo);
    	String userNickname = (String)session.getAttribute("userId");
    	
        Member m = mService.getMember(userNickname);
        LoginUser u = mService.getUser(userNickname);
       
        if(u == null && m == null){
        	return "loginForm";
        }
        if(u == null) {
	        room.setUserEmail(m.getEmail());
	        room.setUserName(m.getUsername());
        } else if (m == null) {
        	room.setUserEmail(u.getEmail());
	        room.setUserName(u.getUsername());
        } 
        
        room.setMasterEmail(vo.getEmail());
        room.setMasterName(vo.getUsername());
        
        System.out.println("값 확인:"+room);
        
        ChatRoom exist  = cService.searchChatRoom(room);
        ChatRoom reverseExist = cService.reverseSearchChatRoom(room);
        // DB에 방이 없을 때
        if(exist == null && reverseExist == null && room.getUserEmail() != room.getMasterEmail()) {
            System.out.println("방이 없다!!");
            int result = cService.createChat(room);
            if(result == 1) {
                System.out.println("방 만들었다!!");
                return "new";
            }else {
                return "fail";
            }
        }
        // DB에 방이 있을 때
        else{
            System.out.println("방이 있다!!");
            return "exist";
        }
    }
    /**
     * 채팅 방 목록 불러오기
     * @param room
     * @param userEmail
     * @param response
     * @throws JsonIOException
     * @throws IOException
     */
    
    @ResponseBody
    @RequestMapping("chatRoomList")
    public List<ChatRoom> createChatList(ChatRoom room, ChatMessage message, String userEmail, HttpServletResponse response) throws JsonIOException, IOException{

    	List<ChatRoom> cList = cService.chatRoomList(userEmail,userEmail);
        
        //System.out.println("값 1:"+ cList);
        
        
      return cList;
      
       
    }
    
  
    
  
   
}
