package br.ufes.inf.lprm.scene.examples.aedes;

/*import br.ufes.inf.lprm.scene.examples.aedes.*;
import java.util.Random;

import br.ufes.inf.lprm.scene.SceneApplication;
import br.ufes.inf.lprm.scene.base.listeners.SCENESessionListener;
import org.drools.KnowledgeBase;

import org.drools.builder.KnowledgeBuilder;

import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;

import org.drools.builder.ResourceType;

import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.FactHandle;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.runtime.KieContainer;
//import br.ufes.inf.lprm.scene.SituationKnowledgeBaseFactory;
//import br.ufes.inf.lprm.scene.SituationKnowledgeBuilderFactory;
import org.kie.api.runtime.KieSession;
import br.ufes.inf.lprm.scene.examples.shared.Scenary;
import br.ufes.inf.lprm.scene.examples.aedes.RuleEngineThread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.conf.EventProcessingOption;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.conf.ClockTypeOption;
import org.drools.time.SessionClock;
import org.drools.time.SessionPseudoClock;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/

import br.ufes.inf.lprm.scene.SceneApplication;
import br.ufes.inf.lprm.scene.base.listeners.SCENESessionListener;
import br.ufes.inf.lprm.scene.examples.shared.Scenary;

import org.drools.core.time.SessionPseudoClock;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.definition.KiePackage;
import org.kie.api.definition.rule.Rule;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
//import org.kie.api.runtime.KieSessionConfiguration;
//import org.kie.api.runtime.conf.ClockTypeOption;
//import org.kie.api.runtime.rule.EntryPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a sample class to launch a rule.
 */

public class AedesMain {

	static final Logger LOG = LoggerFactory.getLogger(AedesMain.class);
	
    public static final void main() throws InterruptedException{
    	KieServices kieServices = KieServices.Factory.get();

        KieContainer kContainer = kieServices.getKieClasspathContainer();
        
        Results verifyResults = kContainer.verify();
        for (Message m : verifyResults.getMessages()) {
            LOG.info("{}", m);
        }

        Scenary ufes = new Scenary("c0,c1,c2,c3,c4,c5,c6,c7,c8,c9",
				   "c0-c1,c1-c2,c2-c3,c3-c4,c4-c5,c5-c6,c6-c7,c7-c8,c8-c9",
				   "",
				   "c0,c1,c2,c3,c4,c5,c6,c7,c8,c9",
				   "c0",
				   50);
        
        LOG.info("Creating kieBase");

        KieBaseConfiguration config = KieServices.Factory.get().newKieBaseConfiguration();
        config.setOption(EventProcessingOption.STREAM);
        KieBase kieBase = kContainer.newKieBase(config);

        LOG.info("There should be rules: ");
        for ( KiePackage kp : kieBase.getKiePackages() ) {
            for (Rule rule : kp.getRules()) {
                LOG.info("kp " + kp + " rule " + rule.getName());
            }
        }

        LOG.info("Creating kieSession");
        KieSession session = kieBase.newKieSession();

        SceneApplication app = new SceneApplication("botox-drugs-scenario", session);

        session.addEventListener(new SCENESessionListener());
        

        final RuleEngineThread ruleEngineThread = new RuleEngineThread(session);
        ruleEngineThread.start();

        LOG.info("Now running data");
        
        session.insert(ufes);
    }
        
    	/*try {
        	
            // load up the knowledge base
            KnowledgeBase kbase = readKnowledgeBase();
            KnowledgeSessionConfiguration config = KnowledgeBaseFactory.newKnowledgeSessionConfiguration();
            config.setOption( ClockTypeOption.get("pseudo") );
            //StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession(config,null);
            KieSession session = kieBase.newKieSession();
            
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
    }*/


}