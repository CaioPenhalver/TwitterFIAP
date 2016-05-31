package com.fiap.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.Formatter;
import java.util.List;

import twitter4j.Status;

public class TweetWriter {


	private TweetWriter(){}

	public static void save(List<Status> tweets, File file){
		File defaultFile = file;
		if(defaultFile == null) defaultFile = new File("Tweets/" + LocalTime.now() + ".txt");

		try {
			Formatter write = new Formatter(defaultFile);
			int i = 0;
			for(Status status : tweets){
				write.format("%3d %22s %40s isRetweet:%6b isRetweeted:%10b getRetweetCount:%5d\n", 
						++i, status.getUser().getName(), status.getCreatedAt(), status.isRetweet(), status.isRetweeted(), status.getRetweetCount());
			}
			write.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
