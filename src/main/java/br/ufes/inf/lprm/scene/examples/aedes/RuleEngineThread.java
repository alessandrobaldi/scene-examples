package br.ufes.inf.lprm.scene.examples.aedes;

import org.drools.runtime.StatefulKnowledgeSession;

//import org.kie.api.runtime.KieSession;


public class RuleEngineThread extends Thread {    
    private StatefulKnowledgeSession ksession;
	public RuleEngineThread(StatefulKnowledgeSession ksession) {
		this.ksession = ksession;
	}
    public void run() {  	
    	this.ksession.fireUntilHalt(); 	
    }
}