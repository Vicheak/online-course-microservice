package com.vicheak.app.course.service.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "courses")
public class Course {

    @MongoId
    private Long id;

    @Field("course_uuid")
    private String uuid;

    @Field("course_title")
    private String title;

    @Field("course_description")
    private String description;

    @Field("course_image")
    private String image;

    @Field("course_duration_in_hour")
    private Integer durationInHour;

    @Field("course_cost")
    private BigDecimal cost;

    @Field("course_number_of_view")
    private Long numberOfView;

    @Field("course_number_of_like")
    private Long numberOfLike;

    @Field("course_created_at")
    private LocalDateTime createdAt;

    @Field("course_updated_at")
    private LocalDateTime updatedAt;

    @Field("category_id")
    private Integer categoryId;

}