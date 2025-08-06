package game.UI;

public interface CardSelectionListener {
	boolean onCardSelected(CardLabel card);
    void onCardDeselected(CardLabel card);
}
