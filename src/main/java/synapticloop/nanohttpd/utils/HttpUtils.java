package synapticloop.nanohttpd.utils;

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


import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response.IStatus;

public class HttpUtils {

	private static final Logger LOGGER = Logger.getLogger(HttpUtils.class.getName());

	public static String cleanUri(String uri) {
		if(null == uri) {
			return(null);
		}

		return(uri.replaceAll("/\\.\\./", "/").replaceAll("//", "/"));
	}

	private static Response defaultTextResponse(IStatus status) { return(newFixedLengthResponse(status, NanoHTTPD.MIME_PLAINTEXT, status.getDescription())); }
	private static Response defaultTextResponse(IStatus status, String content) { return(newFixedLengthResponse(status, NanoHTTPD.MIME_PLAINTEXT, content)); }

	public static Response okResponse() { return(defaultTextResponse(Response.Status.OK)); }
	public static Response okResponse(String content) { return(defaultTextResponse(Response.Status.OK, content)); }
	public static Response okResponse(String mimeType, String content) { return(newFixedLengthResponse(Response.Status.OK, mimeType, content)); }
	public static Response okResponse(String mimeType, InputStream content, long totalBytes) { return(newFixedLengthResponse(Response.Status.OK, mimeType, content, totalBytes)); }

	public static Response notFoundResponse() { return(defaultTextResponse(Response.Status.NOT_FOUND)); }
	public static Response notFoundResponse(String content) { return(defaultTextResponse(Response.Status.NOT_FOUND, content)); }
	public static Response notFoundResponse(String mimeType, String content) { return(newFixedLengthResponse(Response.Status.NOT_FOUND, mimeType, content)); }
	public static Response notFoundResponse(String mimeType, InputStream content, long totalBytes) { return(newFixedLengthResponse(Response.Status.NOT_FOUND, mimeType, content, totalBytes)); }

	public static Response rangeNotSatisfiableResponse() { return(defaultTextResponse(Response.Status.RANGE_NOT_SATISFIABLE)); }
	public static Response rangeNotSatisfiableResponse(String content) { return(defaultTextResponse(Response.Status.RANGE_NOT_SATISFIABLE, content)); }
	public static Response rangeNotSatisfiableResponse(String mimeType, String content) { return(newFixedLengthResponse(Response.Status.RANGE_NOT_SATISFIABLE, mimeType, content)); }
	public static Response rangeNotSatisfiableResponse(String mimeType, InputStream content, long totalBytes) { return(newFixedLengthResponse(Response.Status.RANGE_NOT_SATISFIABLE, mimeType, content, totalBytes)); }

	public static Response methodNotAllowedResponse() { return(defaultTextResponse(Response.Status.METHOD_NOT_ALLOWED)); }
	public static Response methodNotAllowedResponse(String content) { return(defaultTextResponse(Response.Status.METHOD_NOT_ALLOWED, content)); }
	public static Response methodNotAllowedResponse(String mimeType, String content) { return(newFixedLengthResponse(Response.Status.METHOD_NOT_ALLOWED, mimeType, content)); }
	public static Response methodNotAllowedResponse(String mimeType, InputStream content, long totalBytes) { return(newFixedLengthResponse(Response.Status.METHOD_NOT_ALLOWED, mimeType, content, totalBytes)); }

	public static Response internalServerErrorResponse() { return(defaultTextResponse(Response.Status.INTERNAL_ERROR)); }
	public static Response internalServerErrorResponse(String content) { return(defaultTextResponse(Response.Status.INTERNAL_ERROR, content)); }
	public static Response internalServerErrorResponse(String mimeType, String content) { return(newFixedLengthResponse(Response.Status.INTERNAL_ERROR, mimeType, content)); }
	public static Response internalServerErrorResponse(String mimeType, InputStream content, long totalBytes) { return(newFixedLengthResponse(Response.Status.INTERNAL_ERROR, mimeType, content, totalBytes)); }

