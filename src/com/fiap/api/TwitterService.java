package com.fiap.api;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;


public class TwitterService {

	
	private String consumerKey;
	private String consumerSecret;
	
	private TwitterService(String consumerKey, String consumerSecret){
		this.consumerKey = consumerKey;
		this.consumerSecret = consumerSecret;
	}
	
	public static TwitterService getConnection(String consumerKey, String consumerSecret){
		return new TwitterService(consumerKey, consumerSecret);
		 
	}
	public Twitter userAuthentication(String token, String tokenSecret) throws Exception{
		try {
			ConfigurationBuilder builder = new ConfigurationBuilder();
			builder.setOAuthConsumerKey(consumerKey);
			builder.setOAuthConsumerSecret(consumerSecret);
			Configuration configuration = builder.build();
			
			TwitterFactory factory = new TwitterFactory(configuration);
			Twitter twitter = factory.getInstance();
			twitter.setOAuthAccessToken(new AccessToken(token, tokenSecret));
			
			return twitter; //Return Twitter
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
