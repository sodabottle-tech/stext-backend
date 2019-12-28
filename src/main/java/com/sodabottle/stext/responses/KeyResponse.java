package com.sodabottle.stext.responses;

import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
public class KeyResponse {	
	private List<Map<String, String>> keys;
}
