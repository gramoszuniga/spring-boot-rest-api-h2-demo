package com.einfari.springbootrestapih2demo.application.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-09-29
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Games {

    private List<Game> games;

}