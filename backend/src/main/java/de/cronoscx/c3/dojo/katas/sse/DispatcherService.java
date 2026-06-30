package de.cronoscx.c3.dojo.katas.sse;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.task.TaskExecutor;
import org.springframework.core.task.VirtualThreadTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Slf4j
@Validated
@Service
class DispatcherService {
    private final TaskExecutor taskExecutor = new VirtualThreadTaskExecutor();

    void dispatch(@NotBlank String username, @NotNull SseEmitter emitter) {
        taskExecutor.execute(() -> {
            for (int index = 0; index < 600; index++) {
                final var eventKey = "Event #%02d".formatted(index);

                try {
                    emitter.send("[%s :: %s] succeeded for %s".formatted(Thread.currentThread(), eventKey, username));
                    Thread.sleep(1_000);
                } catch (IOException | InterruptedException ex) {
                    log.warn("[{}] failed for {}: {}", eventKey, username, ex.getMessage());
                }
            }
            emitter.complete();
        });
    }

}
