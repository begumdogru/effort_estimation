package com.example.demo.service;

import com.example.demo.config.GeminiConfig;
import com.example.demo.model.ProjectModel;
import com.example.demo.model.Task;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AiBotService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private GeminiConfig geminiConfig;

    public String getEstimation(ProjectModel projectModel, List<ProjectModel> projects) {
        try{
            String prompt = buildPrompt(projectModel, projects);

            Map<String, Object> promptPart = new HashMap<>();
            promptPart.put("text", prompt);

            Map<String, Object> part = new HashMap<>();
            part.put("parts", new Object[]{promptPart});

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("contents", new Object[]{part});

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    geminiConfig.getApiKeyUrl(),
                    HttpMethod.POST,
                    request,
                    String.class
            );

            JsonNode root =  objectMapper.readTree(response.getBody());
            String output = root
                    .path("candidates").get(0)
                    .path("content")
                    .path("parts").get(0)
                    .path("text").asText();

            return output;


        }catch (Exception e){
            e.printStackTrace();
            return "Failed to estimate effort: " + e.getMessage();        }
    }

    private String buildPrompt(ProjectModel dto, List<ProjectModel> projects) {
        StringBuilder taskDescriptions = new StringBuilder();
        if (dto.getTasks() != null && !dto.getTasks().isEmpty()) {
            taskDescriptions.append("Tasks:\n");
            for (Task task : dto.getTasks()) {
                taskDescriptions.append(String.format(
                        "- Title: %s, Description: %s, Estimated Hour: %.2f, Actual Hour: %.2f\n",
                        task.getTaskName(),
                        task.getDescription(),
                        task.getEstimatedEffort(),
                        task.getActualEffort()
                ));
            }
        } else {
            taskDescriptions.append("No tasks provided.\n");
        }

        return String.format("""
        Please predict the effort for the project with the following details:
        Project Name: %s
        Story Points: %d
        Category: %s
        Complexity: %.1f
        Uncertainty: %.1f
        Risk Management: %s
        Technology: %s
        Past Projects: %s
        %s
        Based on the above details and the past experience of similar projects,
        Estimate the effort in hours. 
        """,
                dto.getProjectName(),
                dto.getStoryPoint(),
                dto.getCategory(),
                dto.getComplexity(),
                dto.getUncertainty(),
                dto.getRiskManagement(),
                dto.getTechnology(),
                projects.stream()
                        .map(ProjectModel::getProjectName)
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("No past projects available."),
                taskDescriptions
        );
    }
}
