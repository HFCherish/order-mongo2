package com.thoughtworks.ketsu.web.validators;

import com.thoughtworks.ketsu.domain.products.ProductRepository;

import javax.inject.Inject;
import java.util.*;

public class OrderValidator extends NullFieldsValidator {
    @Inject
    ProductRepository productRepository;

    public Map<String, List> getNullFields(Map<String, Object> info) {
        Map<String, List> nameNullFields = super.getNullFields(Arrays.asList("name", "address", "phone", "order_items"), info);
        List<Map<String, String>> nullFields = getErrorItems(nameNullFields);

        if(info.get("order_items") != null) {
            for(Map item: (List<Map>)info.get("order_items")) {
                nullFields.addAll(getErrorItems(super.getNullFields(Arrays.asList("product_id", "quantity"), item)));
                if( item.get("product_id") != null && !productRepository.findById(item.get("product_id").toString()).isPresent()) {
                    nullFields.add(new HashMap(){{
                        put("field", "product_id");
                        put("message", "product_id is invalid");
                    }});
                }

            }
        }

        if(nullFields.size() == 0)  return null;
        return new HashMap(){{
            put("items", nullFields);
        }};
    }

    private List<Map<String, String>> getErrorItems(Map<String, List> errorMap) {
        return errorMap == null ? new ArrayList<>() : errorMap.get("items");
    }
}
