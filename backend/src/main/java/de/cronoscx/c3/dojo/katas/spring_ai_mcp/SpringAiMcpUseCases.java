package de.cronoscx.c3.dojo.katas.spring_ai_mcp;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AbstractMessage;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(SpringAiMcpUseCases.API_PATH)
class SpringAiMcpUseCases {
    static final String API_PATH = "/spring-ai-mcp";

    private final ChatClient chatClient;

    SpringAiMcpUseCases(@Qualifier(SpringAiMcpConfig.QUALIFIER_AI_MCP) ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping
    String chat(@RequestBody String message) {
        final var requestSpec = chatClient.prompt(new Prompt(
                List.of(), /* */
                OllamaChatOptions.builder()
                        .temperature(0.2)
                        .build()
        ));

        return Optional.ofNullable(requestSpec
                        .user(message)
                        .call()
                        .chatResponse())
                .flatMap(chatResponse -> Optional.ofNullable(chatResponse.getResult()))
                .map(Generation::getOutput)
                .map(AbstractMessage::getText)
                .orElse("No response from LLM.");
    }

}
