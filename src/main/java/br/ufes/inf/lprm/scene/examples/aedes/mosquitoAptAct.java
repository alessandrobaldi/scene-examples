/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.inf.lprm.scene.examples.aedes;


import br.ufes.inf.lprm.scene.examples.shared.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import br.ufes.inf.lprm.scene.examples.shared.Mosquito;
import br.ufes.inf.lprm.scene.publishing.Publish;
import br.ufes.inf.lprm.situation.Role;
import br.ufes.inf.lprm.situation.SituationType;
import java.util.List;
import java.util.Random;

/**
 *
 * @author alessandro
 */
public class mosquitoAptAct extends SituationType{
    @Role(label="$house")
    private House actual;
    @Role(label = "$mosq")
    private Mosquito flying;
    public void setActual(House actual) {
		this.actual = actual;
	}
    public House getActual() {
        return actual;
    }
    
    public void setFlying(Mosquito flying) {
		this.flying = flying;
	}

	public Mosquito getFlying() {
		return flying;
	}
		
}
    
