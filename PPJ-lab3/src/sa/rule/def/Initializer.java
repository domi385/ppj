package sa.rule.def;
import java.util.ArrayList;
import java.util.List;

public class Initializer extends RuleStrategy {

	@Override
	public void evaluate(NonTerminalNode node, Environment environment) {
		if (node.getChildNodeNumber() == 1) {

			node.getChidlAt(0).visitNode(environment);
			NonTerminalNode child = (NonTerminalNode) node.getChidlAt(0);

			if (child.getProperty(PropertyType.TYPE).getValue() == Types.ARRAY_CONST_CHAR) {

				int length = getCharArrayLength(node) + 1;
				node.setProperty(PropertyType.NUM_ELEM, new Property(length));
				List<Types> types = new ArrayList<>();
				for (int i = 0; i < length; i++) {
					types.add(Types.CONST_CHAR);
				}
				node.setProperty(PropertyType.TYPE, new Property(Types.ARRAY_CHAR));
				node.setProperty(PropertyType.TYPES, new Property(types));

			} else {

				node.setProperty(PropertyType.TYPE, child.getProperty(PropertyType.TYPE));
			}

		} else if (node.getChildNodeNumber() == 3) {
			node.getChidlAt(1).visitNode(environment);

			@SuppressWarnings("unchecked")
			List<Types> expressionListTypes = (List<Types>) ((NonTerminalNode) node.getChidlAt(1))
					.getProperty(PropertyType.TYPES).getValue();

			node.setProperty(PropertyType.TYPES, new Property(expressionListTypes));

			Integer expressionListElementNumber = ((NonTerminalNode) node.getChidlAt(1))
					.getProperty(PropertyType.NUM_ELEM).getValue();

			node.setProperty(PropertyType.NUM_ELEM, new Property(expressionListElementNumber));

		} else {
			// losa produkcija
		}
	}

	private int getCharArrayLength(NonTerminalNode node) {
		Node currNode = node;
		while (!currNode.getSymbol().getSymbol().equals("NIZ_ZNAKOVA") && currNode != null
				&& currNode.getChildNodeNumber() > 0) {

			currNode = currNode.getChidlAt(0);
		}
		if (currNode == null) {
			return -1;
		}

		int length = ((TerminalNode) currNode).getValue().length() - 2;
		length = length - getDoubleChars(((TerminalNode) currNode).getValue());

		return length;

	}

	private int getDoubleChars(String s) {
		String value = new String(s);
		int doubleChars = 0;

		for (int i = 0; i < value.length(); i++) {
			if (value.charAt(i) == '\\') {
				doubleChars++;

				if (i + 1 < value.length()) {
					if (value.charAt(i + 1) == '\\') {
						i++;
					}

				} else if (i + 2 == value.length()) {
					if(value.charAt(i+1) == '0'){
						doubleChars++;
					}
				}

			}

		}

		return doubleChars;
	}

}
