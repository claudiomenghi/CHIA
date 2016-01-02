/**
 * 
 */
package it.polimi.chia.scalability.configuration;

import static org.junit.Assert.*;
import it.polimi.automata.BA;
import it.polimi.automata.transition.ClaimTransitionFactory;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * @author Claudio Menghi
 * 
 *
 */
public class RandomConfigurationGeneratorTest {

	/**
	 * Test method for {@link it.polimi.chia.scalability.configuration.RandomConfigurationGenerator#getNumberOfPossibleConfigurations()}.
	 */
	@Test
	public void testGetNumberOfPossibleConfigurations() {
		List<BA> claims=new ArrayList<BA>();
		claims.add(new BA(new ClaimTransitionFactory()));
		claims.add(new BA(new ClaimTransitionFactory()));
		claims.add(new BA(new ClaimTransitionFactory()));
		RandomConfigurationGenerator randomConfiguratorGenerator=new RandomConfigurationGenerator(claims,
					10, 100, 10, 1.0, 4.0, 1.0, 0.2, 0.5, 0.1, 0.1, 0.5, 0.1, 0.1, 0.5, 0.1, 20,1);
		assertEquals(240000, randomConfiguratorGenerator.getNumberOfPossibleConfigurations());
	}

}
