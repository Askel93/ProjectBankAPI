package com.example.project.bank.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSystemIdDTO {
    @NotNull(message = "property 'id' should be not null")
    private Integer id;
}
