package com.marina.comptaApi.auth;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateProfilRequest {


    private String firstName;


    private String lastName;


    private String email;


    private String telephone;
}
