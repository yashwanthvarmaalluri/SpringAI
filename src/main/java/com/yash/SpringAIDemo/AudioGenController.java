package com.yash.SpringAIDemo;


import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.openai.OpenAiAudioSpeechModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AudioGenController {
    private OpenAiAudioTranscriptionModel audioModel;

    private OpenAiAudioSpeechModel speechModel;

    public AudioGenController(OpenAiAudioTranscriptionModel audioModel, OpenAiAudioSpeechModel speechModel) {
        this.audioModel = audioModel;
        this.speechModel = speechModel;
    }

    @PostMapping("api/stt")
    public String speechToText(@RequestParam MultipartFile file) {

        //return audioModel.call(file.getResource()); This one does the job , but to get the timestamps we need to write the below code

        OpenAiAudioTranscriptionOptions options = OpenAiAudioTranscriptionOptions.builder()
                .responseFormat(OpenAiAudioApi.TranscriptResponseFormat.SRT)
                .build();

        AudioTranscriptionPrompt prompt = new AudioTranscriptionPrompt(file.getResource(), options);

        return audioModel.call(prompt).getResult().getOutput();

    }

    @PostMapping("api/tss")
    public byte[] textToSpeech(@RequestParam String text) {
        return speechModel.call(text);
    }
}
