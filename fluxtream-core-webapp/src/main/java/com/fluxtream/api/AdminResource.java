package com.fluxtream.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fluxtream.Configuration;
import com.fluxtream.mvc.models.StatusModel;
import com.fluxtream.services.BodyTrackStorageService;
import com.fluxtream.services.GuestService;
import com.google.gson.Gson;

@Path("/admin")
@Component("adminApi")
@Scope("request")
public class AdminResource {

	@Autowired
	GuestService guestService;

	@Autowired
	BodyTrackStorageService bodytrackStorageService;

	Gson gson = new Gson();

	@Autowired
	Configuration env;

	@GET
	@Path("/get/property/{propertyName}")
	@Produces({ MediaType.APPLICATION_JSON })
	public String loadHistory(@PathParam("propertyName") String propertyName)
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException {

		if (env.get(propertyName) != null) {
			JSONObject property = new JSONObject();
			property.accumulate("name", propertyName).accumulate("value",
					env.get(propertyName));
			return property.toString();
		}

		StatusModel failure = new StatusModel(false, "property not found: "
				+ propertyName);
		return gson.toJson(failure);
	}

}