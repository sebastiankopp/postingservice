package de.sebikopp.ownjodel.jfxclient.request;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import de.sebikopp.ownjodel.jfxclient.model.PostFromServer;

public class JsonConverterPostFromServer {
	private static Gson gson;
	static{
		gson = new GsonBuilder().create();
	}
	private static final Function<Map<String,Object>, PostFromServer> mapToPostFromServer = inputMap -> {
		PostFromServer x = new PostFromServer();
		x.setContent((String) inputMap.get("content"));
		x.setId((String) inputMap.get("id"));
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> subpostList = (List<Map<String,Object>>) inputMap.get("subposts");
		if (subpostList.isEmpty())
			x.setSubposts(new ArrayList<PostFromServer>());
		else
			x.setSubposts(listToPostsFromServer(subpostList));
		
		x.setTitle((String) inputMap.get("title"));
		return x;
	};
	private static final List<PostFromServer> listToPostsFromServer (List<Map<String,Object>> input){
		return input
				.stream()
				.map(mapToPostFromServer)
				.collect(Collectors.toList());
	}
	public static List<PostFromServer> convertPostListFromServer(String resp) throws IOException{
		Type typeOfListOfMaps = new TypeToken<List<Map<String, Object>>>(){}.getType();
		List<Map<String,Object>> currData = gson.fromJson(resp, typeOfListOfMaps);
		return listToPostsFromServer(currData);
	}
}
