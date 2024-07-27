package com.kishan.resources;


import com.kishan.core.UrlShortenerEngine;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Path("/resolver")
@Tag(name = "URL Resolver APIs", description = "Use these APIs to resolve short URLs to long URLs")
@Produces("application/json")
@Consumes("application/json")
@AllArgsConstructor(onConstructor = @__(@Inject))
@Slf4j
public class Resolver {

    private final UrlShortenerEngine urlShortenerEngine;
    @POST
    @Path("/")
    public Response shorten(@NonNull @QueryParam("url") final String url) throws Exception {
        try {
            // resolve the urls
            String longUrl = urlShortenerEngine.resolver(url);
            return Response.temporaryRedirect(URI.create(longUrl)).status(307).build();
        } catch (Exception e) {
            log.error("Unable to shorten your URL: ", e);
            throw e;
        }

    }
}
