package com.sparta.week4.service;

import com.sparta.week4.models.ItemDto;
import com.sparta.week4.models.Product;
import com.sparta.week4.models.ProductMypriceRequestDto;
import com.sparta.week4.models.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service //서비스라고 알려주는 어노테이션
public class ProductService {

    private final ProductRepository productRepository; //얘는 service에 항상 필요하므로 private final로 선언

    @Transactional
    public Long update(Long id, ProductMypriceRequestDto requestDto)
    {

        //일단 db에서 id를 찾으려면 repository를 사용함
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NullPointerException("아이디가 존재하지 않습니다")
        );

        //정보를 업데이트 해주는 과정
        product.update(requestDto);
        return id;
    }

    @Transactional
    public Long updateBySearch(Long id, ItemDto itemDto){
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        product.updateByItemDto(itemDto);
        return id;
    }
}
