package com.example.autoverifier;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BothRequests {
    Request legacy;
    Request latest;
}
