//일정한 시간마다 상품의 가격이 변경된게 있으면 업데이트 해주는 애

package com.sparta.week4.utils;

import com.sparta.week4.models.ItemDto;
import com.sparta.week4.models.Product;
import com.sparta.week4.models.ProductRepository;
import com.sparta.week4.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;


@RequiredArgsConstructor
@Component
public class Scheduler {
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final NaverShopSearch naverShopSearch;

    //초 분 시 일 월 주 순서 , *은 뭐든지 상관 없다 이 말 ex) ******이렇게 작성하면 매초 하겠다는 의미
    @Scheduled(cron="0 0 1 * * *") //cron에 할당된 시간과 딱 일치 할때 아래 메소드를 실행하겠다
    public void updatePrice() throws InterruptedException {
        System.out.println("가격 업데이트 실행");


        //저장된 모든 관심상품들을 조회
        List<Product> productList = productRepository.findAll();
        for (int i = 0; i < productList.size(); i++) {
            TimeUnit.SECONDS.sleep(1); //1초 단위로 1초동안 쉬어라, 쓰는 이유?: 코드 자체에는 문제가 없지만 너무 자주 요청이 오면 네이버에서막아버림 그래서 넣어준거
            //i번째 관심 상품을 꺼냅니다
            Product p = productList.get(i);
            //i번째 관심상품의 제목으로 검색을 실행합니다
            String title = p.getTitle();
            String resultString = naverShopSearch.search(title);
            //i번째 관심상품의 검색 결과 목록중에서 첫 번째 결과를 꺼냅니다.
            List<ItemDto> itemDtoList = naverShopSearch.fromJSONtoItems(resultString);
            ItemDto itemDto = itemDtoList.get(0);
            //i번째 관심 상품 정보를 업데이트 합니다
            Long id = p.getId();
            productService.updateBySearch(id,itemDto);
        }
    }
}
