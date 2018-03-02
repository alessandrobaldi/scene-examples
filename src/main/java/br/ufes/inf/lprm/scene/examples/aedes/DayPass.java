/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *///$sc.clearDay();
        //System.out.println("DayPassed");
package br.ufes.inf.lprm.scene.examples.aedes;



import br.ufes.inf.lprm.scene.examples.shared.Scenary;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import br.ufes.inf.lprm.scene.publishing.Publish;
import br.ufes.inf.lprm.situation.Role;
import br.ufes.inf.lprm.situation.SituationType;
/**
 *
 * @author alessandro
 */
public class DayPass extends SituationType{
     @Role(label="$sc")
     private Scenary passing;

    public Scenary getPassing() {
        return passing;
    }

    public void setPassing(Scenary passing) {
        this.passing = passing;
    }
     
    @Override
	public void setActive() {
		super.setActive();
                passing.clearDay();
		System.out.println("another day");
	}
	@Override
	public void setInactive() {
		super.setInactive();
                
	}	
     
     
}
