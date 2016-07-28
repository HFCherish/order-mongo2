package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.users.Order;
import com.thoughtworks.ketsu.domain.users.Payment;
import com.thoughtworks.ketsu.web.jersey.Routes;
import com.thoughtworks.ketsu.web.validators.NullFieldsValidator;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PaymentApi {
    private Order order;

    public PaymentApi(Order order) {
        this.order = order;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response pay(Map<String, Object> info,
                        @Context Routes routes) {
        Map<String, List> nullFields = new NullFieldsValidator().getNullFields(Arrays.asList("pay_type", "amount"), info);
        if(nullFields != null ) {
            return Response.status(Response.Status.BAD_REQUEST).entity(nullFields).build();
        }
        order.pay(info);
        return Response.created(routes.paymentUrl(order.getUserId(), order.getId())).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Payment getPayment() {
        return order.getPayment()
                .map(payment -> payment)
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }
}
