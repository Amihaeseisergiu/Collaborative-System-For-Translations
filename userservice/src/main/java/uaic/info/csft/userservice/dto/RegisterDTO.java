package uaic.info.csft.userservice.dto;

import lombok.Data;
import uaic.info.csft.userservice.entities.Language;

import java.util.List;

@Data
public class RegisterDTO {

    String username;
    String password;
    List<Language> languages;
}
