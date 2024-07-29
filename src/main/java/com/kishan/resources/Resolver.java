package com.kishan.resources;


import com.kishan.core.UrlShortener;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

@Path("/")
@Tag(name = "URL Resolver APIs", description = "Use these APIs to resolve short URLs to long URLs")
@Produces("application/json")
@Consumes("application/json")
@AllArgsConstructor(onConstructor = @__(@Inject))
@Slf4j
public class Resolver {

  private final UrlShortener urlShortener;
  @GET
  @Path("/{shortUrl}")
  public Response resolve(@NonNull @PathParam("shortUrl") final String shortUrl) throws Exception {
    try {
      // resolve the urls
      String longUrl = urlShortener.resolver(shortUrl);
      return Response.temporaryRedirect(URI.create(longUrl)).status(307).build();
    } catch (Exception e) {
      log.error("Unable to Resolve your URL: ", e);
      throw e;
    }

  }
}
