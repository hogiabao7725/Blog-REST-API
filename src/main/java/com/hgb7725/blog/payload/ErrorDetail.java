package com.hgb7725.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorDetail {
    private Date timestamp;
    private String message;
    private List<String> details;
    private String path;
}
