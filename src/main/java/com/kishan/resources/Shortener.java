package com.kishan.resources;


import com.kishan.core.UrlShortener;
import com.kishan.core.engines.EngineType;
import io.swagger.v3.core.util.Json;
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

    private final UrlShortener urlShortener;

    @POST
    @Path("/")
    public Response shorten(@NonNull @QueryParam("url") final String url, @QueryParam("alias") final String alias, @QueryParam("type") @DefaultValue("HASHING") final EngineType type) throws Exception {
        try {
            // allow the user to choose an alias
            String shortenUrl = urlShortener.shorten(url, alias, type);
            return Response.ok(Json.pretty(shortenUrl)).build();
        } catch (Exception e) {
            log.error("Unable to shorten your URL: ", e);
            throw e;
        }

    }
}
