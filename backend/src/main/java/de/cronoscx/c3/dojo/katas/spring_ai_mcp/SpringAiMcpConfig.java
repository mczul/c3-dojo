package de.cronoscx.c3.dojo.katas.spring_ai_mcp;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
class SpringAiMcpConfig {
    static final String QUALIFIER_AI_MCP = "ai_mcp";

//    private final ToolCallbackProvider toolCallbackProvider;

//    @Bean
//    @Qualifier(SpringAiMcpConfig.QUALIFIER_AI_MCP)
//    ChatClient mcpChatClient(ChatClient.Builder builder) {
//        return builder.defaultSystem("""
//                        Du bist ein freundlicher Chatbot, der Fragen von Kunden des Unternehmens "Mustermann GmbH & Co KG"
//                        beantwortet.
//                        """
//                )
//                .defaultToolCallbacks(toolCallbackProvider)
//                .build();
//    }


}
