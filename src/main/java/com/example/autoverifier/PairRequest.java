package com.example.autoverifier;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PairRequest {
    Request legacy;
    Request latest;
}
