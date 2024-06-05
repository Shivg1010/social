package com.social.post_service.entity.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class JpaUserDto {
    private String uniqueName;
    private String email;
    private boolean privateAccount = false;
}
