package com.example.autoverifier;

import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpMethod;

@Data
@ToString
public class Request {

    String url;
    String method;
    Object body;

    HttpMethod getMethod() {
        return HttpMethod.resolve(method);
    }

}
