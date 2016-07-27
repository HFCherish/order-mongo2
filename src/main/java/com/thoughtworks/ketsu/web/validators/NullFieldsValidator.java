package com.thoughtworks.ketsu.web.validators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NullFieldsValidator {

    public Map<String, List<Map>> getNullFields(List<String> toValidates, Map<String, Object> info) {
        List nullFields = new ArrayList<>();

        for(String toValidate: toValidates) {
            if(info.get(toValidate) == null) {
                nullFields.add(new HashMap(){{
                    put("field", toValidate);
                    put("message", toValidate + " cannot be empty.");
                }});
            }
        }

        if(nullFields.size() == 0) return null;
        return new HashMap(){{
            put("items", nullFields);
        }};
    }
}
