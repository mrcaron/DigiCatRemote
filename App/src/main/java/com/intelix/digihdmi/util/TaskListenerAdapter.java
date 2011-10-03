/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.intelix.digihdmi.util;

import org.jdesktop.application.TaskEvent;
import org.jdesktop.application.TaskListener;

/**
 *
 * @author mcaron
 */
public class TaskListenerAdapter implements TaskListener {

    @Override
    public void doInBackground(TaskEvent event) {}

    @Override
    public void process(TaskEvent event) {}

    @Override
    public void succeeded(TaskEvent event) {}

    @Override
    public void failed(TaskEvent event) {}

    @Override
    public void cancelled(TaskEvent event) {}

    @Override
    public void interrupted(TaskEvent event) {}

    @Override
    public void finished(TaskEvent event) {}

}
