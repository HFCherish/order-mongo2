package com.thoughtworks.ketsu.web.validators;

import java.util.*;

public class OrderValidator extends NullFieldsValidator {
    public Map<String, List<Map>> getNullFields(Map<String, Object> info) {
        return super.getNullFields(Arrays.asList("name","address","phone"), info);
    }
}
