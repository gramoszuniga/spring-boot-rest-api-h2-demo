package com.einfari.springbootrestapih2demo.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-09-25
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "game")
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String meta_score;
    private String title;
    private String platform;
    private String date;
    private String user_score;
    private String link;
    private String esrb_rating;

}