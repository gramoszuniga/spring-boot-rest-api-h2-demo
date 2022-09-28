package com.einfari.springbootrestapih2demo.resource.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-09-28
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameRequest {

    private String metaScore;
    @NotNull(message = "Title is required.")
    @NotBlank(message = "Title cannot be empty.")
    private String title;
    @NotNull(message = "Platform is required.")
    @NotBlank(message = "Platform cannot be empty.")
    private String platform;
    @NotNull(message = "Date is required.")
    @NotBlank(message = "Date cannot be empty.")
    private String date;
    private String userScore;
    @NotNull(message = "Link is required.")
    @NotBlank(message = "Link cannot be empty.")
    private String link;
    private String esrbRating;

}