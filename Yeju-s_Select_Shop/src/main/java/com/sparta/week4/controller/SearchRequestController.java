package com.sparta.week4.controller;

import com.sparta.week4.models.ItemDto;
import com.sparta.week4.utils.NaverShopSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor //컴퍼논트 등록이 안되어 있으면 불가능한데, 이거 하게 해주려고 우리가 아까 @component 해준거
@RestController //json응답 선언
public class SearchRequestController {
    private final NaverShopSearch naverShopSearch;

        @GetMapping("/api/search") //요청을 받음
        public List<ItemDto> execSearch(@RequestParam String query)//물음표 뒤에 오는 쿼리를 변수로 받고 싶으면 @requestparam을 써줘야 함)
         {
            String result = naverShopSearch.search(query);

            return naverShopSearch.fromJSONtoItems(result);//listItemDto로 바꿔주는 작업
        }

}
