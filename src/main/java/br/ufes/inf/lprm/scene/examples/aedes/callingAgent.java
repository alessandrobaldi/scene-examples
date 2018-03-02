/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.inf.lprm.scene.examples.aedes;

import br.ufes.inf.lprm.scene.examples.shared.House;
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
public class callingAgent extends SituationType{
     @Role(label="$house")
     private House calling;

    public House getCalling() {
        return calling;
    }

    public void setCalling(House calling) {
        this.calling = calling;
    }
     
     @Override
	public void setActive() {
		super.setActive();
		
	}
	@Override
	public void setInactive() {
		super.setInactive();
              calling.callAgent();
	}	
     
}
