package jpabook.jpashop.api;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateMemberRequest {

    @NotEmpty
    private String name;

}
