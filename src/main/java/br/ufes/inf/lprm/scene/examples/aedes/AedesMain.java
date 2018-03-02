package br.ufes.inf.lprm.scene.examples.aedes;

import br.ufes.inf.lprm.scene.examples.aedes.*;
import java.util.Random;

import br.ufes.inf.lprm.scene.base.listeners.SCENESessionListener;
import org.drools.KnowledgeBase;

import org.drools.builder.KnowledgeBuilder;

import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;

import org.drools.builder.ResourceType;

import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.FactHandle;

import br.ufes.inf.lprm.scene.SituationKnowledgeBaseFactory;
import br.ufes.inf.lprm.scene.SituationKnowledgeBuilderFactory;
import br.ufes.inf.lprm.scene.examples.shared.Scenary;
import java.util.concurrent.TimeUnit;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.conf.EventProcessingOption;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.conf.ClockTypeOption;
import org.drools.time.SessionClock;
import org.drools.time.SessionPseudoClock;

/**
 * This is a sample class to launch a rule.
 */

public class AedesMain {

    public static final void main(String[] args) {
        
    	try {
        	
            // load up the knowledge base
            KnowledgeBase kbase = readKnowledgeBase();
            KnowledgeSessionConfiguration config = KnowledgeBaseFactory.newKnowledgeSessionConfiguration();
            config.setOption( ClockTypeOption.get("pseudo") );
            StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession(config,null);
            
            ksession.addEventListener(new SCENESessionListener());
            
            final RuleEngineThread eng = new RuleEngineThread(ksession);
			eng.start();			
			Scenary ufes = new Scenary("c0,c1,c2,c3,c4,c5,c6,c7,c8,c9",
									   "c0-c1,c1-c2,c2-c3,c3-c4,c4-c5,c5-c6,c6-c7,c7-c8,c8-c9",
									   "",
									   "c0,c1,c2,c3,c4,c5,c6,c7,c8,c9",
									   "c0",
									   50);
			
            FactHandle fh1 = ksession.insert(ufes);
           
            while(true){
            	Thread.sleep(3000);
                SessionPseudoClock clock = ksession.getSessionClock();
                clock.advanceTime(20, TimeUnit.DAYS);
                ksession.update(fh1, ufes);
            }
        } 
    	
    	catch (Throwable t) {
            t.printStackTrace();
        }
    }

    private static KnowledgeBase readKnowledgeBase() throws Exception {
    	
    	KnowledgeBuilder kbuilder = SituationKnowledgeBuilderFactory.newKnowledgeBuilder();
    
        kbuilder.add(ResourceFactory.newClassPathResource("br/inf/lprm/scene/examples/aedes/Aedes.drl"), ResourceType.DRL);
        
        KnowledgeBuilderErrors errors = kbuilder.getErrors();
        if (errors.size() > 0) {
            for (KnowledgeBuilderError error: errors) {
                System.err.println(error);
            }
            throw new IllegalArgumentException("Could not parse knowledge.");
        }
        KnowledgeBaseConfiguration config = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
        config.setOption( EventProcessingOption.STREAM );
        KnowledgeBase kbase = SituationKnowledgeBaseFactory.newKnowledgeBase(kbuilder);
                 
        
        return kbase;
    }

}
