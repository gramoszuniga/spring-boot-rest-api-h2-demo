package com.einfari.springbootrestapih2demo.common.error;

/**
 * @author : Gonzalo Ramos Zúñiga
 * @since : 2022-09-30
 **/
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

}