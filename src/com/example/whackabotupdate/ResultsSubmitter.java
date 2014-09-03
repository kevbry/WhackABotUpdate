package com.example.whackabotupdate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;
/**
 * This class is currently broken due to changes in the Google docs api
 * @author ins208
 *
 */
public class ResultsSubmitter extends AsyncTask<Object, Void, Void>
{
	private static final String NAME_FIELD="entry.1536929076";
	private static final String SCORE_FIELD="entry.1559022498";
	private static final String PAGE_HISTORY_FIELD="pageHistory";
	private static final String DRAFT_RESPONSE_FIELD="draftResponse";
	private static final String FBXZ="fbxz";
	private static final String SUBMIT_FIELD="submit";
	
	private static final String FORM_URL="https://docs.google.com/a/deliciouscake.ca/forms/d/1W5RtJ8qofNOXeWecqoQC-tX5N9s_m5rrC64d38RnqBY/formResponse";
	
	private String username;
	private int score;
	private HighScoreSubmissionDelegate delegate;
	private HttpResponse response;
	public ResultsSubmitter(String name, int score, HighScoreSubmissionDelegate delegate)
	{
		this.delegate = delegate;
		this.username=name;
		this.score=score;
	}
	
	
	@Override
	protected Void doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		try
		{
			this.submitVote();
		}
		catch(Exception e)
		{
			
		}
		return null;
	}

	
	private void submitVote() throws ClientProtocolException, IOException
	{
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(FORM_URL);
		
		List<BasicNameValuePair> results = new ArrayList<BasicNameValuePair>();
		results.add(new BasicNameValuePair(NAME_FIELD, this.username));
		results.add(new BasicNameValuePair(SCORE_FIELD, Integer.toString(this.score)));
		
		results.add(new BasicNameValuePair(DRAFT_RESPONSE_FIELD, "[,,\"7646145443675520240\"]"));
		results.add(new BasicNameValuePair(FBXZ, "7646145443675520240"));
		results.add(new BasicNameValuePair(PAGE_HISTORY_FIELD, "0"));
		results.add(new BasicNameValuePair(SUBMIT_FIELD, "Submit"));
		
		post.setEntity(new UrlEncodedFormEntity(results));
		
		this.response = client.execute(post);
		
	}
	
	@Override
	protected void onPostExecute(Void v)
	{
	    Log.d("Tag", response.toString());
		this.delegate.scoreSubmitted();
	}
	
}
