package synapticloop.nanohttpd.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.Response;
import fi.iki.elonen.NanoHTTPD.Response.IStatus;

public class HttpUtils {
	public static String cleanUri(String uri) {
		return(uri.replaceAll("/\\.\\./", "/"));
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

	//public static Response notFoundResponse(InputStream content) { return(notFoundResponse(NanoHTTPD.MIME_PLAINTEXT, content)); }
//	public static Response notFoundResponse(String mimeType, InputStream content) { return(new Response(Response.Status.NOT_FOUND, mimeType, content)); }

	public static Response rangeNotSatisfiableResponse() { return(defaultTextResponse(Response.Status.RANGE_NOT_SATISFIABLE)); }
	public static Response rangeNotSatisfiableResponse(String content) { return(defaultTextResponse(Response.Status.RANGE_NOT_SATISFIABLE, content)); }
	public static Response rangeNotSatisfiableResponse(String mimeType, String content) { return(newFixedLengthResponse(Response.Status.RANGE_NOT_SATISFIABLE, mimeType, content)); }

	//	public static Response rangeNotSatisfiableResponse(String mimeType, InputStream content) { return(new Response(Response.Status.RANGE_NOT_SATISFIABLE, mimeType, content)); }
//	public static Response rangeNotSatisfiableResponse(InputStream content) { return(rangeNotSatisfiableResponse(NanoHTTPD.MIME_PLAINTEXT, content)); }

	public static Response methodNotAllowedResponse() { return(defaultTextResponse(Response.Status.METHOD_NOT_ALLOWED)); }
	public static Response methodNotAllowedResponse(String content) { return(defaultTextResponse(Response.Status.METHOD_NOT_ALLOWED, content)); }
	public static Response methodNotAllowedResponse(String mimeType, String content) { return(newFixedLengthResponse(Response.Status.METHOD_NOT_ALLOWED, mimeType, content)); }
//	public static Response methodNotAllowedResponse(String mimeType, InputStream content) { return(new Response(Response.Status.METHOD_NOT_ALLOWED, mimeType, content)); }
//	public static Response methodNotAllowedResponse(InputStream content) { return(methodNotAllowedResponse(NanoHTTPD.MIME_PLAINTEXT, content)); }

	public static Response internalServerErrorResponse() { return(defaultTextResponse(Response.Status.INTERNAL_ERROR)); }
	public static Response internalServerErrorResponse(String content) { return(defaultTextResponse(Response.Status.INTERNAL_ERROR, content)); }
	public static Response internalServerErrorResponse(String mimeType, String content) { return(newFixedLengthResponse(Response.Status.INTERNAL_ERROR, mimeType, content)); }
//	public static Response internalServerErrorResponse(String mimeType, InputStream content) { return(new Response(Response.Status.INTERNAL_ERROR, mimeType, content)); }
//	public static Response internalServerErrorResponse(InputStream content) { return(internalServerErrorResponse(NanoHTTPD.MIME_PLAINTEXT, content)); }

	public static Response forbiddenResponse() { return(defaultTextResponse(Response.Status.FORBIDDEN)); }
	public static Response forbiddenResponse(String content) { return(defaultTextResponse(Response.Status.FORBIDDEN, content)); }
	public static Response forbiddenResponse(String mimeType, String content) { return(newFixedLengthResponse(Response.Status.FORBIDDEN, mimeType, content)); }
//	public static Response forbiddenResponse(String mimeType, InputStream content) { return(new Response(Response.Status.FORBIDDEN, mimeType, content)); }
//	public static Response forbiddenResponse(InputStream content) { return(forbiddenResponse(NanoHTTPD.MIME_PLAINTEXT, content)); }

	public static Response notModifiedResponse() { return(defaultTextResponse(Response.Status.NOT_MODIFIED)); }
	public static Response notModifiedResponse(String content) { return(defaultTextResponse(Response.Status.NOT_MODIFIED, content)); }
	public static Response notModifiedResponse(String mimeType, String content) { return(newFixedLengthResponse(Response.Status.NOT_MODIFIED, mimeType, content)); }
//	public static Response notModifiedResponse(String mimeType, InputStream content) { return(new Response(Response.Status.NOT_MODIFIED, mimeType, content)); }
//	public static Response notModifiedResponse(InputStream content) { return(notModifiedResponse(NanoHTTPD.MIME_PLAINTEXT, content)); }

	public static Response partialContentResponse() { return(defaultTextResponse(Response.Status.PARTIAL_CONTENT)); }
	public static Response partialContentResponse(String content) { return(defaultTextResponse(Response.Status.PARTIAL_CONTENT, content)); }
	public static Response partialContentResponse(String mimeType, String content) { return(newFixedLengthResponse(Response.Status.PARTIAL_CONTENT, mimeType, content)); }
	public static Response partialContentResponse(String mimeType, InputStream content, long totalBytes) { return(newFixedLengthResponse(Response.Status.PARTIAL_CONTENT, mimeType, content, totalBytes)); }
	public static Response partialContentResponse(InputStream content, long totalBytes) { return(newFixedLengthResponse(Response.Status.PARTIAL_CONTENT, NanoHTTPD.MIME_PLAINTEXT, content, totalBytes)); }

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
	 */
	public static Response newFixedLengthResponse(IStatus status, String mimeType, String txt) {
		if (txt == null) {
			return newFixedLengthResponse(status, mimeType, new ByteArrayInputStream(new byte[0]), 0);
		} else {
			byte[] bytes;
			try {
				bytes = txt.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
//				NanoHTTPD.LOG.log(Level.SEVERE, "encoding problem, responding nothing", e);
				bytes = new byte[0];
			}
			return newFixedLengthResponse(status, mimeType, new ByteArrayInputStream(bytes), bytes.length);
		}
	}
}
