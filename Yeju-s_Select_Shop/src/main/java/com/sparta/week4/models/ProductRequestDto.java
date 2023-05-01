package com.sparta.week4.models;

import lombok.Getter;

//title,image,link,lprice
@Getter
public class ProductRequestDto {

    private String title;
    private String link;
    private String image;
    private int lprice;

}
