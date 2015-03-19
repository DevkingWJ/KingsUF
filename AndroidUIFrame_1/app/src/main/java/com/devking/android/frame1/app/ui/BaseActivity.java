package com.devking.android.frame1.app.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import de.greenrobot.event.EventBus;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;

/**
 * Base Activity extends ActionbarActivity
 * Created by Kings on 2015/3/6.
 */
public class BaseActivity extends ActionBarActivity {

    private Card card;
    private CardHeader cardHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        card = new Card(this);
        cardHeader = new CardHeader(this);
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public CardHeader getCardHeader() {
        return cardHeader;
    }

    public void setCardHeader(CardHeader cardHeader) {
        this.cardHeader = cardHeader;
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
