
```
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package host.param.beans;

import org.jdom.Element;

/**
 *
 * @author tan.nq
 */
public class RateAcceptor {

    private String id;
    private String currency;
    private int amount;
    private double exchange;

    public RateAcceptor(String id, int amount, double exchange, String currency) {
        this.id = id;
        this.currency = currency;
        this.amount = amount;
        this.exchange = exchange;
    }

    /**
     * get element
     * @return
     */
    public Element getElement() {
        Element element = new Element("rate");
        element.setAttribute("id", this.id);

        Element ePar = new Element("type");
        ePar.setText(amount + "");

        Element eExchange = new Element("exchange");
        eExchange.setText(exchange + "");
        Element ecurrency = new Element("currency");
        ecurrency.setText(currency);

        element.addContent(ePar);
        element.addContent(ecurrency);
        element.addContent(eExchange);

        return element;
    }
}
```