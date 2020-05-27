package components.correlators.managingelement;

import java.util.ArrayList;

import interfaces.rule.RuleI;

public class RuleBase {

	private ArrayList<RuleI> ruleBase;
	
	public RuleBase() {
		this.ruleBase = new ArrayList<>();
	}
	/**
	 * ajouter un role 
	 * @param r
	 */
	public void addRule(RuleI r) {
		this.ruleBase.add(r);
	}
	
	/**
	 * retourne vrai si une regle s�est declenchee et faux sinon
	 * @param eventBase
	 * @return
	 */
	public boolean fireFirstOn(EventBase eventBase) {
		for (RuleI r : this.ruleBase) {
			if (r.executeOn(eventBase)) //executeOn: tant qu�un regle ne s�est pas declenchee et elle s�arrete des qu�un regle s�est declenchee ou qu�aucune des regles ne s�est declenchee 
				return true;
		}
		return false;
	}
	
	/**
	 * retourne vrai si au moins une regle s�est declenchee et faux sinon
	 * @param eventBase
	 * @return
	 */
	public boolean fireAllOn(EventBase eventBase) {
		while (this.fireFirstOn(eventBase))//tant qu�une regle se declenche et ne s�arrete que lorsque plus aucune regle ne se declenche
			return true;
		return false;
	}
}