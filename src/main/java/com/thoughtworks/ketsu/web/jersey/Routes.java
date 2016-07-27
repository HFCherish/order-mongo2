package com.thoughtworks.ketsu.web.jersey;

import javax.ws.rs.core.UriInfo;

public class Routes {

    private final String baseUri;

    public Routes(UriInfo uriInfo) {
        baseUri = uriInfo.getBaseUri().toASCIIString();
    }

}
