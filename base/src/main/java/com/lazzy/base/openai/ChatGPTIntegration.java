package com.lazzy.base.openai;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

/**
 * open ai 三方库集成demo
 */
public class ChatGPTIntegration {
    public static void main(String[] args) {
       new ChatGPTIntegration().chat("中医与西医相比有什么区别");
    }
    public void chat(String question) {
        OpenAiService service = new OpenAiService("openai key");
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt("Somebody once told me the world is gonna roll me")
                .model("gpt-3.5-turbo")
                .echo(true)
                .build();
        service.createCompletion(completionRequest).getChoices().forEach(System.out::println);
    }

}