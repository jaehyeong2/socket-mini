package jjfactory.sockett;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class ChattingHandler extends TextWebSocketHandler {

    private List<WebSocketSession> sessionList = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("afterConnectionEstablished() 호출");
        sessionList.add(session);
        log.info(session.getPrincipal().getName() + "님 입장");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("handleTextMessage() 호출");

        for(WebSocketSession s: sessionList){
            s.sendMessage(new TextMessage(session.getPrincipal().getName() + ":" + message.getPayload()));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("afterConnectionClosed() 호출");

        sessionList.remove(session);
        log.info(session.getPrincipal().getName() + "님 퇴장");
    }
}
