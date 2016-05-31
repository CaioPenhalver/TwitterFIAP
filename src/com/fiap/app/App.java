package com.fiap.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fiap.controller.TweetController;
import com.fiap.model.Tweets;

public class App {

	public static void main(String[] args) {
		TweetController controller = new TweetController();
		
		String query = "#javaone";
		controller.searchBy(query);
		
		Map<String, String> firstAndLastAuthors = controller.getFirstAndLastAuthors();
		String firstAuthor = firstAndLastAuthors.get("First Author");
		String lastAuthor = firstAndLastAuthors.get("Last Author");

		Map<String, String> firstAndLastDates = controller.getFirstAndLastDate();
		String firstDate = firstAndLastDates.get("First date");
		String lastDate = firstAndLastDates.get("Last date");
		
		long numOfTweets = controller.getTotalNumOfTweets();
		long numOfRetweets = controller.getTotalNumOfRetweets();
		long numOfFavourites = controller.getTotalNumOfFavorites();
		
		List<Tweets> tweetsByday = controller.getTweetsSortedByDay();
		
		List<String> messages = new ArrayList<>();
		
		String professor = "@Penhalver";
		StringBuilder firstMessage = new StringBuilder()
				.append(professor)
				.append(". Last Week: ")
				.append(" Number of tweets: ").append(numOfTweets)
				.append("; Number of Retweets: ").append(numOfRetweets)
				.append("; Number of favourites: ").append(numOfFavourites)
				.append(" ").append(query);
		messages.add(firstMessage.toString());
		
		StringBuilder secondMessage = new StringBuilder()
				.append(professor)
				.append(". Tweets in alphabetical order: ")
				.append(" First Author: ").append(firstAuthor)
				.append(", Last Author: ").append(lastAuthor)
				.append(" ").append(query);
		messages.add(secondMessage.toString());
		
		StringBuilder thirdMessage = new StringBuilder()
				.append(professor)
				.append(". Tweets ordered by date: ")
				.append(" First Date: ").append(firstDate)
				.append(", Last Date: ").append(lastDate)
				.append(" ").append(query);;
		messages.add(thirdMessage.toString());
		
		//controller.generateTweet();
		
		for(Tweets tweets : tweetsByday){
			if(!tweets.isEmpty()){
				StringBuilder dayMessage = new StringBuilder()
						.append(professor)
						.append(". Day ")
						.append(tweets.getDate())
						.append(" - Tweets: ").append(tweets.getTweetsNumber())
						.append(", Retweets: ").append(tweets.getRetweetsNumber())
						.append(",  Favourites: ").append(tweets.getFavoriteNumber())
						.append(" ").append(query);
				messages.add(dayMessage.toString());
			}
		}
		
		for(String message : messages){
			controller.generateTweet(message);
		}
		
	}

}
