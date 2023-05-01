package com.sparta.week4.models;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter //get 함수를 자동으로 생성
@MappedSuperclass //멤버 변수가 컬럼이 되도록 함, 생성시간 또는 변경시간을 이 타임스탬프를 상속한 클래스의 컬럼으로 만들어주는 역할
@EntityListeners(AuditingEntityListener.class)//변경되었을 때 자동으로 기록함
public abstract class Timestamped { //abstract가 들어가는 이유: 얘는 상속이 되어야만 사용이 가능함을 명시적으로 알려주기 위해 사용
    @CreatedDate //최초 생성 시점
    private LocalDateTime createdAt;

    @LastModifiedDate //마지막으로 변경된 시점
    private LocalDateTime modifiedAt;
}
