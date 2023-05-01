package com.sparta.week4.models;
//클라이언트에게 json 데이터 나르는 애

import lombok.Getter;
import org.json.JSONObject;

@Getter //setter가 필요 없는 이유 생성자를 통해 정보 셋팅하기 떄문
public class ItemDto {

    private String title;
    private String link;
    private String image;
    private int lprice;

    public ItemDto(JSONObject itemJson){
        this.title = itemJson.getString("title");
        this.image = itemJson.getString("image");
        this.link = itemJson.getString("link");
        this.lprice = itemJson.getInt("lprice");
    }
}
