package cn.tuyucheng.taketoday.junit5.parameterized;

import java.util.stream.Stream;

public class StringParams {

	static Stream<String> blankStrings() {
		return Stream.of(null, "", "  ");
	}
}
