package common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.awt.Color;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.golledge.empire.common.Player;

class PlayerTest {
	static Stream<Arguments> nameColorProvider() {
	    return Stream.of(
	        arguments("Bob", Color.BLUE),
	        arguments("Jill", Color.CYAN)
	    );
	}
	
    @DisplayName("Constructor should bind all parameters.")
    @ParameterizedTest
    @MethodSource("nameColorProvider")
    void testConstructor(String name, Color clr) {
		Player playerOne = new Player(name, clr);
		
		assertEquals(playerOne.getName(), name);
		assertEquals(playerOne.getColor(), clr);
	}

}
