package com.fiap.api;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;
import java.util.stream.Collectors;

import com.fiap.model.Tweets;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TweetSearch {

	private Twitter twitter;
	private String wordRequired;
	private List<Status> tweets = new ArrayList<>();
	private List<LocalDate> days;
	private static int counter;
	
	public TweetSearch(Twitter twitter) {
		this.twitter = twitter;
	}

	public List<Status> getTweetsFromTheLast7days(String query){
		wordRequired = query;
		setupDates();
		return tweets;
	}

	private void setupDates(){

		LocalDate today = LocalDate.now();
		LocalDate tomorrow = today.plusDays(1);
		LocalDate yesterday = today.minusDays(1);
		LocalDate twoDaysAgo = today.minusDays(2);
		LocalDate threeDaysAgo = today.minusDays(3);
		LocalDate fourDaysAgo = today.minusDays(4);
		LocalDate fiveDaysAgo = today.minusDays(5);
		LocalDate sixDaysAgo = today.minusDays(6);

		days = Arrays.asList(sixDaysAgo, fiveDaysAgo, fourDaysAgo, threeDaysAgo, twoDaysAgo, yesterday, today, tomorrow);

		for(int i=0; i < days.size(); i++){
			if(days.size() > i+1){
				getTweets(days.get(i).toString(), days.get(i+1).toString());
			}
		}
	}

	private void getTweets(String date, String date1){
		QueryResult result;
		try {
			Query query = new Query(wordRequired)
					.count(100)
					.since(date)
					.until(date1);

			do {
				rateLimitedManager();
				result = twitter.search(query);
				tweets.addAll(result.getTweets());              
			} while ((query = result.nextQuery()) != null);

		} catch (TwitterException e) {
			System.out.println(
					"Sorry about that! \nGive it a try again in 15 minutes. \nThe Twitter API has a search rate limited at 180 queries per 15 minute window!");
			e.printStackTrace(); 
		}
	}

	private synchronized void rateLimitedManager(){
		if(++counter < 170){//90
			return;
		}else {
			System.out.println(
					"Sorry about that! \nYou have to wait 15 minutes. \nThe Twitter API has a search rate limited at 180 queries per 15 minute window!");
			try {
				wait(930_000);
				counter = 0;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Tweets> splitIntoDays(){
	
		List<Tweets> tweetsByDay = new ArrayList<>();
		
		for(LocalDate day : days){
			List<Status> tweetList = tweets.stream().filter(tweet -> {	
				return tweet.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(day);
			}).collect(Collectors.toList());
			//System.out.println(day+"  "+tweetList.size());
			
			tweetsByDay.add(new Tweets(day, tweetList));
		}
		return tweetsByDay;
	}

}
