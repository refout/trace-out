package com.refout.trace.datasource.domain.page;

import org.springframework.data.domain.Sort;

public record OrderBy(Sort.Direction direction, String property) {

}
