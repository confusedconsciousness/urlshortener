package com.kishan.resources;


import com.kishan.core.KGS;
import com.kishan.core.UrlShortenerEngine;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Path("/shorten")
@Tag(name = "URL Shortener APIs", description = "Use these APIs to shorten your long URLs")
@Produces("application/json")
@Consumes("application/json")
@AllArgsConstructor(onConstructor = @__(@Inject))
@Slf4j
public class Shortener {

    private final UrlShortenerEngine urlShortenerEngine;

    @POST
    @Path("/")
    public Response shorten(@NonNull @QueryParam("url") final String url, @QueryParam("alias") final String alias) throws Exception {
        try {
            // allow the user to choose an alias
            return Response.ok().build();
        } catch (Exception e) {
            log.error("Unable to shorten your URL: ", e);
            throw e;
        }

    }
}
