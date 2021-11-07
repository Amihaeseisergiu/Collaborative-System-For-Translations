package uaic.info.csft.mainapp.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("translation-service")
public interface TranslationServiceClient {

    @GetMapping("api/v1/translate/test")
    String test();
}
