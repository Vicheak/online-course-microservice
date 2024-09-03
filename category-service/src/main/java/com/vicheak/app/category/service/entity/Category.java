package com.vicheak.app.category.service.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "categories")
public class Category {

    @MongoId
    //@Field("category_id")
    private Integer id;

    @Field("category_name")
    private String name;

}
