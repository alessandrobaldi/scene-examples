package ca.study.scenary.aedes;

import br.ufes.inf.lprm.scene.SceneApplication;
import br.ufes.inf.lprm.scene.base.listeners.SCENESessionListener;
import ca.study.scenary.entities.Scenary;
import ca.study.scenary.entities.House;
import ca.study.scenary.entities.Mosquito;

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
//import org.kie.api.runtime.rule.FactHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is a sample class to launch a rule.
 */

public class AedesMain {

	static final Logger LOG = LoggerFactory.getLogger(AedesMain.class);

    public static final void main(String[] args) throws InterruptedException{

    	try{
	        System.out.print("Starting Project");
	    	KieServices kieServices = KieServices.Factory.get();
	
	        KieContainer kContainer = kieServices.getKieClasspathContainer();
	        
	        Results verifyResults = kContainer.verify();
	        for (Message m : verifyResults.getMessages()) {
	            LOG.info("{}", m);
	        }
	
	        /*Par�metros:
	         * 1�) casas existentes no cen�rio
	         * 2�) rela��o de vizinhan�a (quem � vizinho de quem)
	         * 3�) casas que possuem armadilhas
	         * 4�) casas com foco de mosquito
	         * 5�) casas que possuem mosquito
	         * 6�) n�mero de dias que o cen�rio ser� executado. */
	        Scenary ufes = new Scenary("c0,c1,c2,c3,c4,c5,c6,c7,c8,c9",
					   "c0-c1,c1-c2,c2-c3,c3-c4,c4-c5,c5-c6,c6-c7,c7-c8,c8-c9",
					   "",
					   "c0,c1,c2,c3,c4,c5,c6,c7,c8,c9",
					   "c0",
					   50);
	        
	        LOG.info("Creating kieBase");
	        System.out.print("Creating kieBase");
	
	        KieBaseConfiguration config = KieServices.Factory.get().newKieBaseConfiguration();
	        config.setOption(EventProcessingOption.STREAM);
	        //config.setOption(ClockTypeOption.get("pseudo"));
	        KieBase kieBase = kContainer.newKieBase(config);
	
	        LOG.info("There should be rules: ");
	        for ( KiePackage kp : kieBase.getKiePackages() ) {
	            for (Rule rule : kp.getRules()) {
	                LOG.info("kp " + kp + " rule " + rule.getName());
	            }
	        }
	
	        LOG.info("Creating kieSession");
	        System.out.println("Creating kieSession");
	        KieSession session = kieBase.newKieSession();
	
	        @SuppressWarnings("unused")
			SceneApplication app = new SceneApplication("Aedes", session);
	
	        session.addEventListener(new SCENESessionListener());
	        
	
	        final RuleEngineThread ruleEngineThread = new RuleEngineThread(session);
	        ruleEngineThread.start();
	
	        LOG.info("Now running data");
	        System.out.println("Now running data");   
	        
	        //Insere todas as cosias na work memory
	        for(House h : ufes.getScenary()) {
	        	session.insert(h);
	        	System.out.println(h);
	        	for(Mosquito m : h.getMosquitos()){
		        	session.insert(m);
		        	System.out.println(m);
	        	}
	        }
	        
	        session.insert(ufes);
	        
	        /*while(true){
            	Thread.sleep(3000);
                SessionPseudoClock clock = session.getSessionClock();
                clock.advanceTime(20, TimeUnit.DAYS);
                session.update(fh1, ufes);
            }*/
    	}
    	catch (Throwable t) {
            t.printStackTrace();
        }
    }
}