package correlator;

import java.util.ArrayList;

import interfaces.rule.RuleI;

public class RuleBase {

	private ArrayList<RuleI> ruleBase;
	
	public RuleBase() {
		this.ruleBase = new ArrayList<>();
	}
	public void addRule(RuleI r) {
		this.ruleBase.add(r);
	}
	public boolean fireFirstOn(EventBase eventBase) {
		for (RuleI r : this.ruleBase) {
			if (r.executeOn(eventBase))
				return true;
		}
		return false;
	}
	public boolean fireAllOn(EventBase eventBase) {
		while (this.fireFirstOn(eventBase))
			return true;
		return false;
	}
}