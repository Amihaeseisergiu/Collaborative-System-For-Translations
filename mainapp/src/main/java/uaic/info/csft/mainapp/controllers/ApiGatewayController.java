package uaic.info.csft.mainapp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uaic.info.csft.mainapp.entities.Language;
import uaic.info.csft.mainapp.services.ApiGatewayService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/")
@AllArgsConstructor
public class ApiGatewayController {

    private final ApiGatewayService apiGatewayService;

    @GetMapping("users/{id}/languages")
    public List<Language> getUserLanguages(@PathVariable @Valid @Min(0) UUID id)
    {
        return apiGatewayService.getUserLanguages(id);
    }

    @GetMapping("test")
    public String test()
    {
        return apiGatewayService.test();
    }

    @PostMapping("users/{id}/languages")
    public void addUserLanguage(@PathVariable @Valid @Min(0) UUID id, @RequestBody Language language)
    {
        apiGatewayService.addUserLanguage(id, language);
    }
}
