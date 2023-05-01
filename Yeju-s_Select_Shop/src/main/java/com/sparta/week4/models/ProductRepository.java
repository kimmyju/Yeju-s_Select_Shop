//생성, 조회, 삭제를 담당하는 애
package com.sparta.week4.models;

import org.springframework.data.jpa.repository.JpaRepository;
//데이터베이스
public interface ProductRepository extends JpaRepository<Product, Long> {
}