	public static Response forbiddenResponse() { return(defaultTextResponse(Response.Status.FORBIDDEN)); }
	public static Response forbiddenResponse(String content) { return(defaultTextResponse(Response.Status.FORBIDDEN, content)); }
	public static Response forbiddenResponse(String mimeType, String content) { return(newFixedLengthResponse(Response.Status.FORBIDDEN, mimeType, content)); }
	public static Response forbiddenResponse(String mimeType, InputStream content, long totalBytes) { return(newFixedLengthResponse(Response.Status.FORBIDDEN, mimeType, content, totalBytes)); }

	public static Response badRequestResponse() { return(defaultTextResponse(Response.Status.BAD_REQUEST)); }
	public static Response badRequestResponse(String content) { return(defaultTextResponse(Response.Status.BAD_REQUEST, content)); }
	public static Response badRequestResponse(String mimeType, String content) { return(newFixedLengthResponse(Response.Status.BAD_REQUEST, mimeType, content)); }
	public static Response badRequestResponse(String mimeType, InputStream content, long totalBytes) { return(newFixedLengthResponse(Response.Status.BAD_REQUEST, mimeType, content, totalBytes)); }

	public static Response notModifiedResponse() { return(defaultTextResponse(Response.Status.NOT_MODIFIED)); }
	public static Response notModifiedResponse(String content) { return(defaultTextResponse(Response.Status.NOT_MODIFIED, content)); }
	public static Response notModifiedResponse(String mimeType, String content) { return(newFixedLengthResponse(Response.Status.NOT_MODIFIED, mimeType, content)); }
	public static Response notModifiedResponse(String mimeType, InputStream content, long totalBytes) { return(newFixedLengthResponse(Response.Status.NOT_MODIFIED, mimeType, content, totalBytes)); }

	public static Response partialContentResponse() { return(defaultTextResponse(Response.Status.PARTIAL_CONTENT)); }
	public static Response partialContentResponse(String content) { return(defaultTextResponse(Response.Status.PARTIAL_CONTENT, content)); }
	public static Response partialContentResponse(String mimeType, String content) { return(newFixedLengthResponse(Response.Status.PARTIAL_CONTENT, mimeType, content)); }
	public static Response partialContentResponse(String mimeType, InputStream content, long totalBytes) { return(newFixedLengthResponse(Response.Status.PARTIAL_CONTENT, mimeType, content, totalBytes)); }

	public static Response redirectResponse(String uri) { return(redirectResponse(uri, "<html><body>Redirected: <a href=\"" + uri + "\">" + uri + "</a></body></html>")); }
	public static Response redirectResponse(String uri, String message) {
		Response res = newFixedLengthResponse(Response.Status.REDIRECT, NanoHTTPD.MIME_HTML, message);
		res.addHeader("Location", uri);
		return(res);
	}

	public static Response newFixedLengthResponse(IStatus status, String mimeType, InputStream data, long totalBytes) {
		return new Response(status, mimeType, data, totalBytes);
	}

	/**
	 * Create a text response with known length.
	 * 
	 * @param status the HTTP status
	 * @param mimeType the mime type of the response
	 * @param response the response message 
	 * 
	 * @return The fixed length response object
	 */
	public static Response newFixedLengthResponse(IStatus status, String mimeType, String response) {
		if (response == null) {
			return newFixedLengthResponse(status, mimeType, new ByteArrayInputStream(new byte[0]), 0);
		} else {
			byte[] bytes;
			try {
				bytes = response.getBytes("UTF-8");
			} catch (UnsupportedEncodingException ueex) {
				LOGGER.log(Level.SEVERE, "Encoding problem, responding nothing", ueex);
				bytes = new byte[0];
			}
			return newFixedLengthResponse(status, mimeType, new ByteArrayInputStream(bytes), bytes.length);
		}
	}

	public static String getMimeType(String uri) {
		int lastIndexOf = uri.lastIndexOf(".");
		String extension = uri.substring(lastIndexOf + 1);

		String mimeType = NanoHTTPD.MIME_HTML;

		if(MimeTypeMapper.getMimeTypes().containsKey(extension)) {
			mimeType = MimeTypeMapper.getMimeTypes().get(extension);
		}
		return(mimeType);
	}
}
