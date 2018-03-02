/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.inf.lprm.scene.examples.aedes;
import br.ufes.inf.lprm.scene.examples.shared.House;
import br.ufes.inf.lprm.scene.examples.shared.Mosquito;
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
public class lifeMosquito extends SituationType{
     @Role(label="$mosq")
     private Mosquito living;
     @Role(label="$house")
     private House actual;

    public House getActual() {
        return actual;
    }

    public void setActual(House actual) {
        this.actual = actual;
    }

    public Mosquito getLiving() {
        return living;
    }

    public void setLiving(Mosquito living) {
        this.living = living;
    }
     
}
