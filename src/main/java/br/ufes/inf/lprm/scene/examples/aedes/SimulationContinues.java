/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufes.inf.lprm.scene.examples.aedes;

import br.ufes.inf.lprm.scene.examples.shared.Scenary;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author alessandro
 */
public class SimulationContinues{
	private Scenary executing;

    public Scenary getExecuting() {
        return executing;
    }

    public void setExecuting(Scenary executing) {
        this.executing = executing;
    }
}
