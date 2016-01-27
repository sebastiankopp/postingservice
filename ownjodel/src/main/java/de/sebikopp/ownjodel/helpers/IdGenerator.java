package de.sebikopp.ownjodel.helpers;

import java.util.Random;
import java.util.UUID;

public class IdGenerator {
	public static String newPostId(){
		return (UUID.randomUUID().toString()+System.currentTimeMillis()).replace('-', 'x');
	}
	public static String newLocId(){
		return System.currentTimeMillis() + "" + (new Random().nextInt() % 1000);
	}
}
