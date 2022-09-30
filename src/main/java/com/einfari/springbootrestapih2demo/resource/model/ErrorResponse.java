package com.einfari.springbootrestapih2demo.resource.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-09-30
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private String message;

}