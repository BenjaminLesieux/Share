package com.sharemedia.share;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Provider
@ApplicationPath("/api")
public class HelloApplication extends Application {

}