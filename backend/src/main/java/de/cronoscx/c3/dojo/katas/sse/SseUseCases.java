package de.cronoscx.c3.dojo.katas.sse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController
@RequestMapping(path = "/sse")
@RequiredArgsConstructor
class SseUseCases {
    private final DispatcherService dispatcherService;

    @GetMapping(path = "/dummy", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    SseEmitter events(@AuthenticationPrincipal User user) {
        final var emitter = new SseEmitter(0L); /* kein serverseitiger Timeout */

        dispatcherService.dispatch(user.getUsername(), emitter);

        return emitter;
    }

}
