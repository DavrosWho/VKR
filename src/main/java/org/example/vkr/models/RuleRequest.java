package org.example.vkr.models;

import lombok.Data;
import lombok.Getter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Set;

@Data
public class RuleRequest {
    int type;
    Boolean isDone;
}
