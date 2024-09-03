package com.vicheak.app.course.service.spec;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Objects;

@Builder
@RequiredArgsConstructor
public class CourseSpec {

    private final CourseFilter courseFilter;

    public Query buildPredicate() {
        Query query = new Query();

        if (Objects.nonNull(courseFilter.title())) {
            query.addCriteria(Criteria.where("title").regex(".*%s.*".formatted(courseFilter.title())));
        }

        if (Objects.nonNull(courseFilter.durationInHour())) {
            query.addCriteria(Criteria.where("durationInHour").is(courseFilter.durationInHour()));
        }

        if (Objects.nonNull(courseFilter.cost())) {
            query.addCriteria(Criteria.where("cost").is(courseFilter.cost()));
        }

        return query;
    }

}