package com.sparta.week4.controller;

import com.sparta.week4.models.Product;
import com.sparta.week4.models.ProductMypriceRequestDto;
import com.sparta.week4.models.ProductRepository;
import com.sparta.week4.models.ProductRequestDto;
import com.sparta.week4.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController //json으로 응답하는 자동 응답기
public class ProductRestController {

    private final ProductRepository productRepository; //product repository가 뭔지 모르므로 멤버변수로 선언해줘야 함, 내가 원할 떄마다 자동생성이 되어야 하고  그리고 그걸 위해서는 restcontroller에게 꼭 필요함을 private final로 알려줌
    private final ProductService productService;
    //조회
    @GetMapping("/api/products") //여기로 요청이 오면 아래 메소드를 실행을 해라
    public List<Product> readProduct(){ //product의 목록을 반환
        return productRepository.findAll(); //찾는 거는 p-repository에 의존하고 있으므로

    }

    //신규 상품 생성
    @PostMapping("/api/products")
    public Product createProduct(@RequestBody ProductRequestDto requestDto){//@RequestBody가 있어야 전달받는  body에 있는 정보가 잘 들어감
        Product product = new Product(requestDto);
        return productRepository.save(product);
    }
    @PutMapping("/api/products/{id}")
    public Long updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto)
    {
        return productService.update(id, requestDto);
    }
}
