package synapticloop.nanohttpd.router;

/*
 * Copyright (c) 2013-2020 synapticloop.
 * 
 * All rights reserved.
 *
 * This source code and any derived binaries are covered by the terms and
 * conditions of the Licence agreement ("the Licence").  You may not use this
 * source code or any derived binaries except in compliance with the Licence.
 * A copy of the Licence is available in the file named LICENCE shipped with
 * this source code or binaries.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * Licence for the specific language governing permissions and limitations
 * under the Licence.
 */

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD.IHTTPSession;
import fi.iki.elonen.NanoHTTPD.Response;

public abstract class Routable {
	// the route that this routable is bound to
	protected String routeContext = null;
	// the map of option key values
	protected Map<String, String> options = new HashMap<String, String>();

	/**
	 * Create a new routable class with the bound routing context
	 *
	 * @param routeContext the context to route to
	 */
	public Routable(String routeContext) {
		this.routeContext = routeContext;
	}

	/**
	 * Serve the correctly routed file
	 *
	 * @param rootDir The root directory of the RouteMaster server
	 * @param httpSession The session
	 *
	 * @return The response
	 */
	public abstract Response serve(File rootDir, IHTTPSession httpSession);
	
	/**
	 * Set an option for this routable into the options map.
	 * 
	 * @param key The option key
	 * @param value the value
	 */
	public void setOption(String key, String value) {
		options.put(key, value);
	}

	/**
	 * Get an option from the map, or null if it does not exist
	 * 
	 * @param key the key to search for
	 * 
	 * @return the value of the option, or null if it doesn't exist
	 */
	public String getOption(String key) {
		return(options.get(key));
	}
}
