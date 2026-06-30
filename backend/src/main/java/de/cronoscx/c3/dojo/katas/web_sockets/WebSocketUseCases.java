package de.cronoscx.c3.dojo.katas.web_sockets;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(WebSocketUseCases.API_PATH)
@RequiredArgsConstructor
public class WebSocketUseCases {
    static final String API_PATH = "/web-sockets";

    String placeholder() {
        return "Test.";
    }

}
