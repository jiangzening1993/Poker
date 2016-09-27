import java.util.*;

/**
 * A poker hand is a list of cards, which can be of some "kind" (pair, straight,
 * etc.)
 * 
 */
public class Hand implements Comparable<Hand> {

	public enum Kind {
		HIGH_CARD, PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH
	}

	private final List<Card> cards;

	/**
	 * Create a hand from a string containing all cards (e,g, "5C TD AH QS 2D")
	 */
	public Hand(String c) {
		cards = new ArrayList<Card>();
		String[] arrayOfString = c.split("\\s+");
		for (String s : arrayOfString) {
			Card card = new Card(s);
			cards.add(card);
		}
	}

	/**
	 * @returns true if the hand has n cards of the same rank e.g.,
	 *          "TD TC TH 7C 7D" returns True for n=2 and n=3, and False for n=1
	 *          and n=4
	 */
	protected boolean hasNKind(int n) {
		int count;
		for (int j = 0; j < cards.size() - 1; j++) {
			count = 1;
			for (int i = 1; i < cards.size(); i++) {
				if (cards.get(j).getRank().equals(cards.get(i).getRank())) {
					count++;
					if (count == n) {
						return true;
					}
				}
			}
		}
		return false;

	}

	public List<Card.Rank> getRank() {
		List<Card.Rank> ranks = new ArrayList<Card.Rank>();
		for (Card card : cards) {
			ranks.add(card.getRank());
		}
		Collections.sort(ranks);
		return ranks;
	}

	public List<Card.Suit> getSuit() {
		List<Card.Suit> suits = new ArrayList<Card.Suit>();
		for (Card card : cards) {
			suits.add(card.getSuit());
		}
		Collections.sort(suits);
		return suits;
	}

	/**
	 * Optional: you may skip this one. If so, just make it return False
	 * 
	 * @returns true if the hand has two pairs
	 */
	public boolean isTwoPair() {
		return false;
	}

	/**
	 * @returns true if the hand is a straight
	 */
	public boolean isStraight() {
		if (!hasNKind(1)) {
			return false;
		}

		List<Card.Rank> ranks = getRank();
		if (ranks.contains(new Card("AC").getRank()) && ranks.contains(ranks.size())) {
			return true;
		} else if (ranks.size() - 1 == Collections.max(ranks).ordinal() - Collections.min(ranks).ordinal()) {
			return true;
		}
		return false;

	}

	/**
	 * @returns true if the hand is a flush
	 */
	public boolean isFlush() {
		List<Card.Suit> suits = getSuit();
		for (Card.Suit s : suits) {
			if (Collections.frequency(suits, s) == cards.size())
				return true;
		}
		return false;
	}

	@Override
	public int compareTo(Hand h) {
		return this.kind().compareTo(h.kind());
	}

	/**
	 * This method is already implemented and could be useful!
	 * 
	 * @returns the "kind" of the hand: flush, full house, etc.
	 */
	public Kind kind() {
		if (isStraight() && isFlush())
			return Kind.STRAIGHT_FLUSH;
		else if (hasNKind(4))
			return Kind.FOUR_OF_A_KIND;
		else if (hasNKind(3) && hasNKind(2))
			return Kind.FULL_HOUSE;
		else if (isFlush())
			return Kind.FLUSH;
		else if (isStraight())
			return Kind.STRAIGHT;
		else if (hasNKind(3))
			return Kind.THREE_OF_A_KIND;
		else if (isTwoPair())
			return Kind.TWO_PAIR;
		else if (hasNKind(2))
			return Kind.PAIR;
		else
			return Kind.HIGH_CARD;
	}
	
	public static void main(String[] args){
		Hand h = new Hand("TD TC TH 7C 7D");
		//Hand h = new Hand("5D 6H 7C 8D 9D");
		//Hand h = new Hand("TD TD TD 7D 7D");
		System.out.println(h.isFlush());
		System.out.println(h.isStraight());
		System.out.println(h.hasNKind(2));
		System.out.println(h.hasNKind(3));
	}

}
