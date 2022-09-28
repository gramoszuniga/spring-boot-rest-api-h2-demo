package com.einfari.springbootrestapih2demo.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-09-28
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Game {

    private Long id;
    private String metaScore;
    private String title;
    private String platform;
    private String date;
    private String userScore;
    private String link;
    private String esrbRating;

}