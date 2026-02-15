package de.cronoscx.c3.dojo.katas.spring_ai_rag;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(SpringAiRagUseCases.API_PATH)
@RequiredArgsConstructor
class SpringAiRagUseCases {
    static final String API_PATH = "/spring-ai-rag";

    private final ChatClient chatClient;

    @PostMapping
    String chat(@RequestBody String message) {
        final var requestSpec = chatClient.prompt(new Prompt(
                "",
                OllamaChatOptions.builder()
                        .model(OllamaModel.LLAMA3_1)
                        .temperature(0.2)
                        .build()
        ));

        return Optional.ofNullable(requestSpec.user(message)
                        .call()
                        .chatResponse())
                .map(response -> response.getResult()
                        .getOutput()
                        .getText())
                .orElse("LLM antwortet nicht.");
//        throw new UnsupportedOperationException("Not supported yet.");
    }

}

