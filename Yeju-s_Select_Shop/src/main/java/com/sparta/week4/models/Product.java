package com.sparta.week4.models;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.NoArgsConstructor;
//여기는 db 테이블의 틀을 짜둔 곳
@Getter //아래 컬럼 속성들이 private이기 때문에 getter가 필요
@NoArgsConstructor //기본생성자 자동생성
@Entity//테이블
public class Product extends Timestamped{

    //Id자동생성, 및 자동증가
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String image;

    @Column(nullable = false)
    private String link;

    @Column(nullable = false)
    private int lprice;

    @Column(nullable = false)
    private int myprice;

    public Product(ProductRequestDto requestDto){ // productrequestdto를 통해 가져온 정보들을 받아서 생성자를 통해 정보 초기화 또는 갱신
        this.title = requestDto.getTitle();
        this.link = requestDto.getLink();
        this.image = requestDto.getImage();
        this.lprice = requestDto.getLprice();
        this.myprice = 0; //사용자가 최저가 가격을 설정 안했으면 최저가를 0으로 두면 상품들이 다 나올 것(최저가 딱지 안붙음)/ myprive도 nullabe이 false니까 0으로 넣어줌
    }


    // 얘가 있어야만 db정보가 업데이트 됨
    public void update(ProductMypriceRequestDto requestDto)
    {
        System.out.println("myprice: "+requestDto.getMyprice());
        this.myprice = requestDto.getMyprice();
    }


    public void updateByItemDto(ItemDto itemDto)
    {
        this.lprice = itemDto.getLprice();
    }


}
