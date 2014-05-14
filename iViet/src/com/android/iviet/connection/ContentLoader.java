package com.android.iviet.connection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import android.os.AsyncTask;

import com.android.iviet.utils.FilterLog;

public abstract class ContentLoader<T> {
	protected static final String TAG = "ContentLoader";

	public abstract void onContentLoaderStart();

	public abstract void onContentLoaderSucceed(T entity);

	public abstract void onContentLoaderFailed(Throwable e);

	protected abstract T handleStream(InputStream in) throws IOException;

	private HttpUriRequest request;
	private final boolean useCache;

	FilterLog log = new FilterLog(TAG);

	public ContentLoader(HttpUriRequest request, boolean useCache) {
		this.request = request;
		this.useCache = useCache;
	}

	public HttpUriRequest getRequest() {
		return request;
	}

	protected Executor getExecutor() {
		// return AsyncTask.SERIAL_EXECUTOR;
		return AsyncTask.THREAD_POOL_EXECUTOR;
	}

	/* package */boolean enableCache() {
		return useCache;
	}

	/* package */void execute(ContentManager manager) {
		task.executeOnExecutor(getExecutor(), manager);
	}

	/* package */void cancel() {
		task.cancel(true);
	}

	private final AsyncTask<ContentManager, Void, T> task = new AsyncTask<ContentManager, Void, T>() {
		private Throwable error = null;

		@Override
		protected void onPreExecute() {
			log.d("NECVN>>>" + "AsyncTask >>>>>>task onPreExecute");
			if (isCancelled()) {
				return;
			}
			super.onPreExecute();
			onContentLoaderStart();
		}

		@Override
		protected T doInBackground(ContentManager... params) {
			if (isCancelled()) {
				return null;
			}
			final ContentManager contentManager = params[0];
			final DefaultHttpClient client = new DefaultHttpClient();
			try {
				T value = handleCache(contentManager);
				if (value != null) {
					return value;
				}
				HttpConnectionParams.setConnectionTimeout(client.getParams(), 45000);
				HttpConnectionParams.setSoTimeout(client.getParams(), 300000);

				final String auth = request.getURI().getUserInfo();
				if (auth != null) {
					client.getCredentialsProvider()
					        .setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(auth));
				}
				log.d("necvn>>>ContentLoader request: " + request);

				return client.execute(request, new ResponseHandler<T>() {

					@Override
					public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
						final int status = response.getStatusLine().getStatusCode();
						if (status < 200 || status >= 300) {
							return null;
						}
						InputStream in = null;
						try {
							in = response.getEntity().getContent();
							if (useCache) {
								ByteArrayOutputStream data = new ByteArrayOutputStream();
								int buf;
								while ((buf = in.read()) >= 0) {
									data.write(buf);
								}
								in = new ByteArrayInputStream(data.toByteArray());
								synchronized (contentManager) {
									final String fname = getCacheFileName();
									contentManager.putCache(fname, in);
								}
								in = new ByteArrayInputStream(data.toByteArray());

							}
							return handleStream(in);
						} finally {
							if (in != null) {
								in.close();
							}
						}
					}

				});

			} catch (Exception e) {
				error = e;
			} finally {
				if(request != null) {
					request.abort();
					
				}
				client.getConnectionManager().shutdown();
			}
			return null;
		}

		@Override
		protected void onPostExecute(T result) {
			super.onPostExecute(result);
			if (isCancelled()) {
				return;
			}

			if (error != null) {
				onContentLoaderFailed(error);
			} else if (result == null) {
				onContentLoaderFailed(new NullPointerException("value is null"));
			} else {
				onContentLoaderSucceed(result);
			}
		}
	};

	// TODO handle cache
	public T handleCache(ContentManager manager) throws Exception {
		if (!useCache) {
			return null;
		}
		return null;
	}

	protected String getCacheFileName() {
		return getRequest().getURI().toASCIIString();
	}
}
