package jpabook.jpashop.api;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateMemberRequest {
    /**
     * DTO 사용 시
     * 1. API Spec에 맞게 유효성 검사 가능
     * 2. API에서 어떤 값이 넘어오는지 직관적으로 확인 가능
     * */
    @NotEmpty
    private String name;
}
