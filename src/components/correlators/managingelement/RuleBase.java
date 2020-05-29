package components.correlators.managingelement;

import java.util.ArrayList;

import interfaces.rule.RuleI;

/**
 * The class <code>RulesBase</code> used for organised rules in correlators
 *
 * <p><strong>Description</strong></p>
 *
 * <p>
 * In this class we can simply add rules which will be kept in the order of additions to this database
 * </p>
 * 
 * @author Hadrien Cazes, Sebastien Lefevre, Kady Soumahoro
 *
 */
public class RuleBase {

	private ArrayList<RuleI> ruleBase;
	
	public RuleBase() {
		this.ruleBase = new ArrayList<>();
	}
	
	/**
	 * Add rule
	 * @param r
	 */
	public void addRule(RuleI r) {
		this.ruleBase.add(r);
	}
	
	/**
	 * Returns true if a rule is triggered otherwise false
	 * 
	 * @param eventBase		
	 * @return
	 * @throws Exception 
	 */
	public boolean fireFirstOn(EventBase eventBase) throws Exception {
		for (RuleI r : this.ruleBase) {
			if (r.executeOn(eventBase)) //executeOn: tant qu&#145;un regle ne s�est pas declenchee et elle s�arrete des qu�un regle s�est declenchee ou qu�aucune des regles ne s�est declenchee 
				return true;
		}
		return false;
	}
	
	/**
	 * Returns true if at least one rule is triggered otherwise false.
	 * 
	 * @param eventBase
	 * @return
	 * @throws Exception 
	 */
	public boolean fireAllOn(EventBase eventBase) throws Exception {
		while (this.fireFirstOn(eventBase))//tant qu�une regle se declenche et ne s�arrete que lorsque plus aucune regle ne se declenche
			return true;
		return false;
	}
}