package com.sparta.week4.utils;

import com.sparta.week4.models.ItemDto;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
//컨트롤러를 통해 클라이언트와 정보를 받고 띄워주는 애

@Component //스프링이 네이버 api에 대한 권한을 획득하도록 하는 어노테이션
public class NaverShopSearch {
    public String search(String query){
    //arc에서 copy한 코드
        RestTemplate rest = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Naver-Client-Id", "Q5hOLkZXZGOx4xsbqNdG");
        headers.add("X-Naver-Client-Secret", "BHwgfqppON");
        String body = "";

        HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://openapi.naver.com/v1/search/shop.json?query="+query, HttpMethod.GET, requestEntity, String.class);
        HttpStatus httpStatus = (HttpStatus) responseEntity.getStatusCode();
        int status = httpStatus.value(); //잘 받아 왔으면 200이 들어갈 것(표준)
        String response = responseEntity.getBody();
        System.out.println("Response status: " + status);//200 or 404
        System.out.println(response);//받아온 결과에 대한 전반적인 정보

        return response;
    }

    public List<ItemDto> fromJSONtoItems(String result)
    {
        JSONObject rjson = new JSONObject(result); //문자열을 jsonob로 만들어주는 것
        //System.out.println(rjson); //실행 했을 때 items 안에 검색 결과가 배열(리스트) 형태로 쭉 들어 가 있음
        JSONArray items = rjson.getJSONArray("items");//items 배열(리스트)을 꺼내겠다
        System.out.println(items);

        List<ItemDto> itemDtoList = new ArrayList<>();
        for(int i = 0; i <items.length(); i++)//json에서는 .length 를 사용
        {
            JSONObject itemJson = items.getJSONObject(i); //json 오브젝트가 모여 json리스트가 되는거

            ItemDto itemDto = new ItemDto(itemJson);
            //itemDto를 리스트에다 하나씩 넣어줌
            itemDtoList.add(itemDto);
            //리스트를 컨트롤러에서 r반환

        }
        return itemDtoList;
    }

    //public static void main(String[] args)
    //{
        //NaverShopSearch naverShopSearch = new NaverShopSearch();
        //String result = naverShopSearch.search("아이맥"); // 이 search 안에 쿼리가 들어감
        //JSONObject rjson = new JSONObject(result); //문자열을 jsonob로 만들어주는 것
        //System.out.println(rjson); //실행 했을 때 items 안에 검색 결과가 배열(리스트) 형태로 쭉 들어 가 있음
        //JSONArray items = rjson.getJSONArray("items");//items 배열(리스트)을 꺼내겠다
        //System.out.println(items);

        //List<ItemDto> itemDtoList = new ArrayList<>();
        //for(int i = 0; i <items.length(); i++)//json에서는 .length 를 사용
        //{
        //    JSONObject itemJson = items.getJSONObject(i); //json 오브젝트가 모여 json리스트가 되는거

        //    ItemDto itemDto = new ItemDto(itemJson);
            //itemDto를 리스트에다 하나씩 넣어줌
        //    itemDtoList.add(itemDto);
            //리스트를 컨트롤러에서 r반환
        //}

           // System.out.println(itemJson);
           // String title = itemJson.getString("title");
           // String image = itemJson.getString("image");
           // int lprice = itemJson.getInt("lprice");
           // String link = itemJson.getString("link");

            //System.out.println(lprice);

    //}
}
